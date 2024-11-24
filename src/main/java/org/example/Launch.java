package main.java.org.example;

public class Launch {
	private static final String DATABASE = "jdbc:mysql://localhost/lms_data"; //corresponding server address and database name should be assigned
	private static final String USERNAME = "test";	//username for login to the server should be assigned
	private static final String PASSWORD = "123qweasd!@#"; //password for login to the server should be assigned

	public static void main(String[] args) {
		loadData();
		start(args);
	}

	private static void loadData() {
		DataStore.loadData(DATABASE, USERNAME, PASSWORD);
	}
	
	private static void start(String[] args) {
		View.launchApplication(args);
	}
}
