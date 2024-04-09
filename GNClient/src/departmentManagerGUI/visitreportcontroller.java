package departmentManagerGUI;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import client.ClientUI;
import entities.cancellationreport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * class controls the visitreport fxml
 */
public class visitreportcontroller implements Initializable{
	public static HashSet<String> parksname;
	public static HashSet<Integer> theyear;
	public static float[] everg;

	private Date current=new Date();
	Date now=new Date();

	private Integer[] month= {1,2,3,4,5,6,7,8,9,10,11,12};
	
	@FXML
	private AreaChart visitreport;
	
	@FXML
	private ChoiceBox<String> chooseparkname;
	
	@FXML
	private ChoiceBox<Integer>  choosemonth;
	
	@FXML
	private Label repomonth;
	
	@FXML
	private Label label;
	
	@FXML
	private Label averagemedian;
	
	@FXML
	private ChoiceBox<Integer> choosyear;
	
	/**
	 * Initializes the controller after its root element has been completely processed.
	 * 
	 * @param arg0 The location used to resolve relative paths for the root object, or {@code null} if the location is not known.
	 * @param arg1 The resources used to localize the root object, or {@code null} if the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		label.setText("Choose (year,month,the park name)");		
		choosemonth.getItems().addAll(month);
		ArrayList<Object> Data = new ArrayList<>();
		Message messageToServer = new Message(CONSTANTS.gittheparknamesandyearvis, Data);
		ClientUI.chat.accept(messageToServer);
		if (parksname != null) {
		    chooseparkname.getItems().addAll(parksname);
		    chooseparkname.getItems().add("all");
		    Set<Integer> set=theyear;
		    List<Integer> arrlist=new ArrayList<>(set);
		    Collections.sort(arrlist);
		    choosyear.getItems().addAll(theyear);
		}
	}
		
	/**
	 * take the detail (month and park name) from the AreaChart and produce report
	 * there is 3 AreaChart (Orders canceled and orders not executed but not canceled,all)
	 * @param event event button is clicked
	 */
	@FXML
	public void getmonthreport(ActionEvent event) {
		if(choosemonth.getValue()==null||chooseparkname.getValue()==null) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
			return;
		}
		
		float allsum=0;
		
    	visitreport.getData().clear();
		repomonth.setText("the visit report to park("+chooseparkname.getValue()+") for the date "+choosemonth.getValue()+"|"+choosyear.getValue()+" :-");
		
		ArrayList<Object> Data = new ArrayList<>();
		Data.add(choosyear.getValue());
		Data.add(choosemonth.getValue());
		Data.add(chooseparkname.getValue());		
		Message messageToServer = new Message(CONSTANTS.gitvisitdata, Data);
	    ClientUI.chat.accept(messageToServer);	
	    
	    XYChart.Series visit=new XYChart.Series();
	    visit.setName("the all");
	    for(int i=1;i<=31;i++) {
	    	  visit.getData().add(new XYChart.Data(i,everg[i]));
	    	  allsum+=everg[i];

	    }
	    visitreport.getData().addAll(visit);

	    averagemedian.setText("the average is:-("+(allsum/31)+") and the median is:-("+everg[16]+")");
	}
	
	
	/**
	 * log out client and bake to the report page
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void bake(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		((Node) event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		Pane root = loader.load(getClass().getResource("/departmentManagerGUI/reportpage.fxml").openStream());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.setTitle("reports");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	/**
	 * log out client and bake to the log page
	 * @param event the button is pressed
	 * @throws IOException javaFX exception
	 */
	@FXML
	public void logout(ActionEvent event) throws IOException {
		System.exit(0);
	}
}
