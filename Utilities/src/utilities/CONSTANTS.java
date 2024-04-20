package utilities;

/**
 * Constants that developers use through the code
 */
public abstract class CONSTANTS {

	public final static int DEFAULT_PORT = 5555;

	// Unknown command constant
	public final static String UnknownCommand = "UnknownCommand";

	// Client response constants
	public final static String ConnectionMadeToServer = "Connect";
	public final static String ClientDisconnectRequest = "Disconnect";
	public final static String CheckLoginUser = "CheckLoginInfoUser";
	public static final String CheckLoginGuideTraveler = "CheckLoginGuideTraveler";
	public static final String NewVisitor = "addNewVisitor";
	public static final String LogOut = "LogOut";
	public static final String NewOrder = "NewOrder";
	public static final String GetVisitorDetails = "GetVisitorDetails";
	public static final String GetParkDetails = "GetParkDetails";
	public static final String GetSumOfVisitors = "GetSumOfVisitors";
	public final static String WaitList = "EnteringWaitList";
	public final static String GetAllOrdersID = "GetAllOrdersID";
	public final static String GetOrderDetails = "GetOrderDetails";
	public final static String GetAllOrdersIDInParkWaitList = "GetAllOrdersIDInParkWaitList";
	public final static String GetParkWaitListDetails = "GetParkWaitListDetails";
	public final static String DeleteOrder = "DeleteOrder";
	public final static String DeleteParkWaitList = "DeleteParkWaitList";
	public final static String GetMaxIDOrder = "GetMaxIDOrder";
	public static final String GetParkDetailToMove = "GetParkDetailToMove";
	public static final String GetAllWaitListBetweenTwoTimes = "GetAllWaitListBetweenTwoTimes";
	public static final String InsertToAllOrderTable = "InsertToAllOrderTable";
	public static final String OrdersTaken = "OrderFoundAccordingtovisitorid";
	public static final String addNewcancellationreport = "addNewcancellationreport";
	public static final String ValidVisitorExist = "ValidVisitorExist";
	public static final String SaveGuideRequest = "SaveGuideRequest";
	public static final String FindOrderID = "FindOrderID";
	public static final String AddNewEntryLog = "AddNewEntryLog";
	public static final String GetMaxVisAndDiffVis = "GetMaxVisAndDiffVis";
	public static final String GetNewOrderID = "GetNewOrderID";
	public static final String CheckGuideFromW = "CheckGuideFromW";
	public final static String addToAllOrdersPayPage = "addToAllOrdersPayPage";
	public static final String deleteOrderIDExit = "deleteOrderIDExit";
	public static final String parkIsFullStart = "parkIsFullStart";
	public static final String CheckLoginVisitor = "CheckLoginVisitor";
	public static final String LogOutEmp = "LogOutEmp";
	public static final String CheckPark = "CheckPark";
	public static final String CheckParkUpdate = "CheckParkUpdate";
	public static final String GiveMeDataForVisitorsReport = "GiveMeDataForVisitorsReport";
	public static final String StartPresentitaion = "StartPresentitaion";
	public static final String GiveMeDataForUUUUREport = "GiveMeDataForUUUUREport";
	public static final String STARTDISPLAYIN = "STARTDISPLAYIN";
	public static final String DISPLAYINOLDREPORT = "DISPLAYINOLDREPORT";

