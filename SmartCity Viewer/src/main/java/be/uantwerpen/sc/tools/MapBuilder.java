package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.sim.SimMap;

/**
 * Created by Thomas on 27/02/2016.
 */
public class MapBuilder{

    SimMap simMap;

    public MapBuilder(){
        simMap = new SimMap();
        buildMap();
    }

    private void buildMap(){
        int locX, locY = 0;

        //Get database from SQL

        //Generate SimPoints

        //Generate SimLinks

        //Take first standard link (i.e. no turns)
        //And give it coordinates

        //Repeat until no links left
        //For every standard link adjacent to current active Link
        //Add to simMap (with coordinates added (these will be relative coordinates))

    }

}
