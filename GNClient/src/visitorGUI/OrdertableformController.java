package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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
import entities.Orders;
import entities.Park;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

/**
 * 
 * OrdertableformController class control in the Ordertableform.fxml
 *
 */
public class OrdertableformController implements Initializable {
	@FXML
	private ComboBox<String> AvailableTimesCombo;
	public static Orders OrderTableOrder;
	public static Park parkOrderTable;
	public static double SumOfVisitorOrderTable;

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void Backbtn(ActionEvent event) throws IOException {
		// finished
		FXMLLoader loaderT = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStageT = new Stage();
		Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/waitlist.fxml").openStream());
		Scene sceneT = new Scene(rootT);
		sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStageT.initStyle(StageStyle.UNDECORATED);
		primaryStageT.setTitle("waitlist");
		primaryStageT.setScene(sceneT);
		primaryStageT.show();
	}

	/**
	 * add the order id to allorders table add the order to orders table
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void PlaceOrderbtn(ActionEvent event) throws IOException {
		// Save the order to the database order and allorders
		// adding the attributes of the new visitor to the array list
		if (AvailableTimesCombo.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		} else {
			// get the details of the order and the new time
			String newTime = AvailableTimesCombo.getValue().substring(11, 16);
			String newDay = AvailableTimesCombo.getValue().substring(8, 10);
			ArrayList<String> Data = new ArrayList<String>();
			Data.add(OrderTableOrder.getOrderID());
			Data.add(OrderTableOrder.getParkName());
			Data.add(newTime);
			Data.add(OrderTableOrder.getVisitorsNum());
			Data.add(OrderTableOrder.getEmail());
			Data.add(OrderTableOrder.getAddress());
			Data.add(OrderTableOrder.getTypeOrder());
			Data.add(OrderTableOrder.getVisitorID());
			Data.add(OrderTableOrder.getYear());
			Data.add(OrderTableOrder.getMonth());
			Data.add(newDay);
			Data.add(AvailableTimesCombo.getValue());
			// send to server
			Message messageToServer = new Message(CONSTANTS.NewOrder, Data);
			ClientUI.chat.accept(messageToServer);

			// send to server , to add to allorder table
			ArrayList<String> OrderIDArrayList = new ArrayList<String>();
			OrderIDArrayList.add(OrderTableOrder.getOrderID());
			Message messageToServer1 = new Message(CONSTANTS.InsertToAllOrderTable, OrderIDArrayList);
			ClientUI.chat.accept(messageToServer1);
			// alert that present the add order has been successfully
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alert");
			alert.setHeaderText("Added Order :" + OrderTableOrder.getOrderID());
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
	}

	/**
	 * initialization function that present the available times in the combo box
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		int Hour;
		int prevHour;
		int day = Integer.parseInt(OrderTableOrder.getDay());
		int k;
		String year = OrderTableOrder.getYear();
		String month = OrderTableOrder.getMonth();
		String date;
		String prevDate;

		// send to server and get the details for the park
		ArrayList<String> ParkNameArrayList = new ArrayList<String>();
		ParkNameArrayList.add(OrderTableOrder.getParkName());
		Message messageToServer1 = new Message(CONSTANTS.GetParkDetails, ParkNameArrayList);
		ClientUI.chat.accept(messageToServer1);

		// to present into the combobox
		ArrayList<String> AllAvailableTimes = new ArrayList<String>();

		// for loop to 3 days
		for (int i = 0; i < 3; i++) {
			// for loop to 3 hours
			Hour = 8;
			for (int j = 0; j < 3; j++) {

				if (Hour < 10) {
					date = year + "-" + month + "-" + String.valueOf(day + i) + " 0" + Hour + ":00:00";
				}
				// if Hour bigger than 9
				else {
					date = year + "-" + month + "-" + String.valueOf(day + i) + " " + Hour + ":00:00";
				}
				prevHour = Hour - parkOrderTable.getDelayTime();
				if (prevHour < 10) {
					prevDate = year + "-" + month + "-" + String.valueOf(day + i) + " 0" + prevHour + ":00:00";
				}
				// if Hour bigger than 9
				else {
					prevDate = year + "-" + month + "-" + String.valueOf(day + i) + " " + prevHour + ":00:00";
				}
				// send to server and get the sum of the visitor numbers between two times
				ArrayList<String> DetailsForSumTwoTimes = new ArrayList<String>();
				DetailsForSumTwoTimes.add(OrderTableOrder.getParkName());
				DetailsForSumTwoTimes.add(date);
				DetailsForSumTwoTimes.add(prevDate);

				Message messageToServer2 = new Message(CONSTANTS.GetSumOfVisitors, DetailsForSumTwoTimes);
				ClientUI.chat.accept(messageToServer2);
				// check if this time there is place for the order
				if (Integer.parseInt(OrderTableOrder.getVisitorsNum()) <= parkOrderTable.getMaxVisitorsNumber()
						- parkOrderTable.getDiffVisitors() - SumOfVisitorOrderTable) {
					AllAvailableTimes.add(date);
				}
				Hour += 4;
			}

		}

		ObservableList<String> list = FXCollections.observableArrayList(AllAvailableTimes);
		AvailableTimesCombo.setItems(list);
	}
}
