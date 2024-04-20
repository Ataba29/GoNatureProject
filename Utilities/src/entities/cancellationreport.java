package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Class represents the cancellationreport that are in the DB
 */
public class cancellationreport implements Serializable{
	private String orderID,parkname,email,address,typeorder,visitorID,visitornum;
	private int orderwascanceled;
	private Date visittime;
	/**
	 * constructor for the cancellationreport
	 * @param orderID unique id order
	 * @param parkname the name of the park
	 * @param visitornum the number of the visitors
	 * @param email the email of the visitor
	 * @param address the address of the visitor
	 * @param typeorder the type of order (individual or organized group)
	 * @param visitorID the id of the boss visitor
	 * @param orderwascanceled 0 if was canceled 1 if don't work out
	 * @param visittime the time of the order
	 */
	public cancellationreport(String orderID,String parkname,Date visittime,String visitornum,String email,String address,String typeorder,
			String visitorID,int orderwascanceled) {
		this.address=address;
		this.email=email;
		this.orderID=orderID;
		this.orderwascanceled=orderwascanceled;
		this.parkname=parkname;
		this.typeorder=typeorder;
		this.visitorID=visitorID;
		this.visitornum=visitornum;
		this.visittime=visittime;
	}
	/**
	 * converts visittime to a string of correct format
	 * @return returns the formatted string
	 */
	public String visittimeTostring() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		return sdf.format(visittime);
	}
	/**
	 * get the type of cancellation 
	 * @return 0 if was canceled 1 if don't work out
	 */
	public int getOrderwascanceled() {
		return orderwascanceled;
	}
	/**
	 * set the type of cancellation (0 if was canceled 1 if don't work out)
	 * @param orderwascanceled  0 if was canceled 1 if don't work out
	 */
	public void setOrderwascanceled(int orderwascanceled) {
		this.orderwascanceled = orderwascanceled;
	}
	/**
	 * get the time of the order
	 * @return the time of the order
	 */
	public Date getVisittime() {
		return visittime;
	}
	/**
	 * get the time of the order
	 * @param visittime the time of the order
	 */
	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}
	/**
	 * get the email of the visitor
	 * @return the email of the visitor
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * set the email of the visitor
	 * @param email the email of the visitor
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/***
	 * get the number of the visitors
	 * @return the number of the visitors
	 */
	public String getVisitornum() {
		return visitornum;
	}
	/***
	 * set the number of the visitors
	 * @param visitornum the number of the visitors
	 */
	public void setVisitornum(String visitornum) {
		this.visitornum = visitornum;
	}
	/**
	 * get the type of order (individual or organized group)
	 * @return the type of order (individual or organized group)
	 */
	public String getTypeorder() {
		return typeorder;
	}
	/**
	 * set the type of order (individual or organized group)
	 * @param typeorder the type of order (individual or organized group)
	 */
	public void setTypeorder(String typeorder) {
		this.typeorder = typeorder;
	}
	/**
	 * get the address of the visitor
	 * @return the address of the visitor
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * set the address of the visitor
	 * @param address the address of the visitor
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * get the unique id order
	 * @return unique id order
	 */
	public String getOrderID() {
		return orderID;
	}
	/**
	 * set unique id order
	 * @param orderID unique id order
	 */
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	/**
	 * get the name of the park
	 * @return the name of the park
	 */
	public String getParkname() {
		return parkname;
	}
	/**
	 * set the name of the park
	 * @param parkname the name of the park
	 */
	public void setParkname(String parkname) {
		this.parkname = parkname;
	}
	/**
	 * get the id of the boss visitor
	 * @return the id of the boss visitor
	 */
	public String getVisitorID() {
		return visitorID;
	}
	/**
	 * set the id of the boss visitor
	 * @param visitorID the id of the boss visitor
	 */
	public void setVisitorID(String visitorID) {
		this.visitorID = visitorID;
	}
	
}
