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
