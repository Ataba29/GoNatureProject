package server;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;
import utilities.CONSTANTS;
import entities.ClientConnectionStatus;

/**
 * The EhoServer class is the class responsible for handling the server
 * @version 15.3
 */
public class EchoServer extends AbstractServer  {
	
	/**
	 * Observable map of client connections, where the key is the client ID and the
	 * value is the client's connection status.
	 */
	public static ObservableMap<Integer, ClientConnectionStatus> clientMap = FXCollections.observableHashMap();
	public static ObservableList<ClientConnectionStatus> clientList = FXCollections.observableArrayList(clientMap.values());
	private static int i = 0;
	
	/**
	 * Observable list of client connection statuses.
	 * @return the client list
	 */
	public static ObservableList<ClientConnectionStatus> getClientsList() {
		return clientList;
	}
	
	
	/**
	 *  Set the list of clients connections
	 * @param newClientList a list of clients connections status
	 */
	public static void setClientsList(final ObservableList<ClientConnectionStatus> newClientList) {
		clientList = newClientList;
	}
	
	/**
	 * Runs the server
	 * @param port the port that server will listen to
	 */
	public EchoServer(int port) {
		super(port);
	}

	/**
	 * function that handles all messages that come from the client side
	 * @param msg the message from the client
	 * @param client the client 
	 */
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		
		try {
			
			MessageHandler.HandleMessage(msg, client);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * function to indicate that the server has started
	 */
	protected void serverStarted()
	{
		System.out.println ("Server listening for connections on port " + getPort());

	}

	/**
	 * function to indicate that the server has stopped
	 */
	protected void serverStopped()  {
		System.out.println ("Server has stopped listening for connections.");
	} 
	

	/**
	 * Function that handles new client connection
	 * @param client the new client that connected
	 */
	@Override
	protected void clientConnected(ConnectionToClient client) {
		super.clientConnected(client);

		String ip = client.getInetAddress().getHostAddress(); // get their ip address
		String host = client.getInetAddress().getHostName(); // get their host name
		ClientConnectionStatus clientStatus = new ClientConnectionStatus(ip, host, "Connected"); // create a new object
																						// observable list
		clientMap.put(i++, clientStatus);
		EchoServer.clientList.add(clientStatus);
		clientList.setAll(clientMap.values()); // Set all elements of the clientList with values from the clientMap
		System.out.println("\nClient " + client.toString() + " connected from " + ip + "/" + host + "\n");

		/* output client details */

		updateClientDetails(client.getInetAddress(), host, "Connected"); // update the server clients list

	}
	
	
	/**
	 * Update the observable list 
	 * @param ip client's ip
	 * @param hostName client's host name
	 * @param status is client connected or disconnected
	 */
	public static void updateClientDetails(InetAddress ip, String hostName, String status) {
		ServerUI.s.updateConnectedClient(ip, hostName, status);
	}
}
