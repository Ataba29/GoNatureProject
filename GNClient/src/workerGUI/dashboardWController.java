package workerGUI;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import entities.User;
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
 * class controls the dashBoard of the worked
 */
public class dashboardWController {

	public static User user;

	@FXML
	private Button enterbtn = null;
	@FXML
	private Button exitbtn = null;
	@FXML
	private Button logoutbtn = null;

	/**
	 * button enter visitor was clicked
	 * 
	 * @param event event that the button was clicked
	 * @throws IOException javaFX error thrown
	 */
	public void enterVisitor(ActionEvent event) throws IOException {
		// load choose entry scene
		ChooseEntryController.user = user;
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/workerGUI/ChooseEntry.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("ChooseEntry");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * button exit visitor was clicked
	 * 
	 * @param event event that button was clicked
	 * @throws IOException javaFX error exception
	 */
	public void exitVisitor(ActionEvent event) throws IOException {
		ExitVisitorController.user = user;
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/workerGUI/ExitVisitor.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("ExitVisitor");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * log out the worker
	 * 
	 * @param event event that log out button was clicked
	 * @throws IOException javaFx exception
	 */
	public void logOutWorker(ActionEvent event) throws IOException {
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
