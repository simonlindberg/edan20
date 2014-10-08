package parser;

import java.util.List;

import format.Word;
import guide.Features;

public abstract class UnlabeledParser extends ReferenceParser {

	public UnlabeledParser(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected abstract Features extractFeatures();

	protected void addLeft() {
		transitionList.add("la");
	}

	protected void addRight() {
		transitionList.add("ra");
	}


}
