package main.java.org.example.controllers;

import java.util.Optional;

import main.java.org.example.Screens;
import main.java.org.example.constants.MessageLabels;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

public class LoginPanelController {

	@FXML private TextField email;
	@FXML private PasswordField password;

	private final Alert alert = new Alert(Alert.AlertType.NONE);

	/**
	 * Handles user login functionality. Validates user credentials and navigates
	 * to the user dashboard upon successful login or shows appropriate alerts otherwise.
	 */
	@FXML
	public void userLogin() {
		String userEmail = email.getText();
		String userPassword = password.getText();

		if (validateInput(userEmail, userPassword)) {
			try {
				String loginStatus = UserProcessor.getInstance().handleUserLogin(userEmail, userPassword);

				switch (loginStatus) {
					case MessageLabels.SUCCESS_LABEL:
						handleSuccessfulUserLogin(userEmail);
						break;

					case MessageLabels.INCORRECT_PASSWORD_LABEL:
						handleIncorrectPassword();
						break;

					default:
						handleUserNotFound();
				}
			} catch (Exception e) {
				showErrorAlert("Unexpected Error", "An error occurred during user login.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Handles admin login functionality. Validates admin credentials and navigates
	 * to the admin dashboard upon successful login or shows appropriate alerts otherwise.
	 */
	@FXML
	public void adminLogin() {
		String adminEmail = email.getText();
		String adminPassword = password.getText();

		if (validateInput(adminEmail, adminPassword)) {
			try {
				String loginStatus = UserProcessor.getInstance().handleAdminLogin(adminEmail, adminPassword);

				switch (loginStatus) {
					case MessageLabels.SUCCESS_LABEL:
						handleSuccessfulAdminLogin(adminEmail);
						break;

					case MessageLabels.INCORRECT_PASSWORD_LABEL:
						handleIncorrectPassword();
						break;

					default:
						handleAdminNotFound();
				}
			} catch (Exception e) {
				showErrorAlert("Unexpected Error", "An error occurred during admin login.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * Navigates back to the previous screen.
	 */
	@FXML
	public void back() {
		try {
			new Screens().goBack();
		} catch (Exception e) {
			showErrorAlert("Navigation Error", "Unable to navigate to the previous screen.");
			e.printStackTrace();
		}
	}

	// Helper methods for login handling

	private void handleSuccessfulUserLogin(String userEmail) throws Exception {
		UserPanelController.setUser(UserService.getUserServiceInstance().getUserByEmail(userEmail));
		new Screens().showUserDashboard();
	}

	private void handleSuccessfulAdminLogin(String adminEmail) throws Exception {
		AdminScreenController.setAdminDTO((AdminDTO) UserService.getUserServiceInstance().getUserByEmail(adminEmail));
		new Screens().showAdminDashboard();
	}

	private void handleIncorrectPassword() {
		password.clear();
		showErrorAlert(MessageLabels.INCORRECT_PASSWORD_LABEL, "Try Again!");
	}

	private void handleUserNotFound() {
		email.clear();
		password.clear();
		showConfirmationAlert(
				MessageLabels.USER_NOT_FOUND_LABEL,
				"Register?",
				() -> {
					try {
						new Screens().showRegistrationScreen();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
		);
	}

	private void handleAdminNotFound() {
		email.clear();
		password.clear();
		showWarningAlert(
				MessageLabels.ADMIN_NOT_FOUND_LABEL,
				"Make sure that the Username and Password you entered are correct."
		);
	}

	// Input validation
	private boolean validateInput(String email, String password) {
		if (email == null || email.trim().isEmpty()) {
			showErrorAlert("Validation Error", "Email cannot be empty.");
			return false;
		}

		if (password == null || password.trim().isEmpty()) {
			showErrorAlert("Validation Error", "Password cannot be empty.");
			return false;
		}

		return true;
	}

	// Alert helper methods
	private void showErrorAlert(String header, String content) {
		alert.setAlertType(Alert.AlertType.ERROR);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}

	private void showWarningAlert(String header, String content) {
		alert.setAlertType(Alert.AlertType.WARNING);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}

	private void showConfirmationAlert(String header, String content, Runnable onConfirmation) {
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			onConfirmation.run();
		}
	}
}
