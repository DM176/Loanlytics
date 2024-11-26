package main.java.org.example;

import main.java.org.example.AIModel.DataLoader;
import main.java.org.example.AIModel.Model;
import main.java.org.example.AIModel.Preprocessor;

public class Launch {
	private static final String DATABASE = "jdbc:mysql://localhost/lms_data"; //corresponding server address and database name should be assigned
	private static final String USERNAME = "test";	//username for login to the server should be assigned
	private static final String PASSWORD = "123qweasd!@#"; //password for login to the server should be assigned

	public static void main(String[] args) {
		loadAImodel();
		loadData();
		start(args);
	}

	private static void loadAImodel(){
		DataLoader dataLoader = new DataLoader();
		dataLoader.loadData();

		if (!dataLoader.hasData()) {
			System.err.println("No valid data available. Exiting application.");
			return;
		}

		double[][] xTrain = Preprocessor.normalizeFeatures(dataLoader.getUsers());
		double[][] yTrain = Preprocessor.extractTargets(dataLoader.getLoans());

		Model model = Model.getInstance();
		model.train(xTrain, yTrain, 1000, 0.01);

		System.out.println("Model trained and application is ready to serve predictions.");
	}

	private static void loadData() {
		DataStore.loadData(DATABASE, USERNAME, PASSWORD);
	}
	
	private static void start(String[] args) {
		View.launchApplication(args);
	}
}
