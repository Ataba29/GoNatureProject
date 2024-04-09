package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Orders;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * 
 * TwoHoursLeftController class control in the TwoHoursLeft.fxml
 *
 */
public class TwoHoursLeftController implements Initializable {
	public static Orders order;
	@FXML
	private Label OrderIdLabel;
	@FXML
	private Label ParkNameLabel;

	public Label getOrderIdLabel() {
		return OrderIdLabel;
	}

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
	
	/**
	 * 
	 * @param event (clicked on the button)
	 */
	// Event Listener on Button.onAction
	@FXML
	public void AcceptBtn(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
		Thread.interrupted();
	}
	
	/**
	 * function that delete the order from orders table
	 * @param event (clicked on the button)
	 */
	// Event Listener on Button.onAction
	@FXML
	public void CancelOrderBtn(ActionEvent event) {
		// alert to present that the deleted has been successfully
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Alert");
		alert.setHeaderText("Cancellation Order ID! :" + order.getOrderID());
		alert.setContentText("Your Has Been Cancelled Successfully!");
		alert.showAndWait();
		ArrayList<String> OrderIDAraryList = new ArrayList<String>();
		OrderIDAraryList.add(order.getOrderID());
		Message messageToServer = new Message(CONSTANTS.DeleteOrder, OrderIDAraryList);
		ClientUI.chat.accept(messageToServer);
		
		//send to the server to add cancallationreport table
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
		String dateString = order.getYear() + "-" + order.getMonth() + "-" + order.getDay() + " " + order.getVisitTime()
				+ ":00";
		DataForOrder.add(dateString);
		DataForOrder.add("1");
		Message messageToServer1 = new Message(CONSTANTS.addNewcancellationreport, DataForOrder);
		ClientUI.chat.accept(messageToServer1);

		((Node) event.getSource()).getScene().getWindow().hide();
		Thread.interrupted();
	}
	
	/**
	 * initialization function that present the details of the order in labels
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setOrderIdLabel(order.getOrderID());
		setParkNameLabel(order.getParkName());
		setVisitTimeLabel(order.getDate().toString());
		setVisitorsNumberLabel(order.getVisitorsNum());
		setEmailLabel(order.getEmail());
		setAddressLabel(order.getEmail());
		setTypeOrderLabel(order.getTypeOrder());
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

}
