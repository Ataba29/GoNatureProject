package visitorGUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import utilities.CONSTANTS;
import utilities.Message;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ClientUI;
import entities.Orders;
import entities.Park;
import entities.Visitors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * OrderFormController class control in the OrderForm.fxml
 *
 */
public class OrderFormController implements Initializable {
	public static boolean fromDashboard = false;
	public static boolean IsNewTraveler; // to check if the traveler
	public static boolean fromNewVisitor = false;
	public static Visitors visitor;
	public static Park park;
	public static String visitorID;
	public static double SumOfVisitor;
	public static Integer orderID;

	@FXML
	private TextField ParkNameText;
	@FXML
	private TextField VisitTimeText;
	@FXML
	private TextField VisitorsNumberText;
	@FXML
	private TextField EmailText;
	@FXML
	private TextField AddressText;
	@FXML
	private ComboBox<String> OrderTypeCombo;
	@FXML
	private TextField YearText;
	@FXML
	private TextField MonthText;
	@FXML
	private TextField DayText;

	public String getParkNameText() {
		return ParkNameText.getText();
	}

	public String getVisitTimeText() {
		return VisitTimeText.getText();
	}

	public String getVisitorsNumberText() {
		return VisitorsNumberText.getText();
	}

	public String getEmailText() {
		return EmailText.getText();
	}

	public String getAddressText() {
		return AddressText.getText();
	}

	public String getOrderTypeCombo() {
		return OrderTypeCombo.getValue();
	}

	public String getYearText() {
		return YearText.getText();
	}

	public String getMonthText() {
		return MonthText.getText();
	}

	public String getDayText() {
		return DayText.getText();
	}

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException in opening the fxml page
	 */
	// Event Listener on Button.onAction
	@FXML
	public void BackBtnFun(ActionEvent event) throws IOException {
		// if the previous page was the dashboradV page
		if (fromDashboard) {
			FXMLLoader loaderT = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStageT = new Stage();
			Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/dashboardV.fxml").openStream());
			Scene sceneT = new Scene(rootT);
			sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStageT.initStyle(StageStyle.UNDECORATED);
			primaryStageT.setTitle("dashboardV");
			primaryStageT.setScene(sceneT);
			primaryStageT.show();
			// if the previous page was the newVisitor Page
		} else if (fromNewVisitor) {
			FXMLLoader loaderT = new FXMLLoader();
			((Node) event.getSource()).getScene().getWindow().hide();
			Stage primaryStageT = new Stage();
			Pane rootT = loaderT.load(getClass().getResource("/gui/LogIn.fxml").openStream());
			Scene sceneT = new Scene(rootT);
			sceneT.getStylesheets().add(getClass().getResource("/Design/Design.css").toExternalForm());
			primaryStageT.initStyle(StageStyle.UNDECORATED);
			primaryStageT.setTitle("login");
			primaryStageT.setScene(sceneT);
			primaryStageT.show();
		}
	}

