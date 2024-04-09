package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import Threads.MyThread;
import common.ChatIF;
import departmentManagerGUI.cancellationreportcontroller;
import departmentManagerGUI.parametercontroller;
import departmentManagerGUI.visitreportcontroller;
import entities.Orders;
import entities.Park;
import entities.ParkWaitList;
import entities.Parkfulltimes;
import entities.User;
import entities.Visitornumberreport;
import entities.Visitors;
import entities.cancellationreport;
import entities.order;
import gui.LogInController;
import ocsf.client.AbstractClient;
import parkmanagerGUI.BeforeeeDisplay;
import parkmanagerGUI.Beforepresentation;
import parkmanagerGUI.PresentationAreport;
import parkmanagerGUI.Updateparametres;
import parkmanagerGUI.Usagereports;
import parkmanagerGUI.VisitorsReportIncluded;
import parkmanagerGUI.dashboardPMController;
import serviceRrep.dashboardSRController;
import utilities.CONSTANTS;
import utilities.Message;
import visitorGUI.DetailsForIDOrderController;
import visitorGUI.MyOrdersFormController;
import visitorGUI.OrderFormController;
import visitorGUI.OrdertableformController;
import visitorGUI.dashboardVController;
import workerGUI.EntryEnterOrderIDController;
import workerGUI.EntryNoOrderIDController;
import workerGUI.ExitVisitorController;
import workerGUI.PayPageController;

/**
 * Class handles messages from server and from clientUI and creates the client
 * itself and holds local data
 */
public class ChatClient extends AbstractClient {

	ChatIF clientUI;
	public static boolean awaitResponse = false;

