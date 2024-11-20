package main.java.org.example.entities;

public class LoanModelDTO {
	private Integer loanUniqueId;
	private String source;
	private String amountRange;
	private String securityDemand;
	private Double interestRate;
	private Double minimumIncome;
	private String ageRange;
	private String tenure;

	public LoanModelDTO(Integer loanUniqueId, String source, String amountRange, String securityDemand,
						Double interestRate, Double minimumIncome, String ageRange, String tenure) {
		this.loanUniqueId = loanUniqueId;
		this.source = source;
		this.amountRange = amountRange;
		this.securityDemand = securityDemand;
		this.interestRate = interestRate;
		this.minimumIncome = minimumIncome;
		this.ageRange = ageRange;
		this.tenure = tenure;

	}

	public Integer getLoanUniqueId() {
		return loanUniqueId;
	}

	public void setLoanUniqueId(Integer loanUniqueId) {
		this.loanUniqueId = loanUniqueId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAmountRange() {
		return amountRange;
	}

	public void setAmountRange(String amountRange) {
		this.amountRange = amountRange;
	}

	public String getSecurityDemand() {
		return securityDemand;
	}

	public void setSecurityDemand(String securityDemand) {
		this.securityDemand = securityDemand;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Double getMinimumIncome() {
		return minimumIncome;
	}

	public void setMinimumIncome(Double minimumIncome) {
		this.minimumIncome = minimumIncome;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
}
