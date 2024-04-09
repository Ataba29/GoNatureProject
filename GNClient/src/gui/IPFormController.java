package gui;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientController;
import client.ClientUI;
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
import utilities.CONSTANTS;
import utilities.Message;

/**
 * class that displays the ip form GUI
 */
public class IPFormController {
	private static int DEFAULT_PORT = 5555;
	public static ClientController chat; // only one instance

	@FXML
	private TextField txtip;
	@FXML
	private Button connectbtn = null;

	/**
	 * Starts the primary stage
	 * 
	 * @param primaryStage the primary Stage to start
	 * @throws Exception thrown when error is encountered
	 */
	public void start(Stage primaryStage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("/gui/IPForm.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Connect To Server");
		primaryStage.initStyle(StageStyle.UTILITY);
		primaryStage.show();
	}

	/**
	 * get the text that was written in the ip textField
	 * 
	 * @return the ip that was entered
	 */
	private String getip() {
		return txtip.getText();
	}

	/**
	 * Creates connection to the server when pressed on the button by creating the
	 * client and listening to the ip and default port
	 * 
	 * @param event button is clicked
	 * @throws IOException error occurred
	 */
	public void connectToServer(ActionEvent event) throws IOException {

		if (getip().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		}

		else {

			Message msg = new Message(CONSTANTS.ConnectionMadeToServer, null);
			FXMLLoader loader = new FXMLLoader();

			ClientUI.chat = new ClientController(getip(), DEFAULT_PORT);
			ClientUI.chat.accept(msg);
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/gui/LogIn.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.setTitle("login");
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.show();
		}
	}
}
