package main.java.org.example.entities;

import java.sql.SQLException;

import main.java.org.example.controllers.LoanController;

public class Admin extends User{

	public void sanctionLoan(User user, Loan loan) throws SQLException {
		LoanController.getInstance().sanctionLoan(user, loan);
	}
	
	public void rejectLoan(User user, Loan loan) throws SQLException {
		LoanController.getInstance().rejectLoan(user, loan);
	}

	public String predictLoan(User user, Loan loan) throws SQLException {
		return LoanController.getInstance().predictLoan(user, loan);
	}

}
