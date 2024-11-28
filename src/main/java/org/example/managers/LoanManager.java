package main.java.org.example.managers;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

import main.java.org.example.AIModel.LoanPredictionRequest;
import main.java.org.example.AIModel.PredictionService;
import main.java.org.example.constants.LoanStatus;
import main.java.org.example.dao.LoanDao;
import main.java.org.example.entities.Loan;
import main.java.org.example.entities.User;
import main.java.org.example.entities.UserLoan;

public class LoanManager {

	private LoanManager() {
	}
	private final PredictionService predictionService = PredictionService.getInstance();
	private static LoanManager instance = new LoanManager();
	private static LoanDao dao = new LoanDao();

	public static LoanManager getInstance() {
		return instance;
	}

	public Loan createLoan(double interestRate, int[] amountRange, String securityDemand, String source,
			String loanType, int[] repaymentPeriod, double minIncome, int[] ageRange) {
		Loan loan = new Loan();

		loan.setInterestRate(interestRate);
		loan.setSecurityDemand(securityDemand);
		loan.setRepaymentPeriod(repaymentPeriod);
		loan.setLoanType(loanType);
		loan.setSource(source);
		loan.setAgeRange(ageRange);
		loan.setAmountRange(amountRange);
		loan.setMinIncome(minIncome);

		return loan;
	}
	
	public Loan[] getLoans(String loanType) throws SQLException {
		return dao.getLoans(loanType);
	}
	
	public Loan getLoan(int id) throws SQLException {
		return dao.getLoan(id);
	}
	
	public UserLoan[] getUserLoans() throws SQLException {
		return dao.getUserLoans();
	}
	
	public void sanctionLoan(User user, Loan loan) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());
		dao.updateLoanStatus(user, loan, date, time, LoanStatus.ACCEPTED);
	}

	public void applyLoan(User user, Loan loan, double principleAmount) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());
		dao.addUserLoan(user, loan, principleAmount, date, time, LoanStatus.UNKNOWN);
	}

	public void rejectLoan(User user, Loan loan) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());
		dao.updateLoanStatus(user, loan, date, time, LoanStatus.REJECTED);
	}
	public String predictLoan(User user, Loan loan) throws SQLException {
		Date date=new Date(System.currentTimeMillis());
		Time time=new Time(System.currentTimeMillis());

		LoanPredictionRequest request = new LoanPredictionRequest();
		request.setAge(user.getAge());
		request.setIncome(user.getIncome());
		request.setHomeOwnership("OWN");
		request.setEmpLength(10);
		request.setLoanIntent(loan.getLoanType());
		request.setLoanGrade("A");
		request.setPercentIncome(20);
		request.setDefaultOnFile("N");
		request.setCreditHistoryLength(user.getCreditScore());
		predictionService.makePrediction(request);
		double prediction = predictionService.makePrediction(request);
		if(prediction==1 ){
			dao.updateLoanStatus(user, loan, date, time, LoanStatus.CAN_BE_APPROVED);
		} else {
			dao.updateLoanStatus(user, loan, date, time, LoanStatus.SHOULD_BE_REJECTED);
		}
		String res = "Prediction: " + (prediction>0.18 ? "Loan Approved" : "Loan Denied");
		return res;

	}

}
