package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Orders;
import entities.Park;
import entities.ParkWaitList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * 
 * DetailsForIDOrderController class control in the DetailsForIDOrder.fxml
 *
 */
public class DetailsForIDOrderController implements Initializable {
	@FXML
	private Label OrderIdLabel;
	@FXML
	private Label ParkNameLabel;
	@FXML
	private Label VisitTimeLabel;
	@FXML
	private Label VisitorsNumberLabel;
	@FXML
	private Label EmailLabel;
	@FXML
	private Label AddressLabel;

	@FXML
	private Label TypeOrderLabel;
	@FXML
	private Label OrderKindLabel;

	public static String orderID;
	public static Park park;
	public static Orders order;
	public static ParkWaitList parkWaitList;
	public static boolean IsOrder;
	public static boolean IsParkWaitList;
	public static double SumOfVisitorToMove;
	public static ArrayList<ParkWaitList> AllWaitListBetweenTwoTimes = new ArrayList<ParkWaitList>();

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void BackBtn(ActionEvent event) throws IOException {
		// move to my order form
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
	public void CancelOrderBtn(ActionEvent event) throws IOException {
		// remove the order from DB
		ArrayList<String> OrderIDAraryList = new ArrayList<String>();
		OrderIDAraryList.add(orderID);
		// if the order is in the orders table
		if (IsOrder) {
			// send to server to delete the order from the DB
			Message messageToServer1 = new Message(CONSTANTS.DeleteOrder, OrderIDAraryList);
			ClientUI.chat.accept(messageToServer1);
			MoveFromWaitListToOrderTable(order);

			// send to the server to add the cancaled order to the cancallationreport table
			ArrayList<String> DataForOrder = new ArrayList<String>();
			DataForOrder.add(order.getOrderID());
			DataForOrder.add(order.getParkName());
			DataForOrder.add(order.getVisitTime());
			DataForOrder.add(order.getVisitorsNum());
			DataForOrder.add(order.getEmail());
			DataForOrder.add(order.getAddress());
			if (order.getTypeOrder().equals("individual") || order.getTypeOrder().equals("small group"))
				DataForOrder.add("individual");
			else
				DataForOrder.add("organized group");
			DataForOrder.add(order.getVisitorID());
			DataForOrder.add(order.getYear());
			DataForOrder.add(order.getMonth());
			DataForOrder.add(order.getDay());
			String dateString = order.getYear() + "-" + order.getMonth() + "-" + order.getDay() + " "
					+ order.getVisitTime() + ":00";
			DataForOrder.add(dateString);
			DataForOrder.add("1");

			Message messageToServer = new Message(CONSTANTS.addNewcancellationreport, DataForOrder);
			ClientUI.chat.accept(messageToServer);

		} else {
			// if the order in the parkwaitlist table
			if (IsParkWaitList) {
				// send to server
				Message messageToServer2 = new Message(CONSTANTS.DeleteParkWaitList, OrderIDAraryList);
				ClientUI.chat.accept(messageToServer2);
			}
		}
		// show alert that the deleted has been successfully
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setHeaderText("Cancellation Order ID! :" + order.getOrderID());
		alert.setContentText("Your Has Been Cancelled Successfully!");
		alert.showAndWait();
		// move to my order form
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
	 * initialization function that represent all the order id that for to the
	 * visitor
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		IsOrder = false;
		IsParkWaitList = false;
		ArrayList<String> OrderIDAraryList = new ArrayList<String>();
		OrderIDAraryList.add(orderID);
		// send to server to get the details for the order
		Message messageToServer1 = new Message(CONSTANTS.GetOrderDetails, OrderIDAraryList);
		ClientUI.chat.accept(messageToServer1);
		// send to server to get the details for the park
		Message messageToServer2 = new Message(CONSTANTS.GetParkWaitListDetails, OrderIDAraryList);
		ClientUI.chat.accept(messageToServer2);
		// showing the order details in the from
		// iof the order in the orders table
		if (IsOrder) {
			setOrderIdLabel(order.getOrderID());
			setParkNameLabel(order.getParkName());
			setVisitTimeLabel(order.getDate().toString());
			setVisitorsNumberLabel(order.getVisitorsNum());
			setEmailLabel(order.getEmail());
			setAddressLabel(order.getEmail());
			setTypeOrderLabel(order.getTypeOrder());
			setOrderKindLabel("Order");
		} else {
			// if the order in the parkwaitlist table
			if (IsParkWaitList) {
				setOrderIdLabel(parkWaitList.getOrderID());
				setParkNameLabel(parkWaitList.getParkName());
				setVisitTimeLabel(parkWaitList.getDate().toString());
				setVisitorsNumberLabel(parkWaitList.getVisitorsNum());
				setEmailLabel(parkWaitList.getEmail());
				setAddressLabel(parkWaitList.getEmail());
				setTypeOrderLabel(parkWaitList.getTypeOrder());
				setOrderKindLabel("Park Wait List");
			}
		}

	}

	/**
	 * function that check if there is order in the parkwaitlist table that can be
	 * in the orders table because we removed order
	 * 
	 * @param order order
	 */
	public void MoveFromWaitListToOrderTable(Orders order) {
		ArrayList<String> ParkNameAraryList = new ArrayList<String>();
		ParkNameAraryList.add(order.getParkName());
		// send to server to get the details of the park
		Message messageToServer1 = new Message(CONSTANTS.GetParkDetailToMove, ParkNameAraryList);
		ClientUI.chat.accept(messageToServer1);

		// send to the server to get the the orders that in the parkwaitlist and the
		// visittime between two times
		ArrayList<String> DetailsWaitListBetweenTwoTimes = new ArrayList<String>();
		DetailsWaitListBetweenTwoTimes.add(order.getParkName());
		LocalDateTime dateTime = order.getDate();
		DetailsWaitListBetweenTwoTimes.add(dateTime.toString());
		DetailsWaitListBetweenTwoTimes.add(dateTime.plusHours(park.getDelayTime()).toString());
		// send to server
		Message messageToServer2 = new Message(CONSTANTS.GetAllWaitListBetweenTwoTimes, DetailsWaitListBetweenTwoTimes);
		ClientUI.chat.accept(messageToServer2);

		// send to server to get the sum of the visitors in two times
		ArrayList<String> DetailsToSumOfVisitorsBetweenTwoTimes = new ArrayList<String>();
		DetailsToSumOfVisitorsBetweenTwoTimes.add(order.getParkName());
		LocalDateTime dateTime1 = order.getDate();
		DetailsWaitListBetweenTwoTimes.add(dateTime1.toString());
		DetailsWaitListBetweenTwoTimes.add(dateTime.minusHours(park.getDelayTime()).toString());
		Message messageToServer5 = new Message(CONSTANTS.GetSumOfVisitors, DetailsWaitListBetweenTwoTimes);
		ClientUI.chat.accept(messageToServer5);

		double diffVisitorsNumber = park.getMaxVisitorsNumber() - park.getDiffVisitors() - SumOfVisitorToMove;
		for (ParkWaitList parkwaitlist : AllWaitListBetweenTwoTimes) {
			// check if the parkwaitlist order can be in the orders table
			if (Integer.parseInt(parkwaitlist.getVisitorsNum()) <= diffVisitorsNumber) {
				diffVisitorsNumber -= Integer.parseInt(parkwaitlist.getVisitorsNum());
				// remove from parkwaitlist table
				ArrayList<String> OrderIDArrayList = new ArrayList<String>();
				OrderIDArrayList.add(parkwaitlist.getOrderID());
				// send to server
				Message messageToServer3 = new Message(CONSTANTS.DeleteParkWaitList, OrderIDArrayList);
				ClientUI.chat.accept(messageToServer3);

				// add to order table
				// send to server
				ArrayList<String> OrderDetailsArrayList = new ArrayList<String>();
				String dateString = parkwaitlist.getYear() + "-" + parkwaitlist.getMonth() + "-" + parkwaitlist.getDay()
						+ " " + parkwaitlist.getVisitTime() + ":00";
				OrderDetailsArrayList.add(parkwaitlist.getOrderID());
				OrderDetailsArrayList.add(parkwaitlist.getParkName());
				OrderDetailsArrayList.add(parkwaitlist.getVisitTime());
				OrderDetailsArrayList.add(parkwaitlist.getVisitorsNum());
				OrderDetailsArrayList.add(parkwaitlist.getEmail());
				OrderDetailsArrayList.add(parkwaitlist.getAddress());
				OrderDetailsArrayList.add(parkwaitlist.getTypeOrder());
				OrderDetailsArrayList.add(parkwaitlist.getVisitorID());
				OrderDetailsArrayList.add(parkwaitlist.getYear());
				OrderDetailsArrayList.add(parkwaitlist.getMonth());
				OrderDetailsArrayList.add(parkwaitlist.getDay());
				OrderDetailsArrayList.add(dateString);
				Message messageToServer4 = new Message(CONSTANTS.NewOrder, OrderDetailsArrayList);
				ClientUI.chat.accept(messageToServer4);
			}
		}
	}

	public void setOrderIdLabel(String orderIdLabel) {
		OrderIdLabel.setText(orderIdLabel);
	}

	public void setParkNameLabel(String parkNameLabel) {
		ParkNameLabel.setText(parkNameLabel);
	}

	public void setVisitTimeLabel(String visitTimeLabel) {
		VisitTimeLabel.setText(visitTimeLabel);
	}

	public void setVisitorsNumberLabel(String visitorsNumberLabel) {
		VisitorsNumberLabel.setText(visitorsNumberLabel);
	}

	public void setEmailLabel(String emailLabel) {
		EmailLabel.setText(emailLabel);
	}

	public void setAddressLabel(String addressLabel) {
		AddressLabel.setText(addressLabel);
	}

	public void setTypeOrderLabel(String typeOrderLabel) {
		TypeOrderLabel.setText(typeOrderLabel);
	}

	public void setOrderKindLabel(String orderKindLabel) {
		OrderKindLabel.setText(orderKindLabel);
	}
}
