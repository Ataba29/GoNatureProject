package serviceRrep;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import entities.Visitors;
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
 * function controls the new guide registration form
 */
public class NewGuideController {
	
	public static String id;
	
	@FXML
	private Label idtxt;
	@FXML
	private TextField firstnametxt;
	@FXML
	private TextField lastnametxt;
	@FXML
	private TextField emailtxt;
	@FXML
	private TextField phonenumbertxt;
	@FXML
	private Button savebtn = null;
	@FXML
	private Button backbtn = null;
	
	
	/**
	 * initialize the register guide page by setting up the ID
	 */
	@FXML
	void initialize() {
		idtxt.setText(id);
	}
	
	
	/**
	 * save the guide from the fields that were entered
	 * @param event save button was clicked
	 * @throws IOException javaFX exception 
	 */
	public void saveGuide(ActionEvent event) throws IOException {
		
		String firstname = firstnametxt.getText();
		String lastname = lastnametxt.getText();
		String email = emailtxt.getText();
		String phonenumber = phonenumbertxt.getText();
		Visitors visitor;
		ArrayList<Visitors> dataSendServer = new ArrayList<Visitors>();
		
		// check if one field was empty
		if(firstname.trim().isEmpty() || lastname.trim().isEmpty() || 
				email.trim().isEmpty() || phonenumber.trim().isEmpty()) {
			// show alert err
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		}
		else {
			visitor = new Visitors(id, firstname, lastname, email, phonenumber, "guide", 0);
			dataSendServer.add(visitor);
			ClientUI.chat.accept(new Message(CONSTANTS.SaveGuideRequest, dataSendServer));

			// load dashBoard
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/serviceRrep/dashboardSR.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("dashboardSR");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// alert user success
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Success");
			alert.setContentText("guide was saved in DB");
			alert.setHeaderText("SAVED");
			alert.show();
		}
	}
	
	/**
	 * go back to dash-board
	 * @param event back button was clicked
	 * @throws IOException javaFX exception
	 */
	public void backClicked(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/serviceRrep/dashboardSR.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("dashboardSR");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
