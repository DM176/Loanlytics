package main.java.org.example.AIModel;

import java.util.Random;

public class Model {

    private static Model instance;

    private static final int INPUT_SIZE = 8; // Number of input features
    private static final int HIDDEN_SIZE = 5; // Number of neurons in hidden layer
    private static final int OUTPUT_SIZE = 1; // Single output (binary prediction)

    private double[][] W1 = new double[INPUT_SIZE][HIDDEN_SIZE]; // Weights from input to hidden
    private double[] b1 = new double[HIDDEN_SIZE]; // Bias for hidden layer
    private double[][] W2 = new double[HIDDEN_SIZE][OUTPUT_SIZE]; // Weights from hidden to output
    private double[] b2 = new double[OUTPUT_SIZE]; // Bias for output layer

    private Model() {
        initializeWeights();
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    private void initializeWeights() {
        Random rand = new Random();

        // Initialize W1 and W2 with random small values
        for (int i = 0; i < INPUT_SIZE; i++) {
            for (int j = 0; j < HIDDEN_SIZE; j++) {
                W1[i][j] = rand.nextDouble() * 0.01;  // Small random values
            }
        }

        for (int i = 0; i < HIDDEN_SIZE; i++) {
            for (int j = 0; j < OUTPUT_SIZE; j++) {
                W2[i][j] = rand.nextDouble() * 0.01;  // Small random values
            }
        }

        // Initialize biases
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

    // Forward propagation
    public double[] predict(double[] input) {
        // Forward pass from input to hidden
        double[] hiddenLayer = new double[HIDDEN_SIZE];
        for (int i = 0; i < HIDDEN_SIZE; i++) {
            hiddenLayer[i] = 0;
            for (int j = 0; j < INPUT_SIZE; j++) {
                hiddenLayer[i] += input[j] * W1[j][i];
            }
            hiddenLayer[i] += b1[i];
            hiddenLayer[i] = sigmoid(hiddenLayer[i]);
        }

        // Forward pass from hidden to output
        double[] outputLayer = new double[OUTPUT_SIZE];
        for (int i = 0; i < OUTPUT_SIZE; i++) {
            outputLayer[i] = 0;
            for (int j = 0; j < HIDDEN_SIZE; j++) {
                outputLayer[i] += hiddenLayer[j] * W2[j][i];
            }
            outputLayer[i] += b2[i];
            outputLayer[i] = sigmoid(outputLayer[i]);  // Sigmoid for binary classification
        }

        return outputLayer;
    }

    // Train the model with gradient descent
    public void train(double[][] xTrain, double[][] yTrain, int epochs, double learningRate) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            // Loop through each training example
            for (int i = 0; i < xTrain.length; i++) {
                double[] input = xTrain[i];
                double[] trueOutput = yTrain[i];

                // Forward pass
                double[] hiddenLayer = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    hiddenLayer[j] = 0;
                    for (int k = 0; k < INPUT_SIZE; k++) {
                        hiddenLayer[j] += input[k] * W1[k][j];
                    }
                    hiddenLayer[j] += b1[j];
                    hiddenLayer[j] = sigmoid(hiddenLayer[j]);
                }

                double[] outputLayer = new double[OUTPUT_SIZE];
                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    outputLayer[j] = 0;
                    for (int k = 0; k < HIDDEN_SIZE; k++) {
                        outputLayer[j] += hiddenLayer[k] * W2[k][j];
                    }
                    outputLayer[j] += b2[j];
                    outputLayer[j] = sigmoid(outputLayer[j]);
                }

                // Compute loss (mean squared error)
                double[] outputError = new double[OUTPUT_SIZE];
                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    outputError[j] = trueOutput[j] - outputLayer[j];
                }

                // Backpropagation
                double[] outputDelta = new double[OUTPUT_SIZE];
                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    outputDelta[j] = outputError[j] * sigmoidDerivative(outputLayer[j]);
                }

                double[] hiddenError = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    hiddenError[j] = 0;
                    for (int k = 0; k < OUTPUT_SIZE; k++) {
                        hiddenError[j] += outputDelta[k] * W2[j][k];
                    }
                }

                double[] hiddenDelta = new double[HIDDEN_SIZE];
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    hiddenDelta[j] = hiddenError[j] * sigmoidDerivative(hiddenLayer[j]);
                }

                // Update weights and biases using gradient descent
                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    for (int k = 0; k < OUTPUT_SIZE; k++) {
                        W2[j][k] += learningRate * outputDelta[k] * hiddenLayer[j];
                    }
                }

                for (int j = 0; j < OUTPUT_SIZE; j++) {
                    b2[j] += learningRate * outputDelta[j];
                }

                for (int j = 0; j < INPUT_SIZE; j++) {
                    for (int k = 0; k < HIDDEN_SIZE; k++) {
                        W1[j][k] += learningRate * hiddenDelta[k] * input[j];
                    }
                }

                for (int j = 0; j < HIDDEN_SIZE; j++) {
                    b1[j] += learningRate * hiddenDelta[j];
                }
            }

            // Print the loss every 100 epochs
            if (epoch % 100 == 0) {
                System.out.println("Epoch " + epoch + " completed");
            }
        }
    }
}
