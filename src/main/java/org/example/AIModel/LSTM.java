package main.java.org.example.AIModel;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class LSTM {

    // Define the structure for User and Loan classes
    static class User {
        private double age;
        private double income;
        private double homeOwnership;
        private double empLength;
        private double loanIntent;
        private double loanGrade;
        private double percentIncome;
        private double defaultOnFile;
        private double creditHistoryLength;

        public User(double age, double income, double homeOwnership, double empLength, double loanIntent,
                    double loanGrade, double percentIncome, double defaultOnFile, double creditHistoryLength) {
            this.age = age;
            this.income = income;
            this.homeOwnership = homeOwnership;
            this.empLength = empLength;
            this.loanIntent = loanIntent;
            this.loanGrade = loanGrade;
            this.percentIncome = percentIncome;
            this.defaultOnFile = defaultOnFile;
            this.creditHistoryLength = creditHistoryLength;
        }

        // Getters
        public double getAge() { return age; }
        public double getIncome() { return income; }
        public double getHomeOwnership() { return homeOwnership; }
        public double getEmpLength() { return empLength; }
        public double getLoanIntent() { return loanIntent; }
        public double getLoanGrade() { return loanGrade; }
        public double getPercentIncome() { return percentIncome; }
        public double getDefaultOnFile() { return defaultOnFile; }
        public double getCreditHistoryLength() { return creditHistoryLength; }
    }

    static class Loan {
        private double loanAmount;
        private double interestRate;
        private double loanStatus;

        public Loan(double loanAmount, double interestRate, double loanStatus) {
            this.loanAmount = loanAmount;
            this.interestRate = interestRate;
            this.loanStatus = loanStatus;
        }

        // Getters
        public double getLoanAmount() { return loanAmount; }
        public double getInterestRate() { return interestRate; }
        public double getLoanStatus() { return loanStatus; }
    }

    // Constants for training
    public static final int EXPECTED_COLUMNS = 12;
    public static final int INPUT_SIZE = 8;  // Number of features per user
    public static final int HIDDEN_SIZE = 5;  // Number of neurons in the hidden layer
    public static final int OUTPUT_SIZE = 1;  // Binary classification (loan status)

    // Weights for the simple neural network
    double[][] W1 = new double[INPUT_SIZE][HIDDEN_SIZE];
    double[] b1 = new double[HIDDEN_SIZE];
    double[][] W2 = new double[HIDDEN_SIZE][OUTPUT_SIZE];
    double[] b2 = new double[OUTPUT_SIZE];

    public static void main(String[] args)  {
        LSTM lstm = new LSTM();
        List<User> users = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();

        String filePath = "C:\\Users\\HP\\Documents\\Projects\\LMS\\Loanlytics\\src\\main\\resources\\credit_risk_dataset.csv";

        // Load and preprocess the data (same as before)
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Read header line (skip if not needed)

            while ((line = br.readLine()) != null) {
                line = line.trim();
                String[] values = line.split(",");

                if (values.length != EXPECTED_COLUMNS) {
                    continue;
                }

                try {
                    User user = new User(
                            Double.parseDouble(values[0]), // person_age
                            Double.parseDouble(values[1]), // person_income
                            mapHomeOwnership(values[2]),  // person_home_ownership
                            parseEmpLength(values[3]),    // person_emp_length
                            mapLoanIntent(values[4]),     // loan_intent
                            mapLoanGrade(values[5]),      // loan_grade
                            Double.parseDouble(values[9]), // loan_percent_income
                            values[10].equals("Y") ? 1 : 0, // cb_person_default_on_file
                            Double.parseDouble(values[11])  // cb_person_cred_hist_length
                    );

                    Loan loan = new Loan(
                            Double.parseDouble(values[6]), // loan_amnt
                            Double.parseDouble(values[7]), // loan_int_rate
                            Double.parseDouble(values[8])  // loan_status (target)
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

        if (users.isEmpty() || loans.isEmpty()) {
            System.err.println("No valid data available. Please check the CSV file.");
            return;
        }

        // Normalize features and prepare target labels (for training)
        double[][] xTrain = normalize(users);
        double[][] yTrain = extractLoanStatus(loans);

        // Initialize the model weights and biases
        lstm.initializeWeights();

        // Train the model (using gradient descent)
        lstm.train(xTrain, yTrain, 1000, 0.01);

        // Now, let's take input for testing
        Scanner scanner = new Scanner(System.in);

// Request user input for each feature
        System.out.println("Please enter the details of the loan applicant to assess loan approval:");

// Ask for age with an explanation
        System.out.print("1. Applicant's Age (in years): ");
        double age = scanner.nextDouble();

// Ask for income with an explanation
        System.out.print("2. Applicant's Annual Income (in dollars): ");
        double income = scanner.nextDouble();

// Ask for home ownership status with more clarity
        System.out.print("3. Home Ownership Status (1 = Own, 2 = Mortgage, 3 = Rent): ");
        double homeOwnership = scanner.nextDouble();

// Ask for years of employment with an explanation
        System.out.print("4. Years of Employment: ");
        double empLength = scanner.nextDouble();

// Ask for loan intent with clear options
        System.out.print("5. Loan Purpose (1 = Personal, 2 = Education, 3 = Medical, 4 = Venture): ");
        double loanIntent = scanner.nextDouble();

// Ask for loan grade with simple options
        System.out.print("6. Loan Grade (1 = A, 2 = B, 3 = C, 4 = D): ");
        double loanGrade = scanner.nextDouble();

// Ask for the percent of income spent on loan with clarification
        System.out.print("7. Percentage of Income to be Spent on Loan Repayment (in %): ");
        double percentIncome = scanner.nextDouble();

// Ask for default on file status with a clear prompt
        System.out.print("8. Is there a default on previous loan? (1=Y for Yes, 2 = N for No): ");
        double defaultOnFile = scanner.nextDouble();

// Ask for credit history length with a clear instruction
        System.out.print("9. Credit History Length (in years): ");
        double creditHistoryLength = scanner.nextDouble();


        // Pass the input variables to the testModel function
        testModel(lstm, age, income, homeOwnership, empLength, loanIntent, loanGrade, percentIncome, defaultOnFile, creditHistoryLength);

        scanner.close();
    }

    // Function to test the model with real user input
    private static void testModel(LSTM lstm, double age, double income, double homeOwnership, double empLength,
                                  double loanIntent, double loanGrade, double percentIncome, double defaultOnFile, double creditHistoryLength) {

        // Create a new user based on input
        User userInput = new User(age, income, homeOwnership, empLength, loanIntent, loanGrade, percentIncome, defaultOnFile, creditHistoryLength);

        // Normalize input features (same as training data)
        double[] normalizedInput = new double[INPUT_SIZE];
        normalizedInput[0] = userInput.getAge();
        normalizedInput[1] = userInput.getIncome();
        normalizedInput[2] = userInput.getHomeOwnership();
        normalizedInput[3] = userInput.getEmpLength();
        normalizedInput[4] = userInput.getLoanIntent();
        normalizedInput[5] = userInput.getLoanGrade();
        normalizedInput[6] = userInput.getPercentIncome();
        normalizedInput[7] = userInput.getDefaultOnFile();

        // Forward pass: Using the trained model to predict the loan approval
        // Layer 1 (Hidden Layer)
        double[] z1 = new double[HIDDEN_SIZE];
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            z1[j] = 0;
            for (int k = 0; k < INPUT_SIZE; k++) {
                z1[j] += normalizedInput[k] * lstm.W1[k][j];
            }
            z1[j] += lstm.b1[j];
        }
        double[] a1 = lstm.sigmoid(z1);

        // Layer 2 (Output Layer)
        double[] z2 = new double[OUTPUT_SIZE];
        for (int j = 0; j < OUTPUT_SIZE; j++) {
            z2[j] = 0;
            for (int k = 0; k < HIDDEN_SIZE; k++) {
                z2[j] += a1[k] * lstm.W2[k][j];
            }
            z2[j] += lstm.b2[j];
        }
        double[] output = lstm.sigmoid(z2);

        // Display the result: Loan approval (1=approved, 0=denied)
        if (output[0] > 0.5) {
            System.out.println("Loan Approved!");
        } else {
            System.out.println("Loan Denied!");
        }
    }
    // Normalize input features (for simplicity, assume we normalize manually)
    private static double[][] normalize(List<User> users) {
        double[][] data = new double[users.size()][INPUT_SIZE];
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            data[i][0] = user.getAge();
            data[i][1] = user.getIncome();
            data[i][2] = user.getHomeOwnership();
            data[i][3] = user.getEmpLength();
            data[i][4] = user.getLoanIntent();
            data[i][5] = user.getLoanGrade();
            data[i][6] = user.getPercentIncome();
            data[i][7] = user.getDefaultOnFile();
        }
        return data;
    }

    // Extract loan status (target labels)
    private static double[][] extractLoanStatus(List<Loan> loans) {
        double[][] targets = new double[loans.size()][OUTPUT_SIZE];
        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            targets[i][0] = loan.getLoanStatus();
        }
        return targets;
    }

    // Map categorical variables to numeric values
    private static double mapHomeOwnership(String homeOwnership) {
        switch (homeOwnership) {
            case "OWN": return 1.0;
            case "MORTGAGE": return 2.0;
            case "RENT": return 3.0;
            default: return 0.0;
        }
    }

    private static double parseEmpLength(String empLength) {
        try {
            return Double.parseDouble(empLength);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private static double mapLoanIntent(String loanIntent) {
        switch (loanIntent) {
            case "PERSONAL": return 1.0;
            case "EDUCATION": return 2.0;
            case "MEDICAL": return 3.0;
            case "VENTURE": return 4.0;
            default: return 0.0;
        }
    }

    private static double mapLoanGrade(String loanGrade) {
        switch (loanGrade) {
            case "A": return 1.0;
            case "B": return 2.0;
            case "C": return 3.0;
            case "D": return 4.0;
            default: return 0.0;
        }
    }

    // Initialize weights and biases randomly
    private void initializeWeights() {
        Random rand = new Random();

        // Random initialization of weights and biases
        for (int i = 0; i < INPUT_SIZE; i++) {
            for (int j = 0; j < HIDDEN_SIZE; j++) {
                W1[i][j] = rand.nextDouble() * 0.1 - 0.05;  // Random small values
            }
        }

        for (int j = 0; j < HIDDEN_SIZE; j++) {
            b1[j] = rand.nextDouble() * 0.1 - 0.05;
        }

        for (int j = 0; j < HIDDEN_SIZE; j++) {
            for (int k = 0; k < OUTPUT_SIZE; k++) {
                W2[j][k] = rand.nextDouble() * 0.1 - 0.05;
            }
        }

        for (int k = 0; k < OUTPUT_SIZE; k++) {
            b2[k] = rand.nextDouble() * 0.1 - 0.05;
        }
    }

    private void train(double[][] xTrain, double[][] yTrain, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double totalLoss = 0; // Declare totalLoss to accumulate loss for this epoch
            for (int i = 0; i < xTrain.length; i++) {
                // Forward pass
                double[] input = xTrain[i];
                double[] target = yTrain[i];

                // Layer 1 (Hidden Layer)
                double[] z1 = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    z1[j] = 0;
                    for (int k = 0; k < INPUT_SIZE; k++) {
                        z1[j] += input[k] * W1[k][j];
                    }
                    z1[j] += b1[j];
                }
                double[] a1 = sigmoid(z1);

                // Layer 2 (Output Layer)
                double[] z2 = new double[OUTPUT_SIZE];
                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    z2[j] = 0;
                    for (int k = 0; k < HIDDEN_SIZE; k++) {
                        z2[j] += a1[k] * W2[k][j];
                    }
                    z2[j] += b2[j];
                }
                double[] output = sigmoid(z2);

                // Calculate loss (mean squared error)
                double loss = 0;
                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    loss += Math.pow(output[j] - target[j], 2);
                }
                totalLoss += loss; // Add this loss to total loss for the epoch

                // Backpropagation
                double[] outputError = new double[OUTPUT_SIZE];
                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    outputError[j] = (output[j] - target[j]) * sigmoidDerivative(z2[j]);
                }

                double[] hiddenError = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    hiddenError[j] = 0;
                    for (int k = 0; k < OUTPUT_SIZE; k++) {
                        hiddenError[j] += outputError[k] * W2[j][k];
                    }
                    hiddenError[j] *= sigmoidDerivative(z1[j]);
                }

                // Update weights and biases
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    for (int k = 0; k < INPUT_SIZE; k++) {
                        W1[k][j] -= learningRate * input[k] * hiddenError[j];
                    }
                    b1[j] -= learningRate * hiddenError[j];
                }

                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    for (int k = 0; k < HIDDEN_SIZE; k++) {
                        W2[k][j] -= learningRate * a1[k] * outputError[j];
                    }
                    b2[j] -= learningRate * outputError[j];
                }
            }

            // Print progress every 100 epochs
            if (epoch % 100 == 0) {
                System.out.println("Epoch: " + epoch + " Loss: " + totalLoss / xTrain.length); // Average loss for the epoch
            }
        }
    }


    // Sigmoid activation function
    private double[] sigmoid(double[] z) {
        double[] result = new double[z.length];
        for (int i = 0; i < z.length; i++) {
            result[i] = 1.0 / (1.0 + Math.exp(-z[i]));
        }
        return result;
    }

    // Sigmoid derivative function
    private double sigmoidDerivative(double z) {
        double sig = 1.0 / (1.0 + Math.exp(-z));
        return sig * (1 - sig);
    }
}
