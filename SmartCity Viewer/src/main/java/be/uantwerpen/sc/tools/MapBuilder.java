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
    int currSizeX = 0;
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

    private void logMap(){
        Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
        while(iterator.hasNext()){
            ArrayList<Tile> second = iterator.next();
            Iterator<Tile> iterator1 = second.iterator();
            while(iterator1.hasNext()){
                Tile tile = iterator1.next();
                switch(tile){
                    case POINT:
                        System.out.print("P");
                        break;
                    case HORIZONTAL:
                        System.out.print("-");
                        break;
                    case VERTICAL:
                        System.out.print("|");
                        break;
                }
            }
            System.out.println();

        }
    }

    private int buildMap(){

        ProcessingType processingType = ProcessingType.STRAIGHT;

        //Links need to be processed
        Iterator<LinkEntity> linkIterator = linkEntities.iterator();

        if(!linkIterator.hasNext()) //No links
            return -1;

        //Process first link
        LinkEntity link = linkIterator.next();
        //Check if we can process this link with the current processing type
        if(canProcessWithCurrentProcessingType(link, processingType)){
            processLink(link, processingType);
        }

        processLink(link, processingType);

        //Start with all normal links (i.e. no turns)
        //Generate corners last from already existing data as best as possible

        //Add the coordiates of the most negative (-x, -y) point to all other SimPoints
        //(Makes the map completely positive again)

        return 0;
    }

    private boolean canProcessWithCurrentProcessingType(LinkEntity link, ProcessingType type){
        boolean possible = false;
        switch(type){
            case STRAIGHT:
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "NORTH")  // Vertical
                    possible = true;
                if(link.getStartDirection() == "WEST" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "WEST")      //Horizontal
                    possible = true;
                break;
            case BEND:
            case ADVANCED:
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "WEST" || link.getStartDirection() == "WEST" && link.getStopDirection() == "NORTH")     // clockwise
                    possible = true;
                if(link.getStartDirection() == "NORTH" && link.getStopDirection() == "WEST" || link.getStartDirection() == "WEST" && link.getStopDirection() == "SOUTH" || link.getStartDirection() == "SOUTH" && link.getStopDirection() == "EAST" || link.getStartDirection() == "EAST" && link.getStopDirection() == "NORTH")      //counter-clockwise
                    possible = true;
                break;
        }
        return possible;
    }

    private void processLink(LinkEntity link, ProcessingType type){
        SimPoint point = new SimPoint(link.getStartId().getPid(), locX, locY);
        //If starting point of link not yet in list -> add      //This also means that this is the first point -> so locX and locY will be 0
        if(!(simPoints.contains(point))){
            simPoints.add(point);
            //Add link's start point to map
            simMap.mapTiles.get(locY).set(locX, Tile.POINT);
        }else{
            //Point already in list and thus already on map
            //We know we can process -> switch current locX and locY to preexisting point
            point = simPoints.get(simPoints.indexOf(point));
            locX = point.getPosX();
            locY = point.getPosY();
        }

        switch(type){
            case STRAIGHT:  //Only startpoint needs to exist already
                switch (link.getStartDirection()){
                    case "NORTH":
                        addTileAbove(link.getLength());
                        break;
                    case "EAST":
                        addTileRight(link.getLength());
                        break;
                    case "SOUTH":
                        addTileBelow(link.getLength());
                        break;
                    case "WEST":
                        addTileLeft(link.getLength());
                        break;
                }
                break;
            case BEND:  //Both start and Endpoint need to exist

                //Calculate intersection
                int[] intersect = findIntersect(link);
                //Add info to map
                int distVect = locY - intersect[1];
                int distHor = locX - intersect[0];
                if(link.getStartDirection()=="NORTH" || link.getStartDirection() == "SOUTH"){
                    addTileVertical(distVect, true);
                    addTileHorizontal(distHor, false);
                }else{
                    addTileHorizontal(distHor, true);
                    addTileVertical(distVect, false);
                }
                break;
            case ADVANCED:  //Only start needs to exist

                break;
        }
        //Add endpoint to simPoints
        SimPoint endPoint = new SimPoint(link.getStopId().getPid(), locX, locY);
        simPoints.add(endPoint);
    }

    private int[] findIntersect(LinkEntity link){
        int[] intersect = {-1,-1};

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

        return intersect;
    }

    private void updateSimPointLocations(Update update){
        Iterator<SimPoint> it = simPoints.iterator();
        SimPoint point = it.next();
        while(it.hasNext()) {
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
        if(length < 0)
            up = false;
        //Create road up untill the last 'length'
        if(up) {
            while (length > 0) {
                addTileAbove(Tile.VERTICAL);
            }
            if(!noPoint)
                addTileAbove(Tile.POINT);
            else
                addTileAbove(Tile.VERTICAL);
        }else{
            while (length > 0) {
                addTileBelow(Tile.VERTICAL);
            }
            if(!noPoint)
                addTileBelow(Tile.POINT);
            else
                addTileBelow(Tile.VERTICAL);
        }

    }

    private void addTileAbove(int length){
        //Create road up untill the last 'length'
        while(length > 0){
            addTileAbove(Tile.VERTICAL);
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
                    simMap.mapTiles.get(0).add(currSizeX, null);
                else{
                    simMap.mapTiles.get(0).set(locX, tile);
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
        while (length > 0) {
            addTileBelow(Tile.VERTICAL);
        }
        addTileBelow(Tile.POINT);
    }

    private void addTileBelow(Tile tile){
        if(locY == simMap.mapTiles.size()){
            simMap.mapTiles.add(new ArrayList<Tile>()); //Add new arraylist
            //No need to update simPoints
            currSizeY++;
            //Fill new arrayList untill equal in size to current map
            for(int i = 0; i< currSizeX; i++){
                if(locX != i)
                    simMap.mapTiles.get(currSizeY).add(currSizeX, null);
                else
                    simMap.mapTiles.get(currSizeY).set(locX, tile);
            }
        }else{
            simMap.mapTiles.get(currSizeY).set(locX, tile);
        }
        locY++;
    }

    private void addTileHorizontal(int length, boolean noPoint){
        boolean right = true;
        if(length < 0)
            right = false;
        //Create road up untill the last 'length'
        if(right){
            while (length > 0) {
                addTileRight(Tile.HORIZONTAL);
            }
            if(!noPoint)
                addTileRight(Tile.POINT);
            else
                addTileRight(Tile.HORIZONTAL);
        }else{
            while (length > 0) {
                addTileLeft(Tile.HORIZONTAL);
            }
            if(!noPoint)
                addTileLeft(Tile.POINT);
            else
                addTileLeft(Tile.HORIZONTAL);
        }


    }

    private void addTileLeft(int length) {
        //Create road up untill the last 'length'
        while (length > 0) {
            addTileLeft(Tile.HORIZONTAL);
        }
        addTileLeft(Tile.POINT);
    }

    private void addTileLeft(Tile tile){
        if(locX == 0){  //Add one element at start of every every row
            int i = -1;
            Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
            while(iterator.hasNext()) {
                ArrayList<Tile> second = iterator.next();
                i++;
                if (i == locX) {
                    second.add(0, tile);
                } else {
                    second.add(0, null);
                }
            }
            updateSimPointLocations(Update.RIGHT);
            currSizeX++;
        }else {  //Add one element
            int i = -1;
            Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
            while (iterator.hasNext()) {
                ArrayList<Tile> second = iterator.next();
                i++;
                if (i == locX) {
                    second.set(locX, tile);
                }
                //Simpoints does not need to be updated
                locX--;
            }
        }
    }

    private void addTileRight(int length) {
        //Create road up untill the last 'length'
        while (length > 0) {
            addTileRight(Tile.HORIZONTAL);
        }
        addTileRight(Tile.POINT);
    }

    private void addTileRight(Tile tile){
        if(locX == simMap.mapTiles.get(locY).size()){   //Add one element at end of every row
            int i = -1;
            Iterator<ArrayList<Tile>> iterator = simMap.mapTiles.iterator();
            while(iterator.hasNext()) {
                ArrayList<Tile> second = iterator.next();
                i++;
                if (i == locX) {
                    second.add(tile);
                } else {
                    second.add(null);
                }
            }
            currSizeX++;
        }else{  //Add one element
            simMap.mapTiles.get(locY).set(locX, tile);
        }


    }

}
