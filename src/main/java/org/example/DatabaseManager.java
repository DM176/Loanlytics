package main.java.org.example;

import main.java.org.example.constants.UserTypeLabels;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.entities.LoanDTO;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.entities.UserLoanDTO;
import main.java.org.example.service.LoanService;
import main.java.org.example.service.UserService;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

	private static Connection databaseConnection;

	// Load database connection
	public static void loadData(String database, String username, String password) {
		final String CONNECTION_ERROR_MESSAGE = "Error connecting to the database: ";

		try {
			databaseConnection = DriverManager.getConnection(database, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(CONNECTION_ERROR_MESSAGE + e.getMessage(), e);
		}
	}

	// Add new user to the database
	public static void addUser(UserDTO user) throws SQLException {
		String INSERT_USER_QUERY = "INSERT INTO User (firstName, lastName, age, email, password, gender, securityLevel, creditScore, userType, income) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = databaseConnection.prepareStatement(INSERT_USER_QUERY)) {
			statement.setString(1, user.getFirstName());
			statement.setString(2, user.getLastName());
			statement.setInt(3, user.getUserAge());
			statement.setString(4, user.getUserEmail());
			statement.setString(5, user.getUserPassword());
			statement.setString(6, user.getGender());
			statement.setInt(7, user.getSecurityLevel());
			statement.setDouble(8, user.getCreditScore());
			statement.setString(9, user instanceof AdminDTO ? UserTypeLabels.ADMIN_LABEL : UserTypeLabels.GENERAL_LABEL);
			statement.setDouble(10, user.getIncomeOfUser());

			statement.executeUpdate();
		}
	}

	// Get user by email
	public static UserDTO getUser(String email) throws SQLException {
		String SELECT_USER_BY_EMAIL_QUERY = "SELECT * FROM User WHERE Email=?";

		try (PreparedStatement statement = databaseConnection.prepareStatement(SELECT_USER_BY_EMAIL_QUERY)) {
			statement.setString(1, email);

			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					String firstName = result.getString(1);
					String lastName = result.getString(2);
					int age = result.getInt(3);
					String password = result.getString(5);
					String gender = result.getString(6);
					int securityPossesed = result.getInt(7);
					double creditScore = result.getDouble(8);
					String privilege = result.getString(9);
					double income = result.getDouble(10);

					UserDTO user;

					if (privilege.equals(UserTypeLabels.ADMIN_LABEL)) {
						user = UserService.getUserServiceInstance().createAdminObject(firstName, lastName, age, email, password, gender,
								securityPossesed, creditScore, income);
					} else {
						user = UserService.getUserServiceInstance().createUserObject(firstName, lastName, age, email, password, gender,
								securityPossesed, creditScore, income);
					}

					return user;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Error fetching user data for email: " + email, e);
		}
	}

	// Add loan to user
	public static void addUserLoan(UserDTO user, LoanDTO loan, double principalTaken, Date date, Time time, String status)
			throws SQLException {

		String INSERT_USER_LOAN_QUERY = "INSERT INTO UserLoan (userEmail, loanId, principalTaken, date, time, status)" +
				" VALUES(?, ?, ?, ?, ?, ?)";

		try (PreparedStatement statement = databaseConnection.prepareStatement(INSERT_USER_LOAN_QUERY)) {
			statement.setString(1, user.getUserEmail());
			statement.setInt(2, loan.getLoanId());
			statement.setDouble(3, principalTaken);
			statement.setDate(4, date);
			statement.setTime(5, time);
			statement.setString(6, status);

			statement.executeUpdate();
		}
	}

	// Get loan by ID
	public static LoanDTO getLoan(int id) throws SQLException {
		String SELECT_LOAN_BY_ID_QUERY = "SELECT * FROM Loan WHERE id=?";

		try (PreparedStatement statement = databaseConnection.prepareStatement(SELECT_LOAN_BY_ID_QUERY)) {
			statement.setInt(1, id);

			try (ResultSet result = statement.executeQuery()) {
				if (result.next()) {
					double interestRate = result.getDouble(1);
					int principalLB = result.getInt(2);
					int principalUB = result.getInt(3);
					String securityNeeded = result.getString(4);
					String loanSource = result.getString(5);
					String loanType = result.getString(6);
					int repaymentPeriodLB = result.getInt(7);
					int repaymentPeriodUB = result.getInt(8);
					double minimumIncome = result.getDouble(9);
					int ageLB = result.getInt(10);
					int ageUB = result.getInt(11);

					LoanDTO loan = LoanService.getLoanServiceInstance().createLoanObject(interestRate, new int[] { principalLB, principalUB },
							securityNeeded, loanSource, loanType, new int[] { repaymentPeriodLB, repaymentPeriodUB },
							minimumIncome, new int[] { ageLB, ageUB });

					loan.setLoanId(id);
					return loan;
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new SQLException("Error fetching loan data for loan ID: " + id, e);
		}
	}

	// Get all loans by type or all loans
	public static LoanDTO[] getAllLoansByLoanType(String loanType) throws SQLException {
		ArrayList<LoanDTO> list = new ArrayList<>();
		LoanDTO[] loans;
		String SELECT_ALL_LOANS_QUERY = "SELECT * FROM Loan";
		String SELECT_LOANS_BY_TYPE_QUERY = "SELECT * FROM Loan WHERE LoanType=?";

		try {
			ResultSet result;

			if (loanType.equals("*")) {
				try (Statement statement = databaseConnection.createStatement()) {
					result = statement.executeQuery(SELECT_ALL_LOANS_QUERY);
				}
			} else {
				try (PreparedStatement statement = databaseConnection.prepareStatement(SELECT_LOANS_BY_TYPE_QUERY)) {
					statement.setString(1, loanType);
					result = statement.executeQuery();
				}
			}

			while (result.next()) {
				LoanDTO loan = new LoanDTO();

				double interestRate = result.getDouble(1);
				int principalLB = result.getInt(2);
				int principalUB = result.getInt(3);
				String securityNeeded = result.getString(4);
				String loanSource = result.getString(5);
				String loanTypeFromDb = result.getString(6);
				int repaymentPeriodLB = result.getInt(7);
				int repaymentPeriodUB = result.getInt(8);
				double minimumIncome = result.getDouble(9);
				int ageLB = result.getInt(10);
				int ageUB = result.getInt(11);
				int id = result.getInt(12);

				loan = LoanService.getLoanServiceInstance().createLoanObject(
						interestRate,
						new int[]{principalLB, principalUB},
						securityNeeded,
						loanSource,
						loanTypeFromDb,
						new int[]{repaymentPeriodLB, repaymentPeriodUB},
						minimumIncome,
						new int[]{ageLB, ageUB}
				);
				loan.setLoanId(id);
				list.add(loan);
			}

			loans = list.toArray(new LoanDTO[0]);
		} catch (SQLException e) {
			throw new SQLException("Error fetching loan data", e);
		}

		return loans;
	}

	// Get all user loans
	public static UserLoanDTO[] getUserLoans() throws SQLException {
		ArrayList<UserLoanDTO> list = new ArrayList<>();
		UserLoanDTO[] userLoan;
		String SELECT_ALL_USER_LOANS_QUERY = "SELECT * FROM UserLoan";

		try (Statement statement = databaseConnection.createStatement()) {
			ResultSet result = statement.executeQuery(SELECT_ALL_USER_LOANS_QUERY);

			while (result.next()) {
				UserLoanDTO userloan = new UserLoanDTO();

				userloan.setUserEmail(result.getString(1));
				userloan.setLoanId(result.getInt(2));
				userloan.setAmountNeeded(result.getDouble(3));
				userloan.setLastDateReviewed(result.getDate(4));
				userloan.setLastTimeReviewed(result.getTime(5));
				userloan.setStatus(result.getString(6));

				list.add(userloan);
			}
			userLoan = list.toArray(new UserLoanDTO[0]);
		} catch (SQLException e) {
			throw new SQLException("Error fetching user loan data", e);
		}

		return userLoan;
	}

	// Update loan status
	public static void updateLoanStatus(UserDTO user, LoanDTO loan, Date date, Time time, String status) throws SQLException {
		String UPDATE_USER_LOAN_STATUS_QUERY = "UPDATE UserLoan SET status = ?, Date = ?, Time = ? WHERE userEmail = ? AND loanId = ?";

		try (PreparedStatement statement = databaseConnection.prepareStatement(UPDATE_USER_LOAN_STATUS_QUERY)) {
			statement.setString(1, status);
			statement.setDate(2, date);
			statement.setTime(3, time);
			statement.setString(4, user.getUserEmail());
			statement.setInt(5, loan.getLoanId());

			statement.executeUpdate();
		} catch (SQLException e) {
			throw new SQLException("Error updating loan status for user: " + user.getUserEmail() + " and loan ID: " + loan.getLoanId(), e);
		}
	}
}
