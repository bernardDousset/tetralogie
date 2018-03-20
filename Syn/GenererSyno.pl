#!/usr/bin/perl5 

#################################################################################################################
#**  GenererSyno.pl : Genere le synonyme d'une adresse contenant au moins :
#                     un pays, une ville et un organisme.
#**  Usage :
#    GenererSyno.pl <Pays> <Oganismes> <Villes> <Corpus> <ADR.Syn> <ADRIncomplete>
#
#**   Le <Corpus> est de la forme : notice : <adresse_complete> 
#     exemple :  ADRIA, 6, Rue de l'Universite B. P. 313, 29 191 Quimper, France
#
#**  Les fichiers Synonyme_XXXX  : sont tous de la meme forme :  AAAAA   AAAAA_Syno
#    
#**  Le fichier ADR.Syn          : Correspond au resultat final, il est de la forme : Adresse   Adresse_Syno
#    
#
################################################################################################################

unless (@ARGV == 6) { die "\n\n usage : $0  <PA> <ORG> <VI> <CORPUS> <ADR.Syn> <ADRIncomplete>\n\n";}


open(PA_SYN,   "$ARGV[0].Syn" ) || die "\n $0 : Ouverture de $ARGV[0].Syn en lecture est impossible \n\n";
open(ORG_SYN,  "$ARGV[1].Syn" ) || die "\n $0 : Ouverture de $ARGV[1].Syn en lecture est impossible \n\n";
open(VILLE_SYN,"$ARGV[2].Syn" ) || die "\n $0 : Ouverture de $ARGV[2].Syn en lecture est impossible \n\n";

open(ADR,      "$ARGV[3]" )     || die "\n $0 : Ouverture de $ARGV[3] en lecture est impossible \n\n";
open(ADRSYN,   ">$ARGV[4]" )    || die "\n $0 : Ouverture de $ARGV[4] en ecriture est impossible \n\n";
open(ADRINCOPM,">$ARGV[5]" )    || die "\n $0 : Ouverture de $ARGV[5] en ecriture est impossible \n\n";

open(PA_Filtre,   "$ARGV[0].Filtre" ) || die "\n $0 : Ouverture de $ARGV[0].Filtre en lecture est impossible \n\n";
open(ORG_Filtre,  "$ARGV[1].Filtre" ) || die "\n $0 : Ouverture de $ARGV[1].Filtre en lecture est impossible \n\n";
open(VILLE_Filtre,"$ARGV[2].Filtre" ) || die "\n $0 : Ouverture de $ARGV[2].Filtre en lecture est impossible \n\n";



my %Pays; 
my %Villes; 
my %Organismes; 

my @ADRS = <ADR>;
my @PAYS_SYN = <PA_SYN>; 
my @ORGS_SYN = <ORG_SYN>;
my @VILLES_SYN = <VILLE_SYN>;

my @PAYS_Filtre = <PA_Filtre>; 
my @ORGS_Filtre = <ORG_Filtre>;
my @VILLES_Filtre = <VILLE_Filtre>;

##############################################

open(File_TMP_ORG, ">ORG_Syn_TMP");
foreach $_ (@ORGS_SYN ) {
  print File_TMP_ORG $_;
}
foreach $_ (@ORGS_Filtre) {
  chomp;
   s/ *$//;
  print File_TMP_ORG "$_ \t$_\n";
}
close(File_TMP_ORG);
open(File_TMP_ORG,"sort -u ORG_Syn_TMP|");

my @Liste_ORG_Tmp = <File_TMP_ORG>;

close(ORG_SYN);
close(ORG_Filtre);
close(File_TMP_ORG);
##############################################

open(File_TMP_VI,">VI_Syn_TMP");
foreach $_ (@VILLES_SYN ) {
  print File_TMP_VI $_;
}
foreach $_ (@VILLES_Filtre) {
  chomp;
  s/ $//;
  print File_TMP_VI "$_ \t$_\n";
}
close(File_TMP_VI);
open(File_TMP_VI,"sort -u VI_Syn_TMP|");

my @Liste_VI_Tmp = <File_TMP_VI>;


close(VILLE_SYN);
close(VILLE_Filtre);
close(File_TMP_VI);

#system("rm  VI_Syn_TMP ORG_Syn_TMP");

##############################################

#$prog = ($0 = ~m|.*/([^/]+)$|); print "prog. = $1\n";

##############################################

GenererSyn();

##############################################
sub InFiltre {
  my ($chaine, @Filtre) = @_;
  local $trouve = 0;
  
 Filtre: foreach  $ligne (@Filtre) { 
    chop($ligne);
  
    if ( $ligne eq $chaine ) {
      $trouve = 1;
      last Filtre;
    } else {
      next Filtre;
    }
  }
  return($trouve);
}

