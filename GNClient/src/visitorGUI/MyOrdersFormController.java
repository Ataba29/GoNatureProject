package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * 
 * MyOrdersFormController class control in the MyOrdersForm.fxml
 *
 */
public class MyOrdersFormController implements Initializable {
	@FXML
	private ComboBox<String> AllOrdersID;

	public static String visitorID;
	public static ArrayList<String> AllOrders = new ArrayList<String>(); // id for all the orders that in the orders
																			// table
	public static ArrayList<String> AllOrdersInParkWaitList = new ArrayList<String>(); // id for all the orders that in
																						// the parkwaitlist

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void BackFunc(ActionEvent event) throws IOException {
		// move to dashboardV page/form
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
	public void SearchFunc(ActionEvent event) throws IOException {
		DetailsForIDOrderController.orderID = AllOrdersID.getValue();
		// if the combo box was null and pressed the search button
		if (AllOrdersID.getValue() == null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		} else {
			// move to DetailsForIDOrder page/form
			FXMLLoader loaderT = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStageT = new Stage();
			Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/DetailsForIDOrder.fxml").openStream());
			Scene sceneT = new Scene(rootT);
			sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStageT.initStyle(StageStyle.UNDECORATED);
			primaryStageT.setTitle("DetailsForIDOrder");
			primaryStageT.setScene(sceneT);
			primaryStageT.show();
		}
	}

	/**
	 * initialization function that set all the orders id in the combo box (orders
	 * in orders table or parkwaitlist table)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<String> VisitorIDAraryList = new ArrayList<String>();
		ArrayList<String> AllOrdersIDInTwoTables = new ArrayList<String>();
		VisitorIDAraryList.add(visitorID);
		// send to server to get all the orders id of the orders table
		Message messageToServer1 = new Message(CONSTANTS.GetAllOrdersID, VisitorIDAraryList);
		ClientUI.chat.accept(messageToServer1);
		// send to server to get all the orders id of the parkwaitlist table
		Message messageToServer2 = new Message(CONSTANTS.GetAllOrdersIDInParkWaitList, VisitorIDAraryList);
		ClientUI.chat.accept(messageToServer2);
		AllOrdersIDInTwoTables.addAll(AllOrders);
		AllOrdersIDInTwoTables.addAll(AllOrdersInParkWaitList);
		ObservableList<String> list = FXCollections.observableArrayList(AllOrdersIDInTwoTables);
		AllOrdersID.setItems(list);

	}
}
