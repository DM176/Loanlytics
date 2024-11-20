package main.java.org.example.service;

import java.sql.SQLException;

import main.java.org.example.repository.UserRepository;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.entities.UserDTO;

public class UserService {
	private UserService() {
	}

	public static UserService userServiceInstance = new UserService();
	public static UserRepository userRepository = new UserRepository();

	public static UserService getUserServiceInstance() {
		return userServiceInstance;
	}

	public UserDTO createUserObject(String firstName, String lastName, int age, String email, String password, String gender,
									int securityPossesed, double creditScore, double income) {
		UserDTO user = new UserDTO();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUserAge(age);
		user.setUserEmail(email);
		user.setPassword(password);
		user.setGender(gender);
		user.setSecurityLevel(securityPossesed);
		user.setCreditScore(creditScore);
		user.setIncomeOfUser(income);

		return user;
	}

	public AdminDTO createAdminObject(String firstName, String lastName, int age, String email, String password, String gender,
									  int securityPossesed, double creditScore, double income) {
		AdminDTO admin = new AdminDTO();

		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		admin.setUserAge(age);
		admin.setUserEmail(email);
		admin.setPassword(password);
		admin.setGender(gender);
		admin.setSecurityLevel(securityPossesed);
		admin.setCreditScore(creditScore);
		admin.setIncomeOfUser(income);

		return admin;
	}

	public void addNewUser(UserDTO user) throws SQLException {
		userRepository.addNewUser(user);
	}

	public UserDTO getUserByEmail(String email) throws SQLException {
		return userRepository.getUserByEmail(email);
	}

}
