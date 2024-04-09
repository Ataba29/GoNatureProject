package parkmanagerGUI;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * display usage report
 */
public class Displayingausagereport {
	public static Park park;
	public static User user;

	public static boolean isLoginValid = false;
	public static Parkfulltimes parkfulltimes;
	public static ArrayList<Parkfulltimes> dbResponsehaha = new ArrayList<Parkfulltimes>();
	@FXML
	private Button Back = null;
	@SuppressWarnings("rawtypes")
	@FXML
	private ScatterChart scatterChart;

	@FXML
	private CategoryAxis days;
	@FXML
	private NumberAxis hours;

	@FXML
	/**
	 * back button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX error
	 */
	public void BackBtn(ActionEvent event) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();

		Pane root = loader.load(getClass().getResource("/parkmanagerGUI/BeforeeeDisplay.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	@SuppressWarnings("unchecked")
	@FXML
	/**
	 * initialize the class
	 */
	public void initialize() {
		XYChart.Series<String, Integer> seriesstart = new XYChart.Series<>();
		XYChart.Series<String, Integer> seriesend = new XYChart.Series<>();

		seriesstart.setName("Start");
		seriesend.setName("End");

		for (Parkfulltimes parkfulltimes : dbResponsehaha) {
			int hourStart = Integer.parseInt(parkfulltimes.gethourstart());
			int hourEnd = Integer.parseInt(parkfulltimes.gethourend());
			seriesstart.getData().add(new XYChart.Data<>(parkfulltimes.getday(), hourStart));
			seriesend.getData().add(new XYChart.Data<>(parkfulltimes.getday(), hourEnd));

		}
		scatterChart.getData().addAll(seriesstart, seriesend);

	}

}
