package main.java.org.example.AIModel;

public class Loan {
    private double loanAmount, interestRate, loanStatus;

    public Loan(double loanAmount, double interestRate, double loanStatus) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanStatus = loanStatus;
    }

    public double getLoanStatus() {
        return loanStatus;
    }
}
