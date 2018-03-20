#!/usr/bin/perl5.6.1
##############################################
use HTML::TokeParser;
use HTML::Filter;
##############################################

if ( !open(IN, "$ARGV[0]") ) {
  print " Erreur d'ouverture de $ARGV[0] \n"; 
  exit(0);
}

if ( !open(OUT, ">$ARGV[1]") ) {
  print " Erreur d'ouverture de $ARGV[1] \n"; 
  exit(0);
}

$origine = "$ARGV[0]" ;
#print "************* $origine\n";
$sl = '/';
$origine =~ s/#/${sl}/g;

print OUT "\n\nAD: http://$origine"; # separateur de notices 

$p = HTML::TokeParser->new("$ARGV[0]") || die "Can't open: $!";

&parse_meta_tags;          # lecture des META TAGS.
&parse_document;           # lecture du corps du documents.
&parse_hyperlinks;         # lecture des hyperliens dans le document
&ignore_script;            # ignorer les scripts (Javascript).
&ignore_style;             # ignorer les definitions des styles.



sub parse_meta_tags {
  &parse_title;            # lecture du Titre du document.
  &parse_meta_keywords;    # lecture des Mots Cles du document.
  &parse_meta_description; # lecture de la description du document.
  &parse_author;           # lecture de l'auteur du document.
  &parse_date;             # lecture de la date du document.  
}

sub parse_document() {
  my $token;
 
  print OUT "\n\nTX: " ;
  
  local $p = HTML::TokeParser->new("$ARGV[0]");  
  while ( $token = $p->get_tag("body")) {
    local $Data = $p->get_text("/body");

    $Data =~ s/<(([^>]|\n)*)>//g;
    $Data =~ s/\s+(.*)\n/$1\n/g;
    $Data =~ s/\[IMG\]//g;
    $Data =~ s/\[(.)+\]//g;
    $Data =~ s/\n\n+/\n/g;

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
    
    print OUT  $Data;
  }
}  


sub parse_title {
  if ($p->get_tag("title")) {
    local $title = $p->get_trimmed_text;   
    print OUT "\n\nTI: $title"; 
  }
}

sub parse_meta_keywords {
  my $p = HTML::TokeParser->new("$ARGV[0]");
  while (my  $token = $p->get_tag("meta")) {   
    if ($token->[1]{name} =~ /keywords/i) {
      print OUT "\n\nMC: $token->[1]{content}";  
    }
  }
}

sub parse_meta_description {
  my $p = HTML::TokeParser->new("$ARGV[0]");
  while (my $token = $p->get_tag("meta")) {
    if ($token->[1]{name} =~ /description/i) {
      print  OUT "\n\nSU: $token->[1]{content}";  
    }
  }
}
sub parse_author{
  my $p = HTML::TokeParser->new("$ARGV[0]");
  while ( my $token = $p->get_tag("meta") ) { 
    if ($token->[1]{name} =~ /author/i) {
      print  OUT "\nAU: $token->[1]{content}"; 
    }
  }
}

sub parse_date{
  my $p = HTML::TokeParser->new("$ARGV[0]");
  while ( my $token = $p->get_tag("meta")) { 
    if ($token->[1]{name} =~ /date/i) {
      print  OUT "\nDP: $token->[1]{content}"; 
    }
  }
}

sub parse_hyperlinks{
  local $p=HTML::TokeParser->new("$ARGV[0]");
 
  local %urls = ();
  my $i = 0;
  while (my $token = $p->get_tag("a")) {
    $linkURL  = $token->[1]{href};
    my $Text = $p->get_text("/a");
    $Text =~ s/\n//g;
    $Text =~ s/\s+/ /g;
   
    if ($linkURL && $linkURL !~ /mailto/i) { 
      $urls{$Text} = $linkURL;
	$i++;
    } 
   } 
   if ( $i > 0 ) {
     print OUT "\n\nURL: ";
     foreach $key (keys %urls) {
        print OUT " \n$key"." # ". $urls{$key};
      }
  }
}
sub ignore_script{
  local $p=HTML::TokeParser->new("$ARGV[0]");
  while (  my $token = $p->get_tag("script")){
    my $Text = $p->get_text("/script");  
  } 
}
sub ignore_style{
  local $p=HTML::TokeParser->new("$ARGV[0]");
  while (  my $token = $p->get_tag("style")){
    my $Text = $p->get_text("/style");  
  } 
}



close(IN);
close(OUT);




