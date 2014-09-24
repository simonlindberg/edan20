package format;

/**
 *
 * @author pierre
 */
public class Constants {

    public final static String CHUNKER_HOME = "/Users/Simon/git/edan20/Assignment 3/";
    public final static String TRAINING_SET_2000 = CHUNKER_HOME + "train.txt";
    public final static String TEST_SET_2000 = CHUNKER_HOME + "test.txt";
    public final static String TEST_SET_PREDICTED_2000 = CHUNKER_HOME + "test_pred.txt";
    public final static String ARFF_DATA = Constants.CHUNKER_HOME + "chunker.arff";
    public final static String ARFF_MODEL = Constants.CHUNKER_HOME + "chunker.model";
    public final static String ARFF_HEADER = "@relation chunk\n\n"
    		+ "@attribute posMin2 {#, $, \"''\", (, ), \",\", ., :, CC, CD, DT, EX, FW, IN, JJ, JJR, JJS, MD, NN, NNP, NNPS, NNS, PDT, POS, PRP, PRP$, RB, RBR, RBS, RP, SYM, TO, UH, VB, VBD, VBG, VBN, VBP, VBZ, WDT, WP, WP$, WRB, ``, BOS, EOS}\n"
    		+ "@attribute posMin1 {#, $, \"''\", (, ), \",\", ., :, CC, CD, DT, EX, FW, IN, JJ, JJR, JJS, MD, NN, NNP, NNPS, NNS, PDT, POS, PRP, PRP$, RB, RBR, RBS, RP, SYM, TO, UH, VB, VBD, VBG, VBN, VBP, VBZ, WDT, WP, WP$, WRB, ``, BOS, EOS}\n"
    		+ "@attribute pos0 {#, $, \"''\", (, ), \",\", ., :, CC, CD, DT, EX, FW, IN, JJ, JJR, JJS, MD, NN, NNP, NNPS, NNS, PDT, POS, PRP, PRP$, RB, RBR, RBS, RP, SYM, TO, UH, VB, VBD, VBG, VBN, VBP, VBZ, WDT, WP, WP$, WRB, ``, BOS, EOS}\n"
    		+ "@attribute pos1 {#, $, \"''\", (, ), \",\", ., :, CC, CD, DT, EX, FW, IN, JJ, JJR, JJS, MD, NN, NNP, NNPS, NNS, PDT, POS, PRP, PRP$, RB, RBR, RBS, RP, SYM, TO, UH, VB, VBD, VBG, VBN, VBP, VBZ, WDT, WP, WP$, WRB, ``, BOS, EOS}\n"
    		+ "@attribute pos2 {#, $, \"''\", (, ), \",\", ., :, CC, CD, DT, EX, FW, IN, JJ, JJR, JJS, MD, NN, NNP, NNPS, NNS, PDT, POS, PRP, PRP$, RB, RBR, RBS, RP, SYM, TO, UH, VB, VBD, VBG, VBN, VBP, VBZ, WDT, WP, WP$, WRB, ``, BOS, EOS}\n"
    		+ "@attribute chunkMin2 {B-ADJP, B-ADVP, B-CONJP, B-INTJ, B-LST, B-NP, B-PP, B-PRT, B-SBAR, B-UCP, B-VP, I-ADJP, I-ADVP, I-CONJP, I-INTJ, I-NP, I-PP, I-PRT, I-SBAR, I-UCP, I-VP, O, BOS}\n"
    		+ "@attribute chunkMin1 {B-ADJP, B-ADVP, B-CONJP, B-INTJ, B-LST, B-NP, B-PP, B-PRT, B-SBAR, B-UCP, B-VP, I-ADJP, I-ADVP, I-CONJP, I-INTJ, I-NP, I-PP, I-PRT, I-SBAR, I-UCP, I-VP, O, BOS}\n"
    		+ "@attribute chunk {B-ADJP, B-ADVP, B-CONJP, B-INTJ, B-LST, B-NP, B-PP, B-PRT, B-SBAR, B-UCP, B-VP, I-ADJP, I-ADVP, I-CONJP, I-INTJ, I-NP, I-PP, I-PRT, I-SBAR, I-UCP, I-VP, O}\n\n"
            + "@data\n";
}
