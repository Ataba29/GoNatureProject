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
 * opens the fxml before displaying the usage report
 */
public class BeforeeeDisplay {

	public static ArrayList<Parkfulltimes> dbResponsehaha = new ArrayList<Parkfulltimes>();
	public static String amonth;
	public static String ayear;
	public static String anamepark;
	public static Park park;
	public static User user;
	public static boolean icandisplay = false;
	public static boolean isLoginValid = false;
	public static boolean isLoginValid2 = false;
	public static boolean isLoginValid3 = false;
	public static Parkfulltimes parkfulltimes;
	public static boolean parkMloaded = false;

	public static String monthofthisday;
	public static String yearofthisday;
	public static String dayofthisday;

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

	/**
	 * initialize the class
	 */
	@FXML
	public void initialize() {
		anamepark = user.getParkname();

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
		int inegermonththis = Integer.parseInt(monthofthisday);

		int inegeryear = Integer.parseInt(ayear);
		int inegeryearthis = Integer.parseInt(yearofthisday);

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
			Pane root = loader.load(getClass().getResource("/parkmanagerGUI/Usagereports.fxml").openStream());
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
	 * display button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void Displayreport(ActionEvent event) throws Exception {
		succsessubmit.setText(null);

		if (icandisplay) {
			if (ifreportiscreated(amonth, ayear, anamepark)) {
				if (displayreportold(amonth, ayear, anamepark)) {
					System.out.println("its good");
					Displayingausagereport.dbResponsehaha = dbResponsehaha;
					Displayingausagereport.park = park;
					FXMLLoader loaderPM1 = new FXMLLoader();
					((Node) event.getSource()).getScene().getWindow().hide();
					Stage primaryStagePM1 = new Stage();
					Pane rootPM1 = loaderPM1
							.load(getClass().getResource("/parkmanagerGUI/Displayingausagereport.fxml").openStream());
					Scene scenePM1 = new Scene(rootPM1);
					scenePM1.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
					primaryStagePM1.initStyle(StageStyle.UNDECORATED);
					primaryStagePM1.setTitle("To Display");
					primaryStagePM1.setScene(scenePM1);
					primaryStagePM1.show();
					youcantdisplay.setText(null);
					errorindisplay.setText(null);

				} else {

					errorindisplay.setText("Error");

				}

			} else {

				youcantdisplay.setText("The report is not ready yet");
			}

		}

	}

	/**
	 * checks if report is created in DB
	 * 
	 * @param monthe1    month
	 * @param yeare1     year
	 * @param nameparke1 park name
	 * @return true or false
	 */
	public boolean ifreportiscreated(final String monthe1, final String yeare1, final String nameparke1) {
		if (monthe1 == null || yeare1 == null || nameparke1 == null)
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB

		Data.add(nameparke1);
		Data.add(yeare1);
		Data.add(monthe1);

		Message messageToServer = new Message(CONSTANTS.STARTDISPLAYIN, Data);
		ClientUI.chat.accept(messageToServer);
		if (isLoginValid2) {
			isLoginValid2 = false;
			return true;
		}
		return false;

	}

	/**
	 * handles the event of button display was clicked and checks data
	 * 
	 * @param month2    month
	 * @param year2     year
	 * @param namepark2 park name
	 * @return true or false
	 */
	public boolean displayreportold(final String month2, final String year2, final String namepark2) {
		if (month2 == null || year2 == null || namepark2 == null)
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(namepark2);
		Data.add(year2);
		Data.add(month2);
		Message messageToServer = new Message(CONSTANTS.DISPLAYINOLDREPORT, Data);
		ClientUI.chat.accept(messageToServer);
		if (isLoginValid3) {
			isLoginValid3 = false;
			return true;
		}
		return false;

	}

}
