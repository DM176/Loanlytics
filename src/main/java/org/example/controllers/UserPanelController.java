package main.java.org.example.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import main.java.org.example.Screens;
import main.java.org.example.constants.LoanTypeLabels;
import main.java.org.example.entities.LoanDTO;
import main.java.org.example.entities.LoanModelDTO;
import main.java.org.example.entities.UserDTO;
import main.java.org.example.service.LoanService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class UserPanelController implements Initializable {

	@FXML
	private Button btnHomeLoans, btnEducationLoans, btnCarLoans, btnMyLoans;
	@FXML
	private Label lblButtonInfo;
	@FXML
	private Pane pnButtonInfo;
	@FXML
	private GridPane educationLoansGrid, carLoansGrid, homeLoansGrid;
	@FXML
	private TableView<LoanModelDTO> homeLoansTable, educationLoansTable, carLoansTable;
	@FXML
	private TableColumn<LoanModelDTO, Integer> HloanUniqueId, EloanUniqueId, CloanUniqueId;
	@FXML
	private TableColumn<LoanModelDTO, String> Hsource, HamountRange, HsecurityDemand, HageRange, Htenure;
	@FXML
	private TableColumn<LoanModelDTO, Double> HinterestRate, HminimumIncome;
	@FXML
	private TableColumn<LoanModelDTO, String> Esource, EamountRange, EsecurityDemand, EageRange, Etenure;
	@FXML
	private TableColumn<LoanModelDTO, Double> EinterestRate, EminimumIncome;
	@FXML
	private TableColumn<LoanModelDTO, String> Csource, CamountRange, CsecurityDemand, CageRange, Ctenure;
	@FXML
	private TableColumn<LoanModelDTO, Double> CinterestRate, CminimumIncome;
	@FXML
	private TextField HtextFieldId, HtextFieldAmount, EtextFieldId, EtextFieldAmount, CtextFieldId, CtextFieldAmount;
	@FXML
	private TextField resultField;

	private static UserDTO user = new UserDTO();

	public static UserDTO getUser() {
		return user;
	}

	public static void setUser(UserDTO newUser) {
		user = newUser;
	}

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		initializeTableColumns();
	}

	private void initializeTableColumns() {
		configureHomeLoanColumns();
		configureEducationLoanColumns();
		configureCarLoanColumns();
	}

	private void configureHomeLoanColumns() {
		HloanUniqueId.setCellValueFactory(new PropertyValueFactory<>("loanUniqueId"));
		Hsource.setCellValueFactory(new PropertyValueFactory<>("source"));
		HamountRange.setCellValueFactory(new PropertyValueFactory<>("amountRange"));
		HsecurityDemand.setCellValueFactory(new PropertyValueFactory<>("securityDemand"));
		HinterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
		HminimumIncome.setCellValueFactory(new PropertyValueFactory<>("minimumIncome"));
		HageRange.setCellValueFactory(new PropertyValueFactory<>("ageRange"));
		Htenure.setCellValueFactory(new PropertyValueFactory<>("tenure"));
	}

	private void configureEducationLoanColumns() {
		EloanUniqueId.setCellValueFactory(new PropertyValueFactory<>("loanUniqueId"));
		Esource.setCellValueFactory(new PropertyValueFactory<>("source"));
		EamountRange.setCellValueFactory(new PropertyValueFactory<>("amountRange"));
		EsecurityDemand.setCellValueFactory(new PropertyValueFactory<>("securityDemand"));
		EinterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
		EminimumIncome.setCellValueFactory(new PropertyValueFactory<>("minimumIncome"));
		EageRange.setCellValueFactory(new PropertyValueFactory<>("ageRange"));
		Etenure.setCellValueFactory(new PropertyValueFactory<>("tenure"));
	}

	private void configureCarLoanColumns() {
		CloanUniqueId.setCellValueFactory(new PropertyValueFactory<>("loanUniqueId"));
		Csource.setCellValueFactory(new PropertyValueFactory<>("source"));
		CamountRange.setCellValueFactory(new PropertyValueFactory<>("amountRange"));
		CsecurityDemand.setCellValueFactory(new PropertyValueFactory<>("securityDemand"));
		CinterestRate.setCellValueFactory(new PropertyValueFactory<>("interestRate"));
		CminimumIncome.setCellValueFactory(new PropertyValueFactory<>("minimumIncome"));
		CageRange.setCellValueFactory(new PropertyValueFactory<>("ageRange"));
		Ctenure.setCellValueFactory(new PropertyValueFactory<>("tenure"));
	}

	@FXML
	public void loadHomeLoans(ActionEvent event) throws SQLException {
		displayLoans(event, homeLoansGrid, homeLoansTable, LoanTypeLabels.HOME_LOAN_LABELS);
	}

	@FXML
	public void loadEducationLoans(ActionEvent event) throws SQLException {
		displayLoans(event, educationLoansGrid, educationLoansTable, LoanTypeLabels.EDUCATION_LOAN_LABELS);
	}

	@FXML
	public void loadCarLoans(ActionEvent event) throws SQLException {
		displayLoans(event, carLoansGrid, carLoansTable, LoanTypeLabels.CAR_LOAN_LABELS);
	}

	private void displayLoans(ActionEvent event, GridPane gridPane, TableView<LoanModelDTO> tableView, String loanType) throws SQLException {
		setLabelAndBackground(event);
		gridPane.toFront();
		LoanDTO[] loans = LoanService.getLoanServiceInstance().getLoansByLoanType(loanType);
		ObservableList<LoanModelDTO> loanList = FXCollections.observableArrayList();

		for (LoanDTO loan : loans) {
			LoanModelDTO model = createLoanModel(loan);
			loanList.add(model);
		}

		tableView.setItems(loanList);
	}

	private LoanModelDTO createLoanModel(LoanDTO loan) {
		return new LoanModelDTO(
				loan.getLoanId(),
				loan.getFundingSource(),
				integerRangeToString(loan.getLoanAmountRange()),
				loan.getSecurityDemand(),
				loan.getRateOfInterest(),
				loan.getMinimumIncome(),
				integerRangeToString(loan.getEligibleAgeRange()),
				integerRangeToString(loan.getRepaymentDuration())
		);
	}

	@FXML
	public void handleLogout() throws Exception {
		if (confirmLogout()) {
			setUser(null);
			new Screens().goBack();
		}
	}

	private boolean confirmLogout() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText("Logout");
		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent() && result.get() == ButtonType.OK;
	}

	public static String integerRangeToString(int[] range) {
		if (range[1] == -1) {
			return range[0] + " and above";
		}
		return range[0] + " - " + range[1];
	}

	public static String securityPossessedToString(int securityPossessed) {
		String[] labels = {"Personal", "Non-Personal", "Collateral"};
		StringBuilder result = new StringBuilder();
		String binaryString = String.format("%03d", Integer.parseInt(Integer.toBinaryString(securityPossessed)));

		for (int i = 0; i < binaryString.length(); i++) {
			if (binaryString.charAt(i) == '1') {
				if (result.length() > 0) result.append(", ");
				result.append(labels[i]);
			}
		}

		return result.toString();
	}

	private void setLabelAndBackground(ActionEvent event) {
		if (event.getSource() == btnHomeLoans) {
			updateLabelAndColor("Home Loans", Color.rgb(188, 19, 254));
		} else if (event.getSource() == btnEducationLoans) {
			updateLabelAndColor("Education Loans", Color.rgb(229, 96, 36));
		} else if (event.getSource() == btnCarLoans) {
			updateLabelAndColor("Car Loans", Color.rgb(143, 0, 241));
		} else if (event.getSource() == btnMyLoans) {
			updateLabelAndColor("My Loans", Color.rgb(6, 194, 172));
		}
	}

	private void updateLabelAndColor(String label, Color color) {
		lblButtonInfo.setText(label);
		pnButtonInfo.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
	}
}
