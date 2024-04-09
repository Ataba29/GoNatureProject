package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import departmentManagerGUI.dashboardDMController;
import entities.User;
import entities.Visitors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import parkmanagerGUI.dashboardPMController;
import serviceRrep.dashboardSRController;
import utilities.CONSTANTS;
import utilities.Message;
import visitorGUI.NewVisitorController;
import visitorGUI.OrderFormController;
import visitorGUI.dashboardVController;
import workerGUI.dashboardWController;

/**
 * class that creates the log in page
 */
public class LogInController {
	// version 1 start
	public static ClientController chat; // only one instance
	public static User user;
	public static Visitors visitor;
	public static boolean isLoginValid = false;
	public static boolean isLogged = false;

	@FXML
	private TextField usernametxt;
	@FXML
	private PasswordField passwordtxt;
	@FXML
	private Button loginbtn = null;
	@FXML
	private Button exitbtn = null;
	@FXML
	private Label errorLabel;

	/**
	 * initialize the login page by making the user a null
	 */
	@FXML
	void initialize() {
		user = null; // each time client is logout we reset the data and isLoginValid=false
		visitor = null;
		isLoginValid = false;
		isLogged = false;
	}

	/**
	 * get the text that was written in the userName textField
	 * 
	 * @return the userName that was entered
	 */
	private String getUsername() {
		return usernametxt.getText();
	}

	/**
	 * get the text that was written in the password textField
	 * 
	 * @return the password that was entered
	 */
	private String getPassword() {
		return passwordtxt.getText();
	}

	/**
	 * login button was clicked so log in the user based on his type
	 * 
	 * @param event button is clicked
	 * @throws IOException throws exception
	 */
	public void loginclient(ActionEvent event) throws IOException {
		String username = getUsername();
		String password = getPassword();

		// check if user is a guide or traveler
		if (password.isEmpty()) {
			// check if the visitor is logged in
			if (userIsLogged(username)) {
				Alert alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Warning Alert");
				alert.setContentText("The user already logged");
				alert.setHeaderText("Error");
				alert.show();
				return;
			}
			// check if guide or traveler
			if (isValidLoginGuide(username)) {
				OrderFormController.IsNewTraveler = false;
				dashboardVController.visitorID = username;
				FXMLLoader loaderT = new FXMLLoader();
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStageT = new Stage();
				Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/dashboardV.fxml").openStream());
				Scene sceneT = new Scene(rootT);
				sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
				primaryStageT.initStyle(StageStyle.UNDECORATED);
				primaryStageT.setTitle("dashboardV");
				primaryStageT.setScene(sceneT);
				primaryStageT.show();

			}
			// new traveler
			else {
				OrderFormController.IsNewTraveler = true;
				NewVisitorController.visitorID = username;
				FXMLLoader loaderT = new FXMLLoader();
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStageT = new Stage();
				Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/NewVisitor.fxml").openStream());
				Scene sceneT = new Scene(rootT);
				sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
				primaryStageT.initStyle(StageStyle.UNDECORATED);
				primaryStageT.setTitle("NewVisitor");
				primaryStageT.setScene(sceneT);
				primaryStageT.show();

			}

			System.out.println(getUsername() + " logged in");
		}
		// check if user is a park employee
		else {
			if (isValidLoginEmployee(username, password)) {
				System.out.println("login success");
				switch (user.getRole()) {
				case CONSTANTS.departmentManager:
					dashboardDMController.user = user;
					FXMLLoader loaderDM = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStageDM = new Stage();
					Pane rootDM = loaderDM
							.load(getClass().getResource("/departmentManagerGUI/dashboardDM.fxml").openStream());
					Scene sceneDM = new Scene(rootDM);
					sceneDM.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
					primaryStageDM.initStyle(StageStyle.UNDECORATED);
					primaryStageDM.setTitle("dashboardDM");
					primaryStageDM.setScene(sceneDM);
					primaryStageDM.show();
					break;
				case CONSTANTS.parkManager:
					dashboardPMController.user = user;
					FXMLLoader loaderPM = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStagePM = new Stage();
					Pane rootPM = loaderPM
							.load(getClass().getResource("/parkmanagerGUI/dashboardPM1.fxml").openStream());
					Scene scenePM = new Scene(rootPM);
					scenePM.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
					primaryStagePM.initStyle(StageStyle.UNDECORATED);
					primaryStagePM.setTitle("dashboardPM");
					primaryStagePM.setScene(scenePM);
					primaryStagePM.show();
					break;
				case CONSTANTS.worker:
					dashboardWController.user = user;
					FXMLLoader loaderW = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStageW = new Stage();
					Pane rootW = loaderW.load(getClass().getResource("/workerGUI/dashboardW.fxml").openStream());
					Scene sceneW = new Scene(rootW);
					sceneW.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
					primaryStageW.initStyle(StageStyle.UNDECORATED);
					primaryStageW.setTitle("dashboardW");
					primaryStageW.setScene(sceneW);
					primaryStageW.show();
					break;
				case CONSTANTS.serviceRrepresentative:
					dashboardSRController.user = user;
					FXMLLoader loaderSR = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStageSR = new Stage();
					Pane rootSR = loaderSR.load(getClass().getResource("/serviceRrep/dashboardSR.fxml").openStream());
					Scene sceneSR = new Scene(rootSR);
					sceneSR.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
					primaryStageSR.initStyle(StageStyle.UNDECORATED);
					primaryStageSR.setTitle("dashboardSR");
					primaryStageSR.setScene(sceneSR);
					primaryStageSR.show();
					break;

				default:
					System.out.println("error role does not exist");
					break;

				}

				System.out.println(getUsername() + " logged in");
			} else {
				errorLabel.setText("user not found");
			}
		}

	}

