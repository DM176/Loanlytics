package main.java.org.example.AIModel;

import java.util.Random;

public class Model {

    private static Model instance;

    // Constants defining the size of input, hidden, and output layers
    private static final int INPUT_SIZE = 10; // Number of input features (User + Loan features)
    private static final int HIDDEN_SIZE = 5; // Number of neurons in the hidden layer
    private static final int OUTPUT_SIZE = 1; // Single output (binary prediction)

    // Weight matrices and bias vectors
    private double[][] W1 = new double[INPUT_SIZE][HIDDEN_SIZE]; // Weights from input to hidden layer
    private double[] b1 = new double[HIDDEN_SIZE]; // Bias for hidden layer
    private double[][] W2 = new double[HIDDEN_SIZE][OUTPUT_SIZE]; // Weights from hidden to output layer
    private double[] b2 = new double[OUTPUT_SIZE]; // Bias for output layer

    private Model() {
        initializeWeights();
    }

    // Singleton instance
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    // Initialize weights and biases to small random values
    private void initializeWeights() {
        Random rand = new Random();

        // Initialize W1 (input to hidden) with small random values
        for (int i = 0; i < INPUT_SIZE; i++) {
            for (int j = 0; j < HIDDEN_SIZE; j++) {
                W1[i][j] = rand.nextDouble() * 0.01;
            }
        }

        // Initialize W2 (hidden to output) with small random values
        for (int i = 0; i < HIDDEN_SIZE; i++) {
            for (int j = 0; j < OUTPUT_SIZE; j++) {
                W2[i][j] = rand.nextDouble() * 0.01;
            }
        }

        // Initialize biases to zero
        for (int i = 0; i < HIDDEN_SIZE; i++) {
            b1[i] = 0.0;
        }

        for (int i = 0; i < OUTPUT_SIZE; i++) {
            b2[i] = 0.0;
        }
    }

    // Sigmoid activation function
    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    // Sigmoid derivative for backpropagation
    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    // Forward pass function for a layer
    private double[] forwardPass(double[] input, double[][] weights, double[] biases, int outputSize, int inputSize) {
        double[] output = new double[outputSize];
        for (int i = 0; i < outputSize; i++) {
            output[i] = 0;
            for (int j = 0; j < inputSize; j++) {
                output[i] += input[j] * weights[j][i];
            }
            output[i] += biases[i];
            output[i] = sigmoid(output[i]); // Apply the sigmoid activation
        }
        return output;
    }

    // Predict output for a given input (forward pass)
    public double predict(double[] input) {
        // Forward pass from input to hidden
        double[] hiddenLayer = forwardPass(input, W1, b1, HIDDEN_SIZE, INPUT_SIZE);

        // Forward pass from hidden to output
        double[] outputLayer = forwardPass(hiddenLayer, W2, b2, OUTPUT_SIZE, HIDDEN_SIZE);

        // Return the first (and only) element of the output layer (single value between 0 and 1)
        return outputLayer[0];
    }
    public double predictLoanGrade(double[] input) {
        // Extract variables from the input array
        double age = input[0];
        double income = input[1];
        double homeOwnership = input[2];
        double empLength = input[3];
        double loanIntent = input[4];
        double loanGrade = input[5];
        double percentIncome = input[6];
        double defaultOnFile = input[7];
        double creditHistoryLength = input[8];
        double loanStatus = input[9];

        // Initialize the score
        double score = 0.0;

        // Income: Higher income contributes less to the score, making it harder to max out
        if (income > 150000) {
            score += 0.3;
        } else if (income > 80000) {
            score += 0.2;
        } else if (income > 50000) {
            score += 0.1;
        } else {
            score += 0.05;
        }

        // Homeownership: Owning a home is still a strong positive factor, but less impactful
        if (homeOwnership == 1.0) {
            score += 0.2;
        } else if (homeOwnership == 0.5) { // Unknown
            score += 0.05;
        }

        // Employment length: Longer employment provides smaller increments
        if (empLength >= 15) {
            score += 0.2;
        } else if (empLength >= 10) {
            score += 0.15;
        } else if (empLength >= 5) {
            score += 0.1;
        } else {
            score += 0.05;
        }

        // Loan intent: Favorable intents contribute less to the score
        if (loanIntent == 1.0) { // Education
            score += 0.1;
        } else if (loanIntent == 2.0 || loanIntent == 3.0) { // Medical or Home Improvement
            score += 0.05;
        }

        // Loan grade: Favorable grades have smaller increments
        if (loanGrade == 1.0) { // Grade A
            score += 0.2;
        } else if (loanGrade == 2.0) { // Grade B
            score += 0.1;
        } else if (loanGrade == 3.0) { // Grade C
            score += 0.05;
        }

        // Percent income: Tighter thresholds make it harder to score high
        if (percentIncome <= 15) {
            score += 0.2;
        } else if (percentIncome <= 30) {
            score += 0.1;
        } else if (percentIncome <= 50) {
            score += 0.05;
        }

        // Default on file: More severe penalty for a default
        if (defaultOnFile == 1.0) {
            score -= 0.5;
        }

        // Credit history length: Stricter scoring for longer histories
        if (creditHistoryLength > 15) {
            score += 0.15;
        } else if (creditHistoryLength > 10) {
            score += 0.1;
        } else if (creditHistoryLength > 5) {
            score += 0.05;
        }

        // Normalize score to return a value between 0 and 1
        return Math.max(0.0, Math.min(1.0, score));
    }


