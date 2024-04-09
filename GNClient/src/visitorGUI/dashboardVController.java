package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import Threads.MyThread;
import client.ClientUI;
import entities.Orders;
import entities.Visitors;
import javafx.event.ActionEvent;

/**
 * 
 * dashboardVContoller class control in the dashboradV.fxml
 *
 */
public class dashboardVController implements Initializable {
	public static Visitors visitor;
	public static String visitorID;
	public static String orderid;// this is to be displayed in the alert
	public static ArrayList<Orders> orderList = new ArrayList<Orders>();
	private static boolean IsForced = false; // to check if we forced the visitor to cancel or accept his orders , to
												// don't represent it anymore
	private static boolean IsShowedRemind = false; // to check if we remind the visitor about his order , and don't show
													// it again

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void AddOrderFunc(ActionEvent event) throws IOException {
		OrderFormController.fromDashboard = true;
		OrderFormController.visitorID = visitorID;
		// move to order form to enter the details of the order
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

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void ViewOrdersFunc(ActionEvent event) throws IOException {
		MyOrdersFormController.visitorID = visitorID;
		// move to my orders form
		FXMLLoader loaderT = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStageT = new Stage();
		Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/MyOrdersForm.fxml").openStream());
		Scene sceneT = new Scene(rootT);
		sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStageT.initStyle(StageStyle.UNDECORATED);
		primaryStageT.setTitle("MyOrdersForm");
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
	public void LogOutFunc(ActionEvent event) throws IOException {
		// change the isLogged in DB to 0 , because he logout
		ArrayList<String> Data = new ArrayList<String>();
		Data.add(visitorID);
		// send to server
		Message messageToServer = new Message(CONSTANTS.LogOut, Data);
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
	 * initialization function that check if the visitor have orders that want less
	 * 24 hours to do and remind him.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		orderList.clear();
		// to get all the orders for the visitor
		ArrayList<String> Data = new ArrayList<String>();
		Data.add(visitorID);
		Message messageToServer = new Message(CONSTANTS.OrdersTaken, Data);
		ClientUI.chat.accept(messageToServer);

		// check if the order has less more than 24 hours to do
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime BeforeOneDay = null;
		if (!IsShowedRemind) {
			for (Orders order : orderList) {
				BeforeOneDay = order.getDate().minusDays(1);
				if (BeforeOneDay.isBefore(now)) {
					// show an remind alert
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Alert");
					alert.setHeaderText("Order Reminder for order ID! :" + order.getOrderID());
					alert.setContentText("You have order for Tomorrow, don't forget it !");
					alert.showAndWait();
					if (!IsForced) {
						// run thread to force the order to accept or cancel the order if left two hours
						// from send the alert
						MyThread thread = new MyThread(order);
						thread.start();
					}
				}
			}
			IsForced = true;
			IsShowedRemind = true;
		}

	}
}
