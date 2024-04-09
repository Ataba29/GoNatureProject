package parkmanagerGUI;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientController;
import client.ClientUI;
import entities.User;
import entities.Park;

import gui.LogInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * the dashboard of the park manager
 */
public class dashboardPMController {
	public static Integer numeber = 0;
	public static User user;
	public static Park park;
	public static boolean isLoginValid = false;
	public String parknam;
	public static ArrayList<Park> validNamePark2;
	public static ArrayList<Integer> validNamePark3;

	public static ClientController chat; // only one instance

	@FXML
	private TextField usernametxt;
	@FXML
	private TextField parknametxt;
	@FXML
	private Button updatepabtb = null;
	@FXML
	private Button openreports1111 = null;

	Stage primaryStage;

	/**
	 * initialize the dashboard
	 */
	public void initialize() {
		// Check if the user object is not null and if it contains the name
		if (user != null && user.getFirstname() != null) {
			// Set the name in the username text field
			usernametxt.setText(user.getFirstname());
		}
		// Check if the user object is not null and if it contains the name
		if (user != null && user.getParkname() != null) {
			// Set the name in the username text field
			parknametxt.setText(user.getParkname());
		}

	}

	@FXML
	/**
	 * update was clicked
	 * 
	 * @param event button was clicked
	 * @throws IOException javaFX exception
	 */
	private void updateparametres(ActionEvent event) throws IOException {
		parknam = user.getParkname();
		String parkname = parknam;

		if (!parkname.isEmpty()) {

			if (isParkin(parkname)) {
				System.out.println("login success");

				Updateparametres.park = park;
				Updateparametres.newpark = park;
				if (park == null) {
					System.out.println("its also null");
				}
				FXMLLoader loaderPM = new FXMLLoader();
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStagePM = new Stage();
				Pane rootPM = loaderPM
						.load(getClass().getResource("/parkmanagerGUI/Updateparametres.fxml").openStream());
				Scene scenePM = new Scene(rootPM);
				scenePM.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
				primaryStagePM.initStyle(StageStyle.UNDECORATED);
				primaryStagePM.setTitle("dashboardUP");
				primaryStagePM.setScene(scenePM);
				primaryStagePM.show();

			}
		}
	}

	@FXML
	/**
	 * report was clicked
	 * 
	 * @param event button was clicked
	 * @throws IOException javaFX exception
	 */
	private void gopagereport1111(ActionEvent event) throws IOException {
		Reports.park = park;
		Reports.user = user;
		FXMLLoader loaderPM = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStagePM = new Stage();
		Pane rootPM = loaderPM.load(getClass().getResource("/parkmanagerGUI/Reports.fxml").openStream());
		Scene scenePM = new Scene(rootPM);
		scenePM.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStagePM.initStyle(StageStyle.UNDECORATED);
		primaryStagePM.setTitle("Reports");
		primaryStagePM.setScene(scenePM);
		primaryStagePM.show();

	}

	@FXML
	/**
	 * Back was clicked
	 * 
	 * @param event button was clicked
	 * @throws IOException javaFX exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {

		// change the isLogged in DB to 0 , because he logout
		ArrayList<String> Data = new ArrayList<String>();
		Data.add(user.getEmployeeID());
		// send to server
		Message messageToServer = new Message(CONSTANTS.LogOutEmp, Data);
		ClientUI.chat.accept(messageToServer);
		// change between guis
		//// move to login form
		FXMLLoader loaderT = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStageT = new Stage();
		Pane rootT = loaderT.load(getClass().getResource("/gui/LogIn.fxml").openStream());
		Scene sceneT = new Scene(rootT);
		sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStageT.initStyle(StageStyle.UNDECORATED);
		primaryStageT.setTitle("login");
		primaryStageT.setScene(sceneT);
		primaryStageT.show();

	}

	/**
	 * checks park data
	 * 
	 * @param id id of user
	 * @return true or false
	 */
	public boolean isParkin(final String id) {
		if (id.isEmpty())
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(id); // insert id that the park in the UI insert
		Message messageToServer = new Message(CONSTANTS.CheckPark, Data);
		ClientUI.chat.accept(messageToServer);
		if (dashboardPMController.isLoginValid) {
			dashboardPMController.isLoginValid = false;
			return true;
		}
		return false;
	}

}
