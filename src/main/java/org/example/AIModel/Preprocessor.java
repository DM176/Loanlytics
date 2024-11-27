package main.java.org.example.AIModel;

import java.util.List;

public class Preprocessor {
    public static double[][] normalizeFeatures(List<User> users) {
        double[][] features = new double[users.size()][8];
        for (int i = 0; i < users.size(); i++) {
            features[i] = users.get(i).getFeatures();
        }
        return features;
    }

    public static double[][] extractTargets(List<Loan> loans) {
        double[][] targets = new double[loans.size()][1];
        for (int i = 0; i < loans.size(); i++) {
            targets[i][0] = loans.get(i).getLoanStatus();
        }
        return targets;
    }

    public static double mapHomeOwnership(String homeOwnership) {
        switch (homeOwnership) {
            case "OWN": return 1.0;
            case "MORTGAGE": return 2.0;
            case "RENT": return 3.0;
            default: return 0.0;
        }
    }

    public static double parseEmpLength(String empLength) {
        try {
            return Double.parseDouble(empLength);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static double mapLoanIntent(String loanIntent) {
        switch (loanIntent) {
            case "PERSONAL": return 1.0;
            case "EDUCATION": return 2.0;
            case "MEDICAL": return 3.0;
            case "VENTURE": return 4.0;
            default: return 0.0;
        }
    }

    public static double mapLoanGrade(String loanGrade) {
        switch (loanGrade) {
            case "A": return 1.0;
            case "B": return 2.0;
            case "C": return 3.0;
            case "D": return 4.0;
            default: return 0.0;
        }
    }
}
