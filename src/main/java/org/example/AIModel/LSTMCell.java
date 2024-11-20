//package main.java.org.example.AIModel;
//
//public class LSTMCell {
//    private int inputSize;
//    private int hiddenSize;
//
//    // Weight matrices for forget, input, cell, and output gates
//    private double[][] Wf, Wi, Wc, Wo;
//
//    // Bias vectors for forget, input, cell, and output gates
//    private double[] bf, bi, bc, bo;
//
//    // Hidden state and cell state
//    private double[] hiddenState;
//    private double[] cellState;
//
//    // Constructor to initialize the LSTM cell
//    public LSTMCell(int inputSize, int hiddenSize) {
//        this.inputSize = inputSize;
//        this.hiddenSize = hiddenSize;
//
//        // Initialize weight matrices and biases
//        this.Wf = initializeMatrix(hiddenSize, inputSize + hiddenSize);
//        this.Wi = initializeMatrix(hiddenSize, inputSize + hiddenSize);
//        this.Wc = initializeMatrix(hiddenSize, inputSize + hiddenSize);
//        this.Wo = initializeMatrix(hiddenSize, inputSize + hiddenSize);
//
//        this.bf = new double[hiddenSize];
//        this.bi = new double[hiddenSize];
//        this.bc = new double[hiddenSize];
//        this.bo = new double[hiddenSize];
//
//        // Initialize hidden state and cell state
//        this.hiddenState = new double[hiddenSize];
//        this.cellState = new double[hiddenSize];
//    }
//
//    // Helper method to initialize a matrix with random values
//    private double[][] initializeMatrix(int rows, int columns) {
//        double[][] matrix = new double[rows][columns];
//        for (int i = 0; i < rows; i++) {
//            for (int j = 0; j < columns; j++) {
//                matrix[i][j] = Math.random() * 0.01;  // Small random values
//            }
//        }
//        return matrix;
//    }
//
//    // Helper method to initialize a vector (bias) with zeros
//    private double[] initializeBias(int size) {
//        double[] bias = new double[size];
//        return bias; // Bias is initialized to 0 by default
//    }
//
//    // The forward pass method
//    public double[] forward(double[] input) {
//        // Combine input and hidden state
//        double[] combined = concatenate(input, hiddenState);
//
//        // Forget gate
//        double[] forgetGate = sigmoid(matrixAdd(matrixMultiply(Wf, combined), bf));
//
//        // Input gate
//        double[] inputGate = sigmoid(matrixAdd(matrixMultiply(Wi, combined), bi));
//
//        // Cell candidate
//        double[] cellCandidate = tanh(matrixAdd(matrixMultiply(Wc, combined), bc));
//
//        // Output gate
//        double[] outputGate = sigmoid(matrixAdd(matrixMultiply(Wo, combined), bo));
//
//        // Update cell state and hidden state
//        cellState = elementWiseAdd(elementWiseMultiply(forgetGate, cellState), elementWiseMultiply(inputGate, cellCandidate));
//        hiddenState = elementWiseMultiply(outputGate, tanh(cellState));
//
//        return hiddenState;
//    }
//
//    // Activation functions (sigmoid, tanh)
//    private double[] sigmoid(double[] x) {
//        double[] result = new double[x.length];
//        for (int i = 0; i < x.length; i++) {
//            result[i] = 1 / (1 + Math.exp(-x[i]));
//        }
//        return result;
//    }
//
//    private double[] tanh(double[] x) {
//        double[] result = new double[x.length];
//        for (int i = 0; i < x.length; i++) {
//            result[i] = Math.tanh(x[i]);
//        }
//        return result;
//    }
//
//    // Derivative of the tanh function
//    private double[] tanhDerivative(double[] x) {
//        double[] result = new double[x.length];
//        for (int i = 0; i < x.length; i++) {
//            result[i] = 1 - Math.pow(Math.tanh(x[i]), 2);
//        }
//        return result;
//    }
//
//    // Matrix multiplication (dot product of matrix and vector)
//    public double[] matrixMultiply(double[][] matrix, double[] vector) {
//        if (matrix[0].length != vector.length) {
//            throw new IllegalArgumentException("Matrix columns must match vector length.");
//        }
//
//        double[] result = new double[matrix.length];
//
//        // Perform matrix-vector multiplication (Dot product of each row of the matrix with the vector)
//        for (int i = 0; i < matrix.length; i++) {
//            result[i] = 0;
//            for (int j = 0; j < matrix[i].length; j++) {
//                result[i] += matrix[i][j] * vector[j];
//            }
//        }
//
//        return result;
//    }
//
//    // Element-wise addition of two vectors
//    public double[] elementWiseAdd(double[] a, double[] b) {
//        if (a.length != b.length) {
//            throw new IllegalArgumentException("Vectors must be of the same length.");
//        }
//
//        double[] result = new double[a.length];
//        for (int i = 0; i < a.length; i++) {
//            result[i] = a[i] + b[i];
//        }
//
//        return result;
//    }
//
//    // Element-wise multiplication of two vectors
//    public double[] elementWiseMultiply(double[] a, double[] b) {
//        if (a.length != b.length) {
//            throw new IllegalArgumentException("Vectors must be of the same length.");
//        }
//
//        double[] result = new double[a.length];
//        for (int i = 0; i < a.length; i++) {
//            result[i] = a[i] * b[i];
//        }
//        return result;
//    }
//
//    // Concatenate two vectors into one
//    public double[] concatenate(double[] a, double[] b) {
//        double[] result = new double[a.length + b.length];
//        System.arraycopy(a, 0, result, 0, a.length);  // Copy array a into the result array
//        System.arraycopy(b, 0, result, a.length, b.length);  // Copy array b into the result array after array a
//        return result;
//    }
//
//    // Matrix addition (adds corresponding elements of two matrices)
//    public double[][] matrixAdd(double[][] a, double[][] b) {
//        if (a.length != b.length || a[0].length != b[0].length) {
//            throw new IllegalArgumentException("Matrices must have the same dimensions.");
//        }
//
//        double[][] result = new double[a.length][a[0].length];
//        for (int i = 0; i < a.length; i++) {
//            for (int j = 0; j < a[0].length; j++) {
//                result[i][j] = a[i][j] + b[i][j];
//            }
//        }
//
//        return result;
//    }
//
//    // Backpropagation step (used to compute gradients and update weights)
//    public void backward(double[] input, double[] dL_dOut) {
//        // Derivatives of activations
//        double[] dOut_dCell = elementWiseMultiply(dL_dOut, tanhDerivative(cellState)); // dL/dOut * tanh'(cellState)
//
//        // Gradients for output gate
//        double[] dL_dOutputGate = elementWiseMultiply(dOut_dCell, tanh(cellState)); // dL/dOut * tanh(cellState)
//        double[] dL_dCellState = elementWiseMultiply(dL_dOut, outputGate);  // Backprop into cell state
//
//        // Gradients for forget, input gates and cell candidate
//        double[] dL_dForgetGate = elementWiseMultiply(dCell_dForgetGate, elementWiseMultiply(cellState, forgetGate));  // dL/dForgetGate = dL/dCell * f'(tanh(cellState)) * forgetGate
//        double[] dL_dInputGate = elementWiseMultiply(dL_dCellState, tanhDerivative(inputGate));  // dL/dInputGate = dL/dInput * tanh'(inputGate)
//
//        // Gradients for Wf, Wi, Wc, Wo
//        double[] dL_dWf = matrixMultiply(transpose(concatenate(input, hiddenState)), dL_dForgetGate);
//        double[] dL_dWi = matrixMultiply(transpose(concatenate(input, hiddenState)), dL_dInputGate);
//        double[] dL_dWc = matrixMultiply(transpose(concatenate(input, hiddenState)), dL_dCellState);
//        double[] dL_dWo = matrixMultiply(transpose(concatenate(input, hiddenState)), dL_dOutputGate);
//
//        // Gradients of bias terms
//        double[] dL_dbf = sum(dL_dForgetGate);
//        double[] dL_dbi = sum(dL_dInputGate);
//        double[] dL_dbc = sum(dL_dCellState);
//        double[] dL_dbo = sum(dL_dOutputGate);
//
//        // Apply gradients to update weights and biases (here you would typically use an optimization step like gradient descent)
//        updateWeights(dL_dWf, dL_dWi, dL_dWc, dL_dWo, dL_dbf, dL_dbi, dL_dbc, dL_dbo);
//    }
//
//    // Transpose a matrix
//    private double[][] transpose(double[][] matrix) {
//        double[][] result = new double[matrix[0].length][matrix.length];
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[i].length; j++) {
//                result[j][i] = matrix[i][j];
//            }
//        }
//        return result;
//    }
//
//    // Sum of elements in a vector
//    private double[] sum(double[] vector) {
//        double sum = 0;
//        for (int i = 0; i < vector.length; i++) {
//            sum += vector[i];
//        }
//        double[] result = new double[vector.length];
//        for (int i = 0; i < vector.length; i++) {
//            result[i] = sum;
//        }
//        return result;
//    }
//
//    // Update the weights using the computed gradients (simple placeholder, should use an optimizer like gradient descent)
//    private void updateWeights(double[] dL_dWf, double[] dL_dWi, double[] dL_dWc, double[] dL_dWo, double[] dL_dbf, double[] dL_dbi, double[] dL_dbc, double[] dL_dbo) {
//        // This is a simple placeholder for updating weights.
//        // Normally, you would apply an optimization algorithm (e.g., gradient descent) here.
//    }
//}
