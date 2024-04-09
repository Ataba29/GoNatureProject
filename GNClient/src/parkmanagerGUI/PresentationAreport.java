package parkmanagerGUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import entities.Visitornumberreport;

import client.ClientController;
import client.ClientUI;
import entities.User;
import entities.Park;

import gui.LogInController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
 * present report
 */
public class PresentationAreport {
	public static User user;
	public static Visitornumberreport visitornumberreport;
	public static String MONTH;
	public static String YEAR;
	public static String NAMEPARK;
	public static boolean isLoginValid = false;

	@FXML
	private PieChart piechart;

	@FXML
	private Label nameparkname;
	@FXML
	private Label datemonth;

	@FXML
	private Button back;

	@FXML
	/**
	 * initialize the class
	 */
	public void initialize() {
		nameparkname.setText(NAMEPARK);
		datemonth.setText(MONTH + "/" + YEAR);
		if (!(NAMEPARK.isEmpty() || YEAR.isEmpty() || MONTH.isEmpty())) {
			ObservableList<PieChart.Data> piechartdata = FXCollections.observableArrayList(
					new PieChart.Data("organized group",
							Integer.parseInt(visitornumberreport.getvisitorsnumberoforganizedgroup())),
					new PieChart.Data("small group + individual",
							Integer.parseInt(visitornumberreport.getvisitornumberofinvidual())));

			piechartdata.forEach(data -> data.nameProperty()
					.bind(Bindings.concat(data.getName(), " amount:", data.pieValueProperty())));
			piechart.getData().addAll(piechartdata);
		}

	}

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
		Pane root = loader.load(getClass().getResource("/parkmanagerGUI/Beforepresentation.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

}
