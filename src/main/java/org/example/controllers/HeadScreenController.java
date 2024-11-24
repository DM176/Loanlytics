package main.java.org.example.controllers;

import main.java.org.example.View;
import javafx.fxml.FXML;

public class HeadScreenController {
	
	@FXML
	public void loadUserLoginScreen() throws Exception {
		new View().loadUserLoginScreen();
	}
	
	@FXML
	public void loadAdminLoginScreen() throws Exception {
		new View().loadAdminLoginScreen();
	}
	
	@FXML
	public void loadRegistrationScreen() throws Exception {
		new View().loadRegistrationScreen();
	}
}
