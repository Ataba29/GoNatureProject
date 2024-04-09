package gui;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import entities.ClientConnectionStatus;
import server.ServerUI;
import server.mysqlConnection;

/**
 * the ServerConnectFrameController class is the class that creates the GUI 
 * that the server will have, as well as all the all the components that the user
 * needs in order to operate the server
 * @version 15.3
 */
public class ServerConnectFrameController {

    private double xOffset = 0;
    private double yOffset = 0;
	public static boolean imported = false;
	private boolean startClicked = false;
	private static ObservableList<ClientConnectionStatus> clientsList = FXCollections.observableArrayList();

	@FXML
	private TextField txtusername;

	@FXML
	private TextField txtpassword;
	@FXML
	private Label output;
	@FXML
	private TextField txtport;
	@FXML
	private Label txtip;
	@FXML
	private Button btnstart = null;
	@FXML
	private Button extbttn = null;
	@FXML
	private Button importbtn = null;
	
	@FXML
	private TableView<ClientConnectionStatus> connStatusTable;
	
	@FXML
	private TableColumn<ClientConnectionStatus,String> IPCol;
	
	@FXML
	private TableColumn<ClientConnectionStatus,String> HostCol;
	
	@FXML
	private TableColumn<ClientConnectionStatus,String> StatusCol;
	
	private String getusername() {
		return txtusername.getText();			
	}
	private String getpassword() {
		return txtpassword.getText();			
	}
	private String getport() {
		return txtport.getText();			
	}
	
	public void outputServerStatus() {
		output.setText("server started");
	}
	/**
	 * Initializes the server UI components and sets their default values.
	 */
	public void initialize() {
		String ip = getIP();
		txtip.setText(ip);
		imported = false;
		startClicked = false;
		
	}
	
	/**
	 * Starts the primary stage of the server application.
	 * Loads the server UI from the FXML file, sets the scene, and displays the stage.
	 * 
	 * @param primaryStage The primary stage of the JavaFX application
	 * @throws Exception if an error occurs during the operation
	 */
	public void start(Stage primaryStage) throws Exception {	
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/gui/ServerConnectFrame.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
        // Set fixed size
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        // Enable dragging
        scene.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();		
	}
	
	/**
	 * Function handles if the start button was pressed
	 * which will connect to the DB and start the server to
	 * listen to the port that was typed
	 * @param event represent event that button was clicked
	 */
	public void startbutton(ActionEvent event) {
		if(startClicked)
			return;
		
		String port, user, pass;
		startClicked = true;
		connStatusTable.setItems(clientsList);
		this.IPCol.setCellValueFactory(new PropertyValueFactory<>("ip"));
		this.HostCol.setCellValueFactory(new PropertyValueFactory<>("host"));
		this.StatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		
		port = getport();
		user = getusername();
		pass = getpassword();
		
		if(port.trim().isEmpty() || user.trim().isEmpty() || pass.trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		}
		else
		{
			ServerUI.runServer(port, user, pass);
		}
		outputServerStatus();
	}
	
	/**
	 * function handles action of when exit button is clicked on
	 * which closes the server
	 * @param event represent event that button was clicked
	 */
	public void exitbutton(ActionEvent event) {
		System.out.println("Server has been terminated");
		System.exit(0);
	}
	
	/**
	 * Function that gets the ip of the server's 
	 * @return
	 */
	private String getIP() {
		String ipAddress = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			ipAddress = address.getHostAddress();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		}
		return ipAddress;
	}
	
	/**
	 * Updates the table of client connections with the new info 
	 * @param ip client's ip address
	 * @param hostName client's host name
	 * @param status client's status if he is connected or disconnected
	 */
	public void updateConnectedClient(InetAddress ip, String hostName, String status) {
		ClientConnectionStatus client = new ClientConnectionStatus(ip.getHostAddress(), hostName, status);
		if(clientsList.indexOf(client) == -1 )
			clientsList.add(client);
		else {
			clientsList.remove(clientsList.indexOf(client));
			clientsList.add(client);
		}
		System.out.println(ip.getHostAddress() +" Connected succsessfully!");
	}
	
	/**
	 * function handles the import users button
	 * @param event button was clicked
	 */
	public void importUsers(ActionEvent event) {
		if(!startClicked)
		{
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("please start the server first");
			alert.setHeaderText("FAIL");
			alert.show();
			return;
		}
		if(imported == false) {
			mysqlConnection.importUsers();

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Imported");
			alert.setContentText("You have imported the users into the DB");
			alert.setHeaderText("SUCCESS");
			alert.show();
		}
		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Imported");
			alert.setContentText("You have already imported the users into the DB");
			alert.setHeaderText("FAIL");
			alert.show();
		}
	}
}
