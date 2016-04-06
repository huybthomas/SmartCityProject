package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.sim.SimLink;
import be.uantwerpen.sc.models.sim.SimMap;
import be.uantwerpen.sc.models.sim.SimPoint;

import java.util.*;

enum ProcessingType{
    STRAIGHT,
    BEND,
    ADVANCED,
}

enum Update{
    UP,
    RIGHT
}

//TODO If a road is already detected at a location -> upgrade road to intersection
public class MapBuilder{

    SimMap simMap;

    ArrayList<LinkEntity> linkEntities;
    ArrayList<PointEntity> pointEntities;

    //Keep track of point locations
    ArrayList<SimPoint> simPoints = new ArrayList<>();

    int locX, locY = 0;
    int currSizeX = 1;
    int currSizeY = 1;

    public MapBuilder(LinkEntity[] linkEntities, PointEntity[] pointEntities){

        this.linkEntities = new ArrayList<LinkEntity>(Arrays.asList(linkEntities));
        this.pointEntities = new ArrayList<PointEntity>(Arrays.asList(pointEntities));

        simMap = new SimMap();
        buildMap();
    }

    public SimMap getSimMap(){
        return simMap;
    }

    public void logMap(){
        Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
        while(iterator.hasNext()){
            ArrayList<Tile> second = iterator.next();
            Iterator<Tile> iterator1 = second.iterator();
            while(iterator1.hasNext()){
                Tile tile = iterator1.next();
                if(tile == null){
                    System.out.print(" ");
                }else {
                    switch (tile) {
                        case POINT:
                            System.out.print("P");
                            break;
                        case HORIZONTAL:
                            System.out.print("-");
                            break;
                        case VERTICAL:
                            System.out.print("|");
                            break;
                        default:
                            System.out.print("ERR");
                    }
                }
            }
            System.out.println();

        }
    }

    private int buildMap(){

        ProcessingType processingType = ProcessingType.STRAIGHT;

        Iterator<LinkEntity> linkIterator = linkEntities.iterator();
        boolean firstFound = false;
        while(linkIterator.hasNext() && firstFound == false){
            LinkEntity link = linkIterator.next();
            if(processFirstLink(link, processingType)){
                firstFound = true;
            }
        }

        //Process other links //TODO merge with above
        boolean nextMode = true;
        while(!linkEntities.isEmpty()){
            //Links need to be processed    //Reset LinkIterator
            linkIterator = linkEntities.iterator();
            nextMode = true;
            while(linkIterator.hasNext()){
                LinkEntity link = linkIterator.next();
                //Check if we can process this link with the current processing type
                if(canProcessWithCurrentProcessingType(link, processingType)){
                    //Removes link on success, thus, we need to start linkIterator again    //TODO Find alternative
                    processLink(link, processingType);
                    linkIterator = linkEntities.iterator();
                    nextMode = false;
                }
            }
            if(nextMode){
                switch (processingType){
                    case STRAIGHT:
                        processingType = ProcessingType.BEND;
                        break;
                    case BEND:
                        processingType = ProcessingType.ADVANCED;
                        break;
                    default:
                        processingType = processingType.ADVANCED;
                        //System.err.println("NextMode selection error?");
                }
            }
        }

        return 0;
    }

