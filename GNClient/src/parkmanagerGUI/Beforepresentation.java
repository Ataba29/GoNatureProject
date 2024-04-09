package parkmanagerGUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Date;

import client.ClientController;
import client.ClientUI;
import entities.User;
import entities.Visitornumberreport;
import entities.Park;
import entities.Parkfulltimes;
import gui.LogInController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * function before opening the visitors report
 */
public class Beforepresentation {
	public boolean icandisplay = false;
	public static Park park;
	public static User user;
	public static boolean isLoginValid = false;
	public static boolean parkMloaded;

	@FXML
	private Button Back = null;

	@FXML
	private Button submitbtn = null;

	@FXML
	private Button display = null;

	@FXML
	private TextField textmonth;

	@FXML
	private TextField textyear;

	@FXML
	private Label youcantdisplay;
	@FXML
	private Label errorindisplay;
	@FXML
	private Label errorsubmit;
	@FXML
	private Label succsessubmit;
	public static Visitornumberreport visitornumberreport;
	public static String MONTHNOW;
	public static String YEARNOW;
	public static String NAMEPARKNOW;

	public static String amonth;
	public static String ayear;
	public static String anamepark;

	@FXML
	/**
	 * initialize the controllers
	 */
	public void initialize() {
		System.out.println(user.getParkname());
		anamepark = user.getParkname();
		System.out.println(anamepark);

	}

	@FXML
	/**
	 * submit button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void submit(ActionEvent event) throws Exception {
		amonth = textmonth.getText();
		ayear = textyear.getText();
		if (amonth.trim().isEmpty() || ayear.trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning alert");
			alert.setContentText("The field is Empty");
			alert.setHeaderText("Error");
			alert.show();
			errorsubmit.setText("Error");
			succsessubmit.setText(null);
			amonth = null;
			ayear = null;
			return;
		}
		int inegermonth = Integer.parseInt(amonth);
		int inegermonththis = Integer.parseInt(MONTHNOW);

		int inegeryear = Integer.parseInt(ayear);
		int inegeryearthis = Integer.parseInt(YEARNOW);

		if (inegermonth > inegermonththis || inegermonth <= 0 || inegeryear > inegeryearthis || inegeryear < 2000) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning alert");
			alert.setContentText("The month or the year is incorrect");
			alert.setHeaderText("Error");
			alert.show();
			errorsubmit.setText("Error");
			succsessubmit.setText(null);

			amonth = null;
			ayear = null;

			return;
		}
		errorsubmit.setText(null);

		succsessubmit.setText("Succses Submit!!");

		icandisplay = true;

	}

	@FXML
	/**
	 * Back button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		if (parkMloaded) {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/parkmanagerGUI/VisitorsReportIncluded.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		} else {
			FXMLLoader loader = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStage = new Stage();
			Pane root = loader.load(getClass().getResource("/departmentManagerGUI/dashboardDM.fxml").openStream());
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			primaryStage.show();
		}

	}

	@FXML
	/**
	 * Display button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void diplayreportincluded(ActionEvent event) throws Exception {
		if (icandisplay) {
			PresentationAreport.MONTH = amonth;
			PresentationAreport.NAMEPARK = anamepark;
			PresentationAreport.YEAR = ayear;
			System.out.println("step1");
			if (startpresentation(anamepark, ayear, amonth)) {
				System.out.println("step2");
				PresentationAreport.visitornumberreport = visitornumberreport;
				FXMLLoader loaderT = new FXMLLoader();
				((Node) event.getSource()).getScene().getWindow().hide();
				Stage primaryStageT = new Stage();
				Pane rootT = loaderT
						.load(getClass().getResource("/parkmanagerGUI/PresentationAreport.fxml").openStream());
				Scene sceneT = new Scene(rootT);
				sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
				primaryStageT.initStyle(StageStyle.UNDECORATED);
				primaryStageT.setTitle("Before presentation");
				primaryStageT.setScene(sceneT);
				youcantdisplay.setText(null);
				errorindisplay.setText(null);
				primaryStageT.show();

			} else {
				errorindisplay.setText("Error");
			}

		} else {
			youcantdisplay.setText("The report is not ready yet");
		}

	}

	/**
	 * function that checks data when display button was clicked
	 * 
	 * @param namepark park name
	 * @param year     year
	 * @param month    month
	 * @return true or false
	 */
	public boolean startpresentation(final String namepark, final String year, final String month) {
		if (namepark.isEmpty() || year.isEmpty() || month.isEmpty())
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		System.out.println(namepark + "1");
		System.out.println(year + "2");
		System.out.println(month + "3");

		// to check ID in DB
		Data.add(namepark); // insert namepark that the park in the UI insert
		Data.add(year); // insert namepark that the park in the UI insert
		Data.add(month);
		Message messageToServer = new Message(CONSTANTS.StartPresentitaion, Data);
		ClientUI.chat.accept(messageToServer);
		System.out.println("im not accept from client");
		if (isLoginValid) {
			isLoginValid = false;
			return true;
		}
		return false;
	}

}
