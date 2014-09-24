package mlchunker;

import java.io.*;
import java.util.*;

import wekaglue.WekaGlue;
import format.Constants;
import format.ReaderWriterCoNLL2000;
import format.WordCoNLL2000;
import format.Corpus;

/**
 * 
 * @author pierre
 */
public class MLChunker extends Corpus {

	private static final String BOS = "BOS";
	private static final String EOS = "EOS";
	WekaGlue wekaGlue;

	public MLChunker() {
	}

	public void writeARFF(String file) throws IOException {
		ReaderWriterCoNLL2000 reader = new ReaderWriterCoNLL2000();
		reader.saveARFF(new File(file), sentenceList);
	}

//	public void tag() {
//		wekaGlue = new WekaGlue();
//		wekaGlue.create(Constants.ARFF_MODEL, Constants.ARFF_DATA);
//		for (List<WordCoNLL2000> sent : sentenceList) {
//			for (WordCoNLL2000 word : sent) {
//				word.setChunk(wekaGlue.classify(new String[] { word.getPpos() }));
//			}
//		}
//	}

	public void tag() {
		wekaGlue = new WekaGlue();
		wekaGlue.create(Constants.ARFF_MODEL, Constants.ARFF_DATA);

		for (int sen = 0; sen < sentenceList.size(); sen++) {
			final List<WordCoNLL2000> sent = sentenceList.get(sen);

			String posMin2 = BOS, posMin1 = BOS, pos0 = EOS, pos1 = EOS, pos2 = EOS;
			String chunkMin2 = BOS, chunkMin1 = BOS;
			
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

				 currentWord.setChunk(wekaGlue.classify(new String[]{
						 posMin2, posMin1, pos0, pos1, pos2,
						 chunkMin1, chunkMin2
				 }));

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
					pos2 = EOS;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		MLChunker chunker = new MLChunker();
		if (args.length < 1) {
			System.out.println("Usage: java mlchunker.MLChunker (-train|-tag)");
			System.exit(0);
		}
		if (args[0].equals("-train")) {
			chunker.load(Constants.TRAINING_SET_2000);
			chunker.extractBaselineFeatures();
			chunker.writeARFF(Constants.ARFF_DATA);
		} else if (args[0].equals("-tag")) {
			chunker.load(Constants.TEST_SET_2000);
			chunker.tag();
			chunker.save(Constants.TEST_SET_PREDICTED_2000);
		} else {
			System.out.println("Usage: java mlchunker.MLChunker (-train|-tag)");
		}
	}
}
