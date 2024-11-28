package main.java.org.example.entities;

import java.sql.SQLException;

import main.java.org.example.controllers.LoanProcessor;

public class AdminDTO extends User{

	public void sanctionLoan(User user, Loan loan) throws SQLException {
		LoanProcessor.getInstance().sanctionLoan(user, loan);
	}
	
	public void rejectLoan(User user, Loan loan) throws SQLException {
		LoanProcessor.getInstance().rejectLoan(user, loan);
	}

	public String predictLoan(User user, Loan loan) throws SQLException {
		return LoanProcessor.getInstance().predictLoan(user, loan);
	}

}
