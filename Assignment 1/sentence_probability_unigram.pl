#!/usr/bin/env perl
use Data::Dumper;

my $corpus   = $ARGV[0];
my $sentence = $ARGV[1];

open my $fh, '<', $corpus or die "error opening $corpus: $!";
my $text = do { local $/; <$fh> };
close $fh;

$text =~ tr/<\/>a-zåàâäæçéèêëîïôöœßùûüÿA-ZÅÀÂÄÆÇÉÈÊËÎÏÔÖŒÙÛÜŸ/\n/cs;
# tr commented in bigram

@words = split(/\n/, $text);
for ($i = 0; $i <= $#words; $i++) {
	$frequency{$words[$i]}++;
}


my @sentence = split / /, $sentence . " <\/s>";
my $probability = 1;

print "Unigrams:\n";
print "============================================\n";
print "wi\tC(wi)\t#words\tP(wi)\n";
print "============================================\n";

for my $word (@sentence) {
	my $prob = ($frequency{$word} || 1) / $#words;
	print "$word\t$frequency{$word}\t$#words\t$prob\n";
	$probability *= $prob;
}
print "============================================\n";
print "Prob. unigrams: $probability\n";
print "============================================\n";


# foreach $word (sort keys %frequency){
# 	print "$frequency{$word} $word\n";
# }