package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import entities.Orders;
import entities.Park;
import entities.ParkWaitList;
import entities.Parkfulltimes;
import entities.User;
import entities.Visitornumberreport;
import entities.Visitors;
import entities.Visitslogs;
import entities.cancellationreport;
import entities.order;
import gui.ServerConnectFrameController;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * Class that handles all calls to the DB and setups connection to DB
 */
public class mysqlConnection {

	private static Connection conn = null;
	private static Connection usersconn = null;

	/**
	 * Connect into the DB
	 * 
	 * @param username MySql username
	 * @param password MySql password
	 */
	public static void connect(String username, String password) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			System.out.println("Driver definition succeed");
		} catch (Exception ex) {
			/* handle the error */
			System.out.println("Driver definition failed");
		}

		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/prototypenew?serverTimezone=IST", username,
					password);
			usersconn = DriverManager.getConnection("jdbc:mysql://localhost/prototypenew?serverTimezone=IST", username,
					password);
			System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * Function that finds the user in DB and returns it
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message userLogin(Message dataFromClient) {
		ArrayList<User> dbResponse = new ArrayList<>();
		User user;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		final String username = commandsFromClient.get(0);
		final String password = commandsFromClient.get(1);

		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM prototypenew.users WHERE `username` = ? AND `password` = ? AND `isLogged` = '0'");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the user and pass is found in the db

			if (resultQuestionSet.next()) {
				user = new User(resultQuestionSet.getString(1), resultQuestionSet.getString(2),
						resultQuestionSet.getString(3), resultQuestionSet.getString(4), resultQuestionSet.getString(5),
						resultQuestionSet.getString(6), resultQuestionSet.getString(7), resultQuestionSet.getString(8),
						resultQuestionSet.getString(9), resultQuestionSet.getInt(10));

				dbResponse.add(user);
				// Change the users isLogged value to 1
				ps = mysqlConnection.conn
						.prepareStatement("UPDATE prototypenew.users SET `isLogged` = ? WHERE `username` = ?");
				ps.setInt(1, 1);
				ps.setString(2, username);
				ps.executeUpdate();
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.ValidLoginAnswer, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
	}

	/**
	 * Function that finds the visitor in DB and returns it
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message visitorLogin(Message dataFromClient) {
		ArrayList<Visitors> dbResponse = new ArrayList<>();
		Visitors visitor;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		final String ID = commandsFromClient.get(0);

		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM prototypenew.visitors WHERE `visitorID` = ? AND `isLogged` = 0");
			ps.setString(1, ID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				visitor = new Visitors(resultQuestionSet.getString(1), resultQuestionSet.getString(2),
						resultQuestionSet.getString(3), resultQuestionSet.getString(4), resultQuestionSet.getString(5),
						resultQuestionSet.getString(6), resultQuestionSet.getInt(7));

				dbResponse.add(visitor);
				// Change the users isLogged value to 1
				ps = mysqlConnection.conn
						.prepareStatement("UPDATE prototypenew.visitors SET `isLogged` = ? WHERE `visitorID` = ?");
				ps.setInt(1, 1);
				ps.setString(2, ID);
				ps.executeUpdate();
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.ValidVisitorLoginAnswer, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
	}

	// queries
	/**
	 * Function that makes new visitor and add it to the DB
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message addVisitor(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO prototypenew.visitors (visitorID, firstname, lastname, email, phonenumber, typevisitor, isLogged) VALUES (?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, commandsFromClient.get(0));
			ps.setString(2, commandsFromClient.get(1));
			ps.setString(3, commandsFromClient.get(2));
			ps.setString(4, commandsFromClient.get(3));
			ps.setString(5, commandsFromClient.get(4));
			ps.setString(6, "traveller");
			ps.setInt(7, 1);
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.NewVisitorSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in adding new visitor");
			return null;
		}
	}

	/**
	 * Function that change isLogged in DB to 0
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message LogOut(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String ID = commandsFromClient.get(0);

		try {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE prototypenew.visitors SET `isLogged` = ? WHERE `visitorID` = ?");
			ps.setInt(1, 0);
			ps.setString(2, ID);
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.NewVisitorSucceed, dbResponse);
			System.out.println(msg);
			return msg;
		} catch (Exception e) {
			System.out.println("Error in logout");
			return null;
		}
	}

	/**
	 * Function that add new order and add it to the DB
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message addNewOrder(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO prototypenew.orders (orderID, parkname, visittime, visitorsnum, email, address, typeorder, visitorID) VALUES (?, ?, CAST(? AS DateTime), ?, ?, ?, ?, ?)");
			ps.setString(1, commandsFromClient.get(0));
			ps.setString(2, commandsFromClient.get(1));
			ps.setString(3, commandsFromClient.get(11));
			ps.setString(4, commandsFromClient.get(3));
			ps.setString(5, commandsFromClient.get(4));
			ps.setString(6, commandsFromClient.get(5));
			ps.setString(7, commandsFromClient.get(6));
			ps.setString(8, commandsFromClient.get(7));
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.NewOrderSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in adding new order");
			return null;
		}
	}

	/**
	 * Function that get the details of the visitor from DB
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message getVisitorDetails(Message dataFromClient) {
		ArrayList<Visitors> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String ID = commandsFromClient.get(0);
		Visitors visitor;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.visitors WHERE `visitorID` = ?");

			ps.setString(1, ID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				visitor = new Visitors(resultQuestionSet.getString(1), resultQuestionSet.getString(2),
						resultQuestionSet.getString(3), resultQuestionSet.getString(4), resultQuestionSet.getString(5),
						resultQuestionSet.getString(6), resultQuestionSet.getInt(7));

				dbResponse.add(visitor);
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetVisitorDetailsSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding visitor id");
			return null;
		}
	}

	/**
	 * Function that get the details of the park from DB
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message getParkDetails(Message dataFromClient) {
		ArrayList<Park> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parkname = commandsFromClient.get(0);
		Park park;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.park WHERE `parkname` = ?");

			ps.setString(1, parkname);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				park = new Park(resultQuestionSet.getString(1), resultQuestionSet.getInt(2),
						resultQuestionSet.getInt(3), resultQuestionSet.getInt(4), resultQuestionSet.getInt(5));

				dbResponse.add(park);
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetParkDetailsSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding park name");
			return null;
		}
	}

	/**
	 * Function that get sum of numbers of the visitor that between two times
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message GetSumOfVisitors(Message dataFromClient) {
		ArrayList<Double> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		String parkname = commandsFromClient.get(0);
		String date1 = commandsFromClient.get(1);
		String date2 = commandsFromClient.get(2);
		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT SUM(visitorsnum) FROM prototypenew.orders WHERE `parkname` = ? AND visittime BETWEEN ? AND ?");
			ps.setString(1, parkname);
			ps.setString(2, date2);
			ps.setString(3, date1);
			ResultSet resultQuestionSet = ps.executeQuery();
			resultQuestionSet.next();
			String sum = resultQuestionSet.getString(1);
			if (sum == null) {
				dbResponse.add(0.0);
			} else {
				double value = Double.parseDouble(sum);
				dbResponse.add(value);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetSumOfVisitorsSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in returning the sum of visitors");
			return null;
		}
	}

	/**
	 * function that add to parkwaitlist table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message WaitList(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO prototypenew.parkwaitlist (orderID, parkname, visittime, visitorsnum, email, address, typeorder, visitorID) VALUES (?, ?, CAST(? AS DateTime), ?, ?, ?, ?, ?	)");
			ps.setString(1, commandsFromClient.get(0));
			ps.setString(2, commandsFromClient.get(1));
			ps.setString(3, commandsFromClient.get(11));
			ps.setString(4, commandsFromClient.get(3));
			ps.setString(5, commandsFromClient.get(4));
			ps.setString(6, commandsFromClient.get(5));
			ps.setString(7, commandsFromClient.get(6));
			ps.setString(8, commandsFromClient.get(7));
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.WaitListSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in adding new Order to WaitList");
			return null;
		}
	}

	/**
	 * function that return all the order id for specific visitor from orders table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message GetAllOrdersID(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		String visitorID = commandsFromClient.get(0);
		int flag = 0;
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT orderID FROM prototypenew.orders WHERE `visitorID` = ?");

			ps.setString(1, visitorID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db
			while (resultQuestionSet.next()) {
				dbResponse.add(resultQuestionSet.getString(1));
				flag = 1;
			}
			if (flag == 0) {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			// send to client
			Message msg = new Message(CONSTANTS.GetAllOrdersIDSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in finding visitor id in orders table");
			return null;
		}
	}

	/**
	 * function that return all the orders id for specific visitor in the
	 * parkwaitlist table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message GetAllOrdersIDInParkWaitList(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		String visitorID = commandsFromClient.get(0);
		int flag = 0;
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT orderID FROM prototypenew.parkwaitlist WHERE `visitorID` = ?");

			ps.setString(1, visitorID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db
			while (resultQuestionSet.next()) {
				dbResponse.add(resultQuestionSet.getString(1));
				flag = 1;
			}
			if (flag == 0) {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			// send to client
			Message msg = new Message(CONSTANTS.GetAllOrdersIDInParkWaitListSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in finding visitor id in parkwaitlist table");
			return null;
		}
	}

	/**
	 * function that get the details of order
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message GetOrderDetails(Message dataFromClient) {
		ArrayList<Orders> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String ID = commandsFromClient.get(0);
		Orders order;
		String dateString;
		String year;
		String month;
		String day;
		String time;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.orders WHERE `orderID` = ?");

			ps.setString(1, ID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				dateString = resultQuestionSet.getString(3);
				year = dateString.substring(0, 4);
				month = dateString.substring(5, 7);
				day = dateString.substring(8, 10);
				time = dateString.substring(11, 16);
				System.out.println(dateString);
				order = new Orders(resultQuestionSet.getString(1), resultQuestionSet.getString(2), time,
						resultQuestionSet.getString(4), resultQuestionSet.getString(5), resultQuestionSet.getString(6),
						resultQuestionSet.getString(7), resultQuestionSet.getString(8), year, month, day);

				dbResponse.add(order);
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetOrderDetailsSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding order id in orders table");
			return null;
		}
	}

	/**
	 * function that get the details of parkwaitlist
	 *  
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message GetParkWaitListDetails(Message dataFromClient) {
		ArrayList<ParkWaitList> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String ID = commandsFromClient.get(0);
		ParkWaitList ParkWaitListOrder;
		String dateString;
		String year;
		String month;
		String day;
		String time;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.parkwaitlist WHERE `orderID` = ?");

			ps.setString(1, ID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				dateString = resultQuestionSet.getString(3);
				year = dateString.substring(0, 4);
				month = dateString.substring(5, 7);
				day = dateString.substring(8, 10);
				time = dateString.substring(11, 16);
				ParkWaitListOrder = new ParkWaitList(resultQuestionSet.getString(1), resultQuestionSet.getString(2),
						time, resultQuestionSet.getString(4), resultQuestionSet.getString(5),
						resultQuestionSet.getString(6), resultQuestionSet.getString(7), resultQuestionSet.getString(8),
						year, month, day);

				dbResponse.add(ParkWaitListOrder);
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetParkWaitListDetailsSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding order id in parkwaitlist table");
			return null;
		}
	}

	/**
	 * function that delete order from orders table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message DeleteOrder(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String ID = commandsFromClient.get(0);
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM prototypenew.orders WHERE `orderID` = ?");
			ps.setString(1, ID);
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			return new Message(CONSTANTS.DeleteOrderSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding order id in orders table");
			return null;
		}
	}

	/**
	 * function that delete order from parkwaitlist table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message DeleteParkWaitList(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String ID = commandsFromClient.get(0);
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM prototypenew.parkwaitlist WHERE `orderID` = ?");
			ps.setString(1, ID);
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			return new Message(CONSTANTS.DeleteParkWaitListSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding order id in parkwaitlist table");
			return null;
		}
	}

	/**
	 * function that return the max of the orderid of allorders table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message GetMaxIDOrder(Message dataFromClient) {
		ArrayList<Integer> dbResponse = new ArrayList<>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT MAX(CAST(orderID AS UNSIGNED)) AS maxid FROM prototypenew.allorders");
			ResultSet resultQuestionSet = ps.executeQuery();
			if (resultQuestionSet.next()) {
				dbResponse.add(resultQuestionSet.getInt("maxid"));
			} else {
				dbResponse.add(0);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetMaxIDOrderSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding max order id ");
			return null;
		}
	}

	/**
	 * Function that get the details of the park from DB
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message GetParkDetailToMove(Message dataFromClient) {
		ArrayList<Park> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parkname = commandsFromClient.get(0);
		Park park;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.park WHERE `parkname` = ?");

			ps.setString(1, parkname);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				park = new Park(resultQuestionSet.getString(1), resultQuestionSet.getInt(2),
						resultQuestionSet.getInt(3), resultQuestionSet.getInt(4), resultQuestionSet.getInt(5));

				dbResponse.add(park);
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetParkDetailToMoveSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding park name in GetParkDetailToMove func");
			return null;
		}
	}

	/**
	 * function that return all the orders that in parkwaitlist table between two
	 * times
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message GetAllWaitListBetweenTwoTimes(Message dataFromClient) {
		ArrayList<ParkWaitList> dbResponse = new ArrayList<>();
		ParkWaitList parkWaitList;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		String parkname = commandsFromClient.get(0);
		String fromTime = commandsFromClient.get(1);
		String toTime = commandsFromClient.get(2);
		String dateString, year, month, day, time;
		int flag = 0;

		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM prototypenew.parkwaitlist WHERE `parkname` = ? AND visittime BETWEEN ? AND ?");
			ps.setString(1, parkname);
			ps.setString(2, fromTime);
			ps.setString(3, toTime);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			while (resultQuestionSet.next()) {
				dateString = resultQuestionSet.getString(3);
				year = dateString.substring(0, 4);
				month = dateString.substring(5, 7);
				day = dateString.substring(8, 10);
				time = dateString.substring(11, 16);
				parkWaitList = new ParkWaitList(resultQuestionSet.getString(1), resultQuestionSet.getString(2), time,
						resultQuestionSet.getString(4), resultQuestionSet.getString(5), resultQuestionSet.getString(6),
						resultQuestionSet.getString(7), resultQuestionSet.getString(8), year, month, day);

				dbResponse.add(parkWaitList);
				flag = 1;
			}
			if (flag == 0) {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.GetAllWaitListBetweenTwoTimesSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding park name between two times");
			return null;
		}
	}

	/**
	 * function that insert orderid to allorders table
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message InsertToAllOrderTable(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO prototypenew.allorders (orderID) VALUES (?)");
			ps.setString(1, commandsFromClient.get(0));
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.InsertToAllOrderTableSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in adding new id order to allorder table");
			return null;
		}
	}

	/**
	 * function return orders for specific visitors
	 * 
	 * @param dataFromClient client data
	 * @return message to send to client
	 */
	public static Message idOrder(Message dataFromClient) {
		ArrayList<Orders> dbResponse = new ArrayList<>();
		Orders Ordersfound;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		String visitorID = commandsFromClient.get(0);
		String dateString, year, month, day, time;
		int flag = 0;

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.orders WHERE `visitorID` = ?");
			ps.setString(1, visitorID);

			ResultSet resultQuestionSet = ps.executeQuery();

			while (resultQuestionSet.next()) {
				dateString = resultQuestionSet.getString(3);
				year = dateString.substring(0, 4);
				month = dateString.substring(5, 7);
				day = dateString.substring(8, 10);
				time = dateString.substring(11, 16);
				Ordersfound = new Orders(resultQuestionSet.getString(1), resultQuestionSet.getString(2), time,
						resultQuestionSet.getString(4), resultQuestionSet.getString(5), resultQuestionSet.getString(6),
						resultQuestionSet.getString(7), resultQuestionSet.getString(8), year, month, day);

				dbResponse.add(Ordersfound);
				flag = 1;
			}
			if (flag == 0) {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.OrdersTakenSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in finding orders to the visitor id ");
			return null;
		}

	}

	/**
	 * Function that add canceled order to cancellationreport table
	 * 
	 * @param dataFromClient the message that arrived from client
	 * @return message to send to client
	 */
	public static Message addNewcancellationreport(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		int orderwascanceled = Integer.parseInt(commandsFromClient.get(12));
		try {
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO prototypenew.cancellationreport (orderID, parkname, visittime, visitornum, email, address, typeorder, visitorID , orderwascanceled) VALUES (?, ?, CAST(? AS DateTime), ?, ?, ?, ?, ?, ?)");
			ps.setString(1, commandsFromClient.get(0));
			ps.setString(2, commandsFromClient.get(1));
			ps.setString(3, commandsFromClient.get(11));
			ps.setString(4, commandsFromClient.get(3));
			ps.setString(5, commandsFromClient.get(4));
			ps.setString(6, commandsFromClient.get(5));
			ps.setString(7, commandsFromClient.get(6));
			ps.setString(8, commandsFromClient.get(7));
			ps.setInt(9, orderwascanceled);
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.addNewcancellationreportSucceed, dbResponse);
			System.out.println(msg);
			return msg;

		} catch (Exception e) {
			System.out.println("Error in adding new cancellation report");
			return null;
		}
	}

	/**
	 * function that checks if id is in the visitor table
	 * 
	 * @param dataFromClient the message that was received from the client
	 * @return returns the data that was found or null
	 */
	public static Message idExist(Message dataFromClient) {

		ArrayList<String> dbResponse = new ArrayList<>();
		Visitors visitor;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		final String ID = commandsFromClient.get(0);

		try {

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.visitors WHERE `visitorID` = ?");
			ps.setString(1, ID);
			ResultSet res = ps.executeQuery();

			// visitor exists in DB
			if (res.next()) {
				visitor = new Visitors(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7));

				// check if visitor is a guide
				if (visitor.getTypevisitor().equals("guide"))
					dbResponse.add("foundGuide");
				// visitor is a traveler set as guide
				else {
					ps = mysqlConnection.conn.prepareStatement(
							"UPDATE prototypenew.visitors SET `typevisitor` = ? WHERE `visitorID` = ?");
					ps.setString(1, "guide");
					ps.setString(2, ID);
					ps.executeUpdate();
					dbResponse.add("foundTraveller");
				}
			} else {
				dbResponse.add(null);
			}

			ps.close();
			res.close();

			return new Message(CONSTANTS.ValidVisitorExist, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}

	}

	/**
	 * function saves the Guide into the DB
	 * 
	 * @param dataFromClient contains an arrayList of one visitor
	 * @return returns a message to confirm that guide was saved
	 */
	public static Message saveNewGuide(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		Visitors visitor;
		ArrayList<Visitors> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		visitor = commandsFromClient.get(0);

		try {
			PreparedStatement ps = mysqlConnection.conn.prepareStatement(
					"INSERT INTO prototypenew.visitors (visitorID, firstname, lastname, email, phonenumber, typevisitor, isLogged) VALUES (?, ?, ?, ?, ?, ?, ?)");

			ps.setString(1, visitor.getVisitorID());
			ps.setString(2, visitor.getFirstname());
			ps.setString(3, visitor.getLastname());
			ps.setString(4, visitor.getEmail());
			ps.setString(5, visitor.getPhonenumber());
			ps.setString(6, visitor.getTypevisitor());
			ps.setInt(7, visitor.getIsLogged());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
		dbResponse.add("SavedGuide");

		return new Message(CONSTANTS.SavedGuideInDB, dbResponse);
	}

	/**
	 * function takes data from client and returns the order in DB
	 * 
	 * @param dataFromClient data that we got from client
	 * @return returns message to send to client with orderID if exist
	 */
	public static Message orderIDExist(Message dataFromClient) {
		ArrayList<Orders> dbResponse = new ArrayList<>();
		Orders order;
		String orderID;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		orderID = commandsFromClient.get(0);
		System.out.println(orderID);
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.orders WHERE orderID = ?;");
			ps.setString(1, orderID);
			ResultSet res = ps.executeQuery();
			if (res.next()) { // order was found
				order = new Orders(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getString(7), res.getString(8));
				System.out.println(order.getOrderID());
				dbResponse.add(order);
			} else { // no order was found
				dbResponse.add(null);
			}

			ps.close();
			res.close();
		} catch (Exception e) {
			System.out.println("Error in orderID not found in DB");
			return null;
		}

		return new Message(CONSTANTS.OrderCheckComplete, dbResponse);
	}

	/**
	 * function adds a new row to VisitsLogs table in DB
	 * 
	 * @param dataFromClient data from client
	 * @return returns message that it was success or fail
	 */
	public static Message addNewVisitLog(Message dataFromClient) {
		Visitslogs newlog;
		ArrayList<Visitslogs> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		newlog = commandsFromClient.get(0);

		try {
			// add visitLogs row
			PreparedStatement ps = mysqlConnection.conn.prepareStatement(
					"INSERT INTO `prototypenew`.`visitslogs` (`orderID`, `parkname`, `entryTime`, `exitTime`, `orderType`, `visitorsnum`) VALUES (?, ?, ?, ?, ?, ?);");
			ps.setString(1, newlog.getOrderID());
			ps.setString(2, newlog.getParkname());
			ps.setString(3, newlog.entryTimeToString());
			ps.setString(4, newlog.exitTimeToString());
			ps.setString(5, newlog.getOrderType());
			ps.setInt(6, newlog.getVisitorsnum());
			ps.executeUpdate();
			ps.close();

			// update currentVisitors table in DB
			ps = mysqlConnection.conn.prepareStatement(
					"INSERT INTO `prototypenew`.`currentvisitors` (`orderID`, `parkname`, `visittime`, `visitornum`, `visitorID`, `entrancewithnoorder`) VALUES (?, ?, ?, ?, ?, ?);");
			ps.setString(1, newlog.getOrderID());
			ps.setString(2, newlog.getParkname());
			ps.setString(3, newlog.entryTimeToString());
			ps.setString(4, String.valueOf(newlog.getVisitorsnum()));
			ps.setString(5, newlog.getID());
			ps.setInt(6, newlog.getentrancewithnoorder());
			ps.executeUpdate();
			ps.close();

			// update current visitors
			ps = mysqlConnection.conn.prepareStatement(
					"UPDATE `prototypenew`.`park` SET `currentvisitors` = `currentvisitors` + ? WHERE (`parkname` = ?);");
			ps.setInt(1, newlog.getVisitorsnum());
			ps.setString(2, newlog.getParkname());
			ps.executeUpdate();
			ps.close();

			// delete from orders table because it was made
			ps = mysqlConnection.conn.prepareStatement("DELETE FROM `prototypenew`.`orders` WHERE (`orderID` = ?);");
			ps.setString(1, newlog.getOrderID());
			ps.executeUpdate();
			ps.close();

		} catch (Exception e) {
			System.out.println("Error in orderID not found in DB");
			return null;
		}
		return new Message(CONSTANTS.NewLogAdded, null);
	}

	/**
	 * function returns a message to send to server with max visitor number and diff
	 * visitors number of the park table in DB
	 * 
	 * @param dataFromClient has the park name
	 * @return return message
	 */
	public static Message getMaxVandDiffV(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		String parkName;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		parkName = commandsFromClient.get(0);

		try {
			PreparedStatement ps = conn.prepareStatement(
					"SELECT maxvisitorsnumber, diffVisitors FROM prototypenew.park WHERE parkname = ?;");
			ps.setString(1, parkName);
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				dbResponse.add(res.getString(1));
				dbResponse.add(res.getString(2));
			}
			ps.close();
			res.close();

			// get sum current visitors that did order
			ps = conn.prepareStatement(
					"SELECT IFNULL(SUM(visitornum), 0) FROM prototypenew.currentvisitors WHERE parkname = ? AND entrancewithnoorder = 0;");
			ps.setString(1, parkName);
			res = ps.executeQuery();
			if (res.next()) {
				int ress = res.getInt(1);
				dbResponse.add(String.valueOf(ress));
			}
			ps.close();
			res.close();

			// get sum current visitors that did not order
			ps = conn.prepareStatement(
					"SELECT IFNULL(SUM(visitornum), 0) FROM prototypenew.currentvisitors WHERE parkname = ? AND entrancewithnoorder = 1;");
			ps.setString(1, parkName);
			res = ps.executeQuery();
			if (res.next()) {
				int ress = res.getInt(1);
				dbResponse.add(String.valueOf(ress));
			}
			System.out.println(dbResponse.toString());
			ps.close();
			res.close();
		} catch (Exception e) {
			System.out.println("error park was not found or sql error");
			return null;
		}

		return new Message(CONSTANTS.ReturnedMaxVandDiffV, dbResponse);
	}

	/**
	 * function finds and returns the max in the allorders table
	 * 
	 * @param dataFromClient data from client contains null
	 * @return return message with the max order
	 */
	public static Message getMaxOrderID(Message dataFromClient) {
		String maxi;
		ArrayList<String> dbResponse = new ArrayList<>();
		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT MAX(cast(`orderID` as unsigned)) FROM prototypenew.allorders;");
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				dbResponse.add(res.getString(1));
			}
			ps.close();
			res.close();
		} catch (Exception e) {
			System.out.println("error park was not found or sql error");
			return null;
		}
		return new Message(CONSTANTS.MaxTempOrderIDReturned, dbResponse);
	}

	/**
	 * function that gets the details of a guide if exists
	 * 
	 * @param dataFromClient contains the id
	 * @return returns message if exist or not
	 */
	public static Message getIfGuideOrNot(Message dataFromClient) {
		String id;
		Visitors visitor;
		ArrayList<Visitors> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		id = commandsFromClient.get(0);

		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.visitors WHERE `visitorID` = ?;");
			ps.setString(1, id);
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				visitor = new Visitors(res.getString(1), res.getString(2), res.getString(3), res.getString(4),
						res.getString(5), res.getString(6), res.getInt(7));
			} else {
				visitor = null;
			}
			dbResponse.add(visitor);
			ps.close();
			res.close();
		} catch (Exception e) {
			System.out.println("error guide finding sql was broken");
			return null;
		}
		return new Message(CONSTANTS.GuideWasChecked, dbResponse);
	}

	/**
	 * add the orderID to the all orders table DB
	 * 
	 * @param dataFromClient orderID from client
	 * @return message that success
	 */
	public static Message addToAllOrdersPayPage(Message dataFromClient) {
		String id;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		id = commandsFromClient.get(0);
		try {
			PreparedStatement ps = conn
					.prepareStatement("INSERT INTO `prototypenew`.`allorders` (`orderID`) VALUES (?);");
			ps.setString(1, id);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println("error in allorder trying to add order from pay page");
			return null;
		}
		return new Message(CONSTANTS.allOrdersAddedSuccessPayPage, null);
	}

	/**
	 * function removes all details from DB with orderID and updates
	 * 
	 * @param dataFromClient orderID comes from client
	 * @return return message null if orderID not found else success
	 */
	public static Message exitVisitorFromDB(Message dataFromClient) {
		String orderID;
		String visitorID;
		String parkName;
		int visitorsNum;
		boolean deleteVisitor = true;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		orderID = commandsFromClient.get(0);
		ArrayList<String> dbRespond = new ArrayList<>();

		try {
			// get visitorID from orders table in DB
			PreparedStatement ps = conn.prepareStatement(
					"SELECT `visitorID`, `visitornum`, `parkname` FROM prototypenew.currentvisitors WHERE `orderID` = ?;");
			ps.setString(1, orderID);
			ResultSet res = ps.executeQuery();
			if (res.next()) {
				visitorID = res.getString(1);
				visitorsNum = res.getInt(2);
				parkName = res.getString(3);
			}
			// order does not exist
			else {
				dbRespond.add(null);
				return new Message(CONSTANTS.VisitorExited, dbRespond);
			}
			ps.close();
			res.close();

			// delete visitor if no more orders
			ps = conn.prepareStatement("SELECT `orderID` FROM prototypenew.orders WHERE `visitorID` = ?;");

			ps.setString(1, visitorID);
			res = ps.executeQuery();
			// if there is more orders then don't delete the traveler
			if (res.next()) {
				deleteVisitor = false;
			}
			ps.close();
			res.close();

			// delete only if he has no longer anymore orders
			if (deleteVisitor) {
				// query will delete visitor only if he is traveller (we know it is spelling
				// mistake)
				ps = conn.prepareStatement(
						"DELETE FROM `prototypenew`.`visitors` WHERE (`visitorID` = ? AND `typevisitor` = 'traveller');");
				ps.setString(1, visitorID);
				ps.executeUpdate();
				ps.close();
			}

			// updates visitsLogs DB
			ps = conn.prepareStatement("UPDATE `prototypenew`.`visitslogs` SET `exitTime` = ? WHERE (`orderID` = ?);");
			ps.setString(1, formatter.format(date));
			ps.setString(2, orderID);
			ps.executeUpdate();
			ps.close();

			// delete from currentVisitors DB
			ps = conn.prepareStatement("DELETE FROM `prototypenew`.`currentvisitors` WHERE (`orderID` = ?);");
			ps.setString(1, orderID);
			ps.executeUpdate();
			ps.close();

			// update park parameters
			ps = conn.prepareStatement(
					"UPDATE `prototypenew`.`park` SET `currentvisitors` = `currentvisitors` - ? WHERE (`parkname` = ?);");
			ps.setInt(1, visitorsNum);
			ps.setString(2, parkName);
			ps.executeUpdate();
			ps.close();

			// update parkfulltimes DB
			ps = mysqlConnection.conn.prepareStatement(
					"UPDATE `prototypenew`.`parkfulltimes` SET `endTime` = ? WHERE `endTime` = `startTime` AND `parkname` = ?;");
			ps.setString(1, formatter.format(date));
			ps.setString(2, parkName);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println("error in trying to exit user from DB");
			return null;
		}
		dbRespond.add("nice");
		return new Message(CONSTANTS.VisitorExited, dbRespond);
	}

	/**
	 * function adds a row in park is full
	 * 
	 * @param dataFromClient (0) parkName (1) date in string
	 * @return return message of success
	 */
	public static Message addToParkIsFull(Message dataFromClient) {
		String parkName, currDate;
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		parkName = commandsFromClient.get(0);
		currDate = commandsFromClient.get(1);

		try {
			// add to parkfulltimes DB
			PreparedStatement ps = mysqlConnection.conn.prepareStatement(
					"INSERT INTO `prototypenew`.`parkfulltimes` (`parkname`, `startTime`, `endTime`) VALUES (?, ?, ?);");
			ps.setString(1, parkName);
			ps.setString(2, currDate);
			ps.setString(3, currDate);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			System.out.println("error in trying to exit user from DB");
			return null;
		}
		return new Message(CONSTANTS.addedToParkIsFull, null);
	}
	
	/**
	 * function checks if user is logged
	 * @param dataFromClient - username	
	 * @return	- message with null or int(0,1)
	 */
	public static Message CheckIfLogged(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		final String ID = commandsFromClient.get(0);

		try {
			PreparedStatement ps = conn
					.prepareStatement("SELECT * FROM prototypenew.visitors WHERE `visitorID` = ? AND `isLogged` = 1");
			ps.setString(1, ID);
			ResultSet resultQuestionSet = ps.executeQuery();
			// the ID is found in the db

			if (resultQuestionSet.next()) {
				dbResponse.add("connected");
			} else {
				dbResponse.add(null);
			}
			ps.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.CheckLoginVisitorSucceed, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
	}

	/**
	 * function that logs out the employee
	 * @param dataFromClient has the user ID
	 * @return returns message of success
	 */
	public static Message LogOutEmp(Message dataFromClient) {
		ArrayList<String> dbResponse = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String empID = commandsFromClient.get(0);

		try {
			PreparedStatement ps = conn
					.prepareStatement("UPDATE prototypenew.users SET `isLogged` = ? WHERE `employeeID` = ?");
			ps.setInt(1, 0);
			ps.setString(2, empID);
			ps.executeUpdate();
			ps.close();
			dbResponse.add(null);
			Message msg = new Message(CONSTANTS.NewVisitorSucceed, dbResponse);
			System.out.println(msg);
			return msg;
		} catch (Exception e) {
			System.out.println("Error in logout");
			return null;
		}
	}
	
	/**
	 * function deletes all old orders from the DB (orders, parkwaitlist)
	 */
	public static void DelteOldOrders() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ArrayList<Orders> ordersToCancel = new ArrayList<>();
		try {
            // Delete old orders from the 'orders' table 
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.orders WHERE visittime < ?;");
            ps.setString(1, sdf.format(date));
			ResultSet res = ps.executeQuery();
            while(res.next()) {
        		Orders order = new Orders(res.getString(1), res.getString(2), res.getString(3), res.getString(4), 
            			res.getString(5), res.getString(6), res.getString(7), res.getString(8));
        		ordersToCancel.add(order);
            }
            // insert orders into cancellationreport 
            for(Orders order : ordersToCancel) {
            	ps = conn.prepareStatement(
            			"INSERT INTO `prototypenew`.`cancellationreport` (`orderID`, `parkname`, `visittime`, `visitornum`, `email`, `address`, `typeorder`, `visitorID`, `orderwascanceled`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"
            			);
            	ps.setString(1, order.getOrderID());
            	ps.setString(2, order.getParkName());
            	ps.setString(3, order.getVisitTime());
            	ps.setString(4, order.getVisitorsNum());
            	ps.setString(5, order.getEmail());
            	ps.setString(6, order.getAddress());
            	ps.setString(7, (order.getTypeOrder().equals("organized group") ? "organized group" : "individual"));
            	ps.setString(8, order.getVisitorID());
            	ps.setInt(9, 0);
            	ps.executeUpdate();
            	ps.close();
            }
            String deleteOrdersSql = "DELETE FROM prototypenew.orders WHERE visittime < ?";
            ps = conn.prepareStatement(deleteOrdersSql);
            ps.setString(1, sdf.format(date));
            ps.executeUpdate();
            
            // Delete old orders from the 'parkwaitlist' table
            String deleteParkWaitlistSql = "DELETE FROM prototypenew.parkwaitlist WHERE visittime < ?";
            ps = conn.prepareStatement(deleteParkWaitlistSql);
            ps.setString(1, sdf.format(date));
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Old orders deleted unsuccessfully.");
            e.printStackTrace();
        }
	}

	/**
	 * function imports users from the user table into our user table
	 */
	public static void importUsers() {
		ArrayList<User> users = new ArrayList<>();
		try {
            // Delete old orders from the 'orders' table
            String deleteOrdersSql = "SELECT * FROM users.users;";
            PreparedStatement ps = usersconn.prepareStatement(deleteOrdersSql);
            ResultSet res = ps.executeQuery();
            while(res.next()) {
        		User user = new User(res.getString(1), res.getString(2), res.getString(3), res.getString(4), 
            			res.getString(5), res.getString(6), res.getString(7), res.getString(8), 
            			res.getString(9), res.getInt(10));
            	users.add(user);
            }
            ps.close();
            // insert users into the user table in our DB
            for(User user : users) {
                ps = conn.prepareStatement("INSERT INTO `prototypenew`.`users` (`employeeID`, `firstname`, `lastname`, `username`, `password`, `email`, `phonenumber`, `role`, `parkname`, `isLogged`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
                ps.setString(1, user.getEmployeeID());
                ps.setString(2, user.getFirstname());
                ps.setString(3, user.getLastname());
                ps.setString(4, user.getUsername());
                ps.setString(5, user.getPassword());
                ps.setString(6, user.getEmail());
                ps.setString(7, user.getPhonenumber());
                ps.setString(8, user.getRole());
                ps.setString(9, user.getParkname());
                ps.setInt(10, user.getIsLogged());
                
                ps.executeUpdate();
            }
            ps.close();
        } catch (Exception e) {
            System.out.println("Old orders deleted unsuccessfully.");
            e.printStackTrace();
        }
		ServerConnectFrameController.imported = true;
	}	
	/**
	 * get park data from DB
	 * @param dataFromClient msg from client
	 * @return park data
	 */
	public static Message park(Message dataFromClient) {
	    ArrayList<Park> dbResponse1 = new ArrayList<>();
        Park park;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parkname = commandsFromClient.get(0);
		

	    try {
	        PreparedStatement ps = conn.prepareStatement("SELECT * FROM prototypenew.park  WHERE `parkname` = ?");
	    	ps.setString(1, parkname);
	        ResultSet resultQuestionSet1 = ps.executeQuery();
	        while (resultQuestionSet1.next()) {
	             park = new Park(resultQuestionSet1.getString(1),
	                                 resultQuestionSet1.getInt(2),
	                                 resultQuestionSet1.getInt(3),
	                                 resultQuestionSet1.getInt(4),
	                                 resultQuestionSet1.getInt(5));
	           

	            dbResponse1.add(park);
	        }
	      
	        ps.close();
	        resultQuestionSet1.close();
	        return new Message(CONSTANTS.NAMEPARKANSWER, dbResponse1);

	    } catch (Exception e) {
	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	        return null;
	    }
	}
	
	
	/**
	 * check if month and year in usage report DB
	 * @param dataFromClient data from client
	 * @return get park full times data
	 */
	public static Message displayingggOLD(Message dataFromClient) {
		ArrayList<Parkfulltimes> dbResponseEND = new ArrayList<>();
	    Parkfulltimes parkfulltimesEND;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parknamedata = commandsFromClient.get(0);
		final String yeardata = commandsFromClient.get(1);
		final String monthdata = commandsFromClient.get(2);
		boolean checkFI=false;

		
	    try {
	    	PreparedStatement psFI = conn.prepareStatement("SELECT * FROM prototypenew.usereport  WHERE `parkname` = ? AND `year`=? AND `month`=? ");
	    	psFI.setString(1, parknamedata);
	    	psFI.setString(2, yeardata);
	    	psFI.setString(3, monthdata);

	        ResultSet resultQuestionSetFI = psFI.executeQuery();
	        if (resultQuestionSetFI.next()) {
	        	checkFI=true;    
	        }
	        System.out.println(checkFI);
	    	
	        psFI.close();
	        resultQuestionSetFI.close();
	    } catch (Exception e) {
	
	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	        return null;
	    }
	    if(checkFI) {
	    	System.out.println("in in in");
	    	 try {
	    		PreparedStatement psEND = conn.prepareStatement("SELECT `parkname`, DAY(startTime) AS day, HOUR(startTime) AS startTime, HOUR(endTime) AS endTime FROM prototypenew.parkfulltimes WHERE parkname = ? AND MONTH(startTime) = ? AND YEAR(startTime) = ?");
	 	    	psEND.setString(1, parknamedata);
	 	    	psEND.setString(2, monthdata);
	 	    	psEND.setString(3, yeardata);
	 	    	
	 	        ResultSet resultQuestionSetEND = psEND.executeQuery();
	 	        while (resultQuestionSetEND.next()) {
	 	        	parkfulltimesEND = new Parkfulltimes(resultQuestionSetEND.getString(1),resultQuestionSetEND.getString(2),
	 	        			resultQuestionSetEND.getString(3),resultQuestionSetEND.getString(4));
	 	           

	 	            dbResponseEND.add(parkfulltimesEND);
	 	         
	 	        }
	 	        System.out.println(dbResponseEND.get(0).getParkname());
	 	        System.out.println(dbResponseEND.get(0).getday());
	 	        System.out.println(dbResponseEND.get(0).gethourstart());
	 	        System.out.println(dbResponseEND.get(0).gethourend());

	 	        
	 	        psEND.close();
	 	        resultQuestionSetEND.close();
	    		 
	    		 
	    		 
	 	    	
	        return new Message(CONSTANTS.IfidisplayOLD, dbResponseEND);

	 	    } catch (Exception e) {
	 	
	 	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	 	        return null;
	 	    }
	    	
	    	
	    	
	    }
		return null;
	    
	    
	    
	  
	}


	    

	

	/**
	 * get park full times data
	 * @param dataFromClient data from client
	 * @return ArrayList Parkfulltimes
	 */
	public static Message importdataforUUUreport(Message dataFromClient) {
	    ArrayList<Parkfulltimes> dbResponse11 = new ArrayList<>();
	    Parkfulltimes parkfulltimes;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parknamedata = commandsFromClient.get(0);
		final String yeardata = commandsFromClient.get(1);
		final String monthdata = commandsFromClient.get(2);

		boolean check1=false;
		
		

	    try {
	        PreparedStatement ps9 = conn.prepareStatement("DELETE FROM prototypenew.usereport WHERE `parkname` = ? AND `year`=? AND `month=?");
	        ps9.setString(1,parknamedata);
	        ps9.setString(2,yeardata);
	        ps9.setString(3,monthdata);
	        
	        int rowsAffected6 = ps9.executeUpdate();
	        System.out.println(rowsAffected6 + " row(s) delete");
	        ps9.close();
	    } catch (Exception e) {
	        System.out.println("Error deleting data from visitslogs: " + e.getMessage());
	    }

		

	    try {
	    	PreparedStatement ps7 = conn.prepareStatement("SELECT `parkname`, DAY(startTime) AS day, HOUR(startTime) AS startTime, HOUR(endTime) AS endTime FROM prototypenew.parkfulltimes WHERE parkname = ? AND MONTH(startTime) = ? AND YEAR(startTime) = ?");
	    	ps7.setString(1, parknamedata);
	    	ps7.setString(2, monthdata);
	    	ps7.setString(3, yeardata);
	    	


	        ResultSet resultQuestionSet7 = ps7.executeQuery();
	        while (resultQuestionSet7.next()) {
	        	parkfulltimes = new Parkfulltimes(resultQuestionSet7.getString(1),resultQuestionSet7.getString(2),
	        			resultQuestionSet7.getString(3),resultQuestionSet7.getString(4));
	           

	            dbResponse11.add(parkfulltimes);
	         
	            check1=true;
	        }

	        ps7.close();
	        resultQuestionSet7.close();
	      

	    } catch (Exception e) {
	
	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	        return null;
	    }
	    
	    
	    
	    if(check1) {
	    
	    try {
	    	PreparedStatement ps8 = conn.prepareStatement("INSERT INTO prototypenew.usereport (parkname,year,month) VALUES (?,?,?)");
	    	ps8.setString(1, parknamedata);
	    	ps8.setString(2, yeardata);
	    	ps8.setString(3, monthdata);



	        int rowsAffected = ps8.executeUpdate();
	        System.out.println(rowsAffected + " row(s) affected");

	      
	        ps8.close();
	      
	        return new Message(CONSTANTS.AftereimportdataforreportUUUU, dbResponse11);

	    } catch (Exception e) {
	
	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	        return null;
	    }
	   }
		return null;
	}

	/**
	 * check if in usereport month and year exist and parkname
	 * @param dataFromClient data from client
	 * @return park data or null
	 */
	public static Message displayinggg(Message dataFromClient) {
	    ArrayList<String> dbResponse12 = new ArrayList<>();
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parknamedata = commandsFromClient.get(0);
		final String yeardata = commandsFromClient.get(1);
		final String monthdata = commandsFromClient.get(2);

		
	    try {
	    	PreparedStatement ps10 = conn.prepareStatement("SELECT * FROM prototypenew.usereport  WHERE `parkname` = ? AND `year`=? AND `month`=? ");
	    	ps10.setString(1, parknamedata);
	    	ps10.setString(2, yeardata);
	    	ps10.setString(3, monthdata);

	        ResultSet resultQuestionSet10 = ps10.executeQuery();
	        if (resultQuestionSet10.next()) {
	        	dbResponse12.add(parknamedata);
	        	dbResponse12.add(yeardata);
	        	dbResponse12.add(monthdata);
		      
 

	        }
	        else {
	        	dbResponse12.add(null);

	        }
	        ps10.close();
	        resultQuestionSet10.close();
	      
	        return new Message(CONSTANTS.Ifidisplay, dbResponse12);

	    } catch (Exception e) {
	
	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	        return null;
	    }
	    
	    
	    
	  
	}
	
	


	    

	
	
	
	/**
	 * get Visitornumberreport data from DB
	 * @param dataFromClient data client
	 * @return ArrayList Visitornumberreport 
	 */
	public static Message willpresentation(Message dataFromClient) {
	    ArrayList<Visitornumberreport> dbResponsepresentation = new ArrayList<>();
	    Visitornumberreport vsitornumberreport;
		ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		final String parknamepresentation = commandsFromClient.get(0);
		final String yearpresentation = commandsFromClient.get(1);
		final String monthpresentation = commandsFromClient.get(2);
		System.out.println("server");
		System.out.println(parknamepresentation);
		System.out.println(yearpresentation);
		System.out.println(monthpresentation);
		
	    try {
	        PreparedStatement ps5 = conn.prepareStatement("SELECT * FROM prototypenew.visitornumberreport  WHERE `parkname` = ? AND `year`=? AND `month`=? ");
	    	ps5.setString(1, parknamepresentation);
	    	ps5.setString(2, yearpresentation);
	    	ps5.setString(3, monthpresentation);
	    	
	        ResultSet resultQuestionSet5 = ps5.executeQuery();
	        if (resultQuestionSet5.next()) {
	        	vsitornumberreport = new Visitornumberreport(resultQuestionSet5.getString(1),
	                                 resultQuestionSet5.getString(2),
	                                 resultQuestionSet5.getString(3),
	                                 resultQuestionSet5.getString(4), resultQuestionSet5.getString(5));
	           

	        	dbResponsepresentation.add(vsitornumberreport);
	        }
	      
	        ps5.close();
	        resultQuestionSet5.close();
	        return new Message(CONSTANTS.ImComingForPresentation, dbResponsepresentation);

	    } catch (Exception e) {
	        System.out.println("Error in retrieving park data from DB: " + e.getMessage());
	        return null;
	    }

	}
	
	
	/**
	 * function that handles added new lines to visitornumberreport
	 * @param dataFromClient data from client
	 * @return success
	 */
	public static Message importdataforvisitorsreport(Message dataFromClient) {
	    System.out.println("im in importdataforvisitorsreport");

	    ArrayList<String> dbResponse1 = new ArrayList<>();
	  //  ArrayList<Visitslogs> visitslogsList = new ArrayList<>();
	    ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
	    final String month = commandsFromClient.get(0);
	    final String year = commandsFromClient.get(1);
	    final String namep = commandsFromClient.get(2);
        String allthenumYesorganizedgroup=null;
        String allthenumNotorganizedgroup = null ;

	    dbResponse1.add(month);
	    dbResponse1.add(year);
	    dbResponse1.add(namep);
	    
	    try {
	        PreparedStatement ps6 = conn.prepareStatement("DELETE FROM prototypenew.visitornumberreport WHERE `parkname` = ?");
	        ps6.setString(1,namep);
	        int rowsAffected6 = ps6.executeUpdate();
	        System.out.println(rowsAffected6 + " row(s) delete");
	        ps6.close();
	    } catch (Exception e) {
	        System.out.println("Error deleting data from visitslogs: " + e.getMessage());
	    }


	    try {
	        PreparedStatement ps2 = conn.prepareStatement("SELECT SUM(`visitorsnum`) FROM prototypenew.visitslogs WHERE `parkname` = ? AND  MONTH(`entryTime`) = ? AND YEAR(`entryTime`) = ? AND  MONTH(`exitTime`) = ? AND YEAR(`exitTime`) = ? AND `orderType` != 'organized group'");
	        ps2.setString(1, namep);
	        ps2.setString(2, month);
	        ps2.setString(3, year);
	        ps2.setString(4, month);
	        ps2.setString(5, year);
	        ResultSet rs = ps2.executeQuery();
	        if (rs.next()) {
	            // Get the result as an integer
	            int visitorsSum = rs.getInt(1);
	             allthenumNotorganizedgroup=String.valueOf(visitorsSum);
	            // Now visitorsSum contains the sum of visitors
	            System.out.println("Total visitors: " + allthenumNotorganizedgroup);
	        }
	        rs.close();
	        ps2.close();
	   //     return new Message(CONSTANTS.Aftereimportdataforvisitorsreport, dbResponse1);

	    } catch (Exception e) {
	        System.out.println("Error in retrieving Visitslogs data from DB: " + e.getMessage());
	        return null;
	    }
	    
	    try {
	        PreparedStatement ps3 = conn.prepareStatement("SELECT SUM(`visitorsnum`) FROM prototypenew.visitslogs WHERE `parkname` = ? AND  MONTH(`entryTime`) = ? AND YEAR(`entryTime`) = ? AND  MONTH(`exitTime`) = ? AND YEAR(`exitTime`) = ? AND `orderType` = 'organized group'");
	        ps3.setString(1, namep);
	        ps3.setString(2, month);
	        ps3.setString(3, year);
	        ps3.setString(4, month);
	        ps3.setString(5, year);
	        ResultSet rs3 = ps3.executeQuery();
	        if (rs3.next()) {
	            // Get the result as an integer
	            int visitorsSum1 = rs3.getInt(1);
	             allthenumYesorganizedgroup=String.valueOf(visitorsSum1);
	            // Now visitorsSum contains the sum of visitors
	            System.out.println("Total visitors: " + allthenumYesorganizedgroup);
	        }
	        rs3.close();
	        ps3.close();

	    } catch (Exception e) {
	        System.out.println("Error in retrieving Visitslogs data from DB: " + e.getMessage());
	        return null;
	    }
	    try {
	        PreparedStatement ps4 = conn.prepareStatement("INSERT INTO prototypenew.visitornumberreport (parkname, visitornumberofinvidual, visitorsnumberoforganizedgroup,year,month) VALUES (?, ?, ?, ?,?)");

	        ps4.setString(1, namep);
	        ps4.setString(2, allthenumNotorganizedgroup);
	        ps4.setString(3, allthenumYesorganizedgroup);
	        ps4.setString(4, year);
	        ps4.setString(5,month );
	        // Execute the update
	        int rowsAffected = ps4.executeUpdate();
	        System.out.println(3);
	        System.out.println(rowsAffected + " row(s) affected");
	        ps4.close();
	        return new Message(CONSTANTS.Aftereimportdataforvisitorsreport, dbResponse1);
	    } catch (SQLException e) {	 
	        System.out.println("Error inserting data into pendingrequest: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	
	
	/**
	 * send parameters into pendingrequest
	 * @param dataFromClient data from client
	 * @return return success
	 */
	public static Message parkupdate(Message dataFromClient) {
	    ArrayList<String> dbResponse1 = new ArrayList<>();
	    ArrayList<String> commandsFromClient = dataFromClient.convertCommandsToArrayList();
        final String namepark=commandsFromClient.get(0);
        final String typeparameterk=commandsFromClient.get(1);
        final String value=commandsFromClient.get(2);
        final  int intvalue=Integer.parseInt(value);
        
	    dbResponse1.add(namepark);
	    dbResponse1.add(typeparameterk);
	    dbResponse1.add(value);
	    
	    try {
	        PreparedStatement ps1 = conn.prepareStatement("DELETE FROM prototypenew.pendingrequest WHERE `parkname` = ? AND `type`=?");
	        ps1.setString(1,namepark);
	        ps1.setString(2, typeparameterk);
	        int rowsAffected5 = ps1.executeUpdate();
	        System.out.println(rowsAffected5 + " row(s) delete");
	        ps1.close();
	    } catch (Exception e) {
	        System.out.println("Error deleting data from pendingrequest: " + e.getMessage());
	    }

		
	    
	    try {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO prototypenew.pendingrequest (parkname,type,newvalue) VALUES (?, ?, ?)");

	        ps.setString(1, namepark);
	        ps.setString(2, typeparameterk);
	        ps.setInt(3, intvalue);
	        // Execute the update
	        int rowsAffected = ps.executeUpdate();
	        System.out.println(rowsAffected + " row(s) affected");
	        ps.close();
	        return new Message(CONSTANTS.AFTERCheckParkUpdate, dbResponse1);
	    } catch (SQLException e) {
	        System.out.println("Error inserting data into pendingrequest: " + e.getMessage());
	        return null;
	    }
	}
	/**
	 * find the request to update parameter from parkmanager
	 * @return the request to update parameter from parkmanager
	 */
	public static Message gitrequestparameter() {
		ArrayList<order> dbResponse = new ArrayList<>();
		order ord;
		try {
			Statement st = conn.createStatement();
			
			ResultSet resultQuestionSet = st.executeQuery("SELECT * FROM prototypenew.pendingrequest");

			if(resultQuestionSet.next()) {
				ord = new order(resultQuestionSet.getString(1), resultQuestionSet.getString(2), resultQuestionSet.getString(3));
				dbResponse.add(ord);
				while (resultQuestionSet.next()) {
				  ord = new order(resultQuestionSet.getString(1), resultQuestionSet.getString(2), resultQuestionSet.getString(3));				
				  dbResponse.add(ord);
			    } 
			}
			st.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.parameterrequestfromDB, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
		//return null;
		
	}
	
	
	
	
	
	
	/**
	 * update the parameter that choose
	 * @param dataFromClient the message that arrived from client
	 * @return that the update is done
	 */
	
	
	
	
	
	public static Message updateparameter(Message dataFromClient) {	
		ArrayList<order> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		int oldvaluemaxvisitors;
		int oldvaluediffvisitors;
		String parkname = commandsFromClient.get(0).getParkname();
		String type = commandsFromClient.get(0).getType();
	    String newvalue = commandsFromClient.get(0).getNewvalue();
	    boolean updatewaitlist=false;
		try {
			PreparedStatement ps1 = conn
					.prepareStatement("SELECT maxvisitorsnumber, diffVisitors  FROM prototypenew.park WHERE parkname = ?;");
			ps1.setString(1,parkname );
			ResultSet resultQuestionSet = ps1.executeQuery();
			if(resultQuestionSet.next()) {
				oldvaluemaxvisitors=resultQuestionSet.getInt(1);
				oldvaluediffvisitors=resultQuestionSet.getInt(2);
			}
			else {return new Message(null,null);}
			
			
			if(type.equals("diffVisitors")) {
				PreparedStatement ps = conn
						.prepareStatement("UPDATE prototypenew.park SET diffVisitors=? WHERE parkname=?");
				ps.setString(1, newvalue);
				ps.setString(2,parkname );
				ps.executeUpdate();
			    ps.close();
				PreparedStatement ps2 = conn
						.prepareStatement("DELETE FROM prototypenew.pendingrequest WHERE parkname=? and type = ? ");
				ps2.setString(1, parkname);
				ps2.setString(2,type );
				ps2.executeUpdate();
			    ps2.close();
			    if(oldvaluediffvisitors<Integer.valueOf(newvalue)) {
			    	updatewaitlist=true;
			    }
			}
			else if(type.equals("delaytime")) {
				PreparedStatement ps = conn
						.prepareStatement("UPDATE prototypenew.park SET delaytime=? WHERE parkname=?");
				ps.setString(1, newvalue);
				ps.setString(2,parkname );
				ps.executeUpdate();
			    ps.close();
			    PreparedStatement ps2 = conn
						.prepareStatement("DELETE FROM prototypenew.pendingrequest WHERE parkname=? and type = ? ");
				System.out.println(type);
				ps2.setString(1, parkname);
				ps2.setString(2,type );
				ps2.executeUpdate();
			    ps2.close();
			}
			else if(type.equals("maxvisitorsnumber")) {
				PreparedStatement ps = conn
						.prepareStatement("UPDATE prototypenew.park SET maxvisitorsnumber=? WHERE parkname=?");
				ps.setString(1, newvalue);
				ps.setString(2,parkname );
				ps.executeUpdate();
			    ps.close();
			    PreparedStatement ps2 = conn
						.prepareStatement("DELETE FROM prototypenew.pendingrequest WHERE parkname=? and type = ? ");
				ps2.setString(1, parkname);
				ps2.setString(2,type );
				ps2.executeUpdate();
			    ps2.close();
			    if(oldvaluemaxvisitors<Integer.valueOf(newvalue)) {
			    	updatewaitlist=true;
			    }
			}
			
			if(updatewaitlist==true) {
				updatewaitlist(parkname);
			}
			return new Message(CONSTANTS.updating, commandsFromClient);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}		
	}
	
	
	
	
	
	/**
	 * fanction update the wait list after updatin parameter 
	 * @param parkname it's park name
	 */
	private static void updatewaitlist(String parkname) {
		ArrayList<ParkWaitList> all=new ArrayList<>();
		ParkWaitList order;
		
		try {
			PreparedStatement ps1 = conn
					.prepareStatement("SELECT * FROM prototypenew.parkwaitlist WHERE parkname = ? ORDER BY CAST(orderID AS UNSIGNED);");
			
			ps1.setString(1,parkname );
			ResultSet resultQuestionSet = ps1.executeQuery();
			while(resultQuestionSet.next()) {
				order=new ParkWaitList(resultQuestionSet.getString(1),resultQuestionSet.getString(2),
						resultQuestionSet.getString(4),resultQuestionSet.getString(5),resultQuestionSet.getString(6)
						,resultQuestionSet.getString(7),resultQuestionSet.getString(8),resultQuestionSet.getDate(3));
				all.add(order);
			}
			for(int i=0;i<all.size();i++) {
				ParkWaitList curr=all.get(i);
				checkorderinwaitlist(curr);
			}
		}catch (Exception e) {
			System.out.println("Error in update in waitlist");
		}
	}
	
	
	
	
	
	/**
	 * function check if order can be enterd into orders table  
	 * @param curr ParkWaitList datails
	 */
	private static void checkorderinwaitlist(ParkWaitList curr) {
		boolean updateorder=false;
		String parkname = curr.getParkName();
        String date1 = curr.dateTimeToString();
        String date2=null;
        int space=0;
        int sum=0;
        try {
        	PreparedStatement ps = conn.prepareStatement(
                    "SELECT delaytime, (maxvisitorsnumber - diffVisitors) AS available_spaces FROM prototypenew.park WHERE parkname = ?;");
        	ps.setString(1, parkname);
        	ResultSet resultQuestionSet=ps.executeQuery();
        	if(resultQuestionSet.next()) {
        		int delytime=resultQuestionSet.getInt(1);
        		space=resultQuestionSet.getInt(2);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = sdf.parse(date1);
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.HOUR_OF_DAY, (-1)*delytime); // Subtract delytime hours
                Date endDate = cal.getTime();
                date2 = sdf.format(endDate);
        	}
        	
            ps = conn.prepareStatement(
                    "SELECT SUM(visitorsnum) FROM prototypenew.orders WHERE parkname = ? AND visittime BETWEEN ? AND ?;");
            ps.setString(1, parkname);
            ps.setString(2, date2);
            ps.setString(3, date1);
            resultQuestionSet = ps.executeQuery();
            if(resultQuestionSet.next()) {
            	sum = resultQuestionSet.getInt(1);
            }
            if(space-sum>=Integer.valueOf(curr.getVisitorsNum())) {
            	ps = conn
						.prepareStatement("DELETE FROM `prototypenew`.`parkwaitlist` WHERE (`orderID` = ?);");
            	System.out.println(space-sum);
            	System.out.println(Integer.valueOf(curr.getVisitorsNum()));
				ps.setString(1, curr.getOrderID());
				ps.executeUpdate();
				ps = conn
						.prepareStatement("INSERT INTO `prototypenew`.`orders` (`orderID`, `parkname`, `visittime`, `visitorsnum`, `email`,"
								+ " `address`, `typeorder`, `visitorID`) "
								+ "VALUES (?, ?, ?,"
								+ " ?, ?, ?, ?, ?);");
				ps.setString(1,curr.getOrderID());
				ps.setString(2,curr.getParkName());
				ps.setString(3,curr.dateTimeToString());
				ps.setString(4,curr.getVisitorsNum());
				ps.setString(5,curr.getEmail());
				ps.setString(6,curr.getAddress());
				ps.setString(7,curr.getTypeOrder());
				ps.setString(8,curr.getVisitorID());
				ps.executeUpdate();
            }
            ps.close();
            resultQuestionSet.close();

        } catch (Exception e) {
            System.out.println("Error in returning the sum of visitors");
        }
	}
	
	
	
	
	
	/**
	 * delete line from the pendingrequest table
	 * @param dataFromClient the message that arrived from client
	 * @return that the delete is done
	 */
	public static Message rejectoarameter(Message dataFromClient) {
		ArrayList<order> commandsFromClient = dataFromClient.convertCommandsToArrayList();

		String parkname = commandsFromClient.get(0).getParkname();
		String type = commandsFromClient.get(0).getType();
	    String newvalue = commandsFromClient.get(0).getNewvalue();
	    PreparedStatement ps2;
		try {
			ps2 = conn
					.prepareStatement("DELETE FROM prototypenew.pendingrequest WHERE parkname=? and type = ? ");
			ps2.setString(1, parkname);
			ps2.setString(2,type );
			ps2.executeUpdate();
		    ps2.close();
			return new Message(CONSTANTS.updating, commandsFromClient);
		} catch (SQLException e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
		
	}
	/**
	 * get cancelation data for one month in one park
	 * @param dataFromClient the year and month and park name that clicked
	 * @return all the data for cancellation report(ArrayList cancellationreport ) table depend on dataFromClient
	 */
	public static Message gitcancellationdata(Message dataFromClient)  {
		ArrayList<Object> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		ArrayList<cancellationreport> dbResponse = new ArrayList<>();
		int year = (Integer)commandsFromClient.get(0);
		int month = (Integer)commandsFromClient.get(1);
		String parkname=(String)commandsFromClient.get(2);
		PreparedStatement ps2;
		
		cancellationreport canc;
		try {
			ResultSet resultQuestionSet;
			if(parkname.equals("all")) {
			     ps2 = conn
					.prepareStatement("SELECT * FROM prototypenew.cancellationreport WHERE MONTH(visittime)=? and YEAR(visittime)=? ");
			     ps2.setInt(1, month);
				 ps2.setInt(2,year);
				 resultQuestionSet =ps2.executeQuery();
		    }
			else {
				ps2 = conn
						.prepareStatement("SELECT * FROM prototypenew.cancellationreport WHERE MONTH(visittime)=? and YEAR(visittime)=? and parkname=? ");
				ps2.setInt(1, month);
				ps2.setInt(2,year);
				ps2.setString(3, parkname);
			    resultQuestionSet =ps2.executeQuery();
			}			
			if(resultQuestionSet.next()) {
				canc = new cancellationreport(resultQuestionSet.getString(1), resultQuestionSet.getString(2), resultQuestionSet.getDate(3),
						resultQuestionSet.getString(4),resultQuestionSet.getString(5),resultQuestionSet.getString(6),
						resultQuestionSet.getString(7),resultQuestionSet.getString(8),resultQuestionSet.getInt(9));
				dbResponse.add(canc);
				while (resultQuestionSet.next()) {
				  canc = new cancellationreport(resultQuestionSet.getString(1), resultQuestionSet.getString(2), resultQuestionSet.getDate(3),
							resultQuestionSet.getString(4),resultQuestionSet.getString(5),resultQuestionSet.getString(6),
							resultQuestionSet.getString(7),resultQuestionSet.getString(8),resultQuestionSet.getInt(9));				
				  dbResponse.add(canc);
			    } 
			}
		    ps2.close();
			return new Message(CONSTANTS.gitcancellationdata, dbResponse);
		} catch (SQLException e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
		
	}
	/**
	 * git visit data for one month in one park
	 * @param dataFromClient the year and month and park name that clicked
	 * @return all the data for visit report(float[32]) taple depend on dataFromClient
	 */
	public static Message gitvisitdata(Message dataFromClient)  {
		ArrayList<Object> commandsFromClient = dataFromClient.convertCommandsToArrayList();
		ArrayList<Object> dbResponse = new ArrayList<>();
		float[] arr=new float[32];
		int year = (Integer)commandsFromClient.get(0);
		int month = (Integer)commandsFromClient.get(1);
		String parkname=(String)commandsFromClient.get(2);
		PreparedStatement ps2;
		for(int i=1;i<=31;i++) {
	    	arr[i]=0;
	    }
		try {
			
			ResultSet resultQuestionSet;
			if(parkname.equals("all")) {
			     ps2 = conn
					.prepareStatement("SELECT DAY(`entryTime`) AS DayOfMonth, ROUND(AVG(Hours)) AS AverageHours\n"
							+ "FROM (\n"
							+ "    SELECT HOUR(TIMEDIFF(`exitTime`, `entryTime`)) AS Hours,\n"
							+ "           `entryTime`\n"
							+ "    FROM prototypenew.visitslogs \n"
							+ "    WHERE YEAR(`entryTime`) = ? AND MONTH(`entryTime`) = ?\n"
							+ ") AS SubqueryAlias\n"
							+ "GROUP BY DAY(`entryTime`);");
			     ps2.setInt(1, year);
				 ps2.setInt(2,month);
				 resultQuestionSet =ps2.executeQuery();
		    }
			else {
				ps2 = conn
						.prepareStatement("SELECT DAY(`entryTime`) AS DayOfMonth, ROUND(AVG(Hours)) AS AverageHours\n"
								+ "FROM (\n"
								+ "    SELECT HOUR(TIMEDIFF(`exitTime`, `entryTime`)) AS Hours,\n"
								+ "           `entryTime`\n"
								+ "    FROM prototypenew.visitslogs \n"
								+ "    WHERE `parkname` = ? AND YEAR(`entryTime`) = ? AND MONTH(`entryTime`) = ?\n"
								+ ") AS SubqueryAlias\n"
								+ "GROUP BY DAY(`entryTime`);");
				ps2.setString(1,parkname);
				ps2.setInt(2,year);
				ps2.setInt(3, month);
			    resultQuestionSet =ps2.executeQuery();
			}			
			while (resultQuestionSet.next()) {			  			
				arr[resultQuestionSet.getInt(1)]=resultQuestionSet.getInt(2);				
		    }
			dbResponse.add(arr);
		    ps2.close();
			return new Message(CONSTANTS.gitvisitdata, dbResponse);
		} catch (SQLException e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}
		
	}
	/**
	 * get yhe park name and the year that in the prototypenew.cancellationreport taple
	 * @return two HashSet (HashSet  Integer ,HashSet  String ) one for the year and the second for the park name
	 */
	public static Message gittheparknamesandyear() {
		ArrayList<HashSet> dbResponse = new ArrayList<>();
		HashSet <String> dbResponseparkname = new HashSet<>();
		HashSet <Integer> dbResponsedate = new HashSet<>();
		try {
			Statement st = conn.createStatement();
			
			ResultSet resultQuestionSet = st.executeQuery("SELECT * FROM prototypenew.cancellationreport");

			while (resultQuestionSet.next()) {
				dbResponseparkname.add(resultQuestionSet.getString(2));
				dbResponsedate.add((resultQuestionSet.getDate(3).getYear()+1900));
		    } 
			dbResponse.add(dbResponseparkname);
			dbResponse.add(dbResponsedate);
			st.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.gittheparknamesandyear, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}		
	}
	/**
	 * get yhe park name and the year that in the prototypenew.visitslogs taple
	 * @return two HashSet (HashSet  Integer ,HashSet  String ) one for the year and the second for the park name
	 */
	public static Message gittheparknamesandyearvis() {
		ArrayList<HashSet> dbResponse = new ArrayList<>();
		HashSet <String> dbResponseparkname = new HashSet<>();
		HashSet <Integer> dbResponsedate = new HashSet<>();
		try {
			Statement st = conn.createStatement();
			
			ResultSet resultQuestionSet = st.executeQuery("SELECT * FROM prototypenew.visitslogs");

			while (resultQuestionSet.next()) {
				dbResponseparkname.add(resultQuestionSet.getString(2));
				dbResponsedate.add((resultQuestionSet.getDate(3).getYear()+1900));
		    } 
			dbResponse.add(dbResponseparkname);
			dbResponse.add(dbResponsedate);
			st.close();
			resultQuestionSet.close();
			return new Message(CONSTANTS.gittheparknamesandyearvis, dbResponse);

		} catch (Exception e) {
			System.out.println("Error in the user Name From DB");
			return null;
		}		
	}

}

