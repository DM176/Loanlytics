package main.java.org.example.controllers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import main.java.org.example.Screens;
import main.java.org.example.constants.GenderLabels;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.service.UserService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;

public class RegisterationPanelController {

	private final Alert alert = new Alert(Alert.AlertType.NONE);

	@FXML private TextField firstName;
	@FXML private TextField lastName;
	@FXML private TextField age;
	@FXML private TextField income;
	@FXML private TextField email;
	@FXML private PasswordField password;
	@FXML private TextField creditScore;
	@FXML private MenuButton gender;
	@FXML private CheckBox personal;
	@FXML private CheckBox nonPersonal;
	@FXML private CheckBox collateral;


	/**
	 * Handles user registration after validating all input fields.
	 */
	@FXML
	public void register() {
		try {
			if (isInputValid()) {
				UserDTO user = createUserFromInput();
				saveUser(user);
			}
		} catch (Exception e) {
			showErrorAlert("Unexpected Error", "An error occurred during registration.");
			e.printStackTrace();
		}
	}

	/**
	 * Validates user input fields.
	 * @return true if all fields are valid, false otherwise.
	 */
	private boolean isInputValid() {
		return getFirstName() != null
				&& getLastName() != null
				&& getAge() != -1
				&& getEmail() != null
				&& getPassword() != null
				&& getGender() != null
				&& getIncome() != -1
				&& getCreditScore() != -1;
	}

	/**
	 * Creates a UserDTO object using the validated input fields.
	 */
	private UserDTO createUserFromInput() {
		return UserService.getUserServiceInstance().createUserObject(
				getFirstName(),
				getLastName(),
				getAge(),
				getEmail(),
				getPassword(),
				getGender(),
				getSecurityPossessed(),
				getCreditScore(),
				getIncome()
		);
	}

	/**
	 * Saves the user to the database and handles potential errors.
	 * @param user UserDTO object to be saved.
	 */
	private void saveUser(UserDTO user) throws SQLException {
		try {
			UserService.getUserServiceInstance().addNewUser(user);
			handleSuccessfulRegistration();
		} catch (SQLIntegrityConstraintViolationException e) {
			handleExistingUserError();
		} catch (SQLException e) {
			showErrorAlert("Database Error", "Unable to save user to the database.");
			throw e;
		}
	}

	// Input getters with validation
	private String getFirstName() {
		return validateTextField(firstName, "First Name");
	}

	private String getLastName() {
		return validateTextField(lastName, "Last Name");
	}

	private int getAge() {
		return validateIntegerField(age, "Age");
	}

	private double getIncome() {
		return validateDoubleField(income, "Income");
	}

	private String getEmail() {
		return validateTextField(email, "Email");
	}

	private String getPassword() {
		return validateTextField(password, "Password");
	}

	private double getCreditScore() {
		return validateDoubleField(creditScore, "Credit Score");
	}

	private String getGender() {
		String selectedGender = gender.getText();
		if (selectedGender.equals(GenderLabels.MALE_LABEL) || selectedGender.equals(GenderLabels.FEMALE_LABEL) || selectedGender.equals(GenderLabels.TRANSGENDER_LABEL)) {
			return selectedGender;
		} else {
			showErrorAlert("Invalid Gender", "Please select a valid gender.");
			return null;
		}
	}

	private int getSecurityPossessed() {
		StringBuilder security = new StringBuilder();
		security.append(personal.isSelected() ? '1' : '0');
		security.append(nonPersonal.isSelected() ? '1' : '0');
		security.append(collateral.isSelected() ? '1' : '0');
		return Integer.parseInt(security.toString());
	}

	// Helper validation methods
	private String validateTextField(TextField field, String fieldName) {
		String text = field.getText();
		if (text.isEmpty()) {
			showErrorAlert("Validation Error", fieldName + " cannot be empty.");
			return null;
		}
		return text;
	}

	private int validateIntegerField(TextField field, String fieldName) {
		try {
			return Integer.parseInt(field.getText());
		} catch (NumberFormatException e) {
			showErrorAlert("Invalid Input", fieldName + " must be a valid integer.");
			field.clear();
			return -1;
		}
	}

	private double validateDoubleField(TextField field, String fieldName) {
		try {
			return Double.parseDouble(field.getText());
		} catch (NumberFormatException e) {
			showErrorAlert("Invalid Input", fieldName + " must be a valid number.");
			field.clear();
			return -1;
		}
	}

	// UI Handlers
	private void handleSuccessfulRegistration() {
		clearAllFields();
		showConfirmationAlert("Registration Successful", "Head to the login page?");
	}

	private void handleExistingUserError() {
		email.clear();
		showErrorAlert("Email Taken", "User with the provided email already exists. Please choose another email.");
	}

	private void clearAllFields() {
		firstName.clear();
		lastName.clear();
		age.clear();
		income.clear();
		email.clear();
		password.clear();
		creditScore.clear();
		gender.setText("Choose");
		personal.setSelected(false);
		nonPersonal.setSelected(false);
		collateral.setSelected(false);
	}

	// Alert helpers
	private void showErrorAlert(String header, String content) {
		alert.setAlertType(Alert.AlertType.ERROR);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}

	private void showConfirmationAlert(String header, String content) {
		alert.setAlertType(Alert.AlertType.CONFIRMATION);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		Optional<ButtonType> result = alert.showAndWait();
		result.ifPresent(button -> {
			if (button == ButtonType.OK) {
				navigateToLoginScreen();
			}
		});
	}

	@FXML
	public void setMenuButtonText(ActionEvent event) {
		MenuItem source = (MenuItem) event.getSource();
		gender.setText(source.getText());
	}

	@FXML
	public void navigateBack() throws Exception {
		new Screens().goBack();
	}

	private void navigateToLoginScreen() {
		try {
			new Screens().showUserLoginScreen();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
