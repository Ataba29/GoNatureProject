package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * class represents the parkfulltimes in DB
 */
public class Parkfulltimes implements Serializable {

	private static final long serialVersionUID = -6636878666989405877L;
	private String parkname;
	private Date startTime;
	private Date endTime;
	private String day;
	private String hourstart;
	private String hourend;
	
	/**
	 * constructor for the class
	 * @param parkname the name of the park
	 * @param startTime the start time of the park
	 * @param endTime the end time of the park
	 */
	public Parkfulltimes(String parkname, Date startTime, Date endTime) {
		this.parkname = parkname;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	/**
	 * second constructor for the class
	 * @param parkname the name of the park
	 * @param day the day of the park 
	 * @param hourstart start hour
	 * @param hourend end hour
	 */
	public Parkfulltimes(String parkname,String day,String hourstart,String hourend) {
		this.parkname = parkname;
		this.day = day;
		this.hourstart = hourstart;
		this.hourend=hourend;
	}
	/**
	 * get day
	 * @return get day
	 */
	public String getday() {
		return day;
	}
	/**
	 * set day
	 * @param day day to set
	 */
	public void  setday(String day) {
		this.day=day;
	}
	/**
	 * get hour
	 * @return hour to get
	 */
	public String gethourstart() {
		return hourstart;
	}
	/**
	 * set hour
	 * @param hourstart hour to set
	 */
	public void  sethourstart(String hourstart) {
		this.hourstart=hourstart;
	}
	/**
	 * get hour end
	 * @return hour end
	 */
	public String gethourend() {
		return hourend;
	}
	/**
	 * set hour end
	 * @param hourend hour end to set
	 */
	public void  sethourend(String hourend) {
		this.hourend=hourend;
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
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * sets the start time
	 * @param startTime start time to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * gets the end time
	 * @return end time to return
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * sets the end time
	 * @param endTime end time to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * converts startTime to a string of correct format
	 * @return returns the formatted string
	 */
	public String startTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(startTime);
	}
	
	/**
	 * converts endTime to a string of correct format
	 * @return returns the formatted string
	 */
	public String endTimeToString() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(endTime);
	}
}
