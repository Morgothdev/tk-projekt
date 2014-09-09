tk-projekt
==========

Poniżej przesyłam przykładowe reguły upraszczania, które mogą Panowie uwzględnić.
(eps oznacza słowo puste, empty - zbiór pusty):
eps* = > eps                => dzikie
a** => a*                   => gotowe / już podczas parsowania ;]
aa* => a+                   =>
a*a* => a*
a | a* => a*
(a| eps)* => a*
ab|ac => a(b|c)
ac|bc => (a|b)c
(aa|a)* => a*
(a|b*)* => (a|b)*
a eps => a
(a*b*c*)* => (a|b|c)*
(a|(b|c)) => (a|b|c)
a(bc)=>abc
a|b|a => a|b
a empty => empty
empty* => eps

Na stronie http://www.brics.dk/automaton/doc/index.html?dk/brics/automaton/RegExp.html
Gramatyka ta specyfikuje rozszerzony język względem tego znanego z TAiJF, zaletą jest możliwość stosowania dodatkowych reguł, np. typu
a{n,m}a{k,l} => a{n+k,m+l}


Pozdrawiam,
Marcin Kuta