package main.java.org.example.AIModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LSTM {
    // Constants for the LSTM model
    private static final int INPUT_SIZE = 4; // Number of features in the dataset
    private static final int HIDDEN_SIZE = 4; // Hidden layer size
    private static final int OUTPUT_SIZE = 1; // Binary classification output
    private static final int EPOCHS = 100; // Number of training epochs
    private static final double LEARNING_RATE = 0.01; // Learning rate

    // Weights and biases for the LSTM model
    private double[][] Wi, Wf, Wo, Wc; // Input, forget, output, candidate weights
    private double[] bi, bf, bo, bc; // Biases for each gate

    public LSTM() {
        Wi = new double[HIDDEN_SIZE][INPUT_SIZE];
        Wf = new double[HIDDEN_SIZE][INPUT_SIZE];
        Wo = new double[HIDDEN_SIZE][INPUT_SIZE];
        Wc = new double[HIDDEN_SIZE][INPUT_SIZE];
        bi = new double[HIDDEN_SIZE];
        bf = new double[HIDDEN_SIZE];
        bo = new double[HIDDEN_SIZE];
        bc = new double[HIDDEN_SIZE];

        initializeWeights();
    }

    // Initialize weights and biases with small random values
    private void initializeWeights() {
        for (int i = 0; i < HIDDEN_SIZE; i++) {
            for (int j = 0; j < INPUT_SIZE; j++) {
                Wi[i][j] = Math.random() * 0.01;
                Wf[i][j] = Math.random() * 0.01;
                Wo[i][j] = Math.random() * 0.01;
                Wc[i][j] = Math.random() * 0.01;
            }
            bi[i] = Math.random() * 0.01;
            bf[i] = Math.random() * 0.01;
            bo[i] = Math.random() * 0.01;
            bc[i] = Math.random() * 0.01;
        }
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    private double tanh(double x) {
        return Math.tanh(x);
    }

    private double sigmoidDerivative(double x) {
        return x * (1 - x);
    }

    private double tanhDerivative(double x) {
        return 1 - x * x;
    }

    public void forward(double[] x, double[] output, double[] o, double[] f, double[] i_, double[] c, double[] c_) {
        double[] z = new double[HIDDEN_SIZE];
        for (int j = 0; j < HIDDEN_SIZE; j++) {
            z[j] = 0;
            for (int k = 0; k < INPUT_SIZE; k++) {
                z[j] += x[k] * Wi[j][k];
                z[j] += x[k] * Wf[j][k];
                z[j] += x[k] * Wo[j][k];
                z[j] += x[k] * Wc[j][k];
            }
            z[j] += bi[j];
        }

        for (int j = 0; j < HIDDEN_SIZE; j++) {
            i_[j] = sigmoid(z[j]);
            f[j] = sigmoid(z[j]);
            o[j] = sigmoid(z[j]);
            c_[j] = tanh(z[j]);
            c[j] = f[j] * c[j] + i_[j] * c_[j];
            output[0] = o[j] * tanh(c[j]);
        }
    }

    public void backpropagate(double[] x, double[] y, double[] output, double[] o, double[] f, double[] i_, double[] c, double[] c_, double[] dOutput, double[] dC) {
        double[] error = new double[OUTPUT_SIZE];
        error[0] = y[0] - output[0];

        for (int j = 0; j < HIDDEN_SIZE; j++) {
            dOutput[j] = error[0] * sigmoidDerivative(output[0]);
        }

        for (int j = 0; j < HIDDEN_SIZE; j++) {
            dC[j] = dOutput[j] * tanhDerivative(c[j]);
        }

        for (int j = 0; j < HIDDEN_SIZE; j++) {
            for (int k = 0; k < INPUT_SIZE; k++) {
                Wi[j][k] -= LEARNING_RATE * dOutput[j] * x[k];
                Wf[j][k] -= LEARNING_RATE * dOutput[j] * x[k];
                Wo[j][k] -= LEARNING_RATE * dOutput[j] * x[k];
                Wc[j][k] -= LEARNING_RATE * dOutput[j] * x[k];
            }
            bi[j] -= LEARNING_RATE * dOutput[j];
            bf[j] -= LEARNING_RATE * dOutput[j];
            bo[j] -= LEARNING_RATE * dOutput[j];
            bc[j] -= LEARNING_RATE * dOutput[j];
        }
    }

    public void train(double[][] xTrain, double[][] yTrain) {
        for (int epoch = 0; epoch < EPOCHS; epoch++) {
            double totalError = 0.0;
            for (int i = 0; i < xTrain.length; i++) {
                double[] output = new double[OUTPUT_SIZE];
                double[] o = new double[HIDDEN_SIZE];
                double[] f = new double[HIDDEN_SIZE];
                double[] i_ = new double[HIDDEN_SIZE];
                double[] c = new double[HIDDEN_SIZE];
                double[] c_ = new double[HIDDEN_SIZE];

                forward(xTrain[i], output, o, f, i_, c, c_);
                double[] dOutput = new double[HIDDEN_SIZE];
                double[] dC = new double[HIDDEN_SIZE];

                backpropagate(xTrain[i], yTrain[i], output, o, f, i_, c, c_, dOutput, dC);
                totalError += Math.pow(yTrain[i][0] - output[0], 2);
            }
            totalError /= xTrain.length;
            System.out.println("Epoch " + (epoch + 1) + " Error: " + totalError);
        }
    }

    public static double[][] normalize(double[][] data) {
        double[][] normalizedData = new double[data.length][data[0].length];
        for (int j = 0; j < data[0].length; j++) {
            double min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            for (int i = 0; i < data.length; i++) {
                min = Math.min(min, data[i][j]);
                max = Math.max(max, data[i][j]);
            }
            for (int i = 0; i < data.length; i++) {
                normalizedData[i][j] = (data[i][j] - min) / (max - min);
            }
        }
        return normalizedData;
    }

    public static void main(String[] args) {
        LSTM lstm = new LSTM();
        List<double[]> features = new ArrayList<>();
        List<double[]> targets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\HP\\Documents\\Projects\\LMS\\Loanlytics\\src\\main\\resources\\credit_data.csv"))) {
            String line = br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] x = new double[4];
                for (int i = 0; i < 4; i++) x[i] = Double.parseDouble(values[i]);
                features.add(x);
                targets.add(new double[]{Double.parseDouble(values[4])});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double[][] xTrain = normalize(features.toArray(new double[0][]));
        double[][] yTrain = targets.toArray(new double[0][]);

        lstm.train(xTrain, yTrain);
    }
}
