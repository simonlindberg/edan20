package guide;

import format.Word;
import wekaglue.WekaGlue;
import parser.ParserState;

/**
 * 
 * @author Pierre Nugues
 */
public class Guide2 extends Guide {

	public Guide2(WekaGlue wekaModel, ParserState parserState) {
		super(wekaModel, parserState);
	}

	// This is a simple oracle that uses the top and second in the stack and
	// first and second in the queue + the Booleans

	public String predict() {
		Features feats = extractFeatures();
		String[] features = new String[4];
		features[0] = feats.getTopPostagStack();
		features[1] = feats.getFirstPostagQueue();
		features[2] = String.valueOf(feats.getCanLA());
		features[3] = String.valueOf(feats.getCanRE());
		
		return wekaModel.classify(features);
	}

	@Override
	protected Features extractFeatures() {
		String topPostagStack = "nil";
		String firstPosQueue = "nil";
		if (!parserState.stack.empty()) {
			topPostagStack = parserState.stack.peek().getPostag();
		}
		
		if (parserState.queue.size() > 0) {
			firstPosQueue = parserState.queue.get(0).getPostag();
		}
		

		return new Features(
				topPostagStack,
				firstPosQueue,
				parserState.canLeftArc(),
				parserState.canReduce());
	}
}
