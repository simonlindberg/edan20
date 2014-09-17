#!/usr/bin/env perl

use strict;
use warnings;
use utf8;
use feature 'unicode_strings';

binmode(STDOUT, ":utf8");
binmode(STDIN, ":utf8");

my $filename = $ARGV[0];

open my $fh, '<:encoding(UTF-8)', $filename or die "error opening $filename: $!";
my $data = do { local $/; <$fh> };
close $fh;

$data =~ s/\s+/ /g;

$data =~ s/[^\s\p{Letter}.!?]+//g;
#$data =~ s/([[:upper:]](?:[^[:punct:]]|[[:punct:]]\s*[[:lower:]])+)[[:punct:]]+/\n<s> $1 <\/s>/g;
$data =~ s/(\p{Upper}(?:[^\p{punct}]|\p{punct}\s*\p{Lower})+)\p{punct}+/\n<s> $1 <\/s>/g;

print lc($data);
