package server;

import java.io.IOException;

import ocsf.server.ConnectionToClient;
import utilities.CONSTANTS;
import utilities.Message;

/**
 * Function that only handles messages from the client
 */
public class MessageHandler {

	/**
	 * Handles the incoming message from the client and performs the corresponding
	 * action.
	 *
	 * @param msg    The message received from the client.
	 * @param client The connection from which the message originated.
	 * @throws IOException If an I/O error occurs while handling the message.
	 */
	public static void HandleMessage(final Object msg, final ConnectionToClient client) throws IOException {

		// the message isn't a Message
		if (!(msg instanceof Message)) {
			client.sendToClient(null);
			return;
		}

		Message dataFromClient = (Message) msg;

		if (dataFromClient.getOperation() == null) {
			client.sendToClient(new Message(null, null));
			return;
		}

		final String clientAction = dataFromClient.getOperation();
		switch (clientAction) {

		case CONSTANTS.ConnectionMadeToServer:
			client.sendToClient(new Message(CONSTANTS.ClientConnectionSuccess, ""));
			break;

		case CONSTANTS.ClientDisconnectRequest:
			EchoServer.updateClientDetails(client.getInetAddress(), client.getInetAddress().getHostName(),
					clientAction);
			client.sendToClient(new Message(CONSTANTS.ClientDisconnectedSuccess, ""));
			break;

		case CONSTANTS.CheckLoginUser:

			client.sendToClient(mysqlConnection.userLogin(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.CheckLoginGuideTraveler:
			client.sendToClient(mysqlConnection.visitorLogin(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.NewVisitor:
			client.sendToClient(mysqlConnection.addVisitor(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.LogOut:
			client.sendToClient(mysqlConnection.LogOut(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.NewOrder:
			client.sendToClient(mysqlConnection.addNewOrder(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.GetVisitorDetails:
			client.sendToClient(mysqlConnection.getVisitorDetails(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.GetParkDetails:
			client.sendToClient(mysqlConnection.getParkDetails(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.GetSumOfVisitors:
			client.sendToClient(mysqlConnection.GetSumOfVisitors(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.WaitList:
			client.sendToClient(mysqlConnection.WaitList(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.GetAllOrdersID:
			client.sendToClient(mysqlConnection.GetAllOrdersID(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.GetAllOrdersIDInParkWaitList:
			client.sendToClient(mysqlConnection.GetAllOrdersIDInParkWaitList(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.GetOrderDetails:
			client.sendToClient(mysqlConnection.GetOrderDetails(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.GetParkWaitListDetails:
			client.sendToClient(mysqlConnection.GetParkWaitListDetails(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.DeleteOrder:
			client.sendToClient(mysqlConnection.DeleteOrder(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.DeleteParkWaitList:
			client.sendToClient(mysqlConnection.DeleteParkWaitList(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.GetMaxIDOrder:
			client.sendToClient(mysqlConnection.GetMaxIDOrder(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.GetParkDetailToMove:
			client.sendToClient(mysqlConnection.GetParkDetailToMove(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.GetAllWaitListBetweenTwoTimes:
			client.sendToClient(mysqlConnection.GetAllWaitListBetweenTwoTimes(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.InsertToAllOrderTable:
			client.sendToClient(mysqlConnection.InsertToAllOrderTable(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.OrdersTaken:
			client.sendToClient(mysqlConnection.idOrder(dataFromClient));
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.addNewcancellationreport:
			client.sendToClient(mysqlConnection.addNewcancellationreport(dataFromClient));
			System.out.println(dataFromClient);
			break;

		case CONSTANTS.CheckIDExist:
			client.sendToClient(mysqlConnection.idExist(dataFromClient));
			break;

		case CONSTANTS.SaveGuideRequest:
			client.sendToClient(mysqlConnection.saveNewGuide(dataFromClient));
			break;

		case CONSTANTS.FindOrderID:
			System.out.println(dataFromClient);
			client.sendToClient(mysqlConnection.orderIDExist(dataFromClient));
			break;

		case CONSTANTS.AddNewEntryLog:
			client.sendToClient(mysqlConnection.addNewVisitLog(dataFromClient));
			break;

		case CONSTANTS.GetMaxVisAndDiffVis:
			client.sendToClient(mysqlConnection.getMaxVandDiffV(dataFromClient));
			break;

		case CONSTANTS.GetNewOrderID:
			client.sendToClient(mysqlConnection.getMaxOrderID(dataFromClient));
			break;

		case CONSTANTS.CheckGuideFromW:
			client.sendToClient(mysqlConnection.getIfGuideOrNot(dataFromClient));
			break;

		case CONSTANTS.addToAllOrdersPayPage:
			client.sendToClient(mysqlConnection.addToAllOrdersPayPage(dataFromClient));
			break;

		case CONSTANTS.deleteOrderIDExit:
			client.sendToClient(mysqlConnection.exitVisitorFromDB(dataFromClient));
			break;
		case CONSTANTS.parkIsFullStart:
			client.sendToClient(mysqlConnection.addToParkIsFull(dataFromClient));
			break;
		case CONSTANTS.CheckLoginVisitor:
			client.sendToClient(mysqlConnection.CheckIfLogged(dataFromClient));
			break;
			
		case CONSTANTS.LogOutEmp:
			client.sendToClient(mysqlConnection.LogOutEmp(dataFromClient));
			System.out.println(dataFromClient);
			break;
			
			
		case CONSTANTS.CheckPark:
		    client.sendToClient(mysqlConnection.park(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
			
			
		case CONSTANTS.StartPresentitaion:
		    client.sendToClient(mysqlConnection.willpresentation(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
			


		case CONSTANTS.CheckParkUpdate:
		    client.sendToClient(mysqlConnection.parkupdate(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
			
			
		case CONSTANTS.GiveMeDataForVisitorsReport:
			System.out.println("im in GiveMeDataForVisitorsReport");
		    client.sendToClient(mysqlConnection.importdataforvisitorsreport(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
			
			
			
		case CONSTANTS.GiveMeDataForUUUUREport:
			System.out.println("im in UUUUREport");
		    client.sendToClient(mysqlConnection.importdataforUUUreport(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
			
			
			
		case CONSTANTS.STARTDISPLAYIN:
			System.out.println("im in DISPLAYINGG");
		    client.sendToClient(mysqlConnection.displayinggg(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
			
			
			
		case CONSTANTS.DISPLAYINOLDREPORT:
			System.out.println("im in DISPLAYINGOLD");
		    client.sendToClient(mysqlConnection.displayingggOLD(dataFromClient));
		    System.out.println(dataFromClient);
			break;
			
		case CONSTANTS.gitrequest:
			client.sendToClient(mysqlConnection.gitrequestparameter());
			System.out.println(dataFromClient);
			break;
		case CONSTANTS.updateparameter:
			client.sendToClient(mysqlConnection.updateparameter(dataFromClient));
			break;
		case CONSTANTS.rejectoarameter:
			client.sendToClient(mysqlConnection.rejectoarameter(dataFromClient));
			break;
		case CONSTANTS.gitcancellationdata:
			client.sendToClient(mysqlConnection.gitcancellationdata(dataFromClient));
			break;
		case CONSTANTS.gittheparknamesandyear:
			client.sendToClient(mysqlConnection.gittheparknamesandyear());
			break;
		case CONSTANTS.gitvisitdata:
			client.sendToClient(mysqlConnection.gitvisitdata(dataFromClient));
			break;
		case CONSTANTS.gittheparknamesandyearvis:
			client.sendToClient(mysqlConnection.gittheparknamesandyearvis());
			break;
			
		default: // unknown
			client.sendToClient(new Message(CONSTANTS.UnknownCommand, ""));
			break;
		}
	}
}
