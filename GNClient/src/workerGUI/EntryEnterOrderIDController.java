package workerGUI;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientUI;
import entities.Orders;
import entities.User;
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
 * class controls the entry enter by orderID option scene
 */
public class EntryEnterOrderIDController {

	public static User user;
	public static Orders order;
	public static Boolean IsOrderExist = false;
	
	@FXML
	private TextField orderIDtxt;
	@FXML
	private Label errlabel;
	@FXML
	private Button backbtn = null;
	@FXML
	private Button enterbtn = null;
	
	/**
	 * initialize the entry with orderID controller 
	 * needs to make sure that the IsOrderExist is false 
	 */
	@FXML
	void initialize() {
		IsOrderExist = false;
	}
	
	/**
	 * function gets the order details and send traveler to pay page
	 * @param event ENTER button was clicked
	 * @throws IOException javaFX switch scene error
	 */
	public void enterTraveler(ActionEvent event) throws IOException{
		String orderID = getTextOrderID();
		
		// check if orderID was typed
		if(orderID.trim().isEmpty()) {
			seterrLabel("enter orderID please!");
			return;
		}
		
		// check if orderID exist
		if(orderIDFound(orderID)) {
			// PayPage load
			PayPageController.order = order;
			PayPageController.orderOrdered = true;
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/workerGUI/PayPage.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("PayPage");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		// orderID does not exist
		else {
			seterrLabel("orderID not found");
		}
	}
	
	
	/**
	 * function that finds if orderID is in DB 
	 * @param orderID orderID to find
	 * @return returns true if found else false
	 */
	private boolean orderIDFound(String orderID) {
		ArrayList<String> dbSend = new ArrayList<>();
		dbSend.add(orderID);
		
		Message msg = new Message(CONSTANTS.FindOrderID, dbSend);
		ClientUI.chat.accept(msg);
		
		if(IsOrderExist) {
			IsOrderExist = false;
			return true;
		}
		return false;
	}

	/**
	 * function that goes back to choose entry option page
	 * @param event button back was clicked
	 * @throws IOException javaFX error throw
	 */
	public void backButton(ActionEvent event) throws IOException {
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
	
	
	private String getTextOrderID() {
		return orderIDtxt.getText();
	}
	
	private void seterrLabel(String err) {
		errlabel.setText(err);
	}
}
