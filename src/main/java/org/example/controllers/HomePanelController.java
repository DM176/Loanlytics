package main.java.org.example.controllers;

import main.java.org.example.Screens;
import javafx.fxml.FXML;

public class HomePanelController {
	
	@FXML
	public void loadUserLoginScreen() throws Exception {
		new Screens().loadUserLoginScreen();
	}
	
	@FXML
	public void loadAdminLoginScreen() throws Exception {
		new Screens().loadAdminLoginScreen();
	}
	
	@FXML
	public void loadRegistrationScreen() throws Exception {
		new Screens().loadRegistrationScreen();
	}
}
