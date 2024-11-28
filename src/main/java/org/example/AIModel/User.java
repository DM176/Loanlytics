package main.java.org.example.AIModel;

public class User {
    private double age, income, homeOwnership, empLength, loanIntent, loanGrade, percentIncome, defaultOnFile, creditHistoryLength;

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getHomeOwnership() {
        return homeOwnership;
    }

    public void setHomeOwnership(double homeOwnership) {
        this.homeOwnership = homeOwnership;
    }

    public double getEmpLength() {
        return empLength;
    }

    public void setEmpLength(double empLength) {
        this.empLength = empLength;
    }

    public double getLoanIntent() {
        return loanIntent;
    }

    public void setLoanIntent(double loanIntent) {
        this.loanIntent = loanIntent;
    }

    public double getLoanGrade() {
        return loanGrade;
    }

    public void setLoanGrade(double loanGrade) {
        this.loanGrade = loanGrade;
    }

    public double getPercentIncome() {
        return percentIncome;
    }

    public void setPercentIncome(double percentIncome) {
        this.percentIncome = percentIncome;
    }

    public double getDefaultOnFile() {
        return defaultOnFile;
    }

    public void setDefaultOnFile(double defaultOnFile) {
        this.defaultOnFile = defaultOnFile;
    }

    public double getCreditHistoryLength() {
        return creditHistoryLength;
    }

    public void setCreditHistoryLength(double creditHistoryLength) {
        this.creditHistoryLength = creditHistoryLength;
    }

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
