//package main.java.org.example.AIModel;
//
//import java.util.List;
//
//public class LSTMTraining {
//
//    /**
//     * Trains an LSTM model using the specified parameters.
//     *
//     * @param lstmCell The LSTM cell model to train.
//     * @param inputSize Number of input features.
//     * @param dataSize Number of training samples.
//     * @param epochs Number of training epochs.
//     */
//    public static void trainLSTMModel(LSTMCell lstmCell, int inputSize, int dataSize, int epochs) {
//        List<double[]> trainingData = DataGenerator.generateTrainingData(dataSize);
//
//        // Training loop for the specified number of epochs
//        for (int epoch = 0; epoch < epochs; epoch++) {
//            double totalLoss = 0;
//
//            for (double[] sample : trainingData) {
//                // Extract input features (age, income, credit score) and target output (approval)
//                double[] input = {sample[0], sample[1], sample[2]};
//                double expectedOutput = sample[3];
//
//                // Forward pass: Get the LSTM output for the input features
//                double[] output = lstmCell.forward(input);
//
//                // Calculate loss (mean squared error)
//                double loss = Math.pow(output[0] - expectedOutput, 2);
//                totalLoss += loss;
//
//                // Backward pass: Compute gradient and update weights
//                double[] dLoss_dOutput = {2 * (output[0] - expectedOutput)};
//                lstmCell.backward(input, dLoss_dOutput);
//            }
//
//            // Print the loss for the epoch
//            System.out.println("Epoch " + (epoch + 1) + " - Total Loss: " + totalLoss);
//        }
//    }
//
//    public static void main(String[] args) {
//        // Define parameters for training
//        int inputSize = 3;  // Features: age, income, credit score
//        int hiddenSize = 10; // Number of hidden units in LSTM cell
//        int dataSize = 1000; // Number of training samples
//        int epochs = 10;     // Number of training epochs
//
//        // Initialize the LSTM model
//        LSTMCell lstmCell = new LSTMCell(inputSize, hiddenSize);
//
//        // Train the LSTM model
//        trainLSTMModel(lstmCell, inputSize, dataSize, epochs);
//
//        // Define a new input for testing the model after training
//        double[] testInput = {30.0, 55000.0, 650.0}; // Age: 30, Income: 55000, Credit Score: 650
//
//        // Perform a forward pass with the trained model
//        double[] testOutput = lstmCell.forward(testInput);
//
//        // Print the result
//        System.out.printf("Test Input (Age: %.1f, Income: %.2f, Credit Score: %.1f) - Predicted Approval: %.3f%n",
//                testInput[0], testInput[1], testInput[2], testOutput[0]);
//    }
//}
