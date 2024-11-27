package main.java.org.example.AIModel;

public class Main {
    public static void main(String[] args) {
        // Load data
        DataLoader dataLoader = new DataLoader();
        dataLoader.loadData();

        if (!dataLoader.hasData()) {
            System.err.println("No valid data available. Exiting application.");
            return;
        }

        // Preprocess data
        Preprocessor preprocessor = new Preprocessor();
        double[][] xTrain = Preprocessor.normalizeFeatures(dataLoader.getUsers());
        double[][] yTrain = Preprocessor.extractTargets(dataLoader.getLoans());

        // Train model
        Model model = Model.getInstance();
        model.train(xTrain, yTrain, 1000, 0.01);

        System.out.println("Model trained successfully. Ready for predictions!");

        PredictionService predictionService = PredictionService.getInstance();
        LoanPredictionRequest request = new LoanPredictionRequest();

        // Normalized and favorable values
        request.setAge(30);  // Normalized if necessary
        request.setIncome(50000000);  // Normalize if necessary
        request.setHomeOwnership("OWN");  // Encoded to 1
        request.setEmpLength(10);  // Encoded if necessary
        request.setLoanIntent("BUSINESS");  // Encoded to 1
        request.setLoanGrade("A");  // Encoded to 0
        request.setPercentIncome(70);  // This might be normalized
        request.setDefaultOnFile("N");  // Encoded to 0

        // Make the prediction
        double[] prediction = predictionService.makePrediction(request);

        // Use a different threshold if needed
        System.out.println("Prediction: " + (prediction[0] > 0.45 ? "Loan Approved" : "Loan Denied"));



    }
}
