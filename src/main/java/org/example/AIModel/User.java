package main.java.org.example.AIModel;

public class User {
    private double age, income, homeOwnership, empLength, loanIntent, loanGrade, percentIncome, defaultOnFile, creditHistoryLength;

    public User(double age, double income, double homeOwnership, double empLength, double loanIntent, double loanGrade, double percentIncome, double defaultOnFile, double creditHistoryLength) {
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

    public double[] getFeatures() {
        return new double[]{age, income, homeOwnership, empLength, loanIntent, loanGrade, percentIncome, defaultOnFile};
    }
}
