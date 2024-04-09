package parkmanagerGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import client.ClientController;
import client.ClientUI;
import entities.User;
import entities.Park;

import gui.LogInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * report class controller
 */
public class Reports {
	private Date today = new Date();
	@SuppressWarnings("deprecation")
	int daya = today.getDate();
	@SuppressWarnings("deprecation")
	int montha = today.getMonth() + 1;
	@SuppressWarnings("deprecation")
	int yeara = today.getYear() + 1900;
	String month = String.valueOf(montha);
	String year = String.valueOf(yeara);
	String day = String.valueOf(daya);
	public static Park park;
	public static Integer numeber = 0;
	public static User user;
	@FXML
	private Button back = null;
	@FXML
	private Button reportsnum1 = null;
	@FXML
	private Button reportsNum2 = null;

	@FXML
	/**
	 * back button was clicked
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/parkmanagerGUI/dashboardPM1.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	/**
	 * open usage report
	 * 
	 * @param event button was clicked
	 * @throws Exception exception of javaFX
	 */
	public void gousagereports(ActionEvent event) throws Exception {
		Usagereports.year = year;
		Usagereports.month = month;
		Usagereports.day = day;
		Usagereports.user = user;
		Usagereports.park = park;
		BeforeeeDisplay.user = user;
		BeforeeeDisplay.park = park;
		FXMLLoader loaderPM = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStagePM = new Stage();
		Pane rootPM = loaderPM.load(getClass().getResource("/parkmanagerGUI/Usagereports.fxml").openStream());
		Scene scenePM = new Scene(rootPM);
		scenePM.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStagePM.initStyle(StageStyle.UNDECORATED);
		primaryStagePM.setTitle("Usage reports");
		primaryStagePM.setScene(scenePM);
		primaryStagePM.show();

	}

	@FXML
	/**
	 * open visitors report
	 * 
	 * @param event button was clicked
	 * @throws Exception exception of javaFX
	 */
	public void gotoVisitorsReportspage(ActionEvent event) throws Exception {

		VisitorsReportIncluded.year = year;
		VisitorsReportIncluded.month = month;
		VisitorsReportIncluded.day = day;
		VisitorsReportIncluded.user = user;
		VisitorsReportIncluded.park = park;
		FXMLLoader loaderPM = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStagePM = new Stage();
		Pane rootPM = loaderPM.load(getClass().getResource("/parkmanagerGUI/VisitorsReportIncluded.fxml").openStream());
		Scene scenePM = new Scene(rootPM);
		scenePM.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStagePM.initStyle(StageStyle.UNDECORATED);
		primaryStagePM.setTitle("Visitors Number Report");
		primaryStagePM.setScene(scenePM);
		primaryStagePM.show();
	}

}
