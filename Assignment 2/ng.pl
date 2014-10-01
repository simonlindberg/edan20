% Author Pierre Nugues
% Rules describing noun groups

% noun_group(-NounGroup)
% detects a list of words making a noun group and 
% unifies NounGroup with it

% Nominal = part of speech that features nouns and adjectives
% Determinal = Phrase, word, affix that occurs together with noun, "The table"
% Pronoun = Replaces a noun, like a name. "Firas is cool. He really is." 
% Proper noun = names

% Determinal ska kunna vara ensamma.
% 

noun_group([D | N]) --> det(D), noun_group(N).
noun_group(N) --> nominal(N).

noun_group([PN]) --> proper_noun(PN).
noun_group([PRO]) --> pronoun(PRO).
noun_group([PRO | NG]) --> pronoun(PRO), noun_group(NG).

noun_group(NG) -->
	det(D), adj_group(AG), nominal(NOM),
	{append([D | AG], NOM, NG)}.
noun_group(NG) -->
	adj_group(AG), nominal(NOM),
	{append(AG, NOM, NG)}.

noun_group([POS | NG]) --> possessive(POS), noun_group(NG).
noun_group([NUM | NG]) --> num(NUM), noun_group(NG).

verb_group([V]) --> verb(V).

% Nominal expressions
nominal([NOUN | NOM]) --> noun(NOUN), nominal(NOM).
nominal([N]) --> noun(N).


% Nouns divide into common and proper nouns
noun(N) --> common_noun(N).
noun(N) --> proper_noun(N).

% adj_group(-AdjGroup)
% detects a list of words making an adjective
% group and unifies AdjGroup with it

adj_group_x([RB, A]) --> adv(RB), adj(A).
adj_group_x([A]) --> adj(A).

adj_group(AG) --> adj_group_x(AG).
adj_group(AG) -->
	adj_group_x(AGX),
	adj_group(AGR),
	{append(AGX, AGR, AG)}.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% The POS rules

det(Pair) --> [Pair], { Pair = (_, 'DT') }.

common_noun(Pair) --> [Pair], { Pair = (_, 'NN') }.
%Added
common_noun(Pair) --> [Pair], { Pair = (_, 'NNS') }.

proper_noun(Pair) --> [Pair], { Pair = (_, 'NNP') }.
%Added
proper_noun(Pair) --> [Pair], { Pair = (_, 'NNPS') }.

pronoun(Pair) --> [Pair], { Pair = (_, 'PRP') }.
%added
pronoun(Pair) --> [Pair], { Pair = (_, 'PRP$') }.
pronoun(Pair) --> [Pair], { Pair = (_, 'WP') }.
pronoun(Pair) --> [Pair], { Pair = (_, 'WP$') }.


adv(Pair) --> [Pair], { Pair = (_, 'RB') }.
% Added
adv(Pair) --> [Pair], { Pair = (_, 'RBR') }.
adv(Pair) --> [Pair], { Pair = (_, 'RBS') }.

adj(Pair) --> [Pair], { Pair = (_, 'JJ') }.
adj(A) --> past_participle(A).
adj(A) --> gerund(A).
% Added
adj(Pair) --> [Pair], { Pair = (_, 'JJR') }.
adj(Pair) --> [Pair], { Pair = (_, 'JJS') }.

past_participle(Pair) --> [Pair], { Pair = (_, 'VBN') }.

gerund(Pair) --> [Pair], { Pair = (_, 'VBG') }.


%New
num(Pair) --> [Pair], { Pair = (_, 'CD') }.
num(Pair) --> [Pair], { Pair = (_, '$') }.
possessive(Pair) --> [Pair], { Pair = (_, 'POS') }.


% verbs
verb(Pair) --> [Pair], { Pair = (_, 'VBG') }.
verb(Pair) --> [Pair], { Pair = (_, 'VBD') }.
verb(Pair) --> [Pair], { Pair = (_, 'VBG') }.
verb(Pair) --> [Pair], { Pair = (_, 'VBN') }.
verb(Pair) --> [Pair], { Pair = (_, 'VBP') }.
verb(Pair) --> [Pair], { Pair = (_, 'VBZ') }.

