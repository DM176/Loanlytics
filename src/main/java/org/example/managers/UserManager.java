package main.java.org.example.managers;

import java.sql.SQLException;

import main.java.org.example.dao.UserRepository;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.entities.User;

public class UserManager {
	private UserManager() {
	}

	public static UserManager instance = new UserManager();
	public static UserRepository dao = new UserRepository();

	public static UserManager getInstance() {
		return instance;
	}

	public User createUser(String firstName, String lastName, int age, String email, String password, String gender,
			int securityPossesed, double creditScore, double income) {
		User user = new User();

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAge(age);
		user.setEmail(email);
		user.setPassword(password);
		user.setGender(gender);
		user.setSecurityPossesed(securityPossesed);
		user.setCreditScore(creditScore);
		user.setIncome(income);

		return user;
	}

	public AdminDTO createAdmin(String firstName, String lastName, int age, String email, String password, String gender,
								int securityPossesed, double creditScore, double income) {
		AdminDTO adminDTO = new AdminDTO();

		adminDTO.setFirstName(firstName);
		adminDTO.setLastName(lastName);
		adminDTO.setAge(age);
		adminDTO.setEmail(email);
		adminDTO.setPassword(password);
		adminDTO.setGender(gender);
		adminDTO.setSecurityPossesed(securityPossesed);
		adminDTO.setCreditScore(creditScore);
		adminDTO.setIncome(income);

		return adminDTO;
	}

	public void addUser(User user) throws SQLException {
		dao.addUser(user);
	}

	public User getUser(String email) throws SQLException {
		return dao.getUser(email);
	}

	public User[] getUsers() throws SQLException {
		return dao.getUsers();
	}
}
