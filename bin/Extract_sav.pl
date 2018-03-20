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

    $Data =~ s/�|�|�|�|�|�/A/g;
    $Data =~ s/�|�|�|�|�/a/g;
    $Data =~ s/�/c/g;
    $Data =~ s/�/C/g;
    $Data =~ s/�|�|�|�/e/g;
    $Data =~ s/�|�|�|�/E/g;
    $Data =~ s/�|�|�|�/I/g;
    $Data =~ s/�|�|�|�/i/g;
    $Data =~ s/�/N/g;
    $Data =~ s/�/n/g;
    $Data =~ s/�|�|�|�|�|�/O/g;
    $Data =~ s/�|�|�|�|�|�/o/g;
    $Data =~ s/�|�|�|�/U/g;
    $Data =~ s/�|�|�|�/u/g;   
    $Data =~ s///g;
    print OUT  "$Data||";
  }
}  


sub parse_title {
  if ($p->get_tag("title")) {
    local $title = $p->get_trimmed_text;  

    $title =~ s/�|�|�|�|�|�/A/g;
    $title =~ s/�|�|�|�|�/a/g;
    $title =~ s/�/c/g;
    $title =~ s/�/C/g;
    $title =~ s/�|�|�|�/e/g;
    $title =~ s/�|�|�|�/E/g;
    $title =~ s/�|�|�|�/I/g;
    $title =~ s/�|�|�|�/i/g;
    $title =~ s/�/N/g;
    $title =~ s/�/n/g;
    $title =~ s/�|�|�|�|�|�/O/g;
    $title =~ s/�|�|�|�|�|�/o/g;
    $title =~ s/�|�|�|�/U/g;
    $title =~ s/�|�|�|�/u/g; 
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
      
      $mot_cles =~ s/�|�|�|�|�|�/A/g;
      $mot_cles =~ s/�|�|�|�|�/a/g;
      $mot_cles =~ s/�/c/g;
      $mot_cles =~ s/�/C/g;
      $mot_cles =~ s/�|�|�|�/e/g;
      $mot_cles =~ s/�|�|�|�/E/g;
      $mot_cles =~ s/�|�|�|�/I/g;
      $mot_cles =~ s/�|�|�|�/i/g;
      $mot_cles =~ s/�/N/g;
      $mot_cles =~ s/�/n/g;
      $mot_cles =~ s/�|�|�|�|�|�/O/g;
      $mot_cles =~ s/�|�|�|�|�|�/o/g;
      $mot_cles =~ s/�|�|�|�/U/g;
      $mot_cles =~ s/�|�|�|�/u/g;
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
      
      $desc =~ s/�|�|�|�|�|�/A/g;
      $desc =~ s/�|�|�|�|�/a/g;
      $desc =~ s/�/c/g;
      $desc =~ s/�/C/g;
      $desc =~ s/�|�|�|�/e/g;
      $desc =~ s/�|�|�|�/E/g;
      $desc =~ s/�|�|�|�/I/g;
      $desc =~ s/�|�|�|�/i/g;
      $desc =~ s/�/N/g;
      $desc =~ s/�/n/g;
      $desc =~ s/�|�|�|�|�|�/O/g;
      $desc =~ s/�|�|�|�|�|�/o/g;
      $desc =~ s/�|�|�|�/U/g;
      $desc =~ s/�|�|�|�/u/g;
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




