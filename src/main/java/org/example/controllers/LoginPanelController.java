/***
 * 		Alternate versions of userLogin() and adminLogin() are also given in case 
 * 		the user wants to integrate the code with a database.
 */
package main.java.org.example.controllers;

import java.util.Optional;
import main.java.org.example.Screens;
import main.java.org.example.constants.Message;
import main.java.org.example.entities.AdminDTO;
import main.java.org.example.managers.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class LoginPanelController {
	@FXML
	public TextField email;
	@FXML
	public PasswordField password;
	
	public Alert alert = new Alert(AlertType.NONE);
	
//	@FXML
//	public void userLogin() throws Exception {
//		new View().loadUserScreen();
//	}
//
//	@FXML
//	public void adminLogin() throws Exception {
//		new View().loadAdminScreen();
//	}

	/***
	 * @throws Exception
	 * 
	 * 		userLogin() accepts no argument and does the task of getting the user credentials
	 * 		from the user login screen and verifying it with the backend database and if found
	 * 		correct loading the user screen.
	 */

	@FXML
	public void userLogin() throws Exception {
		String Email = email.getText();
		String Password = password.getText();
		String loginStatus = UserProcessor.getInstance().userLogin(Email, Password);
		

		if (loginStatus.equals(Message.SUCCESS)) {
			UserPanelController.setUser(UserManager.getInstance().getUser(Email));
			new Screens().loadUserScreen();
			
		} else if (loginStatus.equals(Message.INCORRECT_PASSWORD)) {
			password.clear();
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText(Message.INCORRECT_PASSWORD);
			alert.setContentText("Try Again!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
			    
		} else {
			email.clear();
			password.clear();
			alert.setAlertType(AlertType.CONFIRMATION);
			alert.setHeaderText(Message.USER_NOT_FOUND);
			alert.setContentText("Register ?");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			
			Optional<ButtonType> result = alert.showAndWait();
			
			if(result.isPresent() && result.get() == ButtonType.OK) {
				new Screens().loadRegistrationScreen();
			}
		}
	}

	
	/***
	 * @throws Exception
	 * 
	 * 		adminLogin() accepts no argument and does the task of getting the admin credentials
	 * 		from the admin login screen and verifying it with the backend database and if found
	 * 		correct loading the admin screen.
	 */


	@FXML
	public void adminLogin() throws Exception {
		String Email = email.getText();
		String Password = password.getText();
		String loginStatus = UserProcessor.getInstance().adminLogin(Email, Password);

		if (loginStatus.equals(Message.SUCCESS)) {
			AdminPanelController.setAdmin((AdminDTO)UserManager.getInstance().getUser(Email));
			new Screens().loadAdminScreen();
			
		} else if (loginStatus.equals(Message.INCORRECT_PASSWORD)) {
			password.clear();
			alert.setAlertType(AlertType.ERROR);
			alert.setHeaderText(Message.INCORRECT_PASSWORD);
			alert.setContentText("Try Again!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
			
		} else {
			email.clear();
			password.clear();
			alert.setAlertType(AlertType.WARNING);
			alert.setHeaderText(Message.ADMIN_NOT_FOUND);
			alert.setContentText("Make sure that the Username and Password you entered are correct.");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.showAndWait();
		}
	}


	@FXML
	public void back() throws Exception {
		new Screens().back();
	}
}
