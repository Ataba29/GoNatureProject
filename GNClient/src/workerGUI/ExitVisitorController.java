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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * class that controls the exit page of traveler from park
 */
public class ExitVisitorController {

	public static User user;
	public static Boolean isOrderIDExist = false;
	
	@FXML
	private Label errlabel;
	@FXML
	private TextField orderIDtxt;
	@FXML
	private Button exitbtn = null;
	@FXML
	private Button backbtn = null;
	
	
	/**
	 * initialize the exit with orderID controller 
	 * needs to make sure that the isOrderIDExist is false 
	 */
	@FXML
	void initialize() {
		isOrderIDExist = false;
	}
	
	
	/**
	 * handles removing the traveler from all places necessary
	 * @param event button was clicked "exit"
	 */
	public void exitTraveler(ActionEvent event) {
		// checks
		if(orderIDtxt.getText().trim().isEmpty()) {
			errlabel.setText("enter orderID");
			return;
		}

		// delete the order and the visitor and update parameters in DB
		ArrayList<String> serverSendTo = new ArrayList<>();
		serverSendTo.add(orderIDtxt.getText());
		ClientUI.chat.accept(new Message(CONSTANTS.deleteOrderIDExit, serverSendTo));
		if(!isOrderIDExist) {
			errlabel.setText("orderID not found");
			return;
		}
		errlabel.setText("");
		
		// alert user that job is done
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Success");
		alert.setContentText("you can exit");
		alert.setHeaderText("Success Exit");
		alert.show();
	}
	
	/**
	 * returns to the dashBoard of the worker page
	 * @param event event that button was clicked
	 * @throws IOException javaFx exception
	 */
	public void backButton(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/workerGUI/dashboardW.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("dashboardW");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
