package format;

import java.io.File;

/**
 *
 * @author pierre
 */
public class Constants {

    public final static String HOME = "/Users/Simon/git/edan20/Assignment 4/";
    public final static File TRAINING_SET 	= new File(HOME + "swedish_talbanken05_train.conll");
    public final static File TEST_SET 		= new File(HOME + "swedish_talbanken05_test_blind.conll");
    public final static File TEST_SET_PARSED= new File(HOME + "result_output.conll");
    public final static File ARFF_FILE 		= new File(HOME + "simple4.arff");
    public final static File ARFF_MODEL 	= new File(HOME + "simple4.model");
    
    public final static String SUBJECT = "SS";
    public final static String OBJECT = "OO";

    //    public final static String UTB_HOME = "/Users/pierre/Documents/Cours/EDAN20/corpus/universal_treebanks_v1.0/";
//    public final static String TRAINING_SET_FR = UTB_HOME + "fr/fr-universal-train.conll";
//    public final static String TRAINING_SET_ES = UTB_HOME + "es/es-universal-train.conll";
    

    //public final static String SUBJECT = "nsubj";
    //public final static String OBJECT = "dobj";
}
