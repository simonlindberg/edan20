#!/usr/bin/env perl
use Data::Dumper;
use utf8;

my $corpus   = $ARGV[0];
my $sentence = $ARGV[1];

open my $fh, '<', $corpus or die "error opening $corpus: $!";
my $text = do { local $/; <$fh> };
close $fh;

@words = split(/\s/, $text);
for ($i = 0; $i <= $#words; $i++) {
	$frequency{$words[$i]}++;
}


my @sentence = split / /, $sentence . " <\/s>";
my $probability = 1;
my $entropy = 0;

print "Unigrams:\n";
print "============================================\n";
print "wi\tC(wi)\t#words\tP(wi)\n";
print "============================================\n";

for my $word (@sentence) {
	my $prob = ($frequency{$word} || 1) / $#words;
	print "$word\t$frequency{$word}\t$#words\t$prob\n";
	$probability *= $prob;

}

$entropy = (-log2($probability)) / $#sentence;
$perplexity = (2**$entropy);

print "============================================\n";
print "Prob. unigrams: $probability\n";
print "Entropy rate:\t$entropy\n";
print "Perplexity:\t" . $perplexity ."\n";
print "============================================\n";


sub log2 {
	my $n = shift;
	if (!$n) {
		return 0;
	}
    return log($n)/log(2);
}