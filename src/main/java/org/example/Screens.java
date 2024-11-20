package main.java.org.example;

import java.util.Stack;

import main.java.org.example.controllers.AdminScreenController;
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

public class Screens extends Application {

	private static final Stack<String> screenHistory = new Stack<>();
	private static Stage primaryStage;

	public static void setPrimaryStage(Stage stage) {
		Screens.primaryStage = stage;
	}

	public static void launchApplication(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		setPrimaryStage(stage);
		loadInitialScreen();
	}

	private void loadInitialScreen() throws Exception {
		navigateToScreen("Resources/fxml/HeadScreen.fxml", "Resources/stylesheets/HeadScreen.css",
				"Welcome", "HeadScreen", new ZoomIn());
	}

	public void showUserLoginScreen() throws Exception {
		navigateToScreen("Resources/fxml/UserLogin.fxml", "Resources/stylesheets/UserLogin.css",
				"User Login", "UserLogin", new SlideInLeft());
	}

	public void showAdminLoginScreen() throws Exception {
		navigateToScreen("Resources/fxml/AdminLogin.fxml", "Resources/stylesheets/AdminLogin.css",
				"Admin Login", "AdminLogin", new SlideInLeft());
	}

	public void showRegistrationScreen() throws Exception {
		navigateToScreen("Resources/fxml/RegistrationScreen.fxml", "Resources/stylesheets/RegistrationScreen.css",
				"Register", "RegistrationScreen", new FadeInLeft());
	}

	public void showUserDashboard() throws Exception {
		String title = "Welcome " + UserPanelController.getUser().getFirstName();
		navigateToScreen("Resources/fxml/UserScreen.fxml", "Resources/stylesheets/UserScreen.css",
				title, "UserScreen", new ZoomIn());
	}

	public void showAdminDashboard() throws Exception {
		String title = "Welcome " + AdminScreenController.getAdminDTO().getFirstName();
		navigateToScreen("Resources/fxml/AdminScreen.fxml", "Resources/stylesheets/AdminScreen.css",
				title, "AdminScreen", new ZoomIn());
	}

	private void navigateToScreen(String fxmlPath, String cssPath, String windowTitle, String screenName, animatefx.animation.AnimationFX animationEffect) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));

		// ScrollPane wrapping for better display
		ScrollPane scrollPane = new ScrollPane(root);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);

		Scene scene = new Scene(scrollPane, 1100, 643); // Fixed dimensions
		scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

		primaryStage.setTitle(windowTitle);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);

		screenHistory.push(screenName);
		animationEffect.setNode(root);
		animationEffect.play();

		primaryStage.show();
	}

	public void goBack() throws Exception {
		if (!screenHistory.isEmpty()) {
			screenHistory.pop();
		}

		if (!screenHistory.isEmpty()) {
			switch (screenHistory.peek()) {
				case "HeadScreen":
					loadInitialScreen();
					break;
				case "UserLogin":
					showUserLoginScreen();
					break;
				case "AdminLogin":
					showAdminLoginScreen();
					break;
				case "Register":
					showRegistrationScreen();
					break;
				case "UserScreen":
					showUserDashboard();
					break;
				case "AdminScreen":
					showAdminDashboard();
					break;
				default:
					break;
			}
		}
	}

    /*
    public static void browseLoans(User user, Loan[] loans) {
        System.out.println(user.toString() + " is browsing loans...");
        for (Loan loan : loans) {
            if (Math.random() > 0.5) {
                user.applyLoan(loan, Math.random() * 1000000);
            }
        }
    }

    public static void reviewLoans(Admin admin, UserLoan[] userLoans) {

        System.out.println(admin.getEmail() + " is reviewing loans...");

        for (UserLoan userLoan : userLoans) {

            User applicant = UserManager.getInstance().getUser(userLoan.getUserId());
            Loan loan = LoanManager.getInstance().getLoan(userLoan.getLoanId());
            double amountRequired = userLoan.getAmountNeeded();
            String status = userLoan.getStatus();

            if (status.equals(LoanStatus.UNKNOWN)) {
                int[] amountRange = loan.getAmountRange();
                if (amountRequired <= amountRange[1] && amountRequired >= amountRange[0]) {

                    String userSecurity = Integer.toString(applicant.getSecurityPossessed()); // p-np-co
                    String requiredSecurity = loan.getSecurityDemand();

                    switch (requiredSecurity) {
                        case Security.PERSONAL:
                            if (userSecurity.charAt(0) == '1') {
                                int[] ageRange = loan.getAgeRange();
                                if (applicant.getIncome() < loan.getMinIncome() || applicant.getAge() > ageRange[1]
                                        || applicant.getAge() < ageRange[0]) {
                                    admin.rejectLoan(applicant, loan);
                                } else {
                                    admin.approveLoan(applicant, loan);
                                }
                            } else {
                                admin.rejectLoan(applicant, loan);
                            }
                            break;

                        case Security.NON_PERSONAL:
                            if (userSecurity.charAt(1) == '1') {
                                int[] ageRange = loan.getAgeRange();
                                if (applicant.getIncome() < loan.getMinIncome() || applicant.getAge() > ageRange[1]
                                        || applicant.getAge() < ageRange[0]) {
                                    admin.rejectLoan(applicant, loan);
                                } else {
                                    admin.approveLoan(applicant, loan);
                                }
                            } else {
                                admin.rejectLoan(applicant, loan);
                            }
                            break;

                        case Security.COLLATERAL:
                            if (userSecurity.charAt(2) == '1') {
                                int[] ageRange = loan.getAgeRange();
                                if (applicant.getIncome() < loan.getMinIncome() || applicant.getAge() > ageRange[1]
                                        || applicant.getAge() < ageRange[0]) {
                                    admin.rejectLoan(applicant, loan);
                                } else {
                                    admin.approveLoan(applicant, loan);
                                }
                            } else {
                                admin.rejectLoan(applicant, loan);
                            }
                            break;
                    }

                } else {
                    admin.rejectLoan(applicant, loan);
                }
            }
        }
    }
    */
}
