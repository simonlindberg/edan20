package parser;

import java.util.List;

import format.Word;
import guide.Features;

public abstract class LabeledParser extends ReferenceParser {

	public LabeledParser(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected abstract Features extractFeatures();

	protected void addLeft() {
		transitionList.add("la." + stack.peek().getDeprel());
	}

	protected void addRight(){
		transitionList.add("ra." + queue.get(0).getDeprel());
	}
}
