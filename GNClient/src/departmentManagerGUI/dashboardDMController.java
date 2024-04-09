package departmentManagerGUI;

import entities.User;
import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * class that repreesent the main dashboard for departmentManager Controller
 */
public class dashboardDMController {
	public static User user;
	@FXML
	private Button PAR = null;

	/**
	 * Department manager click on view requests for parameters
	 * 
	 * @param event button is clicked
	 * @throws IOException error occurred
	 */
	@FXML
	public void parameter(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/departmentManagerGUI/parameterpage.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("request");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Department manager click on working with report for parameters
	 * 
	 * @param event button is clicked
	 * @throws IOException error occurred
	 */
	@FXML
	public void report(ActionEvent event) throws IOException {
		reportcontroller.user = user;
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/departmentManagerGUI/reportpage.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("reports");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * log out client and bake to the log page
	 * 
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void logout(ActionEvent event) throws IOException {

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
}