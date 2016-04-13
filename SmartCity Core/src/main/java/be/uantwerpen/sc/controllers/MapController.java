package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.Map;
import be.uantwerpen.sc.models.Node;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.services.BotControlService;
import be.uantwerpen.sc.services.LinkControlService;
import be.uantwerpen.sc.services.PointControlService;
import be.uantwerpen.sc.services.TrafficLightControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Niels on 3/04/2016.
 */

@RestController
@RequestMapping(value = "/map/")
public class MapController {

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



    private Map myMap;
    private Node node;

    @RequestMapping(method = RequestMethod.GET)
    public Map buildMap(){
        System.out.println("DoSomething");
        linkEntityList =linkControlService.getAllLinks();
        myMap = new Map();

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

}
