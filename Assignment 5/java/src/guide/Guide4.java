package guide;

import format.Word;
import wekaglue.WekaGlue;
import parser.ParserState;

/**
 * 
 * @author Pierre Nugues
 */
public class Guide4 extends Guide {

	public Guide4(WekaGlue wekaModel, ParserState parserState) {
		super(wekaModel, parserState);
	}

	// This is a simple oracle that uses the top and second in the stack and
	// first and second in the queue + the Booleans

	public String predict() {
		Features feats = extractFeatures();
		String[] features = new String[6];
		features[0] = feats.getTopPostagStack();
		features[1] = feats.getSecondPostagStack();
		features[2] = feats.getFirstPostagQueue();
		features[3] = feats.getSecondPostagQueue();
		features[4] = String.valueOf(feats.getCanLA());
		features[5] = String.valueOf(feats.getCanRE());
		return wekaModel.classify(features);
	}

	@Override
	protected Features extractFeatures() {
		String topPostagStack = "nil";
		String secondPostagStack = "nil";
		String secondPostagQueue = "nil";
		if (!parserState.stack.empty()) {
			topPostagStack = parserState.stack.peek().getPostag();
			Word temp = parserState.stack.pop();
			if (!parserState.stack.empty()) {
				secondPostagStack = parserState.stack.peek().getPostag();
			}
			parserState.stack.push(temp);
		}
		if (parserState.queue.size() > 1) {
			secondPostagQueue = parserState.queue.get(1).getPostag();
		}

		return new Features(topPostagStack, secondPostagStack, parserState.queue.get(0).getPostag(), secondPostagQueue,
				parserState.canLeftArc(), parserState.canReduce());
	}
}
