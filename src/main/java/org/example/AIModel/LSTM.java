package main.java.org.example.AIModel;

public class LSTM {
    // Constants for the LSTM model
    private static final int INPUT_SIZE = 3; // Input size (number of features)
    private static final int HIDDEN_SIZE = 4; // Hidden layer size
    private static final int OUTPUT_SIZE = 1; // Output size (e.g., 1 for binary classification)
    private static final int EPOCHS = 100; // Number of training epochs
    private static final double LEARNING_RATE = 0.01; // Learning rate

    // Weights and biases for the LSTM model
    private double[][] Wi, Wf, Wo, Wc; // Weight matrices for input, forget, output, and cell states
    private double[] bi, bf, bo, bc; // Biases for input, forget, output, and cell states

    // Constructor to initialize the weights and biases
    public LSTM() {
        Wi = new double[HIDDEN_SIZE][INPUT_SIZE]; // Input-to-hidden weights
        Wf = new double[HIDDEN_SIZE][INPUT_SIZE]; // Forget-to-hidden weights
        Wo = new double[HIDDEN_SIZE][INPUT_SIZE]; // Output-to-hidden weights
        Wc = new double[HIDDEN_SIZE][INPUT_SIZE]; // Candidate-to-hidden weights

        bi = new double[HIDDEN_SIZE]; // Input bias
        bf = new double[HIDDEN_SIZE]; // Forget bias
        bo = new double[HIDDEN_SIZE]; // Output bias
        bc = new double[HIDDEN_SIZE]; // Candidate bias

        // Initialize weights and biases with small random values
        initializeWeights();
    }

    // Initialize weights with small random values
    private void initializeWeights() {
        for (int i = 0; i < HIDDEN_SIZE; i++) {
            for (int j = 0; j < INPUT_SIZE; j++) {
                Wi[i][j] = Math.random() * 0.01;
                Wf[i][j] = Math.random() * 0.01;
                Wo[i][j] = Math.random() * 0.01;
                Wc[i][j] = Math.random() * 0.01;
            }
            bi[i] = 0.0;
            bf[i] = 0.0;
            bo[i] = 0.0;
            bc[i] = 0.0;
        }
    }

    // Sigmoid activation function
    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    // Tanh activation function
    private double tanh(double x) {
        return Math.tanh(x);
    }

    // Derivative of the sigmoid function
    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    // Derivative of the tanh function
    private double tanhDerivative(double x) {
        return 1 - x * x;
    }

    // Forward pass to compute the output and intermediate variables
    public void forward(double[] x, double[] output, double[] o, double[] f, double[] i_, double[] c, double[] c_) {
        double[] z = new double[HIDDEN_SIZE]; // Weighted sum for each gate

        // Compute the gates and cell states
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            z[j] = 0;
            for (int k = 0; k < INPUT_SIZE; k++) {
                z[j] += x[k] * Wi[j][k]; // Input gate calculation
                z[j] += x[k] * Wf[j][k]; // Forget gate calculation
                z[j] += x[k] * Wo[j][k]; // Output gate calculation
                z[j] += x[k] * Wc[j][k]; // Candidate cell state calculation
            }
            z[j] += bi[j]; // Add bias term
        }

        // Compute the activation for each gate
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            i_[j] = sigmoid(z[j]); // Input gate activation
            f[j] = sigmoid(z[j]); // Forget gate activation
            o[j] = sigmoid(z[j]); // Output gate activation
            c_[j] = tanh(z[j]); // Candidate cell state activation
            c[j] = f[j] * c[j] + i_[j] * c_[j]; // Update cell state
            output[0] = o[j] * tanh(c[j]); // Compute the output (single scalar output)
        }
    }

    // Backpropagation to update the weights and biases
    public void backpropagate(double[] x, double[] y, double[] output, double[] o, double[] f, double[] i_, double[] c, double[] c_, double[] dOutput, double[] dC) {
        // Compute the error gradients (Mean Squared Error)
        double[] error = new double[OUTPUT_SIZE];
        error[0] = y[0] - output[0]; // Error for scalar output

        // Compute gradients for the output layer
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            dOutput[j] = error[0] * sigmoidDerivative(output[0]); // Derivative of the output
        }

        // Compute gradients for cell state
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            dC[j] = dOutput[j] * tanhDerivative(c[j]); // Derivative of cell state
        }

        // Update weights and biases using gradient descent
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            for (int k = 0; k < INPUT_SIZE; k++) {
                Wi[j][k] -= LEARNING_RATE * dOutput[j] * x[k]; // Update input gate weights
                Wf[j][k] -= LEARNING_RATE * dOutput[j] * x[k]; // Update forget gate weights
                Wo[j][k] -= LEARNING_RATE * dOutput[j] * x[k]; // Update output gate weights
                Wc[j][k] -= LEARNING_RATE * dOutput[j] * x[k]; // Update candidate cell state weights
            }
            bi[j] -= LEARNING_RATE * dOutput[j]; // Update biases for input gate
            bf[j] -= LEARNING_RATE * dOutput[j]; // Update biases for forget gate
            bo[j] -= LEARNING_RATE * dOutput[j]; // Update biases for output gate
            bc[j] -= LEARNING_RATE * dOutput[j]; // Update biases for candidate state
        }
    }

    // Train the model
    public void train(double[][] xTrain, double[][] yTrain) {
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            double totalError = 0.0;
            for (int i = 0; i < xTrain.length; i++) {
                double[] output = new double[OUTPUT_SIZE]; // Output array for each training example
                double[] o = new double[HIDDEN_SIZE];  // output gate
                double[] f = new double[HIDDEN_SIZE];  // forget gate
                double[] i_ = new double[HIDDEN_SIZE];  // input gate
                double[] c = new double[HIDDEN_SIZE];  // cell state
                double[] c_ = new double[HIDDEN_SIZE];  // candidate cell state

                // Forward pass
                forward(xTrain[i], output, o, f, i_, c, c_);

                // Compute the error and gradients for backpropagation
                double[] dOutput = new double[HIDDEN_SIZE];  // store gradients for output
                double[] dC = new double[HIDDEN_SIZE];  // store gradients for cell state

                // Backpropagation
                backpropagate(xTrain[i], yTrain[i], output, o, f, i_, c, c_, dOutput, dC);

                // Compute the total error (Mean Squared Error)
                totalError += Math.pow(yTrain[i][0] - output[0], 2);  // Using [0] since it's scalar output
            }
            totalError /= xTrain.length;
            System.out.println("Epoch " + (epoch + 1) + " Error: " + totalError);
        }
    }

    // Main method to test the LSTM model
    public static void main(String[] args) {
        LSTM lstm = new LSTM();

        // Sample training data (Input: 3 features, Output: 1)
        double[][] xTrain = {
                {0.1, 0.2, 0.3},
                {0.4, 0.5, 0.6},
                {0.7, 0.8, 0.9}
        };

        double[][] yTrain = {
                {0.0},
                {1.0},
                {0.0}
        };

        // Train the LSTM model
        lstm.train(xTrain, yTrain);
    }
}
