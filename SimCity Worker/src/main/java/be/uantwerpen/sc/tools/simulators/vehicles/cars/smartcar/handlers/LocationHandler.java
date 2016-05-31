package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.handlers;

import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map.Link;
import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map.Map;
import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map.Node;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.Iterator;

/**
 * Created by Thomas on 28/05/2016.
 */
public class LocationHandler
{
    private int currentLocation;
    private int destinationLocation;
    private double destinationDistance;
    private double travelledDistance;
    private Map map;
    private boolean onMap;
    private Direction currentDirection;
    private Direction destinationDirection;
    private boolean followLine;

    private String serverIP;
    private int serverPort;

    public enum Direction
    {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    public LocationHandler()
    {
        this.currentLocation = -1;
        this.destinationLocation = -1;
        this.destinationDistance = 0L;
        this.travelledDistance = 0L;
        this.map = null;
        this.onMap = false;
        this.currentDirection = Direction.NORTH;
        this.destinationDirection = Direction.NORTH;
        this.followLine = false;

        this.serverIP = "localhost";
        this.serverPort = 1994;
    }

    public LocationHandler(String serverIP, int serverPort)
    {
        this();

        this.serverIP = serverIP;
        this.serverPort = serverPort;
    }

    public boolean initLocationHandler(int startPosition)
    {
        try
        {
            this.map = this.getMap();
        }
        catch(Exception e)
        {
            System.err.println("Could not connect to SmartCity Core for map!");
            e.printStackTrace();

            return false;
        }

        if(this.map == null)
        {
            System.err.println("Could not retrieve map from SmartCity Core!");

            return false;
        }

        //Find node if existing
        boolean found = false;
        Iterator<Node> it = this.map.getNodeList().iterator();
        Node startNode = null;

        while(it.hasNext() && ! found)
        {
            Node node = it.next();

            if(node.getNodeId() == startPosition)
            {
                startNode = node;
                found = true;
            }
        }

        //Node needs at least one neighbour to navigate
        if(found && startNode.getNeighbours().size() > 0)
        {
            //Determine destination location (bot starts on edge point)
            try
            {
                this.currentDirection = parseDirection(startNode.getNeighbours().get(0).getStartDirection());

                //Destination location (look direction) is opposite of absolute drive in direction
                int lookDirection = (parseDirection(startNode.getNeighbours().get(0).getStopDirection()).ordinal() + 2) % 4;
                this.destinationDirection = Direction.values()[lookDirection];
            }
            catch(ParseException e)
            {
                //Unknown direction
                System.err.println("Could not find start position for id " + startPosition + "!");
                System.err.println(e.getMessage());

                return false;
            }

            this.destinationLocation = startNode.getNeighbours().get(0).getStopId().getPid();
            this.destinationDistance = startNode.getNeighbours().get(0).getLength();
            this.travelledDistance = 0L;

            this.currentLocation = startPosition;
        }
        else
        {
            System.err.println("Could not find start position for id " + startPosition + "!");
            return false;
        }

        this.onMap = true;

        return true;
    }

    public void removedFromMap()
    {
        this.onMap = false;
    }

    public boolean onMap()
    {
        return this.onMap;
    }

    public boolean onNode()
    {
        if(this.onMap)
        {
            if(travelledDistance == 0 || (travelledDistance <= destinationDistance + 10 && travelledDistance >= destinationDistance - 10))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public int getDistanceTargetLocation()
    {
        return (int)this.destinationDistance;
    }

    public int getCurrentLocation()
    {
        return this.currentLocation;
    }

    public String getNodeRFID(int nodeID)
    {
        if(map == null)
        {
            //No map loaded
            return null;
        }

        for(Node node : this.map.getNodeList())
        {
            if(node.getNodeId() == nodeID)
            {
                return node.getPointEntity().getRfid();
            }
        }

        return null;
    }

    public void startFollowLine()
    {
        Node currentNode = null;

        if(map == null)
        {
            //No map loaded
            return;
        }

        boolean foundNode = false;
        Iterator<Node> itNode = this.map.getNodeList().iterator();

        while(itNode.hasNext() && !foundNode)
        {
            Node node = itNode.next();

            if(node.getNodeId() == this.currentLocation)
            {
                //Get current node
                currentNode = node;

                foundNode = true;
            }
        }

        if(foundNode)
        {
            boolean foundLink = false;
            Iterator<Link> itLink = currentNode.getNeighbours().iterator();
            Link followLink = null;

            while(itLink.hasNext() && !foundLink)
            {
                Link link = itLink.next();

                try
                {
                    Direction direction = parseDirection(link.getStartDirection());

                    if(direction == this.currentDirection)
                    {
                        followLink = link;

                        foundLink = true;
                    }
                }
                catch(ParseException e)
                {
                    //Could not parse direction
                    System.err.println("Could not parse start direction of link with id " + link.getLid());
                    System.err.println(e.getMessage());
                }
            }

            if(foundLink)
            {
                try
                {
                    //Destination location (look direction) is opposite of absolute drive in direction
                    int lookDirection = (parseDirection(followLink.getStopDirection()).ordinal() + 2) % 4;
                    this.destinationDirection = Direction.values()[lookDirection];
                }
                catch(ParseException e)
                {
                    //Could not parse direction
                    System.err.println("Could not parse end direction of link with id " + followLink.getLid());
                    System.err.println(e.getMessage());
                }

                this.destinationLocation = followLink.getStopId().getPid();
                this.destinationDistance = followLink.getLength();

                this.travelledDistance = 0;

                this.followLine = true;
            }
            else
            {
                //Could not find destination, going off road
                this.onMap = false;
            }
        }
        else
        {
            //Could not find node
            this.onMap = false;
        }

        return;
    }

    public void endFollowLine()
    {
        if(map == null)
        {
            //No map loaded
            return;
        }

        if(followLine)
        {
            this.currentLocation = this.destinationLocation;
            this.currentDirection = this.destinationDirection;
            this.followLine = false;
        }
    }

    public void turn(double angle)
    {
        this.currentDirection = this.getNewDirection((int)angle);
    }

    private Direction parseDirection(String direction) throws ParseException
    {
        switch(direction)
        {
            case "N":
                return Direction.NORTH;
            case "E":
                return Direction.EAST;
            case "S":
                return Direction.SOUTH;
            case "W":
                return Direction.WEST;
            default:
                throw new ParseException("Could not parse direction: " + direction, 0);
        }
    }

    private Direction getNewDirection(int turnAngle)
    {
        //Positive angle == turn left, Negative angle = turn right
        int numberOfQuartTurns = -Math.round(turnAngle / 90) % 4;

        //Relative to North == 0
        int newRelativeDirection = (this.currentDirection.ordinal() + numberOfQuartTurns) % 4;

        if(newRelativeDirection < 0)
        {
            //Convert negative value to positive counterpart
            newRelativeDirection = newRelativeDirection + 4;
        }

        return Direction.values()[newRelativeDirection];
    }

    private Map getMap()
    {
        RestTemplate template = new RestTemplate();
        ResponseEntity<Map> responseMap;
        Map map;

        responseMap = template.getForEntity("http://" + serverIP + ":" + serverPort + "/map/", Map.class);
        map = responseMap.getBody();

        return map;
    }
}
