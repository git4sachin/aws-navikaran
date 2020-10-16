package com.capgemini.navikaran;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.capgemini.utils.CommonUtilities;
import com.capgemini.utils.ResourceFileLoader;

import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.doccat.FeatureGenerator;
import opennlp.tools.doccat.NGramFeatureGenerator;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.model.ModelUtil;
/**
 * Represents the document categorization
 * @author asrathod
 * @since 1.0
 */
public class CategorizeDocuments {
	/**
	 * Prepares the document categorization training model. Uses the {@link BagOfWordsFeatureGenerator} and {@link NGramFeatureGenerator} for creating the training model
	 * @return {@link DoccatModel} the trained model
	 * @throws Exception
	 */
	public static DoccatModel prepareModel() throws Exception {
		/**
		 * Read human understandable data & train a model
		 */
		// Read file with classifications samples of sentences.
		File fileToTrainData = CommonUtilities.getFileCharsConvertToLowerCase(
				ResourceFileLoader.getResourceFile("reviewCommentCategorizer.txt").getAbsolutePath());
		fileToTrainData = CommonUtilities.removeSpecialCharsFromFile(fileToTrainData);
		fileToTrainData = CommonUtilities.removeStopWordsFromFile(fileToTrainData);

		// Read file with classifications samples of sentences.
		InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(fileToTrainData);
		ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
		ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		// Use CUT_OFF as zero since we will use very few samples.
		// BagOfWordsFeatureGenerator will treat each word as a feature. Since we have
		// few samples, each feature/word will have small counts, so it won't meet high
		// cutoff.
		TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
		params.put(TrainingParameters.ITERATIONS_PARAM, 25 + "");
		params.put(TrainingParameters.CUTOFF_PARAM, 0);
		DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator(),
				new NGramFeatureGenerator(1, 1), new NGramFeatureGenerator(2, 3) });

		// Train a model with classifications from above file.
		DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);

		// Serialize model to some file so that next time we don't have to again train a
		// model. Next time We can just load this file directly into model.
		model.serialize(new File(ResourceFileLoader.getResourceFile("output\\reviewCommentCategorizer.bin").getAbsolutePath()));
		return model;

	}
	
	
/**
 * Returns the categories created using the training model
 * @param model
 * {@link DoccatModel} the trained model
 * @param statement
 *  the sample statement to be categorize
 * @return the categories
 */
	public static String getCaregories(DoccatModel model, String statement) {
		String category = null;
		/**
		 * Load model from serialized file & lets categorize reviews.
		 */
		// Load serialized trained model
		try {

			// Initialize document categorizer tool
			DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

			// Get the probabilities of all outcome i.e. positive & negative
			double[] probabilitiesOfOutcomes = myCategorizer.categorize(getTokens(statement));

			// Get name of category which had high probability
			category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return category;

	}

	/**
	 * Tokenizes sentence into tokens.
	 * 
	 * @param sentence
	 * @return String [] of tokens
	 */
	private static String[] getTokens(String sentence) {

		// Use model that was created in earlier tokenizer tutorial
		try (InputStream modelIn = new FileInputStream(ResourceFileLoader.getResourceFile("output\\tokenizerdata.bin").getAbsolutePath())) {
			TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));
			String[] tokens = myCategorizer.tokenize(sentence);
			return tokens;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
