package main.java.org.example.controllers;

import java.sql.SQLException;

import main.java.org.example.entities.LoanDTO;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.service.LoanService;

public class LoanProcessor {
	private LoanProcessor() {
	}

	public static final LoanProcessor instance = new LoanProcessor();

	public static LoanProcessor getInstance() {
		return instance;
	}

	public void applyNewLoan(UserDTO user, LoanDTO loan, double principleAmount) throws SQLException {
		LoanService.getLoanServiceInstance().applyALoan(user, loan, principleAmount);
	}
	public void approveLoan(UserDTO user, LoanDTO loan) throws SQLException {
		LoanService.getLoanServiceInstance().approveLoan(user, loan);
	}
	public void rejectLoanApplication(UserDTO user, LoanDTO loan) throws SQLException {
		LoanService.getLoanServiceInstance().handleRejectLoan(user, loan);
	}
}
