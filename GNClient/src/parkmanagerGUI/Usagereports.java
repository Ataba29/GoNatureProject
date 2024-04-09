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
 * Usagereports controller
 */
public class Usagereports {

	public static Park park;
	public static User user;

	public static boolean isLoginValid = false;
	public static boolean isLoginValid2 = false;
	public static boolean isLoginValid3 = false;

	public static String month;
	public static String year;
	public static String day;
	public static String namepark;
	public static Parkfulltimes parkfulltimes;
	public static ArrayList<Parkfulltimes> dbResponsehaha = new ArrayList<Parkfulltimes>();

	@FXML
	private TextField thedateofday1 = null;

	@FXML
	private TextField namepark1 = null;

	@FXML
	private Button Back = null;
	@FXML
	private Button CreatreportU = null;
	@FXML
	private Button DisplayreportU = null;
	@FXML
	private Label resultforcreating = null;
	@FXML
	private Label resultforcreatingfalse = null;
	@FXML
	private Label resultforcreatingfalse1 = null;

	@FXML
	/**
	 * initialize the controller
	 */
	public void initialize() {

		namepark = user.getParkname();
		System.out.println(namepark);
		BeforeeeDisplay.user = user;
		BeforeeeDisplay.park = park;

		BeforeeeDisplay.dayofthisday = day;
		BeforeeeDisplay.monthofthisday = month;
		BeforeeeDisplay.yearofthisday = year;
		BeforeeeDisplay.dbResponsehaha = dbResponsehaha;

		thedateofday1.setText(day + "/" + month + "/" + year);
		namepark1.setText(namepark);
	}

	@FXML
	/**
	 * BackBtn was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/parkmanagerGUI/Reports.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@FXML
	/**
	 * create was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void CreatAreportU(ActionEvent event) throws IOException {
		String monthnow = month;
		String yearnow = year;
		String parkkname = namepark;
		int dayinteger = Integer.parseInt(day);
		System.out.println(dayinteger + "its tha day");

		if (dayinteger >= 28 && dayinteger <= 31) {
			if (!(monthnow.isEmpty() || yearnow.isEmpty() || parkkname.isEmpty())) {

				if (importdata1(monthnow, yearnow, parkkname)) {
					resultforcreatingfalse.setText(null);
					// Displayingausagereport.dbResponsehaha=dbResponsehaha;
					resultforcreating.setText("The report was created susscessfully");

				} else {
					resultforcreating.setText(null);
					resultforcreatingfalse.setText("Report generation failed");
				}
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("We havent't reached the end of the month yet");
			alert.setHeaderText("Error");
			alert.show();
		}

	}

	@FXML
	/**
	 * display was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void displayusagereport(ActionEvent event) throws IOException {

		BeforeeeDisplay.dbResponsehaha = dbResponsehaha;
		BeforeeeDisplay.park = park;
		BeforeeeDisplay.parkMloaded = true;
		FXMLLoader loaderPM1 = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStagePM1 = new Stage();
		Pane rootPM1 = loaderPM1.load(getClass().getResource("/parkmanagerGUI/BeforeeeDisplay.fxml").openStream());
		Scene scenePM1 = new Scene(rootPM1);
		scenePM1.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStagePM1.initStyle(StageStyle.UNDECORATED);
		primaryStagePM1.setTitle("To Displat");
		primaryStagePM1.setScene(scenePM1);
		primaryStagePM1.show();

	}

	/**
	 * import data
	 * 
	 * @param monthe    month
	 * @param yeare     year
	 * @param nameparke park name
	 * @return return true or false
	 */
	public boolean importdata1(final String monthe, final String yeare, final String nameparke) {
		if (monthe == null || yeare == null || nameparke == null)
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB

		Data.add(nameparke);
		Data.add(yeare);
		Data.add(monthe);

		Message messageToServer = new Message(CONSTANTS.GiveMeDataForUUUUREport, Data);
		ClientUI.chat.accept(messageToServer);
		if (isLoginValid) {
			isLoginValid = false;
			return true;
		}
		return false;
	}

}
