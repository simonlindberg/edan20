package parser;

import java.util.List;

import format.Word;
import guide.Features;

public class _4Labeled extends LabeledParser {

	public _4Labeled(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected Features extractFeatures() {
		return new Features(stack.peek().getPostag(), stack.elementAt(1).getPostag(), queue.get(0).getPostag(), queue.get(1).getPostag(), canLeftArc(), canReduce());
	}

}
