package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * the ParkWaitList class represent the orders in the parkwaitlist table
 */
public class ParkWaitList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderID;
	private String parkName;
	private String visitTime;
	private String visitorsNum;
	private String email;
	private String address;
	private String typeOrder;
	private String visitorID;
	private String year;
	private String month;
	private String day;
	private LocalDateTime date;
	private Date date2;

	/**
	 * constructor for the user class
	 * 
	 * @param orderID     the ID of the order
	 * @param parkName    the park name of the order
	 * @param date        the date of the order
	 * @param visitTime   the time of the order
	 * @param visitorsNum the number of visitors in the order
	 * @param email       the email of the orderer
	 * @param address     the address for the orderer
	 * @param typeOrder   the type order of the order {'invidual', 'small group',
	 *                    'organized group'}
	 * @param visitorID   the ID of the orderer/visitor
	 * @param year        the year of the order
	 * @param month       the month of the order
	 * @param day         the day of the order
	 */
	public ParkWaitList(String orderID, String parkName, String visitTime, String visitorsNum, String email,
			String address, String typeOrder, String visitorID, String year, String month, String day) {
		this.orderID = orderID;
		this.parkName = parkName;
		this.visitTime = visitTime;
		this.visitorsNum = visitorsNum;
		this.email = email;
		this.address = address;
		this.typeOrder = typeOrder;
		this.visitorID = visitorID;
		this.year = year;
		this.month = month;
		this.day = day;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String dateString = year + "-" + month + "-" + day + " " + visitTime + ":00";
		this.date = LocalDateTime.parse(dateString, dtf);
	}
	
	/**
	 * second type of constructor for the user class
	 * 
	 * @param orderID     the ID of the order
	 * @param parkName    the park name of the order
	 * @param visitorsNum the number of visitors in the order
	 * @param email       the email of the orderer
	 * @param address     the address for the orderer
	 * @param typeOrder   the type order of the order {'invidual', 'small group',
	 *                    'organized group'}
	 * @param visitorID   the ID of the orderer/visitor
	 * @param date2       the full date of order
	 */
	public ParkWaitList(String orderID, String parkName, String visitorsNum, String email,
			String address, String typeOrder, String visitorID, Date date2){
		
		this.orderID = orderID;
		this.parkName = parkName;
		this.visitorsNum = visitorsNum;
		this.email = email;
		this.address = address;
		this.typeOrder = typeOrder;
		this.visitorID = visitorID;
		this.setDate2(date2);
	}

	/**
	 * get the Date
	 * 
	 * @return returns the Date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * set the date
	 * 
	 * @param date date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * get the OrderID
	 * 
	 * @return returns the OrderID
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * set the OrderID
	 * 
	 * @param OrderID OrderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * get the ParkName
	 * 
	 * @return returns the ParkName
	 */
	public String getParkName() {
		return parkName;
	}

	/**
	 * set the ParkName
	 * 
	 * @param ParkName ParkName to set
	 */
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	/**
	 * get the VisitTime
	 * 
	 * @return returns the VisitTime
	 */
	public String getVisitTime() {
		return visitTime;
	}

	/**
	 * set the VisitTime
	 * 
	 * @param VisitTime VisitTime to set
	 */
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	/**
	 * get the VisitorsNum
	 * 
	 * @return returns the VisitorsNum
	 */
	public String getVisitorsNum() {
		return visitorsNum;
	}

	/**
	 * set the VisitorsNum
	 * 
	 * @param VisitorsNum VisitorsNum to set
	 */
	public void setVisitorsNum(String visitorsNum) {
		this.visitorsNum = visitorsNum;
	}

	/**
	 * get the Email
	 * 
	 * @return returns the Email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set the Email
	 * 
	 * @param Email Email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * get the Address
	 * 
	 * @return returns the Address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * set the Address
	 * 
	 * @param Address Address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * get the TypeOrder
	 * 
	 * @return returns the TypeOrder
	 */
	public String getTypeOrder() {
		return typeOrder;
	}

	/**
	 * set the TypeOrder
	 * 
	 * @param TypeOrder TypeOrder to set
	 */
	public void setTypeOrder(String typeOrder) {
		this.typeOrder = typeOrder;
	}

	/**
	 * get the getYear
	 * 
	 * @return returns the Year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * set the Year
	 * 
	 * @param Year Year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * get the getMonth
	 * 
	 * @return returns the Month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * set the Month
	 * 
	 * @param Month Month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * get the getDay
	 * 
	 * @return returns the Day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * set the Day
	 * 
	 * @param Day Day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * get the VisitorID
	 * 
	 * @return returns the VisitorID
	 */
	public String getVisitorID() {
		return visitorID;
	}

	/**
	 * set the VisitorID
	 * 
	 * @param VisitorID VisitorID to set
	 */
	public void setVisitorID(String visitorID) {
		this.visitorID = visitorID;
	}

	/**
	 * get the date of the order
	 * @return the date of the order
	 */
	public Date getDate2() {
		return date2;
	}
	/**
	 * set the date of the order
	 * @param date2 
	 */
	public void setDate2(Date date2) {
		this.date2 = date2;
	}
	/**
	 * converts date2 to a string of correct format
	 * @return returns the formatted string
	 */
	public String dateTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date2);
	}
}
