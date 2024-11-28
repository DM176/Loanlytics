package main.java.org.example.controllers;

import java.sql.SQLException;

import main.java.org.example.entities.Loan;
import main.java.org.example.entities.User;
import main.java.org.example.managers.LoanManager;

public class LoanProcessor {

	private LoanProcessor() {
	}

	public static LoanProcessor instance = new LoanProcessor();

	public static LoanProcessor getInstance() {
		return instance;
	}

	public void sanctionLoan(User user, Loan loan) throws SQLException {
		LoanManager.getInstance().sanctionLoan(user, loan);
	}

	public void applyLoan(User user, Loan loan, double principleAmount) throws SQLException {
		LoanManager.getInstance().applyLoan(user, loan, principleAmount);
	}
	
	public void rejectLoan(User user, Loan loan) throws SQLException {
		LoanManager.getInstance().rejectLoan(user, loan);
	}
	public String predictLoan(User user, Loan loan) throws SQLException {
		return LoanManager.getInstance().predictLoan(user, loan);
	}

}