	/**
	 * 
	 * @param event (clicked on the button)
	 * @throws IOException    in opening the fxml page
	 * @throws ParseException exception
	 */
	// Event Listener on Button.onAction
	@FXML
	public void SubmitBtnFun(ActionEvent event) throws IOException, ParseException {
		// checking if there is an empty input
		if (getParkNameText().trim().isEmpty() || getVisitTimeText().trim().isEmpty()
				|| getVisitorsNumberText().trim().isEmpty() || getEmailText().trim().isEmpty()
				|| getAddressText().trim().isEmpty() || (OrderTypeCombo.getValue() == null)
				|| getYearText().trim().isEmpty() || getMonthText().trim().isEmpty() || getDayText().trim().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Warning Alert");
			alert.setContentText("You left some fields empty");
			alert.setHeaderText("Error");
			alert.show();
		} else {
			// if the traveler is new , so add it to the DB
			ArrayList<String> DataForVisitor = new ArrayList<String>();
			if (IsNewTraveler) {
				DataForVisitor.add(visitor.getVisitorID());
				DataForVisitor.add(visitor.getFirstname());
				DataForVisitor.add(visitor.getLastname());
				DataForVisitor.add(visitor.getEmail());
				DataForVisitor.add(visitor.getPhonenumber());
				// send to server
				Message messageToServer1 = new Message(CONSTANTS.NewVisitor, DataForVisitor);
				ClientUI.chat.accept(messageToServer1);
			}
			ArrayList<String> IDVisitorArrayList = new ArrayList<String>();
			IDVisitorArrayList.add(visitorID);
			// get the details of the visitor from the server
			Message messageToServer2 = new Message(CONSTANTS.GetVisitorDetails, IDVisitorArrayList);
			ClientUI.chat.accept(messageToServer2);
			// checking if the traveler choose type order equals to organized group
			if (visitor.getTypevisitor().equals("traveller") && getOrderTypeCombo().equals("organized group")) {
				Alert alert2 = new Alert(Alert.AlertType.WARNING);
				alert2.setTitle("Warning Alert");
				alert2.setContentText("You Are Not Guide");
				alert2.setHeaderText("Error");
				alert2.show();
			} else {
				// checking if the input date is in the future
				if (!IsFutureDate(getYearText(), getMonthText(), getDayText())) {
					Alert alert3 = new Alert(Alert.AlertType.WARNING);
					alert3.setTitle("Warning Alert");
					alert3.setContentText("Insert Future Date");
					alert3.setHeaderText("Error");
					alert3.show();
				} else {
					// checking if the year,month,day are true
					int month = Integer.parseInt(getMonthText());
					int day = Integer.parseInt(getDayText());
					if (month < 1 || month > 12 || day < 1 || day > 30) {
						Alert alert3 = new Alert(Alert.AlertType.WARNING);
						alert3.setTitle("Warning Alert");
						alert3.setContentText("Insert Valid Date");
						alert3.setHeaderText("Error");
						alert3.show();
					} else {
						// check if the user choose typeorder correct for the visitor number
						int visitornumber = Integer.parseInt(getVisitorsNumberText());
						if ((visitornumber > 15) || (visitornumber < 1)
								|| (visitornumber != 1 && getOrderTypeCombo().equals("individual"))) {
							Alert alert3 = new Alert(Alert.AlertType.WARNING);
							alert3.setTitle("Warning Alert");
							alert3.setContentText("Insert A Correct Visitor Number");
							alert3.setHeaderText("Error");
							alert3.show();
						} else {
							// getting the details of the order and add it the DB
							ArrayList<String> DataForOrder = new ArrayList<String>();
							DataForOrder.add(orderID.toString());
							DataForOrder.add(getParkNameText());
							DataForOrder.add(getVisitTimeText());
							DataForOrder.add(getVisitorsNumberText());
							DataForOrder.add(getEmailText());
							DataForOrder.add(getAddressText());
							DataForOrder.add(getOrderTypeCombo());
							DataForOrder.add(visitorID);
							DataForOrder.add(getYearText());
							DataForOrder.add(getMonthText());
							DataForOrder.add(getDayText());
							String dateString = getYearText() + "-" + getMonthText() + "-" + getDayText() + " "
									+ getVisitTimeText() + ":00";
							DataForOrder.add(dateString);
							// check if there is place in the park in this time
							if (IsValidToOrder(DataForOrder)) {
								// send to server
								Message messageToServer3 = new Message(CONSTANTS.NewOrder, DataForOrder);
								ClientUI.chat.accept(messageToServer3);
								dashboardVController.visitorID = visitorID;
								// send to the server to add the id order to allorders table
								ArrayList<String> IDOrderArrayListToAllOrders = new ArrayList<String>();
								IDOrderArrayListToAllOrders.add(orderID.toString());
								Message messageToServer4 = new Message(CONSTANTS.InsertToAllOrderTable,
										IDOrderArrayListToAllOrders);
								ClientUI.chat.accept(messageToServer4);

								Alert alert = new Alert(AlertType.INFORMATION);
								alert.setTitle("Alert");
								alert.setHeaderText("Added Order :" + orderID);
								alert.setContentText("Your Order Has Been Added Successfully!");
								alert.showAndWait();

								FXMLLoader loaderT = new FXMLLoader();
								((Node) event.getSource()).getScene().getWindow().hide();
								Stage primaryStageT = new Stage();
								Pane rootT = loaderT
										.load(getClass().getResource("/visitorGUI/dashboardV.fxml").openStream());
								Scene sceneT = new Scene(rootT);
								sceneT.getStylesheets()
										.add(getClass().getResource("/Design/Design.css").toExternalForm());
								primaryStageT.initStyle(StageStyle.UNDECORATED);
								primaryStageT.setTitle("dashboardV");
								primaryStageT.setScene(sceneT);
								primaryStageT.show();
							} else {
								// if there isn't place so open waitlist page
								waitlistController.OrderWaitList = new Orders(DataForOrder.get(0), DataForOrder.get(1),
										DataForOrder.get(2), DataForOrder.get(3), DataForOrder.get(4),
										DataForOrder.get(5), DataForOrder.get(6), DataForOrder.get(7),
										DataForOrder.get(8), DataForOrder.get(9), DataForOrder.get(10));
								FXMLLoader loaderT = new FXMLLoader();
								((Node) event.getSource()).getScene().getWindow().hide();
								Stage primaryStageT = new Stage();
								Pane rootT = loaderT
										.load(getClass().getResource("/visitorGUI/waitlist.fxml").openStream());
								Scene sceneT = new Scene(rootT);
								sceneT.getStylesheets()
										.add(getClass().getResource("/Design/Design.css").toExternalForm());
								primaryStageT.initStyle(StageStyle.UNDECORATED);
								primaryStageT.setTitle("waitlist");
								primaryStageT.setScene(sceneT);
								primaryStageT.show();
							}
						}
					}
				}
			}
		}

	}

