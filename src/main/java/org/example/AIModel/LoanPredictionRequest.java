package main.java.org.example.AIModel;


public class LoanPredictionRequest {
    private int age;
    private double income;
    private String homeOwnership; // e.g., "OWN", "RENT"
    private int empLength; // in years
    private String loanIntent; // e.g., "EDUCATION", "MEDICAL"
    private String loanGrade; // e.g., "A", "B"
    private double percentIncome; // percentage of income for the loan
    private String defaultOnFile; // e.g., "Y" or "N"
    private int creditHistoryLength; // in years

    // Getters and Setters
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public double getIncome() { return income; }
    public void setIncome(double income) { this.income = income; }

    public String getHomeOwnership() { return homeOwnership; }
    public void setHomeOwnership(String homeOwnership) { this.homeOwnership = homeOwnership; }

    public int getEmpLength() { return empLength; }
    public void setEmpLength(int empLength) { this.empLength = empLength; }

    public String getLoanIntent() { return loanIntent; }
    public void setLoanIntent(String loanIntent) { this.loanIntent = loanIntent; }

    public String getLoanGrade() { return loanGrade; }
    public void setLoanGrade(String loanGrade) { this.loanGrade = loanGrade; }

    public double getPercentIncome() { return percentIncome; }
    public void setPercentIncome(double percentIncome) { this.percentIncome = percentIncome; }

    public String getDefaultOnFile() { return defaultOnFile; }
    public void setDefaultOnFile(String defaultOnFile) { this.defaultOnFile = defaultOnFile; }

    public int getCreditHistoryLength() { return creditHistoryLength; }
    public void setCreditHistoryLength(int creditHistoryLength) { this.creditHistoryLength = creditHistoryLength; }
}
