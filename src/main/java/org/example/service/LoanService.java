package main.java.org.example.service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import main.java.org.example.constants.LoanStatusLabels;
import main.java.org.example.repository.LoanRepository;
import main.java.org.example.entities.LoanDTO;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.entities.UserLoanDTO;

public class LoanService {
	private LoanService() {
	}

	private static final LoanService loanServiceInstance = new LoanService();
	private static final LoanRepository loanRepository = new LoanRepository();

	public static LoanService getLoanServiceInstance() {
		return loanServiceInstance;
	}

	public LoanDTO createLoanObject(double interestRate, int[] amountRange, String securityDemand, String source,
									String loanType, int[] repaymentPeriod, double minIncome, int[] ageRange) {
		LoanDTO loan = new LoanDTO();

		loan.setRateOfInterest(interestRate);
		loan.setSecurityDemand(securityDemand);
		loan.setRepaymentDuration(repaymentPeriod);
		loan.setLoanCategory(loanType);
		loan.setFundingSource(source);
		loan.setEligibleAgeRange(ageRange);
		loan.setLoanAmountRange(amountRange);
		loan.setMinimumIncome(minIncome);

		return loan;
	}

	public LoanDTO[] getLoansByLoanType(String loanType) throws SQLException {
		return loanRepository.getLoansByLoanType(loanType);
	}

	public LoanDTO getLoanById(int id) throws SQLException {
		return loanRepository.getLoanById(id);
	}

	public UserLoanDTO[] fetchUserLoans() throws SQLException {
		return loanRepository.getAllUserLoans();
	}

	public void approveLoan(UserDTO user, LoanDTO loan) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());
		loanRepository.updateLoanStatusForUser(user, loan, date, time, LoanStatusLabels.ACCEPTED_LABEL);
	}

	public void applyALoan(UserDTO user, LoanDTO loan, double principleAmount) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());
		loanRepository.addLoanForUser(user, loan, principleAmount, date, time, LoanStatusLabels.UNKNOWN_LABEL);
	}

	public void handleRejectLoan(UserDTO user, LoanDTO loan) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());
		loanRepository.updateLoanStatusForUser(user, loan, date, time, LoanStatusLabels.REJECTED_LABEL);
	}
}
