package format;

import java.io.*;
import java.util.*;

import mlchunker.MLChunker;

/**
 *
 * @author pierre
 */
// The class to load a CoNLL 2000 corpus.
public class ReaderWriterCoNLL2000 {

    private List<List<WordCoNLL2000>> sentenceList;

    public List<List<WordCoNLL2000>> loadFile(File file) throws IOException {
        sentenceList = new ArrayList<List<WordCoNLL2000>>();
        List<WordCoNLL2000> sentence = new ArrayList<WordCoNLL2000>();

        WordCoNLL2000 curWord;

        int sentenceCount = 0;
        int lineCount = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            lineCount++;
            curWord = makeWord(line);
            if (curWord != null) { // A word
                sentence.add(curWord);
            } else { // An empty line denoting a new sentence
                sentenceList.add(sentence);
                sentence = new ArrayList<WordCoNLL2000>();
                sentenceCount++;
            }
            if ((lineCount % 50000) == 0) {
                System.out.println("Read: " + lineCount + " lines, " + sentenceCount + " sentences.");
            }
        }
        reader.close();
        System.out.println("Read: " + lineCount + " lines, " + sentenceCount + " sentences.");
        return sentenceList;
    }

    // Splits the input line of the CoNLL 2000 file into three fields: word, part of speech, and chunk tag.
    private WordCoNLL2000 makeWord(String line) {
        String[] wordD = line.split(" ");
        WordCoNLL2000 word = null;
        // Both the training and test sets have the three fields.
        if (wordD.length == 3) {
            word = new WordCoNLL2000(wordD[0], wordD[1], wordD[2]);
        }
        return word;
    }

    public void printFile(List<List<WordCoNLL2000>> sentenceList) {
        for (List<WordCoNLL2000> sentence : sentenceList) {
            for (WordCoNLL2000 word : sentence) {
                System.out.print(word.getForm() + " ");
            }
            System.out.println("");
        }
    }

    public void saveFile(File file, List<List<WordCoNLL2000>> sentenceList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));

        for (int i = 0; i < sentenceList.size(); i++) {
            for (int j = 0; j < (sentenceList.get(i)).size(); j++) {
                writer.write((sentenceList.get(i)).get(j).getForm() + " ");
                writer.write((sentenceList.get(i)).get(j).getPpos() + " ");
                writer.write((sentenceList.get(i)).get(j).getChunk() + "\n");
            }
            if (i != sentenceList.size()) {
                writer.write("\n");
            }
        }
        writer.close();
    }

//    // This method saves the training data in the ARFF format. It adds the header.
//    public void saveARFF(File file, List<List<WordCoNLL2000>> sentenceList) throws IOException {
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
//        writer.write(Constants.ARFF_HEADER);
//        for (List<WordCoNLL2000> sentence : sentenceList) {
//            for (WordCoNLL2000 word : sentence) {
//                if (word.getPpos().equals(",")) {
//                    writer.write("\",\", ");
//                } else if (word.getPpos().equals("''")) {
//                    writer.write("\"''\", ");
//                } else {
//                    writer.write(word.getPpos() + ", ");
//                }
//                writer.write(word.getChunk() + "\n");
//            }
//        }
//        writer.close();
//    }
    
    // This method saves the training data in the ARFF format. It adds the header.
    public void saveARFF(File file, List<List<WordCoNLL2000>> sentenceList) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
        writer.write(Constants.ARFF_HEADER);
        
        for (int sen = 0; sen < sentenceList.size(); sen++) {
        	final List<WordCoNLL2000> sent = sentenceList.get(sen);
        	
			String posMin2 = "BOS", posMin1 = "BOS", pos0 = "EOS", pos1 = "EOS", pos2 = "EOS";
			String chunkMin2 = "BOS", chunkMin1 = "BOS";

			if (sent.size() > 0) {
				pos0 = sent.get(0).getPpos();
				if (sent.size() > 1) {
					pos1 = sent.get(1).getPpos();
					if (sent.size() > 2) {
						pos2 = sent.get(2).getPpos();
					}
				}
			}
			
			for (int i = 0; i < sent.size(); i++) {
				final WordCoNLL2000 currentWord = sent.get(i);

				write(new String[] {
						posMin2, posMin1, pos0, pos1, pos2,
						chunkMin1, chunkMin2
						}, currentWord.getChunk(), writer);
				
				chunkMin2 = chunkMin1;
				chunkMin1 = currentWord.getChunk();

				posMin2 = posMin1;
				posMin1 = pos0;
				pos0 = pos1;
				pos1 = pos2;

				if (i + 3 < sent.size()) {
					final WordCoNLL2000 futureWord = sent.get(i + 3);
					pos2 = futureWord.getPpos();
				} else {
					pos2 = "EOS";
				}
			}
		}
        writer.close();
    }

    private void write(String[] featureVector, String chunk, BufferedWriter writer) throws IOException {
    	for (String str : featureVector) {
            if (str.equals(",")) {
                writer.write("\",\", ");
            } else if (str.equals("''")) {
                writer.write("\"''\", ");
            } else {
                writer.write(str + ", ");
            }
    	}
    	writer.write(chunk + "\n");
	}

	List<WordCoNLL2000> getSentence(int index) {
        return sentenceList.get(index);
    }

    List<List<WordCoNLL2000>> getSentenceList() {
        return sentenceList;
    }

    int getSize() {
        return sentenceList.size();
    }
}
