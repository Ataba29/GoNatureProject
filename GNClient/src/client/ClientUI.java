package client;

import gui.IPFormController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Class that starts the clientUI of the client
 */
public class ClientUI extends Application {
	public static ClientController chat; // only one instance

	/**
	 * Main that starts the GUI
	 * @param args arguments from the user
	 * @throws Exception throw exception when error occurs
	 */
	public static void main(String args[]) throws Exception {
		launch(args);
	} // end main

	/**
	 * Function that starts the primaryStage
	 * @param primaryStage the primary Stage to start
	 */
	public void start(Stage primaryStage) throws Exception {
		IPFormController aFrame = new IPFormController();
		aFrame.start(primaryStage);
	}

}
