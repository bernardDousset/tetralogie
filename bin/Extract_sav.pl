#!/usr/bin/perl5

use HTML::TokeParser;

#######################

if ( !open(IN, "$ARGV[0]") ) {
  print " Erreur d'ouverture de $ARGV[0] \n"; 
  exit(0);
}

if ( !open(OUT, ">$ARGV[1]") ) {
  print " Erreur d'ouverture de $ARGV[1] \n"; 
  exit(0);
}


print OUT "\n\nNOTICE-:\n"; # separateur de notices 

$p = HTML::TokeParser->new("$ARGV[0]") || die "Can't open: $!";

&parse_meta_tags;          # lecture des META TAGS.
&parse_document;           # lecture du corps du documents.
&parse_hyperlinks;         # lecture des hyperliens dans le document

sub parse_meta_tags {
  
  &parse_title;            # lecture du Titre du document.
  &parse_meta_keywords;    # lecture des Mots Cles du document.
  &parse_meta_description; # lecture de la description du document.
  &parse_author;           # lecture de l'auteur du document.
  &parse_date;             # lecture de la date du document.  
  &ignore_script;          # ignorer les scripts (Javascript).
  &ignore_script2;          # ignorer les scripts (Javascript).
&ignore_style;           # ignorer les definitions des styles.
}

sub parse_document() {
  my $token;
 
  print OUT "\n\nAB-: " ;
  
  local $p = HTML::TokeParser->new("$ARGV[0]");  
  while ( $token = $p->get_tag("body")) {
    local $Data = $p->get_text("/body");
    
    $Data =~ s/\s+(.*)\n/ $1\n/g;
    $Data =~ s/\[IMG\]//g;

    $Data =~ s/Á|Â|À|Ã|Å|Ä/A/g;
    $Data =~ s/â|á|à|å|ä/a/g;
    $Data =~ s/ç/c/g;
    $Data =~ s/Ç/C/g;
    $Data =~ s/é|ê|è|ë/e/g;
    $Data =~ s/É|Ê|È|Ë/E/g;
    $Data =~ s/Í|Î|Ì|Ï/I/g;
    $Data =~ s/í|î|ì|ï/i/g;
    $Data =~ s/Ñ/N/g;
    $Data =~ s/ñ/n/g;
    $Data =~ s/Ó|Ô|Ò|Ø|Õ|Ö/O/g;
    $Data =~ s/ó|ô|ò|ø|õ|ö/o/g;
    $Data =~ s/Ú|Û|Ù|Ü/U/g;
    $Data =~ s/ú|û|ù|ü/u/g;   
    $Data =~ s///g;
    print OUT  "$Data||";
  }
}  


sub parse_title {
  if ($p->get_tag("title")) {
    local $title = $p->get_trimmed_text;  

    $title =~ s/Á|Â|À|Ã|Å|Ä/A/g;
    $title =~ s/â|á|à|å|ä/a/g;
    $title =~ s/ç/c/g;
    $title =~ s/Ç/C/g;
    $title =~ s/é|ê|è|ë/e/g;
    $title =~ s/É|Ê|È|Ë/E/g;
    $title =~ s/Í|Î|Ì|Ï/I/g;
    $title =~ s/í|î|ì|ï/i/g;
    $title =~ s/Ñ/N/g;
    $title =~ s/ñ/n/g;
    $title =~ s/Ó|Ô|Ò|Ø|Õ|Ö/O/g;
    $title =~ s/ó|ô|ò|ø|õ|ö/o/g;
    $title =~ s/Ú|Û|Ù|Ü/U/g;
    $title =~ s/ú|û|ù|ü/u/g; 
    $Data =~ s///g;
    print OUT "\n\nTI-: $title||"; 
  }
}

