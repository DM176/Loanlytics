package main.java.org.example.entities;

import java.sql.SQLException;

import main.java.org.example.controllers.LoanProcessor;

public class UserDTO {

	private String firstName;
	private String lastName;
	private int userAge;
	private String userEmail;
	private String userPassword;
	private String gender;
	private int securityLevel;
	private double creditScore;
	private double incomeOfUser;

	public double getIncomeOfUser() {
		return incomeOfUser;
	}

	public void setIncomeOfUser(double incomeOfUser) {
		this.incomeOfUser = incomeOfUser;
	}

	public void applyLoan(LoanDTO loan, double principleAmount) throws SQLException {
		LoanProcessor.getInstance().applyNewLoan(this, loan, principleAmount);
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public double getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(double creditScore) {
		this.creditScore = creditScore;
	}

	public int getSecurityLevel() {
		return securityLevel;
	}

	public void setSecurityLevel(int securityLevel) {
		this.securityLevel = securityLevel;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setPassword(String password) {
		this.userPassword = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "User [ firstName=" + firstName + ", lastName=" + lastName + ", age=" + userAge + ", email="
				+ userEmail + ", password=" + userPassword + ", gender=" + gender + ", securityLevel=" + securityLevel
				+ ", creditScore=" + creditScore + "]";
	}

}
