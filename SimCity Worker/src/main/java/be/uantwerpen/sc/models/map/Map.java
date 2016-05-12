package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.models.TrafficLightEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
public class Map {

    private List<Node> nodeList;
    private List<BotEntity> botEntities;
    private List<TrafficLightEntity> trafficlightEntity;

    public Map(){
        nodeList = new ArrayList<>();
        botEntities = new ArrayList<>();
    }

    public  void addNode(Node node){
        nodeList.add(node);
    }

    public void setNodeList(List<Node> nodeList) {
        this.nodeList = nodeList;
    }

    public List<Node> getNodeList() {
        return nodeList;
    }

    public List<BotEntity> getBotEntities() {
        return botEntities;
    }

    public void setBotEntities(List<BotEntity> botEntities) {
        this.botEntities = botEntities;
    }

    public List<TrafficLightEntity> getTrafficlightEntity() {
        return trafficlightEntity;
    }

    public void setTrafficlightEntity(List<TrafficLightEntity> trafficlightEntity) {
        this.trafficlightEntity = trafficlightEntity;
    }
}
