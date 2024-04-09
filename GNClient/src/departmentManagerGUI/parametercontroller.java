package departmentManagerGUI;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
import entities.ClientConnectionStatus;
import entities.Visitors;
import entities.order;
import entities.orderwithbutton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;
/**
 * class that repreesent parameter controller
 */
public class parametercontroller implements Initializable{
	public static ArrayList<order> orderarr;
	public static ArrayList<orderwithbutton> orderarr2;
	@FXML
	private TableView myTable;
	/**
	 * Updating the appropriate table with all requests and a "consent" and no consent button
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TableColumn parkname=new TableColumn("parkname");
		TableColumn type=new TableColumn("type");
		TableColumn newvalue=new TableColumn("newvalue");
		TableColumn aprov=new TableColumn("approve");
		TableColumn rej=new TableColumn("reject");
		
		myTable.getColumns().addAll(parkname,type,newvalue,aprov,rej);
	    parkname.setCellValueFactory(new PropertyValueFactory<>("parkname"));
	    type.setCellValueFactory(new PropertyValueFactory<>("type"));
	    newvalue.setCellValueFactory(new PropertyValueFactory<>("newvalue"));
	    aprov.setCellValueFactory(new PropertyValueFactory<>("button1y"));
	    rej.setCellValueFactory(new PropertyValueFactory<>("button2n"));
	    
		ArrayList<String> Data = new ArrayList<String>();
		Message messageToServer = new Message(CONSTANTS.gitrequest, Data);
	    ClientUI.chat.accept(messageToServer);
	    
	    if(orderarr!=null) {
	    	orderarr2 = new ArrayList<>();
	        ObservableList<orderwithbutton> data= FXCollections.observableArrayList();
	        for(order ord:orderarr) {
	        	orderarr2.add(new orderwithbutton(ord));	    		
	    	}
	    	for(orderwithbutton ord:orderarr2) {
	    		data.add(ord);	    
	            ord.getButton1y().setOnAction(event -> {
	            	handleButtonClick(new order(ord));
	                data.remove(ord); // Remove the row from the table
	            });// Add event handler to each button
	            ord.getButton2n().setOnAction(event -> {
	            	handleButtonClick2(new order(ord));
	                data.remove(ord); // Remove the row from the table
	            });// Add event handler to each button
	    	}
	    	myTable.setItems(data);
	    }  
	}
	/**
	 * It refers to a button reject in the table update
	 * @param order Represents the details of the row on which the button is pressed
	 */
	private void handleButtonClick2(order order) {
		ArrayList<order> Data = new ArrayList<order>();
		Data.add(order);
		Message messageToServer = new Message(CONSTANTS.rejectoarameter, Data);
		ClientUI.chat.accept(messageToServer);
	}
	/**
	 * It refers to a button approve in the table reject
	 * @param order Represents the details of the row on which the button is pressed
	 */
	private void handleButtonClick(order ord) {
		ArrayList<order> Data = new ArrayList<order>();
		Data.add(ord);
		Message messageToServer = new Message(CONSTANTS.updateparameter, Data);
		ClientUI.chat.accept(messageToServer);
		
	}
	/**
	 * bake to the previos page
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void bake(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/departmentManagerGUI/dashboardDM.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("reports");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 * log out client and bake to the log page
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void logout(ActionEvent event) throws IOException {
		System.exit(0);
	}
}
