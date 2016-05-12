package be.uantwerpen.sc.services.javaCoreServices;

import org.springframework.stereotype.Service;

/**
 * Created by Arthur on 24/04/2016.
 */
@Service
public class DataService {

    private Long robotID;

    private float millis;

    public boolean robotBusy = false;

    public String trafficLightStatus;

    private String tag = "NO_TAG";
    private int currentLocation = -1;

    public Long getRobotID() {
        return robotID;
    }

    public void setRobotID(Long robotID) {
        this.robotID = robotID;
    }

    public int getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(int currentLocation) {
        this.currentLocation = currentLocation;
    }

    public float getMillis() {return millis;}
    public void setMillis(float millis) {this.millis = millis;}

    public String getTag() {return tag;}
    public void setTag(String tag) {this.tag = tag;}
}
