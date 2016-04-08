package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.sim.SimLink;
import be.uantwerpen.sc.models.sim.SimMap;
import be.uantwerpen.sc.models.sim.SimPoint;
import org.springframework.security.access.method.P;

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
                            System.out.print("X");
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
                    logMap();
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
                int distVert = locY - intersect[1];
                int distHor = locX - intersect[0];
                int distVert2 = intersect[1] - intersect[3];
                int distHor2 = intersect[0] - intersect[2];
                //Add route to intersect
                if(link.getStartDirection()=="NORTH" || link.getStartDirection() == "SOUTH"){
                    if(link.getStartDirection()=="NORTH") {
                        if (link.getStopDirection() == "EAST") {
                            addTileVertical(distVert, Tile.SOUTH_WEST);
                        } else {   //WEST
                            addTileVertical(distVert, Tile.SOUTH_EAST);
                        }
                    }else{  //SOUTH
                        if (link.getStopDirection() == "EAST") {
                            addTileVertical(distVert, Tile.NORTH_WEST);
                        } else {   //WEST
                            addTileVertical(distVert, Tile.NORTH_EAST);
                        }
                    }
                    locX = intersect[0];
                    locY = intersect[1];
                    addTileHorizontal(distHor2, Tile.POINT);
                }else{
                    if(link.getStartDirection()=="EAST"){
                        if (link.getStopDirection() == "NORTH") {
                            addTileVertical(distHor, Tile.SOUTH_WEST);
                        } else {   //SOUTH
                            addTileVertical(distHor, Tile.NORTH_WEST);
                        }
                    }else{  //WEST
                        if (link.getStopDirection() == "NORTH") {
                            addTileVertical(distHor, Tile.SOUTH_EAST);
                        } else {   //SOUTH
                            addTileVertical(distHor, Tile.NORTH_EAST);
                        }
                    }
                    locX = intersect[0];
                    locY = intersect[1];
                    if(distVert2 > 0){
                        locY++;
                        distVert2--;
                    }else{
                        locY--;
                        distVert2--;
                    }
                    addTileVertical(distVert2, Tile.POINT);
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

    private void addTileVertical(int length, Tile tile){
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
            addTileAbove(tile);

        }else{
            while (length > 1) {
                addTileBelow(Tile.VERTICAL);
                length--;
            }
            addTileBelow(tile);
        }
    }

    private void addTileHorizontal(int length, Tile tile){
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
            addTileRight(tile);

        }else{
            while (length > 1) {
                addTileLeft(Tile.HORIZONTAL);
                length--;
            }
            addTileLeft(tile);
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
            locY--;
            updateTile(simMap.mapTiles.get(locY).get(locX), tile);

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
        locY++;
        if(locY > currSizeY-1){
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
            updateTile(simMap.mapTiles.get(locY).get(locX), tile);
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
            locX--;
            updateTile(simMap.mapTiles.get(locY).get(locX), tile);
        }

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
        locX++;
        if(locX > currSizeX-1){   //Add one element at end of every row
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
            updateTile(simMap.mapTiles.get(locY).get(locX), tile);
        }
    }

    private void updateTile(Tile originalTile, Tile newTile){
        if(newTile == Tile.POINT){
            simMap.mapTiles.get(locY).set(locX, Tile.POINT);
            return;
        }else if(newTile == Tile.INTERSECT){
            simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
            return;
        }else if(originalTile == Tile.INTERSECT){
            return;
        }

        if(originalTile == null){
            simMap.mapTiles.get(locY).set(locX, newTile);
            return;
        }

        switch(originalTile){
            case POINT:
                //Tile stays a point
                break;
            case VERTICAL:
                switch(newTile){
                    case HORIZONTAL:
                    case NORTH_EAST_WEST:
                    case SOUTH_WEST_EAST:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    case NORTH_EAST:
                    case NORTH_WEST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.WEST_NORTH_SOUTH);
                        break;
                    case SOUTH_EAST:
                    case SOUTH_WEST:
                    case EAST_SOUTH_NORTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.EAST_SOUTH_NORTH);
                        break;
                    default:
                        //Do nothing
                }
                break;
            case HORIZONTAL:
                switch(newTile){
                    case VERTICAL:
                    case EAST_SOUTH_NORTH:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    case NORTH_EAST:
                    case NORTH_WEST:
                    case NORTH_EAST_WEST:
                        simMap.mapTiles.get(locY).set(locX, Tile.NORTH_EAST_WEST);
                        break;
                    case SOUTH_EAST:
                    case SOUTH_WEST:
                    case SOUTH_WEST_EAST:
                        simMap.mapTiles.get(locY).set(locX, Tile.SOUTH_WEST_EAST);
                        break;
                    default:
                        //Do nothing
                }
            case NORTH_EAST:
                switch (newTile){
                    case VERTICAL:
                    case SOUTH_EAST:
                    case EAST_SOUTH_NORTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.EAST_SOUTH_NORTH);
                        break;
                    case HORIZONTAL:
                    case NORTH_WEST:
                    case NORTH_EAST_WEST:
                        simMap.mapTiles.get(locY).set(locX, Tile.NORTH_EAST_WEST);
                        break;
                    case SOUTH_WEST:
                    case SOUTH_WEST_EAST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //do nothing
                }
            case NORTH_WEST:
                switch(newTile){
                    case VERTICAL:
                    case SOUTH_WEST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.WEST_NORTH_SOUTH);
                        break;
                    case HORIZONTAL:
                    case NORTH_EAST:
                    case NORTH_EAST_WEST:
                        simMap.mapTiles.get(locY).set(locX, Tile.NORTH_EAST_WEST);
                        break;
                    case SOUTH_EAST:
                    case EAST_SOUTH_NORTH:
                    case SOUTH_WEST_EAST:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //Do nothing
                }
                break;
            case SOUTH_EAST:
                switch (newTile){
                    case VERTICAL:
                    case NORTH_EAST:
                    case EAST_SOUTH_NORTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.EAST_SOUTH_NORTH);
                        break;
                    case HORIZONTAL:
                    case SOUTH_WEST:
                    case SOUTH_WEST_EAST:
                        simMap.mapTiles.get(locY).set(locX, Tile.SOUTH_WEST_EAST);
                        break;
                    case NORTH_WEST:
                    case NORTH_EAST_WEST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //do nothing
                }
                break;
            case SOUTH_WEST:
                switch(newTile){
                    case VERTICAL:
                    case NORTH_WEST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.WEST_NORTH_SOUTH);
                        break;
                    case HORIZONTAL:
                    case SOUTH_EAST:
                    case SOUTH_WEST_EAST:
                        simMap.mapTiles.get(locY).set(locX, Tile.SOUTH_WEST_EAST);
                        break;
                    case NORTH_EAST:
                    case NORTH_EAST_WEST:
                    case EAST_SOUTH_NORTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        ///do nothing
                }
                break;
            case NORTH_EAST_WEST:
                switch (newTile){
                    case VERTICAL:
                    case SOUTH_EAST:
                    case SOUTH_WEST:
                    case EAST_SOUTH_NORTH:
                    case SOUTH_WEST_EAST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //do nothing
                }
                break;
            case EAST_SOUTH_NORTH:
                switch (newTile){
                    case HORIZONTAL:
                    case NORTH_WEST:
                    case SOUTH_WEST:
                    case NORTH_EAST_WEST:
                    case SOUTH_WEST_EAST:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //do nothing
                }
                break;
            case SOUTH_WEST_EAST:
                switch (newTile){
                    case VERTICAL:
                    case NORTH_EAST:
                    case NORTH_WEST:
                    case NORTH_EAST_WEST:
                    case EAST_SOUTH_NORTH:
                    case WEST_NORTH_SOUTH:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //do nothing
                }
                break;
            case WEST_NORTH_SOUTH:
                switch (newTile){
                    case HORIZONTAL:
                    case NORTH_EAST:
                    case SOUTH_EAST:
                    case NORTH_EAST_WEST:
                    case EAST_SOUTH_NORTH:
                    case SOUTH_WEST_EAST:
                        simMap.mapTiles.get(locY).set(locX, Tile.INTERSECT);
                        break;
                    default:
                        //do nothing
                }
                break;
            default:
                //do nothing
                break;
        }
    }

}
