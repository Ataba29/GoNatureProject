package entities;

import java.io.Serializable;

/**
 * the Park class represent the park in the DB
 */
public class Park implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String parkName;
	private int maxVisitorsNumber;
	private int currentVisitors;
	private int delayTime;
	private int diffVisitors;

	/**
	 * constructor for the user class
	 * 
	 * @param parkName          the name of the park
	 * @param maxVisitorsNumber the max visitor number that can be in the park at
	 *                          the same time
	 * @param currentVisitors   the number of current visitors in the park
	 * @param delayTime         the delay time of the park
	 * @param diffVisitors      the number of visitors that can be entry without
	 *                          previous order
	 */
	public Park(String parkName, int maxVisitorsNumber, int currentVisitors, int delayTime, int diffVisitors) {
		this.parkName = parkName;
		this.maxVisitorsNumber = maxVisitorsNumber;
		this.currentVisitors = currentVisitors;
		this.delayTime = delayTime;
		this.diffVisitors = diffVisitors;
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
	 * get the MaxVisitorsNumber
	 * 
	 * @return returns the MaxVisitorsNumber
	 */
	public int getMaxVisitorsNumber() {
		return maxVisitorsNumber;
	}

	/**
	 * set the MaxVisitorsNumber
	 * 
	 * @param MaxVisitorsNumber MaxVisitorsNumber to set
	 */
	public void setMaxVisitorsNumber(int maxVisitorsNumber) {
		this.maxVisitorsNumber = maxVisitorsNumber;
	}

	/**
	 * get the CurrentVisitors
	 * 
	 * @return returns the CurrentVisitors
	 */
	public int getCurrentVisitors() {
		return currentVisitors;
	}

	/**
	 * set the CurrentVisitors
	 * 
	 * @param CurrentVisitors CurrentVisitors to set
	 */
	public void setCurrentVisitors(int currentVisitors) {
		this.currentVisitors = currentVisitors;
	}

	/**
	 * get the DelayTime
	 * 
	 * @return returns the DelayTime
	 */
	public int getDelayTime() {
		return delayTime;
	}

	/**
	 * set the DelayTime
	 * 
	 * @param DelayTime DelayTime to set
	 */
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	/**
	 * get the DiffVisitors
	 * 
	 * @return returns the DiffVisitors
	 */
	public int getDiffVisitors() {
		return diffVisitors;
	}

	/**
	 * set the DiffVisitors
	 * 
	 * @param DiffVisitors DiffVisitors to set
	 */
	public void setDiffVisitors(int diffVisitors) {
		this.diffVisitors = diffVisitors;
	}

}
