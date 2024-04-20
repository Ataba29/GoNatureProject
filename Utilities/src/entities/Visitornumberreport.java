package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class represent the table visitornumberreport in DB
 */
public class Visitornumberreport implements Serializable {

	private static final long serialVersionUID = -6666878666989405897L;
	private String parkname;
	private String visitornumberofinvidual;
	private String visitorsnumberoforganizedgroup;
	private String yearyear;
	private String monthmonth;


	/**
	 * constructor for the class
	 * @param parkname the name of the park
	 * @param startTime the start time of the park
	 * @param endTime the end time of the park
	 */
	public Visitornumberreport(String parkname,String visitornumberofinvidual, String visitorsnumberoforganizedgroup,String yearyear,String monthmonth) {
		this.parkname = parkname;
        this.visitornumberofinvidual=visitornumberofinvidual;
        this.visitorsnumberoforganizedgroup=visitorsnumberoforganizedgroup;
        this.yearyear=yearyear;
        this.monthmonth=monthmonth;

	}

	/**
	 * gets the park name
	 * @return returns park name
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
	 * gets the Date of start Time
	 * @return retuns the start time
	 */
	public String getvisitornumberofinvidual() {
		return visitornumberofinvidual;
	}

	/**
	 * sets the start time
	 * @param startTime start time to set
	 */
	public void setvisitornumberofinvidual(String visitornumberofinvidual) {
		this.visitornumberofinvidual = visitornumberofinvidual;
	}

	/**
	 * gets the end time
	 * @return end time to return
	 */
	public String getvisitorsnumberoforganizedgroup() {
		return visitorsnumberoforganizedgroup;
	}

	/**
	 * sets the end time
	 * @param endTime end time to set
	 */
	public void setvisitorsnumberoforganizedgroup(String visitorsnumberoforganizedgroup) {
		this.visitorsnumberoforganizedgroup = visitorsnumberoforganizedgroup;
	}
	
	/**
	 * get year
	 * @return year
	 */
	public String getyearyear() {
		return yearyear;
	}
	/**
	 * set year
	 * @param yearyear year to set
	 */
	public void setyearyear(String yearyear) {
		this.yearyear=yearyear;
	}
	
	/**
	 * get month
	 * @return month to get
	 */
	public String getmonthmonth() {
		return monthmonth;
	}
	/**
	 * set month
	 * @param monthmonth month to set
	 */
	public void setmonthmonth(String monthmonth) {
		this.monthmonth=monthmonth;
	}
}