	// Server operation constants
	public final static String ClientConnectionSuccess = "connectTrue";
	public final static String ClientDisconnectedSuccess = "disconnectTrue";
	public static final String ValidLoginAnswer = "ValidLoginAnswer";
	public static final String ValidVisitorLoginAnswer = "ValidVisitorLoginAnswer";
	public static final String NewVisitorSucceed = "addNewVisitorSucceed";
	public static final String LogOutSucceed = "LogOutSucceed";
	public static final String NewOrderSucceed = "NewOrderSucceed";
	public static final String GetVisitorDetailsSucceed = "GetVisitorDetailsSucceed";
	public static final String GetParkDetailsSucceed = "GetParkDetailsSucceed";
	public static final String GetSumOfVisitorsSucceed = "GetSumOfVisitorsSucceed";
	public final static String WaitListSucceed = "EnteringWaitListSucceeed";
	public final static String GetAllOrdersIDSucceed = "GetAllOrdersIDSucceed";
	public final static String GetAllOrdersIDInParkWaitListSucceed = "GetAllOrdersIDInParkWaitListSucceed";
	public final static String GetOrderDetailsSucceed = "GetOrderDetailsSucceed";
	public final static String GetParkWaitListDetailsSucceed = "GetParkWaitListDetailsSucceed";
	public final static String DeleteOrderSucceed = "DeleteOrderSucceed";
	public final static String DeleteParkWaitListSucceed = "DeleteParkWaitListSucceed";
	public final static String GetMaxIDOrderSucceed = "GetMaxIDOrderSucceed";
	public static final String GetParkDetailToMoveSucceed = "GetParkDetailToMoveSucceed";
	public static final String GetAllWaitListBetweenTwoTimesSucceed = "GetAllWaitListBetweenTwoTimesSucceed";
	public static final String InsertToAllOrderTableSucceed = "InsertToAllOrderTableSucceed";
	public static final String OrdersTakenSucceed = "OrderFoundAccordingtovisitoridSucceed";
	public static final String addNewcancellationreportSucceed = "addNewcancellationreportSucceed";
	public static final String CheckIDExist = "CheckIDExist";
	public static final String SavedGuideInDB = "SavedGuideInDB";
	public static final String OrderCheckComplete = "OrderCheckComplete";
	public static final String NewLogAdded = "NewLogAdded";
	public static final String ReturnedMaxVandDiffV = "ReturnedMaxVandDiffV";
	public static final String MaxTempOrderIDReturned = "MaxTempOrderIDReturned";
	public static final String GuideWasChecked = "GuideWasChecked";
	public static final String allOrdersAddedSuccessPayPage = "allOrdersAddedSuccessPayPage";
	public static final String VisitorExited = "VisitorExited";
	public static final String addedToParkIsFull = "addedToParkIsFull";
	public static final String CheckLoginVisitorSucceed = "CheckLoginVisitorSucceed";
	public static final String NAMEPARKANSWER = "NAMEPARKANSWER";
	public static final String AFTERCheckParkUpdate = "AfterCheckParkUpdate";
	public static final String Aftereimportdataforvisitorsreport = "Aftereimportdataforvisitorsreport";
	public static final String ImComingForPresentation = "ImComingForPresentation";
	public static final String AftereimportdataforreportUUUU = "AftereimportdataforreportUUUU";
	public static final String Ifidisplay = "Ifidisplay";
	public static final String IfidisplayOLD = "IfidisplayOLD";

	// User roles constants
	public final static String departmentManager = "departmentManager";
	public final static String parkManager = "parkManager";
	public final static String worker = "worker";
	public final static String serviceRrepresentative = "serviceRrepresentative";
	public static final String Traveler = "traveller";
	public static final String Guide = "guide";;
	public final static String Park = "Park";

	// Extra constants
	public final static int ticketPrice = 5;
	

	//DM constants 
	//parameter
	public final static String gitrequest = "gitrequest";
	public final static String parameterrequestfromDB = "gitrequest";
	public final static String updateparameter = "updateparameter";
	public final static String updating = "updating";
	public final static String rejectoarameter = "rejectoarameter";
	//cancellation report
	public final static String gitcancellationdata = "gitcancellationdata";
	public final static String gittheparknamesandyear = "gittheparknamesandyear";
	//visit report
	public final static String gitvisitdata = "gitvisitdata";
	public final static String gittheparknamesandyearvis = "gittheparknamesandyearvis";

}
