//package main.java.org.example.AIModel;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class DataGenerator {
//    public static List<double[]> generateTrainingData(int dataSize) {
//        List<double[]> data = new ArrayList<>();
//        Random random = new Random();
//
//        for (int i = 0; i < dataSize; i++) {
//            double age = random.nextInt(60) + 20; // Age between 20 and 80
//            double income = random.nextDouble() * 100000; // Income between 0 and 100000
//            double creditScore = random.nextDouble() * 850; // Credit Score between 0 and 850
//
//            // Dummy logic for loan approval: High credit score and income result in approval
//            double approval = (creditScore > 600 && income > 50000) ? 1.0 : 0.0;
//
//            data.add(new double[]{age, income, creditScore, approval});
//        }
//
//        return data;
//    }
//}
