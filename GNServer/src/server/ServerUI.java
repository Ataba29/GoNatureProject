package server;

import java.util.Timer;
import java.util.TimerTask;

import gui.ServerConnectFrameController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The main class that starts the GUI for the server
 */
public class ServerUI extends Application{

	public static ServerConnectFrameController s;
	final public static int DEFAULT_PORT = 5555;
	
	/**
	 * Main function
	 * @param args args from user
	 * @throws Exception print stack 
	 */
	public static void main( String args[] ) throws Exception
	{   
		 launch(args);
	} // end main
	
	
	/**
	 * Starts the primary Stage for the server
	 * @param primaryStage the primary Stage
	 */
	public void start(Stage primaryStage) throws Exception {

		try {

			s = new ServerConnectFrameController();
			s.start(primaryStage);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Starts the server, and connects to the DB
	 * @param p the port to listen to
	 * @param user MySql username
	 * @param pass MySql password
	 */
	public static void runServer(String p, String user, String pass)
	{
		 int port = 0; //Port to listen on

	        try
	        {
	        	port = Integer.parseInt(p); //Set port to 5555
	          
	        }   
	        catch(Throwable t)
	        {
	        	System.out.println("ERROR - Could not connect!");
	        }
	        EchoServer sv = new EchoServer(port);
	        mysqlConnection.connect(user, pass);
	        Timer timer = new Timer();
	        // delete all orders that are old from DB
	        TimerTask deleteOldOrders = new TimerTask() {
	        	@Override
	        	public void run() {
	        		mysqlConnection.DelteOldOrders();
	        	}
	        };
	        // function deleteOldOrders will work once each hour and once server starts
	        // also works
	        timer.scheduleAtFixedRate(deleteOldOrders, 0,  3600000);
	        try 
	        {
	          sv.listen(); //Start listening for connections
	        } 
	        catch (Exception ex) 
	        {
	          System.out.println("ERROR - Could not listen for clients!");
	        }
	}
}