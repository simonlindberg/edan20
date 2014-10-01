package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import parser.ReferenceParser;
import parser._2Labeled;
import format.ARFFData;
import format.CONLLCorpus;
import format.Constants;
import format.Word;
import guide.Features;

public final class Main {

	public static void main(String[] args) throws IOException {
        File trainingSet = new File(Constants.TRAINING_SET);
        File arff = new File(Constants.ARFF_FILE);
        CONLLCorpus trainingCorpus = new CONLLCorpus();
        ARFFData arffData = new ARFFData();

        List<List<Word>> sentenceList;


        List<String> transitionList = new ArrayList<String>();
        List<Features> featureList = new ArrayList<Features>();

        if (trainingSet.exists()) {
            System.out.println("Loading file...");
        } else {
            System.out.println("File does not exist, exiting...");
            return;
        }

        sentenceList = trainingCorpus.loadFile(trainingSet);

        System.out.println("Parsing the sentences...");
        for (int i = 0; i < sentenceList.size(); i++) {
            final ReferenceParser refParser = new _2Labeled(sentenceList.get(i));
            // Failed parses should be discarded.
            final boolean parseSuccess = refParser.parse();
            refParser.printActions();
            if (parseSuccess) {
                featureList.addAll(refParser.getFeatureList());
                transitionList.addAll(refParser.getActionList());
            }
        }
        arffData.saveFeatures(arff, featureList, transitionList);
    }


}
