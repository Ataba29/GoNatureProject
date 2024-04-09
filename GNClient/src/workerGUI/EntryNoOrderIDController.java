package workerGUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

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
 * class controls the entry with no orderID page
 */
public class EntryNoOrderIDController {

	public static User user;
	public static String newOrderID = null;
	public static Boolean isGuide = false;
	
	@FXML
	private Label orderIDtxt;
	@FXML
	private TextField idtxt;
	@FXML
	private TextField typetxt;
	@FXML
	private TextField numVisitorstxt;
	@FXML
	private Label errlabel;
	@FXML
	private Button continuebtn = null;
	@FXML
	private Button backbtn = null;
	
	
	/**
	 * initialize the enter with no orderID page
	 * need to create a new orderID to give the new order
	 */
	@FXML
	void initialize() {
		newOrderID = null;
		isGuide = false;
		// get new orderID and set to label
		ClientUI.chat.accept(new Message(CONSTANTS.GetNewOrderID, null));
		orderIDtxt.setText(newOrderID);
	}
	
	
	/**
	 * function handles the continue button being pushed
	 * @param event event that button was clicked
	 * @throws IOException exception from javaFX
	 */
	public void continueButton(ActionEvent event) throws IOException {
		// checks
		if(idtxt.getText().trim().isEmpty() || typetxt.getText().trim().isEmpty()
				|| numVisitorstxt.getText().trim().isEmpty()) {
			errlabel.setText("fill all fields");
			return;
		}
		if(!typetxt.getText().trim().equals("individual") && !typetxt.getText().trim().equals("small group")
				&& !typetxt.getText().trim().equals("organized group")) {
			errlabel.setText("type is formatted wrong!");
			return;
		}
		if(typetxt.getText().trim().equals("organized group")) {
			CheckIfIDisGuide();
		}
		System.out.println(isGuide);
		System.out.println(typetxt.getText().trim().equals("organized group"));
		if(!isGuide && typetxt.getText().trim().equals("organized group")) {
			errlabel.setText("you need to be guide");
			return;
		}
		
		// setup payPage
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PayPageController.order = new Orders(orderIDtxt.getText(), user.getParkname(), formatter.format(date).toString(),
				numVisitorstxt.getText(), "", "", typetxt.getText(), idtxt.getText());
		PayPageController.orderOrdered = false;
		
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
	
	
	/**
	 * method that checks if the id is a guide's id
	 */
	private void CheckIfIDisGuide() {
		ArrayList<String> sendToServer = new ArrayList<>();
		sendToServer.add(idtxt.getText());
		ClientUI.chat.accept(new Message(CONSTANTS.CheckGuideFromW, sendToServer));
	}

	/**
	 * function triggers the back button event 
	 * @param event button was clicked
	 * @throws IOException exception of javaFX
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
}
