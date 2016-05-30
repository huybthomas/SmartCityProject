package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map;

import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.Bot;
import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.TrafficLight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
public class Map
{
    private List<Node> nodeList;
    private List<Bot> botEntities;
    private List<TrafficLight> trafficLightEntities;

    public Map()
    {
        nodeList = new ArrayList<>();
        botEntities = new ArrayList<>();
        trafficLightEntities = new ArrayList<>();
    }

    public  void addNode(Node node)
    {
        nodeList.add(node);
    }

    public void setNodeList(List<Node> nodeList)
    {
        this.nodeList = nodeList;
    }

    public List<Node> getNodeList()
    {
        return nodeList;
    }

    public List<Bot> getBotEntities()
    {
        return botEntities;
    }

    public void setBotEntities(List<Bot> botEntities)
    {
        this.botEntities = botEntities;
    }

    public List<TrafficLight> getTrafficLightEntity()
    {
        return trafficLightEntities;
    }

    public void setTrafficLightEntity(List<TrafficLight> trafficLightEntities)
    {
        this.trafficLightEntities = trafficLightEntities;
    }
}
