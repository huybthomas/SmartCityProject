package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.*;
import be.uantwerpen.sc.models.map.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Niels on 14/04/2016.
 */
@Service
public class MapControlService {

    @Autowired
    private PointControlService pointControlService;
    @Autowired
    private LinkControlService linkControlService;
    @Autowired
    private BotControlService botControlService;
    @Autowired
    private TrafficLightControlService trafficLightControlService;

    private List<LinkEntity> linkEntityList;
    private List<LinkEntity> targetlinks;
    private List<Neighbour> neighbourList;

    private Node node;
    private NodeJson nodeJson;

    public Map buildMap(){

        Map myMap = new Map();

        linkEntityList =linkControlService.getAllLinks();

        for(PointEntity point : pointControlService.getAllPoints()){
            node = new Node(point);
            targetlinks = linkEntityList.stream().filter(item -> Objects.equals(item.getStartId().getPid(), node.getNodeId())).collect(Collectors.toList());
            node.setNeighbours(targetlinks);
            myMap.addNode(node);
        }

        myMap.setBotEntities(botControlService.getAllBots());
        myMap.setTrafficlightEntity(trafficLightControlService.getAlTrafficLights());

        myMap.getNodeList();

        return myMap;
    }

    public MapJson buildMapJson(){

        MapJson mapJson = new MapJson();
        linkEntityList =linkControlService.getAllLinks();

        for(PointEntity point : pointControlService.getAllPoints()){
            nodeJson = new NodeJson(point);
            neighbourList = new ArrayList<>();
            for(LinkEntity link: linkEntityList){
                if((link.getStartId().getPid()) == (nodeJson.getPointEntity().getPid())){
                    neighbourList.add(new Neighbour(link));
                }
            }
            nodeJson.setNeighbours(neighbourList);
            mapJson.addNodeJson(nodeJson);
        }

        mapJson.setSize(mapJson.getNodeJsons().size());
        return mapJson;
    }
}
