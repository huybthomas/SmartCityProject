package be.uantwerpen.sc.tools;

/**
 * Created by Arthur on 28/04/2016.
 */
public class DriveDir {

    DriveDirEnum dir;

    public DriveDir(DriveDirEnum dir){
        this.dir = dir;
    }

    @Override
    public String toString(){
        switch(dir){
            case FORWARD:
                return "DRIVE FORWARD 120";
            case LEFT:
                return "DRIVE TURN L";
            case RIGHT:
                return "DRIVE TURN R";
            case FOLLOW:
                return "DRIVE FOLLOWLINE";
            default:
                return "MISSING";
        }
    }
}
