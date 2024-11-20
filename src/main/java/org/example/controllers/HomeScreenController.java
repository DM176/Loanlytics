package main.java.org.example.controllers;

import main.java.org.example.Screens;
import javafx.fxml.FXML;

public class HomeScreenController {

	/**
	 * Loads the user login screen.
	 */
	@FXML
	public void showUserLoginScreen() {
		navigateToScreen("User Login", () -> {
            try {
                new Screens().showUserLoginScreen();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
	}

	/**
	 * Loads the admin login screen.
	 */
	@FXML
	public void showAdminLoginScreen() {
		navigateToScreen("Admin Login", () -> {
            try {
                new Screens().showAdminLoginScreen();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
	}

	/**
	 * Loads the registration screen.
	 */
	@FXML
	public void showRegisterationScreen() {
		navigateToScreen("Registration", () -> {
            try {
                new Screens().showRegistrationScreen();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
	}

	/**
	 * Helper method to handle navigation to different screens.
	 * Provides centralized exception handling for screen transitions.
	 *
	 * @param screenName Name of the screen for logging or debugging purposes.
	 * @param action     The navigation logic encapsulated in a {@code Runnable}.
	 */
	private void navigateToScreen(String screenName, Runnable action) {
		try {
			action.run();
		} catch (Exception e) {
			System.err.println("Failed to load the " + screenName + " screen.");
			e.printStackTrace();
		}
	}
}
