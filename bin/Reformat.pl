#!/usr/bin/perl5 

##########################################################################
# Reformateur.pl : reformate les documents HTML d'un repertoire donné.
# 
#  
# Usage : Reformateur.pl <rep_documents_html> <rep_application_Tetra>
# -----
# 
##########################################################################

unless ( @ARGV == 2 ) { 
  die "\n\nUsage : $0 <rep_documents_html> <rep_application_Tetra> \n\n\n";
}

if (! opendir(DIR1, "$ARGV[0]") ) {
  print "\n Erreur d'ouverture de $ARGV[0] :  Répertoire des Documents Html à Reformater \n\n"; 
  exit(0);
}


if (! opendir(DIR2, "$ARGV[0]_REF") ) {
  system("mkdir $ARGV[0]_REF");
  opendir(DIR2, "$ARGV[0]_REF");
}

if (! opendir(DIR3, "$ARGV[1]") ) {
  print "\n\n  Le repertoire $ARGV[1] n'existe pas\n\n\n";   
  exit(0);
}

$dd = '/';  # directory delimiter;

@htmlfiles = grep !/^\./, readdir DIR1;

foreach $file (@htmlfiles) {
  system(" Extract.pl \"$ARGV[0]${dd}$file\" \"$ARGV[0]_REF${dd}$file.Ref\" "); 
}

# Pour creer le corpus final

@Noticesfiles = grep !/^\./, readdir DIR2;
@Notices      = grep !/^\./, readdir DIR3;

@chemin = split ('/', "$ARGV[1]") ; 
$rep    = $chemin[$#chemin];



$trouve = 0;
$corpus = $rep.".not";

foreach $_ (@Notices) {
  if (m/$corpus/) {
    $trouve = 1;
  }
}
if ($trouve) {
  #print "Destruction du vieux $ARGV[1]${dd}$rep.not";
  system("rm \"$ARGV[1]${dd}$rep.not\"");
}

foreach $notice (@Noticesfiles) {
  system("cat \"$ARGV[0]_REF${dd}$notice\" >> $ARGV[1]${dd}$rep.not");
  system("rm \"$ARGV[0]_REF${dd}$notice\"");
}

print " \n\n\n Fin Reformatage : le corpus final est crée dans le repertoire : \n $ARGV[1] \n\n\n";
system("rm -r $ARGV[0]_REF");

closedir(DIR1);
closedir(DIR2);
closedir(DIR3);

