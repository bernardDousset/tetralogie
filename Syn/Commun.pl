#!/usr/bin/perl5
#

if ( @ARGV != 3 ) { 
  print "\nUsage : Commun.pl <fichier_urls_1> <fichier_urls_2> <urls_communes> \n\n"; 
  exit;
}

open(Fich1, "sort -u $ARGV[0]|");
open(Fich2, "sort -u $ARGV[1]|");
open(COMM,  ">$ARGV[2]");

@lines1 = <Fich1>;
@lines2 = <Fich2>;


foreach $_ (@lines1) {
  if (/\s+\n$/) {
    s/ //g;
  }
}

foreach $_ (@lines2) {
  if (m/\s+\n$/i) {  
    s/ //g;
  }
}

foreach $line1 (@lines1) {
  foreach $line2 (@lines2) {
    
    if ($line1 eq $line2) {
      print COMM $line1; 
    }
  }
}
close(Fich1);
close(Fich2);
close(COMM);
