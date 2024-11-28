package main.java.org.example;


import java.util.Stack;

import main.java.org.example.controllers.AdminPanelController;
import main.java.org.example.controllers.UserPanelController;

import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import animatefx.animation.FadeInLeft;
import animatefx.animation.SlideInLeft;
import animatefx.animation.ZoomIn;

public class Screens extends Application{
	/*public static void browseLoans(User user, Loan[] loans) {
		System.out.println(user.toString() + " is browsing loans...");
		for (Loan loan : loans) {
			if (Math.random() > 0.5) {
				user.applyLoan(loan, Math.random() * 1000000);
			}
		}
	}

	public static void reviewLoans(Admin admin, UserLoan[] userloans) {
		
		System.out.println(admin.getEmail() + " is reviewing loans...");
		
		for (UserLoan userloan : userloans) {
			
			User Applicant = UserManager.getInstance().getUser(userloan.getUserId());
			Loan loan = LoanManager.getInstance().getLoan(userloan.getLoanId());
			double amountNeeded = userloan.getAmountNeeded();
			String status = userloan.getStatus();

			if (status.equals(LoanStatus.UNKNOWN)) {
				int[] amountRange = loan.getAmountRange();
				if (amountNeeded <= amountRange[1] && amountNeeded >= amountRange[0]) {
					
					String securityPossesed = Integer.toString(Applicant.getSecurityPossesed()); // p-np-co
					String securityNeeded = loan.getSecurityDemand();
					
					switch (securityNeeded) {
						case Security.PERSONAL:
							if (securityPossesed.charAt(0) == '1') {
								int[] ageRange = loan.getAgeRange();
								if (Applicant.getIncome() < loan.getMinIncome() || Applicant.getAge() > ageRange[1]
										|| Applicant.getAge() < ageRange[0]) {
									admin.rejectLoan(Applicant, loan);
								} else {
									admin.sanctionLoan(Applicant, loan);
								}
							} else {
								admin.rejectLoan(Applicant, loan);
							}
							break;
	
						case Security.NON_PERSONAL:
							if (securityPossesed.charAt(1) == '1') {
								int[] ageRange = loan.getAgeRange();
								if (Applicant.getIncome() < loan.getMinIncome() || Applicant.getAge() > ageRange[1]
										|| Applicant.getAge() < ageRange[0]) {
									admin.rejectLoan(Applicant, loan);
								} else {
									admin.sanctionLoan(Applicant, loan);
								}
							} else {
								admin.rejectLoan(Applicant, loan);
							}
							break;
	
						case Security.COLLATERAL:
							if (securityPossesed.charAt(2) == '1') {
								int[] ageRange = loan.getAgeRange();
								if (Applicant.getIncome() < loan.getMinIncome() || Applicant.getAge() > ageRange[1]
										|| Applicant.getAge() < ageRange[0]) {
									admin.rejectLoan(Applicant, loan);
								} else {
									admin.sanctionLoan(Applicant, loan);
								}
							} else {
								admin.rejectLoan(Applicant, loan);
							}
							break;
					}

				} else {
					admin.rejectLoan(Applicant, loan);
				}
			}
		}
	}

	*/ //Simulating user interaction prior to GUI development

	private static Stack<String> sceneStack = new Stack<>();
	private static Stage primaryStage;

	public static Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void setPrimaryStage(Stage primaryStage) {
		Screens.primaryStage = primaryStage;
	}

	public static void launchApplication(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setPrimaryStage(primaryStage);
		loadHeadScreen();
	}

	private void loadHeadScreen() throws Exception {
		loadScreen("Resources/fxml/HeadScreen.fxml", "Resources/stylesheets/HeadScreen.css",
				"Welcome", "HeadScreen", new ZoomIn());
	}

	public void loadUserLoginScreen() throws Exception {
		loadScreen("Resources/fxml/UserLogin.fxml", "Resources/stylesheets/UserLogin.css",
				"User-Login", "UserLogin", new SlideInLeft());
	}

	public void loadAdminLoginScreen() throws Exception {
		loadScreen("Resources/fxml/AdminLogin.fxml", "Resources/stylesheets/AdminLogin.css",
				"Admin-Login", "AdminLogin", new SlideInLeft());
	}

	public void loadRegistrationScreen() throws Exception {
		loadScreen("Resources/fxml/RegistrationScreen.fxml", "Resources/stylesheets/RegistrationScreen.css",
				"Register", "RegistrationScreen", new FadeInLeft());
	}

	public void loadUserScreen() throws Exception {
		String title = "Welcome " + UserPanelController.getUser().getFirstName();
		loadScreen("Resources/fxml/UserScreen.fxml", "Resources/stylesheets/UserScreen.css",
				title, "UserScreen", new ZoomIn());
	}

	public void loadAdminScreen() throws Exception {
		String title = "Welcome " + AdminPanelController.getAdmin().getFirstName();
		loadScreen("Resources/fxml/AdminScreen.fxml", "Resources/stylesheets/AdminScreen.css",
				title, "AdminScreen", new ZoomIn());
	}

	private void loadScreen(String fxmlPath, String cssPath, String title, String sceneName, animatefx.animation.AnimationFX effect) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

		// Wrap the root in a ScrollPane to enable scrolling
		ScrollPane scrollPane = new ScrollPane(root);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		Scene scene = new Scene(scrollPane, 1100, 643); // Fixed width and height
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

		primaryStage.setTitle(title);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		sceneStack.push(sceneName);
		effect.setNode(root);
		effect.play();

		primaryStage.show();
	}

	public void back() throws Exception {
		sceneStack.pop();
		switch (sceneStack.pop()) {
			case "HeadScreen":
				loadHeadScreen();
				break;
			case "UserLogin":
				loadUserLoginScreen();
				break;
			case "AdminLogin":
				loadAdminLoginScreen();
				break;
			case "Register":
				loadRegistrationScreen();
				break;
			case "UserScreen":
				loadUserScreen();
				break;
			case "AdminScreen":
				loadAdminScreen();
				break;
			default:
				break;
		}
	}
}