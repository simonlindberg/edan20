package parser;

import java.util.List;

import format.Word;
import guide.Features;

public class _6Unlabeled extends UnlabeledParser {

	public _6Unlabeled(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected Features extractFeatures() {
		return new Features(stack.peek().getPostag(),
				stack.elementAt(1).getPostag(),
				queue.get(0).getPostag(),
				queue.get(1).getPostag(),
				wordList.get(wordList.indexOf(stack.peek()) + 1).getPostag(),
				null,
				canLeftArc(),
				canReduce());
	}

}