	/**
	 * Create the Client
	 * 
	 * @param host     client's host name
	 * @param port     port that client will listen to
	 * @param clientUI the UI of the client
	 * @throws IOException exception thrown when error
	 */
	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
	}

	/**
	 * Creates the client
	 * 
	 * @param host host name
	 * @param port port to listen to
	 */
	public ChatClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}

	/**
	 * function that handles all messages coming from the server
	 * 
	 * @param msg the message from the server
	 */
	@SuppressWarnings("unchecked")
	protected void handleMessageFromServer(Object msg) {
		Message dataFromServer;
		if (msg instanceof Message) // converts message to arrayList type
			dataFromServer = (Message) msg;
		else // in case the server didn't send this type of data arrayList<String>
		{
			System.out.println("server didnt deliver data to client in type of Message");
			awaitResponse = false; // release the waiting client
			return; // return and do nothing

		}

		// check message operation
		switch (dataFromServer.getOperation()) {

		case CONSTANTS.ClientConnectionSuccess:
			awaitResponse = false;
			break;

		case CONSTANTS.ClientDisconnectedSuccess:
			awaitResponse = false;
			break;

		case CONSTANTS.ValidLoginAnswer: // check the answer that server send to if the username and password is in DB
			System.out.println(dataFromServer);
			ArrayList<User> valid_login_responds = (ArrayList<User>) dataFromServer.getCommands();
			// if user data is not null then valid if null then was not found
			LogInController.isLoginValid = !(valid_login_responds.get(0) == null);
			if (LogInController.isLoginValid)
				LogInController.user = valid_login_responds.get(0);
			awaitResponse = false;
			break;

		case CONSTANTS.ValidVisitorLoginAnswer:
			System.out.println(dataFromServer);
			ArrayList<Visitors> validVisitorLoginResponds = (ArrayList<Visitors>) dataFromServer.getCommands();
			// if user data is not null then valid if null then was not found
			LogInController.isLoginValid = !(validVisitorLoginResponds.get(0) == null);
			if (LogInController.isLoginValid)
				LogInController.visitor = validVisitorLoginResponds.get(0);
			awaitResponse = false;
			break;

		case CONSTANTS.NewVisitorSucceed:
			awaitResponse = false;
			break;

		case CONSTANTS.LogOutSucceed:
			awaitResponse = false;
			break;
		case CONSTANTS.NewOrderSucceed:
			awaitResponse = false;
			break;

		case CONSTANTS.GetVisitorDetailsSucceed:
			System.out.println(dataFromServer);
			ArrayList<Visitors> visitorDetailsRespond = (ArrayList<Visitors>) dataFromServer.getCommands();
			// if visitor data is not null then valid if null then was not found
			if (!(visitorDetailsRespond.get(0) == null)) {
				OrderFormController.visitor = visitorDetailsRespond.get(0);
			}
			awaitResponse = false;
			break;

		case CONSTANTS.GetParkDetailsSucceed:
			System.out.println(dataFromServer);
			ArrayList<Park> parkDetailsRespond = (ArrayList<Park>) dataFromServer.getCommands();
			// if park data is not null then valid if null then was not found
			if (!(parkDetailsRespond.get(0) == null)) {
				OrderFormController.park = parkDetailsRespond.get(0);
				OrdertableformController.parkOrderTable = parkDetailsRespond.get(0);
			}
			awaitResponse = false;
			break;

		case CONSTANTS.GetSumOfVisitorsSucceed:
			System.out.println(dataFromServer);
			ArrayList<Double> sumOfVisitorRespond = (ArrayList<Double>) dataFromServer.getCommands();
			OrderFormController.SumOfVisitor = sumOfVisitorRespond.get(0);
			DetailsForIDOrderController.SumOfVisitorToMove = sumOfVisitorRespond.get(0);
			OrdertableformController.SumOfVisitorOrderTable = sumOfVisitorRespond.get(0);
			awaitResponse = false;
			break;

		case CONSTANTS.WaitListSucceed:
			awaitResponse = false;
			break;

		case CONSTANTS.GetAllOrdersIDSucceed:
			System.out.println(dataFromServer);
			ArrayList<String> AllOrdersResponds = (ArrayList<String>) dataFromServer.getCommands();
			MyOrdersFormController.AllOrders = AllOrdersResponds;
			awaitResponse = false;
			break;
		case CONSTANTS.GetAllOrdersIDInParkWaitListSucceed:
			System.out.println(dataFromServer);
			ArrayList<String> AllOrdersInParkWaitListResponds = (ArrayList<String>) dataFromServer.getCommands();
			MyOrdersFormController.AllOrdersInParkWaitList = AllOrdersInParkWaitListResponds;
			awaitResponse = false;
			break;
		case CONSTANTS.GetOrderDetailsSucceed:
			System.out.println(dataFromServer);
			ArrayList<Orders> OrderDetails = (ArrayList<Orders>) dataFromServer.getCommands();
			if (!(OrderDetails.get(0) == null)) {
				DetailsForIDOrderController.order = OrderDetails.get(0);
				DetailsForIDOrderController.IsOrder = true;
			} else {
				MyThread.stillOrder = false;
			}
			awaitResponse = false;
			break;
		case CONSTANTS.GetParkWaitListDetailsSucceed:
			System.out.println(dataFromServer);
			ArrayList<ParkWaitList> ParkWaitListDetails = (ArrayList<ParkWaitList>) dataFromServer.getCommands();
			if (!(ParkWaitListDetails.get(0) == null)) {
				DetailsForIDOrderController.parkWaitList = ParkWaitListDetails.get(0);
				DetailsForIDOrderController.IsParkWaitList = true;
			}
			awaitResponse = false;
			break;
		case CONSTANTS.DeleteOrderSucceed:
			awaitResponse = false;
			break;
		case CONSTANTS.DeleteParkWaitListSucceed:
			awaitResponse = false;
			break;
		case CONSTANTS.GetMaxIDOrderSucceed:
			System.out.println(dataFromServer);
			ArrayList<Integer> MaxIDArrayList = (ArrayList<Integer>) dataFromServer.getCommands();
			OrderFormController.orderID = MaxIDArrayList.get(0);
			awaitResponse = false;
			break;
		case CONSTANTS.GetParkDetailToMoveSucceed:
			System.out.println(dataFromServer);
			ArrayList<Park> parkDetailsToMoveRespond = (ArrayList<Park>) dataFromServer.getCommands();
			// if park data is not null then valid if null then was not found
			if (!(parkDetailsToMoveRespond.get(0) == null)) {
				DetailsForIDOrderController.park = parkDetailsToMoveRespond.get(0);
			}
			awaitResponse = false;
			break;
		case CONSTANTS.GetAllWaitListBetweenTwoTimesSucceed:
			System.out.println(dataFromServer);
			ArrayList<ParkWaitList> GetAllWaitListBetweenTwoTimesRespond = (ArrayList<ParkWaitList>) dataFromServer
					.getCommands();
			// if park data is not null then valid if null then was not found
			if (!(GetAllWaitListBetweenTwoTimesRespond.get(0) == null)) {
				DetailsForIDOrderController.AllWaitListBetweenTwoTimes.addAll(GetAllWaitListBetweenTwoTimesRespond);
			}
			awaitResponse = false;
			break;
		case CONSTANTS.InsertToAllOrderTableSucceed:
			awaitResponse = false;
			break;
		case CONSTANTS.OrdersTakenSucceed:
			System.out.println(dataFromServer);
			ArrayList<Orders> backfromserver = (ArrayList<Orders>) dataFromServer.getCommands();
			// if user data is not null then valid if null then was not found
			if (!(backfromserver.get(0) == null))
				dashboardVController.orderList.addAll(backfromserver);
			awaitResponse = false;
			break;
		case CONSTANTS.addNewcancellationreportSucceed:
			awaitResponse = false;
			break;
		case CONSTANTS.ValidVisitorExist:
			ArrayList<String> validVisitorExistRespond = (ArrayList<String>) dataFromServer.getCommands();
			// if user data is not null then valid if null then was not found
			dashboardSRController.isExist = !(validVisitorExistRespond.get(0) == null);
			
			
			if(dashboardSRController.isExist)
			{
				switch(validVisitorExistRespond.get(0)) {
					case "foundGuide":
						dashboardSRController.isGuide = true;
						break;
					case "foundTraveller":
						dashboardSRController.isGuide = false;
						break;
				}
			}
			awaitResponse = false;
			break;
			
		case CONSTANTS.SavedGuideInDB:
			awaitResponse = false;
			break;
			
			
		case CONSTANTS.OrderCheckComplete:
			ArrayList<Orders> validorderID = (ArrayList<Orders>) dataFromServer.getCommands();
			// if order data not null then valid if null then was not found
			EntryEnterOrderIDController.IsOrderExist = !(validorderID.get(0) == null);
			if(EntryEnterOrderIDController.IsOrderExist)
				EntryEnterOrderIDController.order = validorderID.get(0);
			awaitResponse = false;
			break;
			
			
		case CONSTANTS.NewLogAdded:
			awaitResponse = false;
			break;

		case CONSTANTS.ReturnedMaxVandDiffV:
			ArrayList<String> parkdata = (ArrayList<String>) dataFromServer.getCommands();
			PayPageController.maxVisitors = Integer.valueOf(parkdata.get(0));
			PayPageController.diffVisitors = Integer.valueOf(parkdata.get(1));
			PayPageController.currVisitorsOrdered = Integer.valueOf(parkdata.get(2));
			PayPageController.currVisitorsNotOrdered = Integer.valueOf(parkdata.get(3));
			awaitResponse = false;
			break;
			
			
		case CONSTANTS.MaxTempOrderIDReturned:
			ArrayList<String> response = (ArrayList<String>) dataFromServer.getCommands();
			int tempMax = Integer.valueOf(response.get(0));
			tempMax++;
			EntryNoOrderIDController.newOrderID = String.valueOf(tempMax);
			awaitResponse = false;
			break;
			
		case CONSTANTS.GuideWasChecked:
			ArrayList<Visitors> guideData = (ArrayList<Visitors>) dataFromServer.getCommands();
			boolean doesExist = !(guideData.get(0) == null);
			if(doesExist)
				EntryNoOrderIDController.isGuide = (guideData.get(0).getTypevisitor().equals("guide"));
			awaitResponse = false;
			break;
			
		case CONSTANTS.allOrdersAddedSuccessPayPage:
			awaitResponse = false;
			break;
			
		case CONSTANTS.VisitorExited:
			ArrayList<String> exitData = (ArrayList<String>) dataFromServer.getCommands();
			ExitVisitorController.isOrderIDExist = !(exitData.get(0) == null);
			awaitResponse = false;
			break;
			
		case CONSTANTS.addedToParkIsFull:
			awaitResponse = false;
			break;
			
		case CONSTANTS.CheckLoginVisitorSucceed:
			System.out.println(dataFromServer);
			ArrayList<String> IsLoggedVisitorResponds = (ArrayList<String>) dataFromServer.getCommands();
			// if user data is not null then not valid if null then was not found
			LogInController.isLogged = !(IsLoggedVisitorResponds.get(0) == null);
			awaitResponse = false;
			break;
		
		case CONSTANTS.NAMEPARKANSWER:
			System.out.println(dataFromServer);
			ArrayList<Park> validNamePark = (ArrayList<Park>) dataFromServer.getCommands();
			
			

			// if user data is not null then valid if null then was not found
		
			dashboardPMController.isLoginValid = !(validNamePark.get(0) == null);
			if (dashboardPMController.isLoginValid) {
				dashboardPMController.park = validNamePark.get(0);
				
				if(dashboardPMController.park==null) {System.out.println("the problem in chatclient");}
			}
			awaitResponse = false;
			break;
			
			
			
			
			
		case CONSTANTS.ImComingForPresentation:
			System.out.println(dataFromServer);
			ArrayList<Visitornumberreport> validNameParkpresentation = (ArrayList<Visitornumberreport>) dataFromServer.getCommands();
			Visitornumberreport visitornumberreport = validNameParkpresentation.get(0);
			System.out.println("in client");
			System.out.println(visitornumberreport.getParkname()+visitornumberreport.getvisitornumberofinvidual());

			// if user data is not null then valid if null then was not found
			Beforepresentation.isLoginValid = !(visitornumberreport == null);
			if (Beforepresentation.isLoginValid) {
				Beforepresentation.visitornumberreport = visitornumberreport;		
				if(Beforepresentation.visitornumberreport==null) {System.out.println("the problem in chatclient");}
			}
			awaitResponse = false;
			break;
			
			
			
			
			
		case CONSTANTS.AFTERCheckParkUpdate:
			System.out.println("im in client after update to pending requist");
			System.out.println(dataFromServer);
			ArrayList<String> validNamePark1 = (ArrayList<String>) dataFromServer.getCommands();
			Updateparametres.isLoginValid = !(validNamePark1.get(0) == null);
		
			awaitResponse = false;	
			break;
			
		
			
			 
		case CONSTANTS.AftereimportdataforreportUUUU:
			System.out.println(dataFromServer);
			ArrayList<Parkfulltimes> vailidparkfulltimes = (ArrayList<Parkfulltimes>) dataFromServer.getCommands();
			Usagereports.dbResponsehaha=vailidparkfulltimes;
			Usagereports.parkfulltimes=vailidparkfulltimes.get(0);
			
			Usagereports.isLoginValid = !(vailidparkfulltimes.get(0) == null);
			if (Usagereports.isLoginValid) {
				
			}
			awaitResponse = false;	
			break;
			
			   
			
			
			
		case CONSTANTS.IfidisplayOLD:
			System.out.println(dataFromServer);
			ArrayList<Parkfulltimes> vailidparkfulltimes1 = (ArrayList<Parkfulltimes>) dataFromServer.getCommands();
		     System.out.println("chatclient");
		     System.out.println(vailidparkfulltimes1.get(0).getParkname());
		     System.out.println(vailidparkfulltimes1.get(0).getday());
		     System.out.println(vailidparkfulltimes1.get(0).gethourstart());
		     System.out.println(vailidparkfulltimes1.get(0).gethourend());
		     BeforeeeDisplay.dbResponsehaha=vailidparkfulltimes1;
		     BeforeeeDisplay.parkfulltimes=vailidparkfulltimes1.get(0);
		     BeforeeeDisplay.isLoginValid3 = !(vailidparkfulltimes1.get(0) == null);
			if (BeforeeeDisplay.isLoginValid3) {
                 
			}
			awaitResponse = false;	
			break;
			 
			
		case CONSTANTS.Ifidisplay:
			System.out.println(dataFromServer);
			ArrayList<String> MonthandYearandnamePark1 = (ArrayList<String>) dataFromServer.getCommands();
			BeforeeeDisplay.isLoginValid2 = !(MonthandYearandnamePark1.get(0) == null||MonthandYearandnamePark1.get(1)==null||MonthandYearandnamePark1.get(2)==null );
			awaitResponse = false;	
			break;
			
		case CONSTANTS.Aftereimportdataforvisitorsreport:
			System.out.println("im in importdata");
			System.out.println(dataFromServer);
			ArrayList<String> MonthandYearandnamePark = (ArrayList<String>) dataFromServer.getCommands();
			VisitorsReportIncluded.isLoginValid = !(MonthandYearandnamePark.get(0) == null||MonthandYearandnamePark.get(1)==null||MonthandYearandnamePark.get(2)==null );
			if (VisitorsReportIncluded.isLoginValid) {
				VisitorsReportIncluded.aftermonth = MonthandYearandnamePark.get(0);
				VisitorsReportIncluded.afteryear = MonthandYearandnamePark.get(1);
				VisitorsReportIncluded.afternamepark = MonthandYearandnamePark.get(2);

				
				
				if(VisitorsReportIncluded.park==null) {System.out.println("the problem in chatclient");}
			}
			awaitResponse = false;	
			break;
			
		case CONSTANTS.parameterrequestfromDB:
			parametercontroller.orderarr=(ArrayList<order>) dataFromServer.getCommands();
			awaitResponse = false;
			break;
		case CONSTANTS.updating:
			System.out.println("updating success");
			awaitResponse = false;
			break;
		case CONSTANTS.gittheparknamesandyear:
			ArrayList<HashSet> dbResponse = (ArrayList<HashSet>) dataFromServer.getCommands();
			cancellationreportcontroller.parksname=(HashSet<String>) dbResponse.get(0);
			cancellationreportcontroller.theyear=(HashSet<Integer>) dbResponse.get(1);
			visitreportcontroller.parksname=(HashSet<String>) dbResponse.get(0);
			visitreportcontroller.theyear=(HashSet<Integer>) dbResponse.get(1);
			awaitResponse = false;
			break;
		case CONSTANTS.gitcancellationdata:
			cancellationreportcontroller.cancellation=(ArrayList<cancellationreport>) dataFromServer.getCommands();
			awaitResponse = false;
			break;
		case CONSTANTS.gitvisitdata:
			ArrayList<Object> ARR=(ArrayList<Object>) dataFromServer.getCommands();
			float[] arr=(float[]) ARR.get(0);
			visitreportcontroller.everg=arr;
			awaitResponse = false;
			break;
		case CONSTANTS.gittheparknamesandyearvis:
			ArrayList<HashSet> dbResponse2 = (ArrayList<HashSet>) dataFromServer.getCommands();
			visitreportcontroller.parksname=(HashSet<String>) dbResponse2.get(0);
			visitreportcontroller.theyear=(HashSet<Integer>) dbResponse2.get(1);
			awaitResponse = false;
			break;	
		default: // if the first element in the array list is not defined in the operations of
			System.out.println("server send unknown operation for the client to do");
			awaitResponse = false;
			break;

		}
	}

	/**
	 * function that handles all messages coming from the ClietUI
	 * 
	 * @param message the message from the client's UI
	 */
	public void handleMessageFromClientUI(Object message) {
		try {
			openConnection();
			awaitResponse = true;
			sendToServer(message);
			while (awaitResponse) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			clientUI.display("Could not send message to server: Terminating client" + e);
			quit();
		}
	}

	/**
	 * function that closes the connection to the server when clients disconnects
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
