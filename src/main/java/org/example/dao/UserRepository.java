package main.java.org.example.dao;

import java.sql.SQLException;

import main.java.org.example.DatabaseController;
import main.java.org.example.entities.User;

public class UserRepository {
	public User[] getUsers() throws SQLException{
		return DatabaseController.getUsers();
	}

	public void addUser(User user) throws SQLException {
		DatabaseController.addUser(user);
	}
	
	public User getUser(String email) throws SQLException {
		return DatabaseController.getUser(email);
	}
}
