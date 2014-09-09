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

$data =~ s/\s/ /g;

$data =~ s/[^\n ña-zåàâäæçéèêëîïôöœßùûüÿ.']+//gi;
$data =~ s/([[:upper:]](?:[^[:punct:]]|[[:punct:]]\s*[[:lower:]])+)[[:punct:]]+/<s> $1 <\/s>\n/g;
#$data =~ s/([^[:punct:]]+)[[:punct:]]/<s> $1 <\/s>\n/g;

$data =~ s/ +/ /g;
$data =~ s/^ | $//gm;
$data =~ s/\.//g;

$data = lc($data);
print $data;
