package main.java.org.example.entities;

public class UserModelDTO {
	private String firstName;
	private String surname;
	private Integer userAge;
	private String userEmail;
	private String gender;
	private Double userIncome;
	private Double creditScore;
	private String securityLevel;
	//investments -> property, stocks,

	public UserModelDTO(String firstName, String surname, Integer userAge, String userEmail, String gender, Double userIncome,
						Double creditScore, String securityLevel) {
		this.firstName = firstName;
		this.surname = surname;
		this.userAge = userAge;
		this.userEmail = userEmail;
		this.gender = gender;
		this.userIncome = userIncome;
		this.creditScore = creditScore;
		this.securityLevel = securityLevel;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Integer getUserAge() {
		return userAge;
	}
	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Double getUserIncome() {
		return userIncome;
	}
	public void setUserIncome(Double userIncome) {
		this.userIncome = userIncome;
	}
	public Double getCreditScore() {
		return creditScore;
	}
	public void setCreditScore(Double creditScore) {
		this.creditScore = creditScore;
	}
	public String getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(String securityLevel) {
		this.securityLevel = securityLevel;
	}
}