    private boolean canProcessWithCurrentProcessingType(LinkEntity link, ProcessingType type){
        switch(type){
            case STRAIGHT:
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "NORTH")  // Vertical
                    return true;
                if(link.getStartDirection() == "WEST" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "WEST")      //Horizontal
                    return true;
                break;
            case BEND:  //Both start and stop are already on map
                SimPoint point = new SimPoint(link.getStartId().getPid());
                SimPoint point2 = new SimPoint(link.getStopId().getPid());
                //check if start already on map
                if(!(simPoints.contains(point)) || !(simPoints.contains(point2)))
                    return false;

                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "WEST" || link.getStartDirection() == "WEST" && link.getStopDirection() == "NORTH")     // clockwise
                    return true;
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "WEST" || link.getStartDirection() == "WEST" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "NORTH")      //counter-clockwise
                    return true;

            case ADVANCED:
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "WEST" || link.getStartDirection() == "WEST" && link.getStopDirection() == "NORTH")     // clockwise
                    return true;
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "WEST" || link.getStartDirection() == "WEST" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "NORTH")      //counter-clockwise
                    return true;
                break;
        }
        return false;
    }

    private boolean processFirstLink(LinkEntity link, ProcessingType type){
        SimPoint point = new SimPoint(link.getStartId().getPid(), locX, locY);
        //Add first point to map
        simPoints.add(point);
        //Add link's start point to map
        simMap.mapTiles.get(0).set(0, Tile.POINT);

        return processLink(link, type);
    }

    private boolean processLink(LinkEntity link, ProcessingType type){
        SimPoint point = new SimPoint(link.getStartId().getPid(), locX, locY);
        SimPoint point2 = new SimPoint(link.getStopId().getPid(), locX, locY);
        boolean swap = false;
        //check if start already on map
        if(!(simPoints.contains(point))){   //If starting point of link not yet in list -> check if end is in list
            if(!simPoints.contains(point2)) {   //End point also not located on map -> Try again later
                return false;
            }else{
                //Point already in list and thus already on map
                //We know we can process -> switch current locX and locY to preexisting point
                point = simPoints.get(simPoints.indexOf(point2));
                locX = point.getPosX();
                locY = point.getPosY();
                swap = true;
            }
        }else{
            //Point already in list and thus already on map
            //We know we can process -> switch current locX and locY to preexisting point
            point = simPoints.get(simPoints.indexOf(point));
            locX = point.getPosX();
            locY = point.getPosY();
        }

        switch(type){
            case STRAIGHT:  //Only one point needs to exist already
                switch (link.getStartDirection()){
                    case "NORTH":
                        if(swap)
                            addTileBelow(link.getLength());
                        else
                            addTileAbove(link.getLength());
                        break;
                    case "EAST":
                        if(swap)
                            addTileLeft(link.getLength());
                        else
                            addTileRight(link.getLength());
                        break;
                    case "SOUTH":
                        if(swap)
                            addTileAbove(link.getLength());
                        else
                            addTileBelow(link.getLength());
                        break;
                    case "WEST":
                        if(swap)
                            addTileRight(link.getLength());
                        else
                            addTileLeft(link.getLength());
                        break;
                }
                break;
            case BEND:  //Both points need to exist

                //Calculate intersection
                int[] intersect = findIntersect(link);
                //Add info to map
                int distVect = locY - intersect[1];
                int distHor = locX - intersect[0];
                int distVert2 = intersect[1] - intersect[3];
                int distHor2 = intersect[0] - intersect[2];
                //Add route to intersect
                if(link.getStartDirection()=="NORTH" || link.getStartDirection() == "SOUTH"){
                    addTileVertical(distVect, true);
                    locX = intersect[0];
                    locY = intersect[1];
                    addTileHorizontal(distHor2, true);
                }else{
                    addTileHorizontal(distHor, true);
                    locX = intersect[0];
                    locY = intersect[1];
                    addTileVertical(distVert2, true);
                }
                //add route from intersect to end

                break;
            case ADVANCED:  //Only one point needs to exist
                //TODO Advanced
                break;
        }
        //Add endpoint to simPoints
        SimPoint endPoint = new SimPoint(link.getStopId().getPid(), locX, locY);
        simPoints.add(endPoint);
        //Remove link from LinkEntities
        linkEntities.remove(link);
        return true;
    }

    private int[] findIntersect(LinkEntity link){
        int[] intersect = {-1,-1,-1,-1};

        int startId = link.getStartId().getPid();
        SimPoint startPoint = new SimPoint(startId);
        int startIndex = simPoints.indexOf(startPoint);
        startPoint = simPoints.get(startIndex);

        int stopId = link.getStopId().getPid();
        SimPoint stopPoint = new SimPoint(stopId);
        int stopIndex = simPoints.indexOf(stopPoint);
        stopPoint = simPoints.get(stopIndex);

        if (link.getStartDirection().equals("NORTH") || link.getStartDirection().equals("SOUTH")) {
            intersect[0] = startPoint.getPosX();
            intersect[1] = stopPoint.getPosY();

        } else if (link.getStartDirection().equals("EAST") || link.getStartDirection().equals("WEST")) {
            intersect[1] = startPoint.getPosY();
            intersect[0] = stopPoint.getPosY();
        }
        intersect[2] = stopPoint.getPosX();
        intersect[3] = stopPoint.getPosY();

        return intersect;
    }

    private void updateSimPointLocations(Update update){
        Iterator<SimPoint> it = simPoints.iterator();
        while(it.hasNext()) {
            SimPoint point = it.next();
            switch (update) {
                case UP:
                    point.up();
                    break;
                case RIGHT:
                    point.right();
                    break;
            }
        }
    }
    /*private void updateSimPointLocations(Update update, int constraint){
        Iterator<SimPoint> it = simPoints.iterator();
        SimPoint point = it.next();
        while(it.hasNext()) {
            switch (update) {
                case UP:
                    if(point.getPosY() >= constraint)
                        point.up();
                    break;
                case RIGHT:
                    if(point.getPosX() >= constraint)
                        point.right();
                    break;
            }
        }
    }*/

    private void addTileVertical(int length, boolean noPoint){
        boolean up = true;
        if(length < 0) {
            up = false;
            length = -length;
        }
        //Create road up untill the last 'length'
        if(up) {
            while (length > 1) {
                addTileAbove(Tile.VERTICAL);
                length--;
            }
            if(!noPoint)
                addTileAbove(Tile.POINT);
            else
                addTileAbove(Tile.VERTICAL);
        }else{
            while (length > 1) {
                addTileBelow(Tile.VERTICAL);
                length--;
            }
            if(!noPoint)
                addTileBelow(Tile.POINT);
            else
                addTileBelow(Tile.VERTICAL);
        }

    }

    private void addTileAbove(int length){
        //Create road up untill the last 'length'
        while(length > 1){
            addTileAbove(Tile.VERTICAL);
            length--;
        }
        addTileAbove(Tile.POINT);
    }

    //TODO merge addTileAbove and below -> Same for Left and Right
    private void addTileAbove(Tile tile){
        if(locY == 0){
            simMap.mapTiles.add(0, new ArrayList<Tile>());  //Add new arraylist at first position
            //Update all point's location data
            updateSimPointLocations(Update.UP);
            //Fill new arrayList untill equal in size to current map
            for(int i = 0; i< currSizeX; i++){
                if(locX != i)
                    simMap.mapTiles.get(0).add(null);
                else{
                    simMap.mapTiles.get(0).add(tile);
                }
            }
            currSizeY++;
        }else{
            simMap.mapTiles.get(locY).set(locX, tile);
            locY--;
        }

    }

    private void addTileBelow(int length) {
        //Create road up untill the last 'length'
        while (length > 1) {
            addTileBelow(Tile.VERTICAL);
            length--;
        }
        addTileBelow(Tile.POINT);
    }

    private void addTileBelow(Tile tile){
        if(locY == currSizeY-1){
            simMap.mapTiles.add(new ArrayList<Tile>()); //Add new arraylist
            //No need to update simPoints
            currSizeY++;
            //Fill new arrayList untill equal in size to current map
            for(int i = 0; i< currSizeX; i++){
                if(locX != i)
                    simMap.mapTiles.get(currSizeY-1).add(null);
                else
                    simMap.mapTiles.get(currSizeY-1).add(tile);
            }
        }else{
            simMap.mapTiles.get(locY+1).set(locX, tile);
        }
        locY++;
    }

    private void addTileHorizontal(int length, boolean noPoint){
        boolean right = false;
        if(length < 0) {
            right = true;
            length = -length;
        }
        //Create road up untill the last 'length'
        if(right){
            while (length > 1) {
                addTileRight(Tile.HORIZONTAL);
                length--;
            }
            if(!noPoint)
                addTileRight(Tile.POINT);
            else
                addTileRight(Tile.HORIZONTAL);
        }else{
            while (length > 1) {
                addTileLeft(Tile.HORIZONTAL);
                length--;
            }
            if(!noPoint)
                addTileLeft(Tile.POINT);
            else
                addTileLeft(Tile.HORIZONTAL);
        }


    }

    private void addTileLeft(int length) {
        //Create road up untill the last 'length'
        while (length > 1) {
            addTileLeft(Tile.HORIZONTAL);
            length--;
        }
        addTileLeft(Tile.POINT);
    }

    private void addTileLeft(Tile tile){
        if(locX == 0){  //Add one element at start of every every row
            int i = -1;
            Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
            while(iterator.hasNext()) {
                ArrayList<Tile> rows = iterator.next();
                i++;
                if (i == locX) {
                    rows.add(0, tile);
                } else {
                    rows.add(0, null);
                }
            }
            updateSimPointLocations(Update.RIGHT);
            currSizeX++;
        }else {  //Add one element
            simMap.mapTiles.get(locY).set(locX, tile);
        }
        locX--;
    }

    private void addTileRight(int length) {
        //Create road up untill the last 'length'
        while (length > 1) {
            addTileRight(Tile.HORIZONTAL);
            length--;
        }
        addTileRight(Tile.POINT);
    }

    private void addTileRight(Tile tile){
        if(locX == currSizeX-1){   //Add one element at end of every row
            int i = -1;
            Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
            while(iterator.hasNext()) {
                ArrayList<Tile> rows = iterator.next();
                i++;
                if (i == locY) {
                    rows.add(tile);
                } else {
                    rows.add(null);
                }
            }
            currSizeX++;
        }else{  //Add one element
            simMap.mapTiles.get(locY).set(locX, tile);
        }
        locX++;


    }

}
