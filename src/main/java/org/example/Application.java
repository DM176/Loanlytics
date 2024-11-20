package main.java.org.example;

public class Application {

	private static final String DB_URL = "jdbc:mysql://localhost/lms_data";  // Database URL (update with actual details)
	private static final String DB_USER = "test";  // Database username
	private static final String DB_PASSWORD = "123qweasd!@#";  // Database password

	public static void main(String[] args) {
		initializeData();
		launchApp(args);
	}

	private static void initializeData() {
		DatabaseManager.loadData(DB_URL, DB_USER, DB_PASSWORD);
	}

	private static void launchApp(String[] args) {
		Screens.launchApplication(args);
	}
}
