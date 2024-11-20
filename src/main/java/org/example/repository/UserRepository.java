package main.java.org.example.repository;

import java.sql.SQLException;

import main.java.org.example.DatabaseManager;
import main.java.org.example.entities.UserDTO;

public class UserRepository {

	public void addNewUser(UserDTO user) throws SQLException {
		DatabaseManager.addUser(user);
	}
	
	public UserDTO getUserByEmail(String email) throws SQLException {
		return DatabaseManager.getUser(email);
	}
}
