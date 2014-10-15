package parser;

import java.util.*;
import java.io.File;

import format.CONLLCorpus;
import format.Constants;
import format.Word;
import guide.Guide;
import guide.Guide2;
import guide.Guide4;
import guide.Guide6;

import java.io.IOException;

import wekaglue.WekaGlue;

/**
 * 
 * @author Pierre Nugues
 */
public class Parser {

	ParserState parserState;
	Guide oracle;

	Parser(ParserState parserState, Guide oracle) {
		this.parserState = parserState;
		this.oracle = oracle;
	}

	public List<Word> parse() {
		String transition;

		while (!parserState.queue.isEmpty()) {
			transition = oracle.predict();
			// Executes the predicted transition. If not possible, then shift
			// COMPLETE THE CODE HERE
			String deprel = "";
			String[] tont = transition.split("\\.");
			if (tont.length > 1) {
				deprel = tont[1];
			}
			if (transition.toLowerCase().contains("la")) {
				if (parserState.canLeftArc()) {
					parserState.doLeftArc(deprel);
					parserState.addTransition("la");
				} else {
					parserState.doShift();
					parserState.addTransition("sh");
				}
			} else if (transition.toLowerCase().contains("ra")) {
				parserState.doRightArc(deprel);
				parserState.addTransition("ra");
			} else if (transition.toLowerCase().contains("re")) {
				if (parserState.canReduce()) {
					parserState.doReduce();
					parserState.addTransition("re");
				} else {
					parserState.doShift();
					parserState.addTransition("sh");
				}
			} else {
				parserState.doShift();
				parserState.addTransition("sh");
			}
		}

		// We empty the stack. When words have no head, we set it to root
		while (parserState.stack.size() > 1) {
			if (parserState.canReduce()) {
				parserState.doReduce();
			} else {
				parserState.doReduceAndSetRoot();
			}
		}
		// In the end, we build the word list
		// All the words must have a head
		// otherwise, the graph would not be connected.
		// Only the root in the stack should have no head
		for (int i = 0; i < parserState.wordList.size(); i++) {
			boolean hasHead = false;
			for (int j = 0; j < parserState.depGraph.size(); j++) {
				if (parserState.wordList.get(i).getId() == parserState.depGraph.get(j).getId()) {
					parserState.wordList.get(i).setHead(parserState.depGraph.get(j).getHead());
					hasHead = true;
					break;
				}
			}
			if (!hasHead) {
				parserState.wordList.get(i).setHead(0);
			}
		}

		boolean printGraph = true;
		if (printGraph) {
			
			for (int i = 0; i < parserState.wordList.size(); i++) {
				System.out.print(parserState.wordList.get(i).getForm() + " ");
			}
			System.out.println();
			for (int i = 0; i < parserState.transitionList.size(); i++) {
				System.out.print(parserState.transitionList.get(i) + " ");
			}
			System.out.println();
			for (int i = 0; i < parserState.depGraph.size(); i++) {
				System.out.print(parserState.depGraph.get(i).getId() + ", " + parserState.depGraph.get(i).getHead() + " "
						+ parserState.depGraph.get(i).getForm() + " ");
			}
			System.out.println();
		}

		parserState.wordList.remove(0);
		return parserState.wordList;
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		final WekaGlue wg = new WekaGlue(Constants.ARFF_MODEL, Constants.ARFF_FILE);

		final List<List<Word>> sentenceList = new CONLLCorpus().loadFile(new File(Constants.TEST_SET));
		final List<List<Word>> parsedList = new ArrayList<List<Word>>();

		System.out.println("Parsing the sentences...");
		for (final List<Word> sentence : sentenceList) {
			final ParserState parserState = new ParserState(sentence);
			parsedList.add(new Parser(parserState, new Guide6(wg, parserState)).parse());
		}
		new CONLLCorpus().saveFile(new File(Constants.TEST_SET_PARSED), parsedList);
	}
}
