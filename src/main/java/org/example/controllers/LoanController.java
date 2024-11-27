package main.java.org.example.controllers;

import java.sql.SQLException;

import main.java.org.example.AIModel.PredictionService;
import main.java.org.example.entities.Loan;
import main.java.org.example.entities.User;
import main.java.org.example.managers.LoanManager;

public class LoanController {

	private LoanController() {
	}

	public static LoanController instance = new LoanController();

	public static LoanController getInstance() {
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
	public void predictLoan(User user, Loan loan) throws SQLException {
		LoanManager.getInstance().predictLoan(user, loan);
	}

}
