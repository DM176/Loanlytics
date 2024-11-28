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
        // Normalize features: xTrain will have the features for training
        double[][] xTrain = Preprocessor.normalizeFeatures(dataLoader.getUsers(), dataLoader.getLoans());
        // Extract targets: yTrain will have the loan statuses for training
        double[] yTrain = Preprocessor.extractTargets(dataLoader.getLoans());

        // Train model
        Model model = Model.getInstance();
        model.train(xTrain, yTrain, 1000, 0.01);

        System.out.println("Model trained successfully. Ready for predictions!");

        PredictionService predictionService = PredictionService.getInstance();
        LoanPredictionRequest request = new LoanPredictionRequest();

        // Set input features for a loan prediction request
        request.setAge(40);  // Normalized if necessary
        request.setIncome(5000000);  // Normalize if necessary
        request.setHomeOwnership("OWN");  // Encoded to 1
        request.setEmpLength(5);  // Encoded if necessary
        request.setLoanIntent("BUSINESS");  // Encoded to 1
        request.setLoanGrade("A");  // Encoded to 0
        request.setPercentIncome(60);  // This might be normalized
        request.setDefaultOnFile("N");  // Encoded to 0
        request.setLoanStatus(0);
        // Make the prediction
        double prediction = predictionService.makePrediction(request);
        System.out.println("Prediction: " + prediction);

        // Use a different threshold if needed
        System.out.println("Prediction: " + (prediction >0.18 ? "Loan Approved" : "Loan Denied"));
    }
}
