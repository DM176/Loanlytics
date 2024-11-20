package main.java.org.example.entities;

import java.util.Arrays;

public class LoanDTO {
	private double rateOfInterest;
	private int[] loanAmountRange;
	private String securityDemand;
	private String fundingSource;
	private String loanCategory;
	private int[] repaymentDuration;
	private double minimumIncome;
	private int[] eligibleAgeRange;
	private int loanId;

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int[] getLoanAmountRange() {
		return loanAmountRange;
	}

	public void setLoanAmountRange(int[] loanAmountRange) {
		this.loanAmountRange = loanAmountRange;
	}

	public int[] getEligibleAgeRange() {
		return eligibleAgeRange;
	}

	public int[] getRepaymentDuration() {
		return repaymentDuration;
	}

	public void setRepaymentDuration(int[] repaymentDuration) {
		this.repaymentDuration = repaymentDuration;
	}

	public void setEligibleAgeRange(int[] eligibleAgeRange) {
		this.eligibleAgeRange = eligibleAgeRange;
	}

	public double getMinimumIncome() {
		return minimumIncome;
	}

	public void setMinimumIncome(double minimumIncome) {
		this.minimumIncome = minimumIncome;
	}

	public double getRateOfInterest() {
		return rateOfInterest;
	}

	public void setRateOfInterest(double rateOfInterest) {
		this.rateOfInterest = rateOfInterest;
	}

	public String getSecurityDemand() {
		return securityDemand;
	}

	public String getFundingSource() {
		return fundingSource;
	}

	public void setFundingSource(String fundingSource) {
		this.fundingSource = fundingSource;
	}

	public String getLoanCategory() {
		return loanCategory;
	}

	public void setLoanCategory(String loanCategory) {
		this.loanCategory = loanCategory;
	}

	public void setSecurityDemand(String securityDemand) {
		this.securityDemand = securityDemand;
	}

	@Override
	public String toString() {
		return "Loan [id=" + loanId + ", ageRange=" + Arrays.toString(eligibleAgeRange) + ", minIncome=" + minimumIncome
				+ ", interestRate=" + rateOfInterest + ", repaymentPeriod=" + Arrays.toString(repaymentDuration)
				+ ", amountRange=" + Arrays.toString(loanAmountRange) + ", securityDemand=" + securityDemand + ", source="
				+ fundingSource + ", loanType=" + loanCategory + "]";
	}
}
