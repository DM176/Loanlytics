package main.java.org.example.controllers;

import java.sql.SQLException;

import main.java.org.example.constants.Message;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.entities.User;
import main.java.org.example.managers.UserManager;

public class UserProcessor {
	private UserProcessor() {}
	
	private static UserProcessor instance = new UserProcessor();
	
	public static UserProcessor getInstance() {
		return instance;
	}
	
	public String userLogin(String email, String password) throws SQLException {
		User user = UserManager.getInstance().getUser(email);
		
		if(user!=null) {
			if(!user.getPassword().equals(password)) {
				return Message.INCORRECT_PASSWORD;
			}
			else {
				return Message.SUCCESS;
			}
		}
		else {
			return Message.USER_NOT_FOUND;
		}
	}

	public String adminLogin(String email, String password) throws SQLException {
		User user = UserManager.getInstance().getUser(email);
		
		if(user!=null && user instanceof AdminDTO) {
			if(!user.getPassword().equals(password)) {
				return Message.INCORRECT_PASSWORD;
			}
			else {
				return Message.SUCCESS;
			}
		}
		else {
			return Message.USER_NOT_FOUND;
		}
	}
}
