package main.java.org.example.AIModel;

import java.util.List;

public class Preprocessor {

    // Method to normalize the features from both User and Loan data
    public static double[][] normalizeFeatures(List<User> users, List<Loan> loans) {
        int numRows = users.size();
        double[][] features = new double[numRows][10]; // Adjust size based on the number of features

        for (int i = 0; i < numRows; i++) {
            User user = users.get(i);
            Loan loan = loans.get(i);

            // Add user features
            features[i][0] = user.getAge();         // Assuming getAge() is a method in User
            features[i][1] = user.getIncome();      // Assuming getIncome() is a method in User
            features[i][2] = user.getHomeOwnership(); // Assuming getHomeOwnership() is a method in User
            features[i][3] = user.getEmpLength();    // Assuming getEmpLength() is a method in User

            // Add loan features
            features[i][4] = loan.getLoanAmount();  // getLoanAmount() from Loan class
            features[i][5] = loan.getLoanTerm();    // getLoanTerm() from Loan class
            features[i][6] = loan.getInterestRate(); // getInterestRate() from Loan class
        }

        return features;
    }

    // Method to extract loan statuses (0 or 1 for approval)
    public static double[] extractTargets(List<Loan> loans) {
        int size = loans.size();
        double[] targets = new double[size];

        for (int i = 0; i < size; i++) {
            targets[i] = loans.get(i).getLoanStatus();  // Loan status 0 or 1 for approved/rejected
        }

        return targets;
    }

    // Methods for feature mappings (if needed)
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
            return 0.0;  // Default value if the conversion fails
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
