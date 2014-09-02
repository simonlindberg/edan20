#!/usr/bin/env perl

use utf8;
binmode(STDOUT, ":encoding(UTF-8)");
binmode(STDIN, ":encoding(UTF-8)");

$text = <>;
while ($line = <>) { 
	$text .= $line;
}
$text =~ tr/a-zåàâäæçéèêëîïôöœßùûüÿA-ZÅÀÂÄÆÇÉÈÊËÎÏÔÖŒÙÛÜŸ/\n/cs;
# tr commented in bigram
@words = split(/\n/, $text);
for ($i = 0; $i <= $#words; $i++) {
	if (!exists($frequency{$words[$i]})) {
		$frequency{$words[$i]} = 1;
	} else {
		$frequency{$words[$i]}++;
	}
}
foreach $word (sort keys %frequency){
	print "$frequency{$word} $word\n";
}

# The file does the following:
# 1. Replace the characters matched by the regex on line 11 by newline
# 2. Create a new array with the previously tokenized string split by newline
# 3. Loop through the array
# 4. Count how many times a given word is mentioned and store the data in a hash
#    with the word as key.
# 5. Sort the array and print how many times each word occured.