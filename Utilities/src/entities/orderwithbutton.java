package entities;

import javafx.scene.control.Button;

/**
 * buttons for the update paramaters for DM
 */
public class orderwithbutton {
	private String parkname;
	private String type;
	private String newvalue;
	private Button button1y;
	private Button button2n;

	/**
	 * constructor
	 * 
	 * @param ord order line in table
	 */
	public orderwithbutton(order ord) {
		parkname = ord.getParkname();
		type = ord.getType();
		newvalue = ord.getNewvalue();
		this.button1y = new Button("approve");
		this.button2n = new Button("reject");
	}

	/**
	 * get park name
	 * 
	 * @return
	 */
	public String getParkname() {
		return parkname;
	}

	/**
	 * set park name
	 * 
	 * @param parkname
	 */
	public void setParkname(String parkname) {
		this.parkname = parkname;
	}

	/**
	 * get type
	 * 
	 * @return type type to get
	 */
	public String getType() {
		return type;
	}

	/**
	 * set type
	 * 
	 * @param type type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * get new value
	 * 
	 * @return new value
	 */
	public String getNewvalue() {
		return newvalue;
	}

	/**
	 * set new value
	 * 
	 * @param newvalue new value to set
	 */
	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}

	/**
	 * button to get
	 * 
	 * @return button
	 */
	public Button getButton1y() {
		return button1y;
	}

	/**
	 * set button
	 * 
	 * @param button1y button to set
	 */
	public void setButton1y(Button button1y) {
		this.button1y = button1y;
	}

	/**
	 * get second button
	 * 
	 * @return button to get
	 */
	public Button getButton2n() {
		return button2n;
	}

	/**
	 * set second button
	 * 
	 * @param button2n button to set
	 */
	public void setButton2n(Button button2n) {
		this.button2n = button2n;
	}
}
