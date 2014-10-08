package parser;

import java.util.List;

import format.Word;
import guide.Features;

public class _6Labeled extends LabeledParser {

	public _6Labeled(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected Features extractFeatures() {
		return new Features(safeStackPos(0),
							safeStackPos(1),
							safeQueuePos(0),
							safeQueuePos(1),
							safeWordPos(),
							safeQueuePos(2),
							canLeftArc(),
							canReduce());
	}

}
