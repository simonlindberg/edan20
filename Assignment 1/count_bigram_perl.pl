#!/usr/bin/env perl

use utf8;
use Data::Dumper;

$text = <>;
while ($line = <>) { 
   $text .= $line;
}
# The next line is a very primitive tokenizer
$text =~ tr/a-zåàâäæçéèêëîïôöœßùûüÿA-ZÅÀÂÄÆÇÉÈÊËÎÏÔÖŒÙÛÜŸ/\n/cs;
# tr makes a character-by-character match and replaces
# a potential match with, in this case, newline.
# a more limited way of simply using s///...

@words = split(/\n/, $text);
for ($i = 0; $i <= $#words - 1; $i++) {
	$bigrams[$i] = $words[$i] . " " . $words[$i + 1];
}

for ($i = 0; $i < $#words; $i++) {
	if (!exists($frequency_bigrams{$bigrams[$i]})) {
		$frequency_bigrams{$bigrams[$i]} = 1;
	} else {
		$frequency_bigrams{$bigrams[$i]}++;
	}
}
foreach $bigram (sort keys %frequency_bigrams){
	print "$frequency_bigrams{$bigram} $bigram \n";
}

# In this case we do the same tokenization as count_perl.perl
# The looping process is different:
# The words are stored two by two (separated by space...? Bug?) in a new array.
# Each word will be stored in $i and $i+1, except for the first/last.
# "abc def ghi jkl" will end up as:
# ["abc def", "def ghi", "ghi jkl"]
# Then the same thing as before is repeated (counting process)

#####
# Q: What is the possible number of bigrams and their real number? Explain why such a difference. What would be the possible number of 4-grams.
# A: Possible number should be $#words - 1...
#    Using 'Selma.txt' as input; $#bigrams = 114622
#                                $#words   = 114623
#    The reason is that if there's an odd number of words, the last word is skipped.
#    The number of 4-grams should be $#words - 3
#
# Q: Propose a solution to cope with bigrams unseen in the corpus. This topic will be discussed during the lab session.
# A: Add a "dummy"-word thats used for the last word



