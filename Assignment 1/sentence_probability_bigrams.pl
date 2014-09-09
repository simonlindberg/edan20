#!/usr/bin/env perl
use Data::Dumper;
use List::Util qw[min max];
use utf8;
use feature 'unicode_strings';

my $corpus   = $ARGV[0];
my $sentence = $ARGV[1];

open my $fh, '<', $corpus or die "error opening $corpus: $!";
my $text = do { local $/; <$fh> };
close $fh;

@words = split(/\s/, $text);


for ($i = 0; $i < $#words; $i++) {
	$bigrams[$i] = $words[$i] . " " . $words[$i + 1];
}

for ($i = 0; $i <= $#words; $i++) {
	$frequency{$words[$i]}++;
}
for ($i = 0; $i < $#words; $i++) {
	$frequency_bigrams{$bigrams[$i]}++;
}

my @sentence = split / /, "<s> " . $sentence . " <\/s>";
my $probability = 1;
my $entropy = 0;

print "Bigrams:\n";
print "====================================================\n";
print "wi\twi+1\tCi,i+1\tC(i)\tP(wi + 1|wi)\n";
print "====================================================\n";
$probability = 1;
for ($i = 0; $i < scalar(@sentence) - 1; $i++) {
	my $bigram = @sentence[$i] . " " . @sentence[$i + 1];
	print @sentence[$i] . "\t" . @sentence[$i + 1];
	print "\t";
	print ($frequency_bigrams{$bigram} || "0");
	print "\t";
	print $frequency{@sentence[$i]} ? $frequency{@sentence[$i]} : "0";
	print "\t";

	my $prob;
	if ($frequency_bigrams{$bigram} > 0) {
		$prob = $frequency_bigrams{$bigram} / $frequency{@sentence[$i]};
	} else {
		print "*backoff: ";
		$prob = $frequency{@sentence[$i + 1]} / $#words;
	} 
	print $prob;

	$probability *= $prob;

	# Chapter 4, page 77
	#$entropy -= $prob * log2($prob);

	print "\n";
}

$entropy = (-1 / $#sentence) * log2($probability);

print "====================================================\n";
print "P(Bigram);\tProb. bigrams:\t$probability\n";
print "H(X);\t\tEntropy rate:\t$entropy\n";
print "\t\tPerplexity:\t" . (2**$entropy)."\n";
print "====================================================\n";


sub log2 {
	my $n = shift;
	if (!$n) {
		return 0;
	}
    return log($n)/log(2);
}