sub parse_meta_keywords {
  local $mot_cles;

  my $p = HTML::TokeParser->new("$ARGV[0]");
  while (my  $token = $p->get_tag("meta")) { 
    
    if ($token->[1]{name} =~ /keywords/i) {
      $mot_cles = $token->[1]{content};
      
      $mot_cles =~ s/Á|Â|À|Ã|Å|Ä/A/g;
      $mot_cles =~ s/â|á|à|å|ä/a/g;
      $mot_cles =~ s/ç/c/g;
      $mot_cles =~ s/Ç/C/g;
      $mot_cles =~ s/é|ê|è|ë/e/g;
      $mot_cles =~ s/É|Ê|È|Ë/E/g;
      $mot_cles =~ s/Í|Î|Ì|Ï/I/g;
      $mot_cles =~ s/í|î|ì|ï/i/g;
      $mot_cles =~ s/Ñ/N/g;
      $mot_cles =~ s/ñ/n/g;
      $mot_cles =~ s/Ó|Ô|Ò|Ø|Õ|Ö/O/g;
      $mot_cles =~ s/ó|ô|ò|ø|õ|ö/o/g;
      $mot_cles =~ s/Ú|Û|Ù|Ü/U/g;
      $mot_cles =~ s/ú|û|ù|ü/u/g;
      $Data =~ s///g;
      print OUT "\n\nMC-: $mot_cles||";  
    }
  }
}

sub parse_meta_description {
  local $desc;

  my $p = HTML::TokeParser->new("$ARGV[0]");
  while (my $token = $p->get_tag("meta")) {
    if ($token->[1]{name} =~ /description/i ) {
       $desc = $token->[1]{content};
      
      $desc =~ s/Á|Â|À|Ã|Å|Ä/A/g;
      $desc =~ s/â|á|à|å|ä/a/g;
      $desc =~ s/ç/c/g;
      $desc =~ s/Ç/C/g;
      $desc =~ s/é|ê|è|ë/e/g;
      $desc =~ s/É|Ê|È|Ë/E/g;
      $desc =~ s/Í|Î|Ì|Ï/I/g;
      $desc =~ s/í|î|ì|ï/i/g;
      $desc =~ s/Ñ/N/g;
      $desc =~ s/ñ/n/g;
      $desc =~ s/Ó|Ô|Ò|Ø|Õ|Ö/O/g;
      $desc =~ s/ó|ô|ò|ø|õ|ö/o/g;
      $desc =~ s/Ú|Û|Ù|Ü/U/g;
      $desc =~ s/ú|û|ù|ü/u/g;
      $Data =~ s///g;
      print  OUT "\n\nSUJET-: $desc||";  
    }
  }
}
sub parse_author{
  
  my $p = HTML::TokeParser->new("$ARGV[0]");
  while ( my $token = $p->get_tag("meta") ) { 
    if ($token->[1]{name} =~ /author/i) {
      print  OUT "\nAU-: $token->[1]{content}||"; 
    }
  }
}

sub parse_date{
  my $p = HTML::TokeParser->new("$ARGV[0]");
  while ( my $token = $p->get_tag("meta")) { 
    if ($token->[1]{name} =~ /date/i) {
      print  OUT "\nDT-: $token->[1]{content}||"; 
    }
  }
}

sub ignore_script2{
  local $p=HTML::TokeParser->new("$ARGV[0]");
  while (  my $token = $p->get_tag("SCRIPT")){
    my $Text = $p->get_trimmed_text("/SCRIPT");  
  } 
}

sub ignore_script{
  local $p=HTML::TokeParser->new("$ARGV[0]");
  while (  my $token = $p->get_tag("!--")){
    my $Text = $p->get_text("--");  
  } 
}

sub ignore_style{
  local $p=HTML::TokeParser->new("$ARGV[0]");
  while (  my $token = $p->get_tag("style")){
    my $Text = $p->get_text("/style");  
  } 
}

sub parse_hyperlinks{
  local $p=HTML::TokeParser->new("$ARGV[0]");
  print OUT "\n\nURL-: ";
  while (my $token = $p->get_tag("a")) {
    $linkURL  = $token->[1]{href};
    my $Text = $p->get_text("/a");
    $Text =~ s/\n//g;
    $Text =~ s/\s+/ /g;
   
    if ($linkURL && $linkURL !~ /mailto/i) { 
      print OUT "\n$Text"."\n". $linkURL;
    } 
  }
  print OUT "||";
}

close(IN);
close(OUT);




