package main.java.org.example.AIModel;

import java.io.*;
import java.util.*;

public class DataLoader {
    private static final int EXPECTED_COLUMNS = 12;
    private List<User> users = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

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
                    User user = new User(
                            Double.parseDouble(values[0]),
                            Double.parseDouble(values[1]),
                            Preprocessor.mapHomeOwnership(values[2]),
                            Preprocessor.parseEmpLength(values[3]),
                            Preprocessor.mapLoanIntent(values[4]),
                            Preprocessor.mapLoanGrade(values[5]),
                            Double.parseDouble(values[9]),
                            values[10].equals("Y") ? 1 : 0,
                            Double.parseDouble(values[11])
                    );

                    Loan loan = new Loan(
                            Double.parseDouble(values[6]),
                            Double.parseDouble(values[7]),
                            Double.parseDouble(values[8])
                    );

                    users.add(user);
                    loans.add(loan);

                } catch (NumberFormatException e) {
                    continue;
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
}
