package workerGUI;

import java.io.IOException;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * class that controls the choose entry option scene
 */
public class ChooseEntryController {
	
	public static User user;
	@FXML
	private Button noOrderID = null; 
	@FXML
	private Button orderIDbtn = null;
	@FXML
	private Button backbtn = null;
	
	/**
	 * function opens the entry with orderID scene
	 * @param event button was clicked
	 * @throws IOException javaFX exception
	 */
	public void orderIDEntry(ActionEvent event) throws IOException {
		EntryEnterOrderIDController.user = user;
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
	
	/**
	 * function open the entry with no orderID scene
	 * @param event event that button was clicked
	 * @throws IOException javaFX error exception
	 */
	public void noOrderIDEntry(ActionEvent event) throws IOException{
		EntryNoOrderIDController.user = user;
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

	
	/**
	 * function brings back previous page
	 * @param event event that back button was clicked
	 * @throws IOException javaFx error
	 */
	public void backButton(ActionEvent event) throws IOException{
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
	}
}
