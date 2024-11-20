package main.java.org.example.controllers;

import java.sql.SQLException;

import main.java.org.example.constants.MessageLabels;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.service.UserService;

public class UserProcessor {
	private UserProcessor() {}
	
	private static final UserProcessor instance = new UserProcessor();
	
	public static UserProcessor getInstance() {
		return instance;
	}


	public String handleAdminLogin(String userEmail, String userPassword) throws SQLException {
		UserDTO user = UserService.getUserServiceInstance().getUserByEmail(userEmail);

		if(user instanceof AdminDTO) {
			if(!user.getUserPassword().equals(userPassword)) {
				return MessageLabels.INCORRECT_PASSWORD_LABEL;
			} else {
				return MessageLabels.SUCCESS_LABEL;
			}
		} else {
			return MessageLabels.ADMIN_NOT_FOUND_LABEL;
		}
	}

	public String handleUserLogin(String userEmail, String userPassword) throws SQLException {
		UserDTO user = UserService.getUserServiceInstance().getUserByEmail(userEmail);
		
		if(user!=null) {
			if(!user.getUserPassword().equals(userPassword)) {
				return MessageLabels.INCORRECT_PASSWORD_LABEL;
			} else {
				return MessageLabels.SUCCESS_LABEL;
			}
		} else {
			return MessageLabels.USER_NOT_FOUND_LABEL;
		}
	}

}