##############################################
sub Syno_Pays {
  print " \n\n ********* Debut Syno Pays  ******** \n\n";
  my @adr; 
  my $chaine_pa; 
  my $syno_pa;
  my $pays;
  my $cpt_pays = 0;

 Adresse: foreach $_ (@ADRS) { 
    chop;
    s/ $//;
    @adr =  split(/;\s|,\s/, $_);  
    
    $pays = $adr[$#adr];
    $pays =~ tr/ /-/;
    $pays =~ tr/[a-z]/[A-Z]/;
    
    if ( InFiltre($pays, @PAYS_Filtre) ) { # si le pays est dans le Filtre des pays
      $Pays{$_} = "$pays";
      $cpt_pays++;     
      next Adresse;
    } else {                               # sinon chercher dans PA.Syn
      goto Pays;
    }
  Pays: foreach  $pays_syn (@PAYS_SYN) {     
      ($chaine_pa, $syno_pa)  = split(/\t/, $pays_syn );  
      $chaine_pa =~ tr/ /-/ ;
      $chaine_pa =~ tr/[a-z]/[A-Z]/;
     
      $trouve = SousChaine($chaine_pa, $pays); 
      if ( $trouve ) {
	chop($syno_pa);
	$syno_pa =~ tr/ $//;
	$Pays{$_} = "$syno_pa"; 
	$cpt_pays++;
	last Pays;
      }
      else { 
	$Pays{$_} = "";
	next Pays;
      }
    }
  }
  print "\n\n ********* Fin syno Pays  $cpt_pays ********* \n\n"; 
  close(PA_SYN);
  close(PA_Filtre);
}
##############################################

sub Syno_Villes_Organismes {

  my $trouve = -1;
  my $chaine_ville;
  my $syno_ville;
  my $chaine_org;
  my $syno_org;

  print " \n\n ********* Debut Syno Ville/Organismes  ******** \n\n";
  $debut = (times)[0];

 ADRESSE: foreach $_ (@ADRS) {  
    $adr = $_;
    $adr = "-".$adr;
    $adr =~ tr/ |,|;|.|(|)|\'/-/;
    $adr =~ tr/[a-z]/[A-Z]/; 
      
  VilleSyn: foreach  $ville ( @Liste_VI_Tmp ) {  
      ($chaine_ville, $syno_ville)  = split(/ \t/, $ville);  

      $chaine_ville =~ tr/ |,|;|.|(|)|\'/-/;
      $chaine_ville =~ tr/[a-z]/[A-Z]/;
      $chaine_ville = "-".$chaine_ville."-";
      
      $trouve = SousChaine($adr, $chaine_ville);
      if ( $trouve ) {
	chop($syno_ville);
	$syno_ville =~ tr/ $//;
	$syno_ville =~ tr/[a-z]/[A-Z]/;
	$Villes{$_} = "$syno_ville"; 
	goto OrganSyn;
      }
    }

  OrganSyn: foreach  $org (@Liste_ORG_Tmp) {  
      ($chaine_org, $syno_org)  = split(/ \t/, $org);  
      
      $chaine_org =~ tr/ |,|;|.|(|)|\'/-/;
      $chaine_org =~ tr/[a-z]/[A-Z]/; 
      $chaine_org =  "-".$chaine_org."-";
      
      $trouve = SousChaine($adr, $chaine_org);
      if ( $trouve ) {
	chop($syno_org);
	$syno_org =~ tr/ $//;
	$Organismes{$_} = "$syno_org"; 
	next ADRESSE;
      }
    }
  }
  $fin = (times)[0];
  printf " \n \n ********* Fin syno Villes en : %.2f CPU secondes\n\n", $fin - $debut;
}  

#******************************#
sub GenererSyn {
  Syno_Pays(); 
  Syno_Villes_Organismes();

  foreach $_ ( keys (%Pays) ) {   
    if ( $Pays{$_} ) {
      if ( $Villes{$_} && $Organismes{$_} ) {
	print ADRSYN  "$_ \t$Organismes{$_}, $Villes{$_}, $Pays{$_}\n"; 
      }
      else { 
	print ADRINCOPM "$_ : $Organismes{$_}, $Villes{$_}, $Pays{$_}\n"; 
      }
    }
  }
}
#****************************#
sub SousChaine {
  my ($chaine, $souschaine) = @_;
  my $pos = -1;

  do {
    $pos = index( $chaine, $souschaine ); 
    $pos++;
  } until ($pos > -1);
  
  return $pos; 
}
#****************************#

close(PA_SYN);
close(ORG_SYN);
close(VILLE_SYN);
close(ADR);
close(ADRSYN);
close(ADRINCOPM);
close(PA_Filtre);
close(ORG_Filtre);
close(VILLE_Filtre);
