package workerGUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import client.ClientUI;
import entities.Orders;
import entities.Visitslogs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * controller that controls the pay page 
 */
public class PayPageController {
	public static Orders order;
	public static Boolean orderOrdered;
	public static int maxVisitors;
	public static int currVisitorsOrdered;
	public static int currVisitorsNotOrdered;
	public static int diffVisitors;
	private boolean isSpace;
	private boolean parkIsFfull = false;
	private boolean guidePays;
	private double amount;
	
	@FXML
	private Label orderidtxt;
	@FXML
	private Label typetxt;
	@FXML
	private Label orderwasorderedtxt;
	@FXML
	private Label numbervisitorstxt;
	@FXML
	private Label discounttxt;
	@FXML
	private Label totalamounttxt;
	@FXML
	private Button patbtn = null;
	@FXML
	private Button backbtn = null;
	
	
	/**
	 * initialize the pay page
	 * needs to make sure that the isExist and isGuide is false 
	 * @throws IOException 
	 */
	@FXML
	void initialize() throws IOException {
		// check if there is space in the park
		int numOfVisitors = Integer.valueOf(order.getVisitorsNum());
		ClientUI.chat.accept(new Message(CONSTANTS.GetMaxVisAndDiffVis, new ArrayList<String>(Arrays.asList(order.getParkName()))));
		isSpace = true;
		if(orderOrdered) {
			// not enough space
			System.out.println(maxVisitors - diffVisitors - currVisitorsOrdered);
			if(numOfVisitors > maxVisitors - diffVisitors - currVisitorsOrdered) {
				isSpace = false;
			}
		}
		else {
			// not enough space
			if(numOfVisitors > diffVisitors - currVisitorsNotOrdered) {
				isSpace = false;
			}
		}
		parkIsFfull = false;
		// this order will make the park full
		if(numOfVisitors == maxVisitors - currVisitorsOrdered) {
			parkIsFfull = true;
		}
		orderidtxt.setText(order.getOrderID());
		typetxt.setText(order.getTypeOrder());
		orderwasorderedtxt.setText(orderOrdered ? "yes" : "no");
		numbervisitorstxt.setText(order.getVisitorsNum());
		discounttxt.setText(calcDiscount() + "%");
		totalamounttxt.setText(calcAmount() + "$");
	}
	
	
	/**
	 * function alerts user that there is no space in park
	 * @throws IOException exception from javaFX
	 */
	private void ErrorNumOfVisitors() throws IOException {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Err");
		alert.setContentText("There is no space in park");
		alert.setHeaderText("Error");
		alert.show();
	}


	/**
	 * function that calculates the amount that needs to be pain
	 * @return String of amount to pay
	 */
	private String calcAmount() {
		int discount = Integer.valueOf(calcDiscount()); 
		int numOfVisitors = Integer.valueOf(order.getVisitorsNum());
		amount = numOfVisitors * CONSTANTS.ticketPrice; // get total
		amount = amount - (amount * discount / 100); // remove discount
		amount = ( guidePays ? amount - CONSTANTS.ticketPrice : amount); // remove guides ticket
		return String.format("%,.2f", amount); 
	}


	/**
	 * function that calculates the discount of the order
	 * @return String of total amount to pay
	 */
	private String calcDiscount() {
		// no guide in group
		if(order.getTypeOrder().equals("individual") || order.getTypeOrder().equals("small group")) {
			if(orderOrdered) {
				return "15";
			}
			else {
				return "0";
			}
		}
		// group with guide
		else {
			if(orderOrdered) {
				guidePays = false;
				return "25";
			}
			else {
				guidePays = true;
				return "10";
			}
		}
		
	}


	/**
	 * function triggered when pay bill is clicked
	 * @param event event that button was clicked
	 * @throws IOException throws javaFX exception
	 */
	public void payBill(ActionEvent event) throws IOException {
		// no space so button does nothing
		if(!isSpace) {
			ErrorNumOfVisitors();
			return;
		}
		addToALLORDERS();
		Date date = new Date();
		ArrayList<Visitslogs> addToDB = new ArrayList<Visitslogs>();
		ArrayList<String> sendParkisFullToDB = new ArrayList<String>();
		Visitslogs newLog = new Visitslogs(order.getOrderID(), order.getParkName(), date, date, 
				order.getTypeOrder(), Integer.valueOf(order.getVisitorsNum()));
		newLog.setentrancewithnoorder(orderOrdered ? 0 : 1);
		newLog.setID(order.getVisitorID());
		addToDB.add(newLog);
		sendParkisFullToDB.add(newLog.getParkname());
		sendParkisFullToDB.add(newLog.entryTimeToString());
		ClientUI.chat.accept(new Message(CONSTANTS.AddNewEntryLog, addToDB));
		if(parkIsFfull) {
			ClientUI.chat.accept(new Message(CONSTANTS.parkIsFullStart, sendParkisFullToDB));
		}
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
		
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Payed");
		alert.setContentText("You can enter the park");
		alert.setHeaderText("Success");
		alert.show();
		
		
	}
	
	/**
	 * adds the orderID to the table ALLORDERS in the DB
	 */
	private void addToALLORDERS() {
		
		if(orderOrdered)
			return;
		
		// we only want new orders to work with
		ArrayList<String> dbSend = new ArrayList<>();
		dbSend.add(order.getOrderID());
		ClientUI.chat.accept(new Message(CONSTANTS.addToAllOrdersPayPage, dbSend));
	}


	/**
	 * function handles back button
	 * @param event event that back button was clicked
	 * @throws IOException javaFX error
	 */
	public void backToCorrectPage(ActionEvent event) throws IOException {
		if(orderOrdered) {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/workerGUI/EntryEnterOrderID.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("EntryEnterOrderID");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		else {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/workerGUI/EntryNoOrderID.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setTitle("EntryNoOrderID");
			primaryStage.setScene(scene);
			primaryStage.show();
		}
	}
}
