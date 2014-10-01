package parser;

import java.util.ArrayList;
import java.util.List;

import format.Word;
import guide.Features;

public abstract class UnlabeledParser extends ReferenceParser {

	public UnlabeledParser(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected abstract Features extractFeatures();

	@Override
	public boolean parse() {
		transitionList = new ArrayList<String>();
		featureList = new ArrayList<Features>();

		while (!queue.isEmpty()) {
			featureList.add(extractFeatures());
			// COMPLETE HERE THE CODE TO DETERMINE THE ACTION
		}
		emptyStack(transitionList, featureList);

		boolean printCntOper = false;
		if (printCntOper) {
			System.out.println("#words: " + wordList.size() + " \tStack: " + stack.size() + "\tTransition count: " + transitionList.size());
		}

		// Final test to check if the hand-annotated graph and reference-parsed
		// graph are equal.
		// If they are different, this is probably due to nonprojective links
		return equalGraphs();
	}
}
