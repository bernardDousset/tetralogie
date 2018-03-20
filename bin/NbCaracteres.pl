#!/usr/bin/perl5 -w
# <fich_In> : sera en general le dictionnaire.

open(IN, "$ARGV[0]");
$i = 0;

@lignes = <IN>;

foreach ( @lignes ) {
   $i += $#lignes;

}


print "\n $i\n";
close(IN);

