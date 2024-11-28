package main.java.org.example.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import main.java.org.example.DatabaseController;
import main.java.org.example.entities.Loan;
import main.java.org.example.entities.User;
import main.java.org.example.entities.UserLoanDTO;

public class LoanRepository {

	public void addUserLoan(User user, Loan loan, double principleAmount, Date date, Time time, String status)
			throws SQLException {
		DatabaseController.addUserLoan(user, loan, principleAmount, date, time, status);
	}

	public void updateLoanStatus(User user, Loan loan, Date date, Time time, String status) throws SQLException {
		DatabaseController.updateLoanStatus(user, loan, date, time, status);
	}

	public Loan[] getLoans(String loanType) throws SQLException {
		return DatabaseController.getLoans(loanType);
	}

	public Loan getLoan(int id) throws SQLException {
		return DatabaseController.getLoan(id);
	}

	public UserLoanDTO[] getUserLoans() throws SQLException {
		return DatabaseController.getUserLoans();
	}

}
