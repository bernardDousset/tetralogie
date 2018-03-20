#!/usr/bin/perl5.6.1

#### #### #### #### #### #### #### #### #### #### #### 
# Extraction des trois et quatres premiers caracteres 
# de chaque mots de la liste.
#
# Usage : Entete.pl Dico A3 A4
#
#         Dico : fichier sous forme de liste de mots
#         A3   : fichier des prefixes a 3 caracteres
#         A4   : fichier des prefixes a 4 caracteres
#### #### #### #### #### #### #### #### #### #### #### 

open( DICO,  "$ARGV[0]" );
open( EnteteA3, ">$ARGV[1]" );
open( EnteteA4, ">$ARGV[2]");

@liste  = <DICO>;

foreach $_ (@liste) {
  chomp;
  $a3 = substr($_, 0, 3);
  $a4 = substr($_, 0, 4);
  print EnteteA3 "$_\t$a3\n"; 
  print EnteteA4 "$_\t$a4\n";
}
close(DICO);
close(EnteteA3);
close(EnteteA4);
