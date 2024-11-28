package main.java.org.example.AIModel;

import java.io.*;
import java.util.*;

public class DataLoader {
    private static final int EXPECTED_COLUMNS = 12;
    private List<User> users = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();
    private List<Integer> loanStatuses = new ArrayList<>();

    public void loadData() {
        String filePath = "C:\\Users\\HP\\Documents\\Projects\\LMS\\Loanlytics\\src\\main\\resources\\credit_risk_dataset.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] values = line.split(",");

                if (values.length != EXPECTED_COLUMNS) {
                    continue;
                }

                try {
                    // Create User object
                    User user = new User(
                            Double.parseDouble(values[0]), // Age
                            Double.parseDouble(values[1]), // Income
                            Preprocessor.mapHomeOwnership(values[2]), // Home Ownership encoded
                            Preprocessor.parseEmpLength(values[3]), // Employment Length encoded
                            Preprocessor.mapLoanIntent(values[4]), // Loan Intent encoded
                            Preprocessor.mapLoanGrade(values[5]), // Loan Grade encoded
                            Double.parseDouble(values[9]), // Percent Income
                            values[10].equals("Y") ? 1 : 0, // Default on file (1 for Y, 0 for N)
                            Double.parseDouble(values[11]) // Percent Income
                    );

                    // Create Loan object, passing loanStatus
                    Loan loan = new Loan(
                            Double.parseDouble(values[6]), // Loan Amount
                            Double.parseDouble(values[7]), // Interest Rate
                            Double.parseDouble(values[8]), // Loan Term
                            values[10].equals("Y") ? 1 : 0  // Loan status (1 for approved, 0 for denied)
                    );

                    // Add User, Loan, and LoanStatus to lists
                    users.add(user);
                    loans.add(loan);
                    loanStatuses.add(values[10].equals("Y") ? 1 : 0); // Loan status (1 for approved, 0 for denied)

                } catch (NumberFormatException e) {
                    continue; // Skip rows with invalid data
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasData() {
        return !users.isEmpty() && !loans.isEmpty();
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<Integer> getLoanStatuses() {
        return loanStatuses;
    }
}
