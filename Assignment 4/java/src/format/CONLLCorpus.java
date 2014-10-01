package format;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import store.Pair;
import store.Store;
import store.Triple;

/**
 * 
 * @author Pierre Nugues
 */
// The class to load a CONLL corpus 2006 and 2007 and store it into a list.
public class CONLLCorpus {

	private static final Word root = new Word("0", "ROOT", "ROOT", "ROOT", "ROOT", "ROOT", "0", "ROOT", "0", "ROOT");

	private static final String ENCODING = "UTF-8";

	public static List<List<Word>> loadFile(File file) throws IOException {
		final List<List<Word>> sentenceList = new ArrayList<List<Word>>();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING));

		List<Word> sentence = new ArrayList<Word>();
		sentence.add(root);

		int sentenceCount = 0;
		int lineCount = 0;

		String line = null;
		while ((line = reader.readLine()) != null) {
			final Word curWord = makeWord(line);
			if (curWord != null) { // A word
				sentence.add(curWord);
			} else { // An empty line denoting a new sentence
				sentenceList.add(sentence);
				sentence = new ArrayList<Word>();
				sentence.add(root);
				sentenceCount++;
			}
			lineCount++;
			if ((lineCount % 10000) == 0) {
				System.out.println("Read: " + lineCount + " lines, " + sentenceCount + " sentences.");
			}
		}
		reader.close();
		System.out.println("Read: " + lineCount + " lines, " + sentenceCount + " sentences.");
		return sentenceList;
	}

	private static Word makeWord(String line) {
		String[] wordD = line.split("\t");
		Word word = null;
		// Test set
		if (wordD.length == 6) {
			word = new Word(wordD[0], wordD[1], wordD[2], wordD[3], wordD[4], wordD[5]);
		}
		// Training set
		if (wordD.length == 10) {
			word = new Word(wordD[0], wordD[1], wordD[2], wordD[3], wordD[4], wordD[5], wordD[6], wordD[7], wordD[8], wordD[9]);
		}
		return word;
	}

	public static void printSentenceList(List<List<Word>> sentenceList) throws UnsupportedEncodingException {
		for (final List<Word> sent : sentenceList) {
			printSentence(sent);
		}
	}

	private static void printSentence(List<Word> sent) {
		for (final Word word : sent) {
			System.out.print(word.getForm() + " ");
		}
		System.out.println("");
	}

	public static void saveFile(File file, List<List<Word>> sentenceList) throws IOException {
		final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), ENCODING));

		for (int i = 0; i < sentenceList.size(); i++) {
			for (int j = 0; j < (sentenceList.get(i)).size(); j++) {
				writer.write((sentenceList.get(i)).get(j).getId() + "\t");
				writer.write((sentenceList.get(i)).get(j).getForm() + "\t");
				writer.write((sentenceList.get(i)).get(j).getLemma() + "\t");
				writer.write((sentenceList.get(i)).get(j).getCpostag() + "\t");
				writer.write((sentenceList.get(i)).get(j).getPostag() + "\t");
				writer.write((sentenceList.get(i)).get(j).getFeats() + "\t");
				writer.write((sentenceList.get(i)).get(j).getHead() + "\t");
				writer.write((sentenceList.get(i)).get(j).getDeprel() + "\t");
				writer.write((sentenceList.get(i)).get(j).getPhead() + "\t");
				writer.write((sentenceList.get(i)).get(j).getPdeprel() + "\n");
			}
			if (i != sentenceList.size()) {
				writer.write("\n");
			}
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		final List<List<Word>> sentenceList = loadFile(Constants.TRAINING_SET);
		final Store pairs = getPairs(sentenceList);
		final Store triples = getTriples(sentenceList);

		System.out.println();
		System.out.println("Pairs: " + pairs.count());
		System.out.println("Triples: " + triples.count());
		System.out.println();
		System.out.println(triples.getFiveBest());
	}

	private static Store getPairs(List<List<Word>> sentenceList) {
		final Store store = new Store();
		for (final List<Word> sent : sentenceList) {
			for (final Word word : sent) {
				if (word.getDeprel().equals(Constants.SUBJECT)) {
					store.store(new Pair(word.getForm(), sent.get(word.getHead()).getForm()));
				}
			}
		}
		return store;
	}

	private static Store getTriples(List<List<Word>> sentenceList) {
		final Store store = new Store();
		for (final List<Word> sent : sentenceList) {
			final Map<Integer, Word> map = new HashMap<>();
			for (final Word word : sent) {
				if (word.getDeprel().equals(Constants.SUBJECT) || word.getDeprel().equals(Constants.OBJECT)) {
					if (!map.containsKey(word.getHead())) {
						map.put(word.getHead(), word);
					} else {
						final Word other = map.get(word.getHead());
						if (!other.getDeprel().equals(word.getDeprel())) {
							store.store(
									new Triple(
										(word.getDeprel().equals(Constants.SUBJECT) ? word : other).getForm(),
										sent.get(word.getHead()).getForm(),
										(word.getDeprel().equals(Constants.OBJECT) ? word : other).getForm())
									);
						}
					}
				}
			}
		}
		return store;
	}
}
