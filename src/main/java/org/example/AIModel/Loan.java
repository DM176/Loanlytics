package main.java.org.example.AIModel;

public class Loan {
    private double loanAmount;
    private double interestRate;
    private double loanTerm;
    private int loanStatus; // Add loanStatus to Loan class (approved = 1, denied = 0)

    // Constructor for the Loan class
    public Loan(double loanAmount, double interestRate, double loanTerm, int loanStatus) {
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.loanStatus = loanStatus;  // Set loanStatus
    }

    // Getters and setters
    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(double loanTerm) {
        this.loanTerm = loanTerm;
    }

    public int getLoanStatus() {  // Add getter for loanStatus
        return loanStatus;
    }

    public void setLoanStatus(int loanStatus) {  // Add setter for loanStatus
        this.loanStatus = loanStatus;
    }
}
