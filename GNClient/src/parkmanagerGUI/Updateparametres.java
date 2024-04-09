package parkmanagerGUI;

import javafx.scene.control.TextField;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.util.converter.IntegerStringConverter;
import client.ClientUI;
import entities.User;
import parkmanagerGUI.dashboardPMController;
import entities.Park;
import entities.Park;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;
import visitorGUI.OrderFormController;
import visitorGUI.dashboardVController;

/**
 * update parameters controller
 */
public class Updateparametres {
	public static boolean ican = true;

	public static boolean isLoginValid = false;
	public static boolean icanupdate = false;

	public static Park park;
	public static Park newpark;
	public static String thevalue;
	public static String nametheparameter;
//
//    @FXML
//    private TableView<Park> table;

	@FXML
	private Button Back = null;

	@FXML
	private Button submit1 = null;

	@FXML
	private Button update1 = null;

	@FXML
	private Label errorparameter = null;
	@FXML
	private Label updateparameter = null;

//    @FXML
//    private TableColumn<Park,String> parkname;
//    
//    @FXML
//    private TableColumn<Park,Integer> maxvisitorsnumber;
//    
//    @FXML
//    private TableColumn<Park,Integer> currentvisitors;
//    
//    @FXML
//    private TableColumn<Park,Integer> delaytime;
//    
//    @FXML
//    private TableColumn<Park,Integer> diffvisitors;

	@FXML
	private ComboBox<String> combobox = null;

	// private ObservableList<Park> parkList = FXCollections.observableArrayList();

	@FXML
	private TextField newvalue;

	@FXML
	private Label theparameter = null;

	@FXML
	/**
	 * initialize the class
	 */
	public void initialize() {

		String[] parameters = { "maxvisitorsnumber", "delaytime", "diffVisitors" };
		combobox.getItems().addAll(parameters);
		combobox.setOnAction(event -> {
			nametheparameter = combobox.getSelectionModel().getSelectedItem();
			theparameter.setText(nametheparameter);
		});

	}

	@FXML
	/**
	 * submit button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void submit(ActionEvent event) throws Exception {
		thevalue = newvalue.getText();
		if (thevalue.trim().isEmpty()) {
			errorparameter.setText("ERROR");
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning alert");
			alert.setContentText("The field is Empty");
			alert.setHeaderText("Error");
			alert.show();
			return;
		}
		int inegervalue = Integer.parseInt(thevalue);
		if (inegervalue <= 0) {
			errorparameter.setText("ERROR");
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning alert");
			alert.setContentText("the value is negative or Zero");
			alert.setHeaderText("Error");
			alert.show();
			return;
		}
		icanupdate = true;
		errorparameter.setText(null);

	}

	@FXML

	/**
	 * updateparameter button was clicked
	 * 
	 * @param event button was clicked
	 * @throws Exception javaFX exception
	 */
	public void updateparameter(ActionEvent event) throws Exception {
		if (icanupdate) {
			if (nametheparameter == "maxvisitorsnumber") {

				if (iwantupdate(park.getParkName(), nametheparameter, thevalue)) {
					System.out.println("true");
					updateparameter.setText("The update is send");
					errorparameter.setText(null);
				} else {
					errorparameter.setText("Error");
					errorparameter.setText(null);
				}
			} else if (nametheparameter == "delaytime") {
				if (iwantupdate(park.getParkName(), nametheparameter, thevalue)) {
					System.out.println("true");
					updateparameter.setText("The update is send");
					errorparameter.setText(null);
				} else {

					errorparameter.setText("Error");
					updateparameter.setText(null);
				}

			} else {

				if (iwantupdate(park.getParkName(), nametheparameter, thevalue)) {
					System.out.println("true");
					updateparameter.setText("The update is send");
					errorparameter.setText(null);
				} else {
					errorparameter.setText("Error");
					updateparameter.setText(null);

				}

			}
		} else {
			errorparameter.setText("You should first to Submit");
			updateparameter.setText(null);

		}

	}

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
		Pane root = loader.load(getClass().getResource("/parkmanagerGUI/dashboardPM1.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * send update to DB
	 * 
	 * @param parkname      park name
	 * @param typeparameter parameter type
	 * @param value         value of parameter
	 * @return true or false
	 */
	public boolean iwantupdate(final String parkname, final String typeparameter, final String value) {
		if (parkname == null || typeparameter == null || value == null)
			return false;

		ArrayList<String> Data = new ArrayList<String>();
		Data.add(parkname);
		Data.add(typeparameter);
		Data.add(value);
		Message messageToServer = new Message(CONSTANTS.CheckParkUpdate, Data);
		ClientUI.chat.accept(messageToServer);
		if (Updateparametres.isLoginValid) {
			Updateparametres.isLoginValid = false;

			return true;
		}

		return false;
	}
}
