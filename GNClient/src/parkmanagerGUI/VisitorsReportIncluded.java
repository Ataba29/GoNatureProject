package parkmanagerGUI;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
 * VisitorsReport controller
 */
public class VisitorsReportIncluded {
	public static Park park;
	public static User user;

	public static boolean isLoginValid = false;

	public static String month;
	public static String year;
	public static String day;
	public static String namepark1;

	public static String aftermonth;
	public static String afteryear;
	public static String afternamepark;

	@FXML
	private Button Back = null;
	@FXML
	private Button Check = null;
	@FXML
	private Button Presentationofareport = null;

	@FXML
	private TextField thedateofday = null;

	@FXML
	private TextField namepark = null;

	@FXML
	private Label resultforcreating = null;
	@FXML
	private Label resultforcreatingfalse = null;

	@FXML
	/**
	 * back button was clicked
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
	 * initialize the controller
	 */
	public void initialize() {
		namepark1 = user.getParkname();

		thedateofday.setText(day + "/" + month + "/" + year);
		namepark.setText(namepark1);
	}

	@FXML
	/**
	 * check button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void Checkvisi(ActionEvent event) throws IOException {
		String monthnow = month;
		String yearnow = year;
		String parkkname = namepark1;
		int dayinteger = Integer.parseInt(day);
		System.out.println(dayinteger + "its tha day");

		if (dayinteger >= 28 && dayinteger <= 31) {
			if (!(monthnow.isEmpty() || yearnow.isEmpty() || parkkname.isEmpty())) {

				if (importdata(monthnow, yearnow, parkkname)) {
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
	 * switch to Beforepresentation.fxml
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	private void Presentationofareportreal(ActionEvent event) throws IOException {
		Beforepresentation.MONTHNOW = month;
		Beforepresentation.YEARNOW = year;
		Beforepresentation.NAMEPARKNOW = namepark1;
		Beforepresentation.parkMloaded = true;
		Beforepresentation.user = user;
		FXMLLoader loaderT = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStageT = new Stage();
		Pane rootT = loaderT.load(getClass().getResource("/parkmanagerGUI/Beforepresentation.fxml").openStream());
		Scene sceneT = new Scene(rootT);
		sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStageT.initStyle(StageStyle.UNDECORATED);
		primaryStageT.setTitle("Before presentation");
		primaryStageT.setScene(sceneT);
		primaryStageT.show();
	}

	/**
	 * import data
	 * 
	 * @param monthe    month
	 * @param yeare     year
	 * @param nameparke park name
	 * @return return true or false
	 */
	public boolean importdata(final String monthe, final String yeare, final String nameparke) {
		if (monthe == null || yeare == null || nameparke == null)
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		// to check ID in DB
		Data.add(monthe);
		Data.add(yeare);
		Data.add(nameparke);
		Message messageToServer = new Message(CONSTANTS.GiveMeDataForVisitorsReport, Data);
		ClientUI.chat.accept(messageToServer);
		if (VisitorsReportIncluded.isLoginValid) {
			VisitorsReportIncluded.isLoginValid = false;
			return true;
		}
		return false;
	}

}
