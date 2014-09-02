#!/usr/bin/env perl

use strict;
use warnings;

my $filename = $ARGV[0];

open my $fh, '<', $filename or die "error opening $filename: $!";
my $data = do { local $/; <$fh> };
close $fh;

$data =~ s@([A-ZÅÄÖ][^.]+)\.@<s> $1 </s>@g;
$data = lc($data);
print $data;
