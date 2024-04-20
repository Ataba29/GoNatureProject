package entities;

//import javafx.scene.control.Button;
import java.io.Serializable;

/**
 * function for drawing DM param update
 */
public class order implements Serializable{
    private String parkname;
    private String type;
    private String newvalue;
    //private Button button;
    
    /**
     * constructor
     * @param parkname park name
     * @param type type of param
     * @param newvalue new value
     */
    public order(String parkname, String type, String newvalue) {
        this.parkname = parkname;
        this.type = type;
        this.newvalue = newvalue;
        //this.button=new Button("approve");
    }
    /**
     * second constructor
     * @param ord takes button
     */
    public order(orderwithbutton ord) {
        this.parkname = ord.getParkname();
        this.type = ord.getType();
        this.newvalue = ord.getNewvalue();
        //this.button=new Button("approve");
    }
    /**
     * get the park name
     * @return park name
     */
    public String getParkname() {
        return parkname;
    }
    /**
     * set park name
     * @param parkname park name to set
     */
    public void setParkname(String parkname) {
        this.parkname = parkname;
    }
    /**
     * get type
     * @return return the type
     */
    public String getType() {
        return type;
    }
    /**
     * set the type
     * @param type return the type
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * get new value
     * @return return new value
     */
    public String getNewvalue() {
        return newvalue;
    }
    /**
     * set the new value
     * @param newvalue new value to set
     */
    public void setNewvalue(String newvalue) {
        this.newvalue = newvalue;
    }
   /* public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }*/
}