package entities;

import java.io.Serializable;

/**
 * Class represents the visitors that are in the DB
 */
public class Visitors implements Serializable{

	private static final long serialVersionUID = -5237048827068879386L;
	private String visitorID;
	private String firstname;
	private String lastname;
	private String email;
	private String phonenumber;
	private String typevisitor;
	private int isLogged;
	
	/**
	 * Constructor for the class
	 * @param visitorID ID of visitor
	 * @param firstname first name of visitor
	 * @param lastname last name of visitor
	 * @param email email of visitor
	 * @param phonenumber phone number of visitor
	 * @param typevisitor the type of visitor
	 * @param isLogged is visitor logged 
	 */
	public Visitors(final String visitorID, final String firstname, final String lastname, 
						final String email, final String phonenumber, final String typevisitor, final int isLogged) 
	{
		this.visitorID = visitorID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phonenumber = phonenumber;
		this.typevisitor = typevisitor;
		this.isLogged = isLogged;
	}

	/**
	 * get the visitorID
	 * @return returns the visitorID
	 */
	public String getVisitorID() {
		return visitorID;
	}
	/**
	 * set the visitorID
	 * @param visitorID the visitorID to set
	 */
	public void setVisitorID(String visitorID) {
		this.visitorID = visitorID;
	}
	/**
	 * get firstname 
	 * @return firstname of visitor
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * set the firstname
	 * @param firstname firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 *  get the lastname
	 * @return the lastname of visitor
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * sets the lastname
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * get the email
	 * @return return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * set the email
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * get the phonenumber
	 * @return return the phonenumber
	 */
	public String getPhonenumber() {
		return phonenumber;
	}
	/**
	 * set the phonenumber
	 * @param phonenumber the phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	/**
	 * get the typevisitor
	 * @return return the type of visitor
	 */
	public String getTypevisitor() {
		return typevisitor;
	}
	/**
	 * set the typevisitor
	 * @param typevisitor the typevisitor to set
	 */
	public void setTypevisitor(String typevisitor) {
		this.typevisitor = typevisitor;
	}
	/**
	 * return the isLogged
	 * @return the user isLogged 
	 */
	public int getIsLogged() {
		return isLogged;
	}
	/**
	 * sets the isLogged
	 * @param isLogged the isLogged to set
	 */
	public void setIsLogged(int isLogged) {
		this.isLogged = isLogged;
	}
	
	
}
