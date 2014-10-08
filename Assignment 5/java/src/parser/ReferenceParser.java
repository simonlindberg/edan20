package parser;

/**
 *
 * @author Pierre Nugues
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import format.Word;
import guide.Features;

public abstract class ReferenceParser {

	Stack<Word> stack;
	List<Word> queue;
	List<Word> wordList;
	List<String> transitionList;
	List<Features> featureList;
	List<Word> depGraph;

	public ReferenceParser(List<Word> wordList) {
		stack = new Stack<Word>();
		queue = new ArrayList<Word>(wordList);
		this.wordList = wordList;
		depGraph = new ArrayList<Word>();
	}

	protected void doLeftArc() {
		addLeft();
		depGraph.add(stack.pop());
	}

	protected void doRightArc() {
		addRight();
		depGraph.add(queue.get(0));
		stack.push(queue.remove(0));
	}

	protected void doReduce() {
		addReduce();
		stack.pop();
	}

	protected void doShift() {
		addShift();
		stack.push(queue.remove(0));
	}

	protected void addShift() {
		transitionList.add("sh");
	}

	protected void addReduce() {
		transitionList.add("re");
	}

	protected abstract void addLeft();

	protected abstract void addRight();

	protected boolean oracleLeftArc() {
		boolean oracleLeftArc = false;
		if (!stack.empty()) {
			if (stack.peek().getHead() == queue.get(0).getId()) {
				// System.out.println(queue.get(0).getForm() + "  --> " +
				// stack.peek().getForm());
				oracleLeftArc = true;
			}
		}
		// Constraint: top of the stack has no head in the graph
		// This means that it is not already in the graph.
		// In reference parsing, this should always be satisfied
		// We double-check it
		if (oracleLeftArc) {
			oracleLeftArc = canLeftArc();
		}
		return oracleLeftArc;
	}

	protected boolean oracleRightArc() {
		boolean oracleRightArc = false;
		if (!stack.empty()) {
			if (stack.peek().getId() == queue.get(0).getHead()) {
				// System.out.println(stack.peek().getForm() + "  --> " +
				// queue.get(0).getForm());
				oracleRightArc = true;
			}
		}
		return oracleRightArc;
	}

	protected boolean oracleReduce() {
		boolean oracleReduce = false;

		for (int i = 0; i < stack.size(); i++) {
			if (queue.get(0).getHead() == stack.get(i).getId()) {
				oracleReduce = true;
				break;
			}
			if (stack.get(i).getHead() == queue.get(0).getId()) {
				oracleReduce = true;
				break;
			}
		}

		// Constraint: top of the stack has a head somewhere is the graph
		// This garantees that the graph is connected
		// Here this means that it is already in the graph
		// In reference parsing, this should always be satisfied for projective
		// graphs
		if (oracleReduce) {
			oracleReduce = canReduce();
		}
		return oracleReduce;
	}

	protected boolean canReduce() {
		boolean canReduce = false;
		if (stack.empty()) {
			return false;
		}

		// Constraint: top of the stack has a head somewhere in the graph
		// This guarantees that the graph is connected
		// Here this means that it is already in the graph
		// In reference parsing, this should always be satisfied for projective
		// graphs
		for (final Word word : depGraph) {
			if (word.getId() == stack.peek().getId()) {
				canReduce = true;
				break;
			}
		}
		return canReduce;
	}

	protected boolean canLeftArc() {
		boolean canLeftArc = true;
		if (stack.empty()) {
			return false;
		}

		// Constraint: top of the stack has no head in the graph
		// This means that it is not already in the graph.
		// In reference parsing, this should always be satisfied
		for (final Word word : depGraph) {
			if (word.getId() == stack.peek().getId()) {
				canLeftArc = false;
				break;
			}
		}
		return canLeftArc;
	}

	protected abstract Features extractFeatures();

	// A sanity check about the action sequence.
	protected boolean equalGraphs() {
		boolean equals = false;
		List<Word> temp = new ArrayList<Word>(wordList);

		wordList.remove(0); // we remove the root word

		for (int i = 0; i < wordList.size(); i++) {
			equals = false;
			for (int j = 0; j < depGraph.size(); j++) {
				if (wordList.get(i).getId() == depGraph.get(j).getId()) {
					if (wordList.get(i).getHead() == depGraph.get(j).getHead()) {
						equals = true;
					}
					break;
				}
			}
			if (equals == false) {
				break;
			}
		}
		wordList = temp;
		return equals;
	}

	public List<String> getActionList() {
		return transitionList;
	}

	public List<Features> getFeatureList() {
		return featureList;
	}

	public List<Word> getQueue() {
		return queue;
	}

	public Stack<Word> getStack() {
		return stack;
	}

	// Will return true if the hand Hand-annotated graph and reference-parsed
	// graph are equal.
	public boolean parse() {
		transitionList = new ArrayList<String>();
		featureList = new ArrayList<Features>();

		while (!queue.isEmpty()) {
			featureList.add(extractFeatures());
			final Word q = queue.get(0);
			if (!stack.isEmpty()) {
				final Word s = stack.peek();
				if (s.getId() == q.getHead()) {
					doRightArc();
				} else if (s.getHead() == q.getId()) {
					doLeftArc();
				} else {
					boolean reduced = false;
					for (final Word w : stack) {
						if (w.getHead() == q.getId() || q.getHead() == w.getId()) {
							doReduce();
							reduced = true;
							break;
						}
					}
					if (!reduced) {
						doShift();
					}
				}
			} else {
				doShift();
			}
		}
		emptyStack(transitionList, featureList);

//			System.out.println("#words: " + wordList.size() + " \tStack: " + stack.size() + "\tTransition count: " + transitionList.size());

		// Final test to check if the hand-annotated graph and reference-parsed
		// graph are equal.
		// If they are different, this is probably due to nonprojective links
		return equalGraphs();
	}

	public void printActions() {
		for (int i = 0; i < wordList.size(); i++) {
			System.out.print(wordList.get(i).getForm() + " ");
		}
		System.out.println();
		for (int i = 0; i < transitionList.size(); i++) {
			System.out.print(transitionList.get(i) + " ");
		}
		System.out.println();
	}

	// emptyStack should only leave the root in the stack if the graph is
	// projective and well-formed
	public int emptyStack(List<String> transitionList, List<Features> featureList) {
		while (stack.size() > 1) {
			featureList.add(extractFeatures());
			if (canReduce()) {
				doReduce();
			} else {
				return -1;
			}
		}
		return 0;
	}

	protected String safeStackPos(final int i) {
		return stack.size() > i ? stack.elementAt(stack.size() - i - 1).getPostag() : "nil";
	}

	protected String safeQueuePos(final int i) {
		return queue.size() > i ? queue.get(i).getPostag() : "nil";
	}
	protected String safeWordPos(){
		if (!stack.empty()){
			final int i = wordList.indexOf(stack.peek());
			if (i > 0) {
				return wordList.get(i).getPostag();
			}
		}
		return "nil";
	}
}
