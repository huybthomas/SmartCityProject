package be.uantwerpen.sc.tools;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Arthur on 28/04/2016.
 */
public class NavigationParser {

    List<Vertex> list;
    Queue<DriveDir> commands = new LinkedList<DriveDir>();

    public NavigationParser(List<Vertex> list){
        this.list = list;
    }

    public Queue<DriveDir> parseMap(){
        if(list.isEmpty()){
            Terminal.printTerminalError("Cannot parse empty map");
        }else{
            //First part is always driving forward.
            commands.add(new DriveDir(DriveDirEnum.FOLLOW));
            //Second part is parsing the rest of the map
            Vertex current = list.get(0);
            Vertex previous = list.get(0);
            Vertex next = list.get(1);
            for(int i = 2; i < list.size(); i++){
                previous = current;
                current = next;
                next = list.get(i);
                direction start = findStartDir(current);
                direction stop = findStopDir(next);
                DriveDir relDir = getNextRelDir(start, stop);
                //Pass crossroad
                commands.add(relDir);
                //Drive followLine
                commands.add(new DriveDir(DriveDirEnum.FOLLOW));
            }
        }
        return commands;
    }

    private direction findStartDir(Vertex current){
        int i = current.getPrevious().getAdjacencies().indexOf(current);
        String dirString = current.getPrevious().getAdjacencies().get(i).getLinkEntity().getStopDirection();
        direction dir = getDirection(dirString);
        return dir;
    }

    private direction findStopDir(Vertex next){
        int i = next.getPrevious().getAdjacencies().indexOf(next);
        String dirString = next.getPrevious().getAdjacencies().get(i).getLinkEntity().getStartDirection();
        direction dir = getDirection(dirString);
        return dir;
    }

    private direction getDirection(String dirString){
        switch(dirString){
            case "N":
                return direction.NORTH;
            case "E":
                return direction.EAST;
            case "S":
                return direction.SOUTH;
            case "W":
                return direction.WEST;
            default:
                return direction.NORTH;
        }
    }

    private DriveDir getNextRelDir(direction startDir, direction stopDir){
        //Calculate relative direction
        switch(startDir)
        {
            //From NORTH
            case NORTH:
                switch(stopDir)
                {
                    //Go EAST
                    case EAST:
                        return new DriveDir(DriveDirEnum.LEFT);   //Turn LEFT
                    //Go SOUTH
                    case SOUTH:
                        return new DriveDir(DriveDirEnum.FORWARD);   //Go STRAIGHT
                    //Go WEST
                    case WEST:
                        return new DriveDir(DriveDirEnum.RIGHT);   //Turn RIGHT
                    
                }
                
            //From EAST
            case EAST:
                switch(stopDir)
                {
                    //Go NORTH
                    case NORTH:
                        return new DriveDir(DriveDirEnum.RIGHT);   //Turn RIGHT
                    //Go SOUTH
                    case SOUTH:
                        return new DriveDir(DriveDirEnum.LEFT);   //Turn LEFT
                    //Go WEST
                    case WEST:
                        return new DriveDir(DriveDirEnum.FORWARD);   //Go STRAIGHT
                }
                
            //From SOUTH
            case SOUTH:
                switch(stopDir)
                {
                    //Go NORTH
                    case NORTH:
                        return new DriveDir(DriveDirEnum.FORWARD);   //Go STRAIGHT
                    //Go EAST
                    case EAST:
                        return new DriveDir(DriveDirEnum.RIGHT);   //Turn RIGHT
                    //Go WEST
                    case WEST:
                        return new DriveDir(DriveDirEnum.LEFT);   //Turn LEFT
                    
                }
                
            //From WEST
            case WEST:
                switch(stopDir)
                {
                    //Go NORTH
                    case NORTH:
                        return new DriveDir(DriveDirEnum.LEFT);   //Turn LEFT
                    
                    //Go EAST
                    case EAST:
                        return new DriveDir(DriveDirEnum.FORWARD);   //Go STRAIGHT
                    
                    //Go SOUTH
                    case SOUTH:
                        return new DriveDir(DriveDirEnum.RIGHT);   //Turn RIGHT
                    
                }
                
        }

        //Invalid direction
        return null;
    }
}

enum direction{
    NORTH,
    EAST,
    SOUTH,
    WEST
}