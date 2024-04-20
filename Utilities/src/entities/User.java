package entities;

import java.io.Serializable;

/**
 * the user class represent the user in the DB
 */
public class User implements Serializable {

	private static final long serialVersionUID = 2L;
	private String employeeID;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private String role;
	private String parkname;
	private int isLogged;
	
	
	/**
	 * constructor for the user class
	 * @param employeeID the ID of the user
	 * @param firstname the first name of the user
	 * @param lastname the last name of the user
	 * @param username the userName of the user
	 * @param password the password of the user
	 * @param email the email address of the user
	 * @param phonenumber the phone number of the user
	 * @param role the role of the user {'departmentManager','parkManager','worker','serviceRrepresentative'}
	 * @param parkname the park that the user works in (if can access all then 'all')
	 * @param isLogged is the user logged in 
	 */
	public User(final String employeeID, final String firstname, final String lastname, final String username, 
			final String password, final String email, final String phonenumber, final String role, 
			final String parkname, final int isLogged) 
	{
		super();
		this.employeeID = employeeID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.phonenumber = phonenumber;
		this.role = role;
		this.parkname = parkname;
		this.isLogged = isLogged;
	}

	/**
	 * get the employeeID
	 * @return returns the employeeID
	 */
	public String getEmployeeID() {
		return employeeID;
	}
	/**
	 * set the employeeID
	 * @param employeeID employeeID to set
	 */
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	/**
	 * gets the firstname
	 * @return returns firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * sets the firstname
	 * @param firstname firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * gets lastname
	 * @return returns the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * sets the Lastname
	 * @param lastname Lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * gets the username
	 * @return username of user
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * sets the username
	 * @param username username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * gets the password
	 * @return password of user
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * sets the password
	 * @param password password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * get email
	 * @return email of user
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * sets the email
	 * @param email email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * gets the phonenumber
	 * @return phonenumber of user
	 */
	public String getPhonenumber() {
		return phonenumber;
	}
	/**
	 * sets the phonenumber
	 * @param phonenumber phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	/**
	 * role to get
	 * @return role of user
	 */
	public String getRole() {
		return role;
	}
	/**
	 * sets the role
	 * @param role role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
	/**
	 * gets the parkname
	 * @return the parkname is returned
	 */
	public String getParkname() {
		return parkname;
	}
	/**
	 * sets the parkname
	 * @param parkname parkname to set
	 */
	public void setParkname(String parkname) {
		this.parkname = parkname;
	}
	/**
	 * gets if user is Logged
	 * @return returns user logged status
	 */
	public int getIsLogged() {
		return isLogged;
	}
	/**
	 * sets the user isLogged 
	 * @param isLogged isLogged sets
	 */
	public void setIsLogged(int isLogged) {
		this.isLogged = isLogged;
	}
}
