package serviceRrep;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import entities.User;
import entities.Visitors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * class controls the dash-board of the service representative
 * where he can just enter the id of the guide that he wants to save
 */
public class dashboardSRController {

	public static User user;
	public static boolean isExist = false;
	public static boolean isGuide = false;

	@FXML
	private Label errLabel;
	@FXML
	private TextField idtxt;
	@FXML
	private Button savebtn = null;
	@FXML
	private Button logoutbtn = null;
	
	/**
	 * initialize the dash-board of the service representative
	 * needs to make sure that the isExist and isGuide is false 
	 */
	@FXML
	void initialize() {
		isExist = false;
		isGuide = false;
	}
	
	/**
	 * function to get the text from the id TextField
	 * @return string of what is in textfield
	 */
	private String getIDtxt() {
		return idtxt.getText();
	}
	
	/**
	 * function that sets the string of the error label
	 * @param str the string to write into the label
	 */
	private void setErrLabel(String str) {
		errLabel.setText(str);
	}
	/**
	 * function is triggered once the button save as guide is clicked
	 * saves the id as guide if he exists as a traveler
	 * else opens new page to save the user
	 * @param event button was clicked
	 * @throws IOException javaFx exception
	 */
	public void saveIDAsGuide(ActionEvent event) throws IOException {
		String ID = getIDtxt();
		
		// visitor exist in the DB
		if(isExist(ID)) {
			// check if visitor is guide
			if(isGuide) {
				setErrLabel("ID of Guide");
			}
			// visitor is a normal visitor
			else {
				setErrLabel("user in DB was updated");
			}
		}
		// new user found
		else {

			NewGuideController.id = ID;
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/serviceRrep/NewGuide.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("New Guide Form");
			primaryStage.setScene(scene);
			primaryStage.show();
		}

		isExist = false;
		isGuide = false;
	}
	
	/**
	 * function triggered once a user is logged out of the system
	 * returns to the login page
	 * @param event button was clicked
	 * @throws IOException exception happened
	 */
	public void logoutUser(ActionEvent event) throws IOException {
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
     * find if id exists in the DB and get the data in the DB
     * 
     * @param id the ID
     * @return true if ID exists, false otherwise
     */
	private boolean isExist(final String id) {
		if (id.isEmpty())
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(id); // insert id that the user in the UI insert
		Message messageToServer = new Message(CONSTANTS.CheckIDExist, Data);
		ClientUI.chat.accept(messageToServer);
		if(isExist) {
			isExist = false;
			return true;
		}
		return false;
	}
}
