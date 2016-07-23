package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.Bot;
import be.uantwerpen.sc.models.TrafficLight;
import be.uantwerpen.sc.tools.pathplanning.AbstractMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
public class Map implements AbstractMap
{
    private List<Node> nodeList;
    private List<Bot> botEntities;
    private List<TrafficLight> trafficlightEntity;

    public Map(){
        nodeList = new ArrayList<>();
        botEntities = new ArrayList<>();
    }

    public void addNode(Node node){
        nodeList.add(node);
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Node> getNodeList()
    {
        return nodeList;
    }

    public List<Bot> getBotEntities() {
        return botEntities;
    }

    public void setBotEntities(List<Bot> botEntities) {
        this.botEntities = botEntities;
    }

    public List<TrafficLight> getTrafficlightEntity() {
        return trafficlightEntity;
    }

    public void setTrafficlightEntity(List<TrafficLight> trafficlightEntity) {
        this.trafficlightEntity = trafficlightEntity;
    }

    @Override
    public String toString() {
        return "AbstractMap{" +
                "nodeList=" + nodeList +
                ", botEntities=" + botEntities +
                ", trafficlightEntity=" + trafficlightEntity +
                '}';
    }
}

