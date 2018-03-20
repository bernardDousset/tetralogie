#!/bin/sh
if ((test -f $3/$1) && (test $2 ) && (test -d $3 )) 
then
rep=$TETRALOGIEHOME/Syn
cd $3
cp $1 synonymies
awk '{ print " ",$0," "}'                                       synonymies  > aux.1
sed -f "$rep/p-sed"                                                   aux.1 > aux.2
awk '{for (i=1;i<NF;i++) printf "%-40s ",$i ; printf "%-40s\n", $NF}' aux.2 > aux.1
awk -f $rep/p-ordre                                                   aux.1 > aux.2
awk '{ if (NF>=1) print $0,"\t", FNR}'                                aux.2 > aux.1
sort -t\t -k 1                                                        aux.1 > aux.2
awk -f $rep/p-makesyn                                                 aux.2 > aux.1
awk -f $rep/p-numero-syn                                              aux.1 > aux.2
sort                                                                  aux.2 > $2
rm  aux.1 aux.2 synonymies
else
echo "la syntax est Syno input(existe) output repertoire(existe)"
fi