    // Train the model with gradient descent
    public void train(double[][] xTrain, double[] yTrain, int epochs, double learningRate) {
        // Ensure that each input has the expected number of features
        for (int i = 0; i < xTrain.length; i++) {
            double[] input = xTrain[i];

            if (input.length != INPUT_SIZE) {
                throw new IllegalArgumentException("Input size mismatch at index " + i + ". Expected size: " + INPUT_SIZE);
            }
        }

        // Start training process
        for (int epoch = 0; epoch < epochs; epoch++) {
            // Loop through each training example
            for (int i = 0; i < xTrain.length; i++) {
                double[] input = xTrain[i];  // The input features for the current sample
                double trueOutput = yTrain[i];  // The true target (loan status: 0 or 1)

                // Forward pass
                double[] hiddenLayer = forwardPass(input, W1, b1, HIDDEN_SIZE, INPUT_SIZE);
                double[] outputLayer = forwardPass(hiddenLayer, W2, b2, OUTPUT_SIZE, HIDDEN_SIZE);

                // Compute loss (mean squared error)
                double outputError = trueOutput - outputLayer[0];

                // Backpropagation
                double outputDelta = outputError * sigmoidDerivative(outputLayer[0]);

                double[] hiddenError = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    hiddenError[j] = outputDelta * W2[j][0];
                }

                double[] hiddenDelta = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    hiddenDelta[j] = hiddenError[j] * sigmoidDerivative(hiddenLayer[j]);
                }

                // Update weights and biases using gradient descent
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    for (int k = 0; k < OUTPUT_SIZE; k++) {
                        W2[j][k] += learningRate * outputDelta * hiddenLayer[j];
                    }
                }

                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    b2[j] += learningRate * outputDelta;
                }

                // Update W1 (input layer to hidden layer weights)
                for (int j = 0; j < INPUT_SIZE; j++) {
                    for (int k = 0; k < HIDDEN_SIZE; k++) {
                        W1[j][k] += learningRate * hiddenDelta[k] * input[j];
                    }
                }

                // Update b1 (hidden layer biases)
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    b1[j] += learningRate * hiddenDelta[j];
                }
            }

            // Optionally: print the loss for every few epochs
            if (epoch % 100 == 0) {
                double loss = 0.0;
                for (int i = 0; i < xTrain.length; i++) {
                    double[] prediction = forwardPass(xTrain[i], W1, b1, HIDDEN_SIZE, INPUT_SIZE);
                    loss += Math.pow(prediction[0] - yTrain[i], 2);
                }
                loss /= xTrain.length;
                System.out.println("Epoch " + epoch + ", Loss: " + loss);
            }
        }
    }
}
