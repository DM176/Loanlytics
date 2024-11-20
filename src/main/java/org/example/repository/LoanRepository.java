package main.java.org.example.repository;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import main.java.org.example.DatabaseManager;
import main.java.org.example.entities.LoanDTO;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.entities.UserLoanDTO;

public class LoanRepository {

	public void addLoanForUser(UserDTO user, LoanDTO loan, double principleAmount, Date date, Time time, String status)
			throws SQLException {
		DatabaseManager.addUserLoan(user, loan, principleAmount, date, time, status);
	}

	public void updateLoanStatusForUser(UserDTO user, LoanDTO loan, Date date, Time time, String status) throws SQLException {
		DatabaseManager.updateLoanStatus(user, loan, date, time, status);
	}

	public LoanDTO[] getLoansByLoanType(String loanType) throws SQLException {
		return DatabaseManager.getAllLoansByLoanType(loanType);
	}

	public LoanDTO getLoanById(int id) throws SQLException {
		return DatabaseManager.getLoan(id);
	}

	public UserLoanDTO[] getAllUserLoans() throws SQLException {
		return DatabaseManager.getUserLoans();
	}

}
