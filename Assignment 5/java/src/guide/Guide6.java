package guide;

import format.Word;
import wekaglue.WekaGlue;
import parser.ParserState;

/**
 * 
 * @author Pierre Nugues
 */
public class Guide6 extends Guide {

	public Guide6(WekaGlue wekaModel, ParserState parserState) {
		super(wekaModel, parserState);
	}

	// This is a simple oracle that uses the top and second in the stack and
	// first and second in the queue + the Booleans

	public String predict() {
		Features feats = extractFeatures();
		String[] features = new String[8];
		features[0] = feats.getTopPostagStack();
		features[1] = feats.getSecondPostagStack();
		features[2] = feats.getFirstPostagQueue();
		features[3] = feats.getSecondPostagQueue();
		features[4] = feats.getNextWordPostag();
		features[5] = feats.getThirdQueuePos(); 
		features[6] = String.valueOf(feats.getCanLA());
		features[7] = String.valueOf(feats.getCanRE());

		return wekaModel.classify(features);
	}

	@Override
	protected Features extractFeatures() {
		String topPostagStack = "nil";
		String secondPostagStack = "nil";
		String firstPosQueue = "nil";
		String secondPostagQueue = "nil";
		String nextWordPostag = "nil";
		String unknown = "nil";
		if (!parserState.stack.empty()) {
			topPostagStack = parserState.stack.peek().getPostag();
			Word temp = parserState.stack.pop();
			if (!parserState.stack.empty()) {
				secondPostagStack = parserState.stack.peek().getPostag();
			}
			
			if (!parserState.stack.empty()){
				final int i = parserState.wordList.indexOf(parserState.stack.peek());
				if (i > 0) {
					nextWordPostag = parserState.wordList.get(i).getPostag();
				}
			}
			
			parserState.stack.push(temp);
		}
		
		if (parserState.queue.size() > 0) {
			firstPosQueue = parserState.queue.get(0).getPostag();
		}
		
		if (parserState.queue.size() > 1) {
			secondPostagQueue = parserState.queue.get(1).getPostag();
		}
		
		if (parserState.queue.size() > 2) {
			unknown = parserState.queue.get(2).getPostag();
		}

		return new Features(
				topPostagStack,
				secondPostagStack,
				firstPosQueue,
				secondPostagQueue,
				nextWordPostag,
				unknown,
				parserState.canLeftArc(),
				parserState.canReduce());
	}
}
