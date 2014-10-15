package guide;

import parser.ParserState;
import wekaglue.WekaGlue;

/**
 * 
 * @author Pierre Nugues
 */
public abstract class Guide {

	WekaGlue wekaModel;
	ParserState parserState;

	Guide(WekaGlue wekaModel, ParserState parserState) {
		this.wekaModel = wekaModel;
		this.parserState = parserState;
	}

	public abstract String predict();

	protected abstract Features extractFeatures();

}
