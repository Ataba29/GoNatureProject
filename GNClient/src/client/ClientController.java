package client;

import java.io.IOException;

import common.ChatIF;

/**
 * The controller class for the GoNature client.
 */
public class ClientController implements ChatIF {

	public static int DEFAULT_PORT;
	ChatClient client;

	/**
	 * Creates a new instance of the ClientController class, Also tries to
	 * connect to the server by instantiating the client instance and running it.
	 * 
	 * @param host The host name of the server.
	 * @param port The port number to connect to.
	 */
	public ClientController(String host, int port) {
		try {
			client = new ChatClient(host, port, this);
			client.run();
			client.openConnection();
			System.out.println("Connected to server Successfully " + client);

		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}
	}

	/**
	 * Accepts a message from the client's UI and handles it.
	 * 
	 * @param str The message received from the UI.
	 */
	public void accept(Object str){
		client.handleMessageFromClientUI(str);
	}

	/**
	 * Displays a message (prints it)
	 */
	public void display(String message) {
		System.out.println("> " + message);
	}
}
