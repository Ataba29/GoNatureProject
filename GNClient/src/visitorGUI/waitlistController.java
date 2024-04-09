package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.ArrayList;

import client.ClientUI;
import entities.Orders;
import javafx.event.ActionEvent;

/**
 * 
 * waitlistController class control in the waitlist.fxml
 *
 */
public class waitlistController {
	public static Orders OrderWaitList;

	/**
	 * Method that when enter waitlist is pressed adds the order to the DB and goes
	 * to the dashboard
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	@FXML
	public void EnterWaitListbtn(ActionEvent event) throws IOException {
		// add to waitlist table in DB
		ArrayList<String> Data = new ArrayList<String>();

		Data.add(OrderWaitList.getOrderID());
		Data.add(OrderWaitList.getParkName());
		Data.add(OrderWaitList.getVisitTime());
		Data.add(OrderWaitList.getVisitorsNum());
		Data.add(OrderWaitList.getEmail());
		Data.add(OrderWaitList.getAddress());
		Data.add(OrderWaitList.getTypeOrder());
		Data.add(OrderWaitList.getVisitorID());
		Data.add(OrderWaitList.getYear());
		Data.add(OrderWaitList.getMonth());
		Data.add(OrderWaitList.getDay());
		String dateString = OrderWaitList.getYear() + "-" + OrderWaitList.getMonth() + "-" + OrderWaitList.getDay()
				+ " " + OrderWaitList.getVisitTime() + ":00";
		Data.add(dateString);
		Message messageToServer = new Message(CONSTANTS.WaitList, Data);
		ClientUI.chat.accept(messageToServer);

		ArrayList<String> IDWaitList = new ArrayList<String>();
		IDWaitList.add(OrderWaitList.getOrderID());
		Message messageToServer1 = new Message(CONSTANTS.InsertToAllOrderTable, IDWaitList);
		ClientUI.chat.accept(messageToServer1);

		// alert that the added was been successfully
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setHeaderText("Added Order To Wait List :" + OrderWaitList.getOrderID());
		alert.setContentText("Your Order Has Been Added Successfully!");
		alert.showAndWait();

		// opening another page
		FXMLLoader loaderT = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStageT = new Stage();
		Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/dashboardV.fxml").openStream());
		Scene sceneT = new Scene(rootT);
		sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStageT.initStyle(StageStyle.UNDECORATED);
		primaryStageT.setTitle("dashboardV");
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
	public void ShowOrderbtn(ActionEvent event) throws IOException {
		OrdertableformController.OrderTableOrder = OrderWaitList;
		FXMLLoader loaderT = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStageT = new Stage();
		Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/Ordertableform.fxml").openStream());
		Scene sceneT = new Scene(rootT);
		sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStageT.initStyle(StageStyle.UNDECORATED);
		primaryStageT.setTitle("Ordertableform");
		primaryStageT.setScene(sceneT);
		primaryStageT.show();
	}

	// Event Listener on Button.onAction
	@FXML
	public void BackBtn(ActionEvent event) throws IOException {
		OrdertableformController.OrderTableOrder = OrderWaitList;
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