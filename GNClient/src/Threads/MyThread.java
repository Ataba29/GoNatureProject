package Threads;

import java.util.ArrayList;

import client.ClientUI;
import entities.Orders;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilities.CONSTANTS;
import utilities.Message;
import visitorGUI.TwoHoursLeftController;

/**
 * 
 * MyThread class are releated to threads what to do
 *
 */
public class MyThread extends Thread {
	private Orders order;
	public static boolean stillOrder = true;

	public MyThread(Orders order) {
		this.order = order;
	}

	/**
	 * run function for the threads
	 */
	@Override
	public void run() {
		try {
			// wait two hours
			Thread.sleep(7200000);
			TwoHoursLeftController.order = order;
			// to check if the order still exist
			ArrayList<String> OrderIDAraryList = new ArrayList<String>();
			OrderIDAraryList.add(order.getOrderID());
			Message messageToServer = new Message(CONSTANTS.GetOrderDetails, OrderIDAraryList);
			ClientUI.chat.accept(messageToServer);
			if (!stillOrder) {
				stillOrder = true;
				Thread.interrupted();
			} else {

				// Load and show GUI
				Platform.runLater(() -> {
					try {
						FXMLLoader loaderT = new FXMLLoader();
						Stage primaryStageT = new Stage();
						Pane rootT = loaderT.load(getClass().getResource("/visitorGUI/TwoHoursLeft.fxml").openStream());
						Scene sceneT = new Scene(rootT);
						primaryStageT.setTitle("TwoHoursLeft");
						primaryStageT.setScene(sceneT);
						primaryStageT.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				});
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
