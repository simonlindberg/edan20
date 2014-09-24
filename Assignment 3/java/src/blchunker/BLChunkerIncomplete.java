package blchunker;

import java.util.*;

import format.Corpus;
import mlchunker.Features;
import format.WordCoNLL2000;
import format.Constants;

import java.io.IOException;

/**
 *
 * @author pierre
 */
public class BLChunkerIncomplete extends Corpus {

    Map<String, Integer> posCnt;
    Map<String, Integer> chunkCnt;
    Map<Features, Integer> associationFreq;
    Map<String, String> baslineAssociations;
    Set<String> uniquePosTags;
    Set<String> uniqueChunkTags;

    // This method counts the associations (POS, Chunk). It uses a hashmap.
    public void countAssoc() {
        associationFreq = new HashMap<Features, Integer>();
		for (final Features f : featureList) {
			if (associationFreq.containsKey(f)) {
				associationFreq.put(f, associationFreq.get(f) + 1);
			} else {
				associationFreq.put(f, 1);
			}
		}
    }

    public void printAssocCounts() {
        Set<Features> feats = new HashSet<Features>(associationFreq.keySet());
        for (Features feat : feats) {
            System.out.println(feat.getPpos() + "\t" + feat.getChunk() + "\t" + associationFreq.get(feat));
        }
    }

    public void printBestAssoc() {
        Set<String> pposs = new HashSet<String>(baslineAssociations.keySet());
        for (String ppos : pposs) {
            System.out.println(ppos + "\t\t" + baslineAssociations.get(ppos));
        }
    }

    public void getUniqueTags() {
        uniquePosTags = new HashSet<String>();
        uniqueChunkTags = new HashSet<String>();
        for (Features feat : featureList) {
            uniquePosTags.add(feat.getPpos());
            uniqueChunkTags.add(feat.getChunk());
        }
    }

    // This method selects the best (most frequent) association: POS --> Chunk
    public void selectBestAssoc() {
        baslineAssociations = new HashMap<String, String>();
        final Map<String, Integer> points = new HashMap<String, Integer>();
        
        for (final Features f : associationFreq.keySet()) {
        	if (points.containsKey(f.getPpos())) {
        		if (points.get(f.getPpos()) < associationFreq.get(f)) {
        			baslineAssociations.put(f.getPpos(), f.getChunk());
        			points.put(f.getPpos(), associationFreq.get(f));
        		}
        	} else { // First time!
        		baslineAssociations.put(f.getPpos(), f.getChunk());
        		points.put(f.getPpos(), associationFreq.get(f));
        	}
        }
    }

    // This method tags the words with their chunk.
    public void tag() {
        for (List<WordCoNLL2000> sent : sentenceList) {
            for (WordCoNLL2000 word : sent) {
                word.setChunk(baslineAssociations.get(word.getPpos()));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BLChunkerIncomplete chunker = new BLChunkerIncomplete();
        chunker.load(Constants.TRAINING_SET_2000);
        chunker.extractBaselineFeatures();
        chunker.getUniqueTags();
        chunker.countAssoc(); // This method counts the associations (POS, Chunk). It uses a hashmap.
        //chunker.printAssocCounts();
        chunker.selectBestAssoc(); // This method selects the best (most frequent) association: POS--Chunk
        chunker.printBestAssoc();
        chunker.load(Constants.TEST_SET_2000);
        chunker.tag(); // This method tags the words with their chunk.
        chunker.save(Constants.TEST_SET_PREDICTED_2000);
        chunker.printFeatures();
    }
}
