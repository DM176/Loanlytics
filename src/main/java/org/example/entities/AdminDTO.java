package main.java.org.example.entities;

import java.sql.SQLException;

import main.java.org.example.controllers.LoanProcessor;

public class AdminDTO extends UserDTO {

	public void predictLoan(UserDTO user, LoanDTO loan) throws SQLException {
		LoanProcessor.getInstance().approveLoan(user, loan);
	}

	public void sanctionLoan(UserDTO user, LoanDTO loan) throws SQLException {
		LoanProcessor.getInstance().approveLoan(user, loan);
	}

	public void rejectLoan(UserDTO user, LoanDTO loan) throws SQLException {
		LoanProcessor.getInstance().rejectLoanApplication(user, loan);
	}

}