	/**
	 * check date
	 * 
	 * @param orderYear  order year
	 * @param orderMonth order month
	 * @param orderDay   order day
	 * @return true if the date in the future , else return false.
	 */
	public boolean IsFutureDate(String orderYear, String orderMonth, String orderDay) {
		LocalDate currentDate = LocalDate.now();
		int year = currentDate.getYear();
		int month = currentDate.getMonthValue();
		int day = currentDate.getDayOfMonth();

		int orderYearint = Integer.valueOf(orderYear);
		int orderMonthint = Integer.valueOf(orderMonth);
		int orderDayint = Integer.valueOf(orderDay);
		// if the input year bigger than the current year
		if (orderYearint > year) {
			return true;
		} else {
			// if the input year equals to the current year
			if (orderYearint == year) {
				// if the input month bigger than the current month
				if (orderMonthint > month) {
					return true;
				} else {
					// if the input month equals to the current month
					if (orderMonthint == month) {
						// if the input day bigger than the current day
						if (orderDayint > day) {
							return true;
						} else {
							return false;
						}
					}
					// if the input month smaller than the current month
					else {
						return false;
					}
				}
			}
			// if the input year smaller than the current year
			else {
				return false;
			}
		}
	}

	/**
	 * 
	 * @param order order
	 * @return true if there is place to the order , else return false.
	 * @throws ParseException exception
	 */
	public boolean IsValidToOrder(ArrayList<String> order) throws ParseException {
		ArrayList<String> parknameArrayList = new ArrayList<String>();
		parknameArrayList.add(order.get(1));
		// send to server to get park details
		Message messageToServer = new Message(CONSTANTS.GetParkDetails, parknameArrayList);
		ClientUI.chat.accept(messageToServer);
		int maxvisitorsnumber = park.getMaxVisitorsNumber();
		int delaytime = park.getDelayTime();
		int diffVisitors = park.getDiffVisitors();
		// send to the server to get the sum of the visitors between two times
		ArrayList<String> SumOfVisitors = new ArrayList<String>();
		SumOfVisitors.add(order.get(1));
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime date = LocalDateTime.parse(order.get(11), dtf);
		LocalDateTime prevDate = date.minusHours(delaytime);
		SumOfVisitors.add(order.get(11));
		SumOfVisitors.add(prevDate.toString());
		// send to server to get park details
		Message messageToServer1 = new Message(CONSTANTS.GetSumOfVisitors, SumOfVisitors);
		ClientUI.chat.accept(messageToServer1);
		if (Integer.parseInt(order.get(3)) < (maxvisitorsnumber - diffVisitors - SumOfVisitor))
			return true;
		else
			return false;

	}

	/**
	 * initialization function that add the values to the combo box
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("individual", "small group", "organized group");
		OrderTypeCombo.setItems(list);
		ArrayList<String> nullArrayList = new ArrayList<String>();
		// send to server
		Message messageToServer = new Message(CONSTANTS.GetMaxIDOrder, nullArrayList);
		ClientUI.chat.accept(messageToServer);
		orderID++;
	}
}
