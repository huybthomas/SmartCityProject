package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.sim.SimLink;
import be.uantwerpen.sc.models.sim.SimMap;
import be.uantwerpen.sc.models.sim.SimPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Spliterators;

/**
 * Created by Thomas on 27/02/2016.
 */
public class MapBuilder{

    SimMap simMap;
    Queue<SimPoint> toProcessNodes;

    public MapBuilder(){
        simMap = new SimMap();
        buildMap();
    }

    private void buildMap() {
        int locX, locY = 0;

        //Current placeholder variables
        ArrayList<SimPoint> simPoints = new ArrayList<>();
        ArrayList<SimPoint> processedPoints = new ArrayList<>();
        ArrayList<SimLink> simLinks = new ArrayList<>();

        //Generate SimPoints from SQL Database
        //Get database from SQL (Possibly create connection)
        SimPoint tempPoint;
        //PLACEHOLDER
        while (SQLPoints.next()) {
            tempPoint = new SimPoint();
            //Fill tempPoint with variables
            simPoints.add(tempPoint);
        }
        SimLink tempLink;
        SimPoint tempStart, tempEnd;
        while (SQLLinks.next()) {
            //Process Link data from SQL

            //Generate Link
            tempLink = new SimLink(tempStart, tempEnd);
            simLinks.add(tempLink);
        }

        //Get 'random' simPoint from Array (First?)

        //Fill SimMap with SimPoints relative to the first one (and generate SimLinks)
        SimPoint simPoint = simPoints.get(0);
        simPoint.setPos(0, 0);
        locX = 0;
        locY = 0;

        boolean found = false;
        boolean normalLinkFound = false;
        boolean bendProcessing = false;
        boolean bendLinkFound = false;
        boolean lastResortProcessing = false;
        Iterator pointItr = simPoints.iterator();
        //Iterate until all points have been processed fully
        while (!(simPoints.isEmpty())) {
            //Iterate all points, then remove them if they have no links left
            while (pointItr.hasNext()) {

                //As long as we find links with the node; process its links
                do {
                    found = false;
                    normalLinkFound = false;
                    //Iterate all links in search for link with first node
                    Iterator itr = simLinks.iterator();
                    while (itr.hasNext()) {
                        SimLink link = (SimLink) itr.next();
                        if (link.getStart().getId() == simPoint.getId()) {

                            if (Start.Dir = End.Dir) //PLACEHOLDER
                                normalLinkFound = true;
                            else if (lastResortProcessing) {
                                //Make an educated guess
                                //Link Processed -> remove
                                itr.remove();
                                found = true;
                            } else if (bendProcessing) {
                                //Process Bend Logic
                                //Process link
                                //if Start and End Already have coordinates (Check processedPoints)
                                //Place link bend on their intersection
                                //bendLinkFound = true;
                                //else
                                //Continue searching
                                //Link Processed -> remove
                                itr.remove();
                                found = true;
                            } else {
                                //Normal link processing
                                //Process link
                                //Calculate direction of Link
                                //Set coordinates for nex SimPoint
                                //Link Processed -> remove
                                itr.remove();
                                found = true;
                            }
                        }
                    }
                } while (found);
            }
            if (!normalLinkFound && !bendProcessing) {
                bendProcessing = true;
            }
            if (!bendLinkFound && !lastResortProcessing) {
                lastResortProcessing = true;
            }
        }

        //Reset Iterator
        pointItr = simPoints.iterator();

        //Start with all normal links (i.e. no turns)
        //Generate corners last from already existing data as best as possible

        //Add the coordiates of the most negative (-x, -y) point to all other SimPoints
        //(Makes the map completely positive again)

    }

}
