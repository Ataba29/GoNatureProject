package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Visitors;
import javafx.event.ActionEvent;
/**
 * 
 * NewVisitorController class control in the NewVisitor.fxml
 *
 */
public class NewVisitorController implements Initializable {
	public static String visitorID;
	private Visitors newVisitor;
	@FXML
	private TextField VisitorIdText;
	@FXML
	private TextField FirstNameText;
	@FXML
	private TextField LastNameText;
	@FXML
	private TextField EmailText;
	@FXML
	private TextField PhoneNumberText;

	public String getVisitorIdText() {
		return VisitorIdText.getText();
	}

	public String getFirstNameText() {
		return FirstNameText.getText();
	}

	public String getLastNameText() {
		return LastNameText.getText();
	}

	public String getEmailText() {
		return EmailText.getText();
	}

	public String getPhoneNumberText() {
		return PhoneNumberText.getText();
	}

	public void setVisitorIdText(String visitorID) {
		VisitorIdText.setText(visitorID);
	}
	
	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void BackFunc(ActionEvent event) throws IOException {
		//opening login page
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
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void ConfirmFunc(ActionEvent event) throws IOException {
		//check if any of the text field is empty
		if (getVisitorIdText().trim().isEmpty() || getFirstNameText().trim().isEmpty()
				|| getLastNameText().trim().isEmpty() || getEmailText().trim().isEmpty()
				|| getPhoneNumberText().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		} else {
			OrderFormController.fromNewVisitor = true;
			newVisitor = new Visitors(getVisitorIdText(), getFirstNameText(), getLastNameText(), getEmailText(),
					getPhoneNumberText(), "traveller", 1);
			OrderFormController.visitor = newVisitor;
			OrderFormController.visitorID = visitorID;

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText("Added Visitor :" + visitorID);
			alert.setContentText("You Are Been Added Successfully!");
			alert.showAndWait();

			FXMLLoader loaderT = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStageT = new Stage();
			Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/OrderForm.fxml").openStream());
			Scene sceneT = new Scene(rootT);
			sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStageT.initStyle(StageStyle.UNDECORATED);
			primaryStageT.setTitle("OrderForm");
			primaryStageT.setScene(sceneT);
			primaryStageT.show();
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setVisitorIdText(visitorID);
	}

}
