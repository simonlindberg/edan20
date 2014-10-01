package parser;

import java.util.List;

import format.Word;
import guide.Features;

public class _2Labeled extends LabeledParser {

	public _2Labeled(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected Features extractFeatures() {
		return new Features(stack.peek().getPostag(), queue.get(0).getPostag(), canLeftArc(), canReduce());
	}

}
