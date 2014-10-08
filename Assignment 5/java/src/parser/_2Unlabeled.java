package parser;

import java.util.List;

import format.Word;
import guide.Features;

public class _2Unlabeled extends UnlabeledParser {

	public _2Unlabeled(List<Word> wordList) {
		super(wordList);
	}

	@Override
	protected Features extractFeatures() {
		return new Features(safeStackPos(0), safeQueuePos(0), canLeftArc(), canReduce());
	}

}
