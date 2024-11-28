package main.java.org.example.AIModel;

public class PredictionService {
    private static PredictionService instance;
    private final Model model;

    private PredictionService() {
        this.model = Model.getInstance();
    }

    public static PredictionService getInstance() {
        if (instance == null) {
            instance = new PredictionService();
        }
        return instance;
    }

    public double makePrediction(LoanPredictionRequest request) {
            // Convert categorical values to numeric using a simple encoding
        double homeOwnership;
        switch (request.getHomeOwnership().toUpperCase()) {
            case "OWN":
                homeOwnership = 1.0;
                break;
            case "RENT":
                homeOwnership = 0.0;
                break;
            default:
                homeOwnership = 0.5; // UNKNOWN
                break;
        }

        double loanIntent;
        switch (request.getLoanIntent().toUpperCase()) {
            case "EDUCATION":
                loanIntent = 1.0;
                break;
            case "MEDICAL":
                loanIntent = 2.0;
                break;
            case "HOME_IMPROVEMENT":
                loanIntent = 3.0;
                break;
            case "PERSONAL":
                loanIntent = 4.0;
                break;
            case "DEBT_CONSOLIDATION":
                loanIntent = 5.0;
                break;
            default:
                loanIntent = 0.0; // UNKNOWN
                break;
        }

        double loanGrade;
        switch (request.getLoanGrade().toUpperCase()) {
            case "A":
                loanGrade = 1.0;
                break;
            case "B":
                loanGrade = 2.0;
                break;
            case "C":
                loanGrade = 3.0;
                break;
            case "D":
                loanGrade = 4.0;
                break;
            case "E":
                loanGrade = 5.0;
                break;
            default:
                loanGrade = 0.0; // UNKNOWN
                break;
        }

        double defaultOnFile = "Y".equalsIgnoreCase(request.getDefaultOnFile()) ? 1.0 : 0.0;

        // Create feature array
        double [] features = new double[]{
                request.getAge(),
                request.getIncome(),
                homeOwnership,
                request.getEmpLength(),
                loanIntent,
                loanGrade,
                request.getPercentIncome(),
                defaultOnFile,
                request.getCreditHistoryLength(),
                request.getLoanStatus()
        };

        return model.predict(features);
    }
}
