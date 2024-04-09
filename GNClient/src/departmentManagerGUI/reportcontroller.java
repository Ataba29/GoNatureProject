package departmentManagerGUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import parkmanagerGUI.BeforeeeDisplay;
import parkmanagerGUI.Beforepresentation;

import java.io.IOException;
import java.util.ArrayList;

import entities.Parkfulltimes;
import entities.User;

/**
 * class that repreesent the main dashboard for the report controller
 */
public class reportcontroller {

	public static User user;

	@FXML
	private TextField parknamtxt;

	/**
	 * bake to the previos page
	 * 
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void bake(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage1 = new Stage();
		Pane root = loader.load(getClass().getResource("/departmentManagerGUI/dashboardDM.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage1.initStyle(StageStyle.UNDECORATED);
		primaryStage1.setTitle("reports");
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	/**
	 * go to the visitreport.fxml gui
	 * 
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void vistproduce(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage1 = new Stage();
		Pane root = loader.load(getClass().getResource("/departmentManagerGUI/visitreport.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage1.initStyle(StageStyle.UNDECORATED);
		primaryStage1.setTitle("reports");
		primaryStage1.setScene(scene);
		primaryStage1.show();
	}

	@FXML
	/**
	 * function opens the park manager report (visitorsNum)
	 * 
	 * @param event button was clicked
	 * @throws IOException javaFX exception
	 */
	public void totaldisplaying(ActionEvent event) throws IOException {
		if (parknamtxt.getText().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fail");
			alert.setContentText("write park name first");
			alert.setHeaderText("error");
			alert.show();
		}
		BeforeeeDisplay.parkMloaded = false;
		User temp = user;
		temp.setParkname(parknamtxt.getText());
		Beforepresentation.user = temp;
		FXMLLoader loader2 = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage2 = new Stage();
		Pane root2 = loader2.load(getClass().getResource("/parkmanagerGUI/Beforepresentation.fxml").openStream());
		Scene scene2 = new Scene(root2);
		scene2.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage2.initStyle(StageStyle.UNDECORATED);
		primaryStage2.setTitle("Beforepresentation");
		primaryStage2.setScene(scene2);
		primaryStage2.show();
	}

	@FXML
	/**
	 * function opens the park manager report (usage)
	 * 
	 * @param event button was clicked
	 * @throws IOException javaFX exception
	 */
	public void usagedisplaying(ActionEvent event) throws IOException {
		if (parknamtxt.getText().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Fail");
			alert.setContentText("write park name first");
			alert.setHeaderText("error");
			alert.show();
		}
		BeforeeeDisplay.parkMloaded = false;
		BeforeeeDisplay.dbResponsehaha = new ArrayList<Parkfulltimes>();
		User temp = user;
		temp.setParkname(parknamtxt.getText());
		BeforeeeDisplay.user = temp;
		FXMLLoader loader2 = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage2 = new Stage();
		Pane root2 = loader2.load(getClass().getResource("/parkmanagerGUI/BeforeeeDisplay.fxml").openStream());
		Scene scene2 = new Scene(root2);
		scene2.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage2.initStyle(StageStyle.UNDECORATED);
		primaryStage2.setTitle("Beforepresentation");
		primaryStage2.setScene(scene2);
		primaryStage2.show();
	}

	/**
	 * go to the cancellationreport.fxml gui
	 * 
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void cancellationproduc(ActionEvent event) throws IOException {
		FXMLLoader loader2 = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage2 = new Stage();
		Pane root2 = loader2.load(getClass().getResource("/departmentManagerGUI/cancellationreport.fxml").openStream());
		Scene scene2 = new Scene(root2);
		scene2.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage2.initStyle(StageStyle.UNDECORATED);
		primaryStage2.setTitle("cancellationreport");
		primaryStage2.setScene(scene2);
		primaryStage2.show();
	}

}
