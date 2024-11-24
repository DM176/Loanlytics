package main.java.org.example.dao;

import java.sql.SQLException;

import main.java.org.example.DataStore;
import main.java.org.example.entities.User;

public class UserDao {
	public User[] getUsers() throws SQLException{
		return DataStore.getUsers();
	}

	public void addUser(User user) throws SQLException {
		DataStore.addUser(user);
	}
	
	public User getUser(String email) throws SQLException {
		return DataStore.getUser(email);
	}
}