	/**
	 * function checks if user is logged
	 * 
	 * @param username the username
	 * @return true if the user is logged , else false
	 */
	private boolean userIsLogged(String username) {
		if (username.isEmpty())
			return false;
		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(username); // insert user name and password that the user in the UI insert
		Message messageToServer = new Message(CONSTANTS.CheckLoginVisitor, Data);
		ClientUI.chat.accept(messageToServer);
		if (isLogged) {
			isLogged = false;
			return true;
		}
		return false;
	}

	/**
	 * Validates the user login. Checks the username and password against the
	 * server's database.
	 * 
	 * @param username the username
	 * @param password the password
	 * @return true if the login is valid, false otherwise
	 */
	private boolean isValidLoginEmployee(final String username, final String password) {
		if (username.isEmpty())
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(username); // insert user name and password that the user in the UI insert
		Data.add(password);
		Message messageToServer = new Message(CONSTANTS.CheckLoginUser, Data);
		ClientUI.chat.accept(messageToServer);
		if (isLoginValid) {
			isLoginValid = false;
			return true;
		}
		return false;
	}

	/**
	 * Validates the Guide login. Checks the ID against the server's database.
	 * 
	 * @param id the ID
	 * @return true if the login is valid, false otherwise
	 */
	private boolean isValidLoginGuide(final String id) {
		if (id.isEmpty())
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(id); // insert id that the user in the UI insert
		Message messageToServer = new Message(CONSTANTS.CheckLoginGuideTraveler, Data);
		ClientUI.chat.accept(messageToServer);
		if (isLoginValid) {
			isLoginValid = false;
			return true;
		}
		return false;
	}

	/**
	 * exit button was clicked so exit client
	 * 
	 * @param event button is clicked
	 * @throws IOException throws exception
	 */
	public void exitClient(ActionEvent event) throws IOException {
		System.out.println("Client has been terminated");
		ClientUI.chat.accept(new Message(CONSTANTS.ClientDisconnectRequest, null));
		System.exit(0);
	}
}
