package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * function that represents the visitor logger that is in DB
 */
public class Visitslogs implements Serializable{

	private static final long serialVersionUID = -2952643135916905984L;
	private String orderID;
	private String parkname;
	private Date entryTime;
	private Date exitTime;
	private String orderType;
	private int visitorsnum;
	private int entrancewithnoorder;
	private String ID;
	
	/**
	 * constructor for the class
	 * @param orderID the order's ID
	 * @param parkname the park name of the order
	 * @param entryTime the entry time of the visitor
	 * @param exitTime the exit time of the visitor
	 * @param orderType the type of order
	 * @param visitorsnum the number of visitors in the order
	 */
	public Visitslogs(String orderID, String parkname, Date entryTime, Date exitTime, 
			String orderType, int visitorsnum) {
		this.orderID = orderID;
		this.parkname = parkname;
		this.entryTime = entryTime;
		this.exitTime = exitTime;
		this.orderType = orderType;
		this.visitorsnum = visitorsnum;
	}

	/**
	 * returns the orderID
	 * @return returns orderID
	 */
	public String getOrderID() {
		return orderID;
	}

	/**
	 * sets the orderID
	 * @param orderID orderID to set
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	/**
	 * gets the park name
	 * @return returns the park name
	 */
	public String getParkname() {
		return parkname;
	}

	/**
	 * sets the park name
	 * @param parkname park name to set
	 */
	public void setParkname(String parkname) {
		this.parkname = parkname;
	}

	/**
	 * gets the entry time of order
	 * @return returns the order time of entry
	 */
	public Date getEntryTime() {
		return entryTime;
	}

	/**
	 * sets the entry time of order
	 * @param entryTime entry time to set
	 */
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	/**
	 * gets the exit time of order
	 * @return returns the exit time
	 */
	public Date getExitTime() {
		return exitTime;
	}

	/**
	 * sets the exit time of order
	 * @param exitTime exit time to set
	 */
	public void setExitTime(Date exitTime) {
		this.exitTime = exitTime;
	}

	/**
	 * gets String of order type
	 * @return returns the order type
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * sets the order type
	 * @param orderType order type to set
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * gets the visitor number in order
	 * @return returns the visitor number
	 */
	public int getVisitorsnum() {
		return visitorsnum;
	}

	/**
	 * sets the visitor number
	 * @param visitorsnum visitor number to set
	 */
	public void setVisitorsnum(int visitorsnum) {
		this.visitorsnum = visitorsnum;
	}
	
	/**
	 * converts entryTime to a string of correct format
	 * @return returns the formatted string
	 */
	public String entryTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(entryTime);
	}
	
	/**
	 * converts exitTime to a string of correct format
	 * @return returns the formatted string
	 */
	public String exitTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(exitTime);
	}
	
	/**
	 * set the entrance with no order
	 * @param i the number to set to
	 */
	public void setentrancewithnoorder(int i) {
		entrancewithnoorder = i;
	}
	
	/**
	 * get the entrance with no order
	 * @return returns the entrance with no order
	 */
	public int getentrancewithnoorder() {
		return entrancewithnoorder;
	}
	
	/**
	 * sets the ID of traveler
	 * @param ID id to set
	 */
	public void setID(String ID) {
		this.ID = ID;
	}
	
	/**
	 * gets the ID
	 * @return the ID
	 */
	public String getID() {
		return ID;
	}
}
