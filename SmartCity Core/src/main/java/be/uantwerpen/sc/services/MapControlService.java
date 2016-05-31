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
public class MapControlService
{
    @Autowired
    private PointControlService pointControlService;

    @Autowired
    private LinkControlService linkControlService;

    @Autowired
    private BotControlService botControlService;

    @Autowired
    private TrafficLightControlService trafficLightControlService;

    public Map buildMap()
    {
        Map map = new Map();

        List<Link> linkEntityList = linkControlService.getAllLinks();

        for(Point point : pointControlService.getAllPoints())
        {
            Node node = new Node(point);
            List<Link> targetLinks = linkEntityList.stream().filter(item -> Objects.equals(item.getStartId().getPid(), node.getNodeId())).collect(Collectors.toList());

            node.setNeighbours(targetLinks);
            map.addNode(node);
        }

        map.setBotEntities(botControlService.getAllBots());
        map.setTrafficlightEntity(trafficLightControlService.getAllTrafficLights());

        return map;
    }

    public MapJson buildMapJson()
    {
        MapJson mapJson = new MapJson();

        List<Link> linkEntityList = linkControlService.getAllLinks();

        for(Point point : pointControlService.getAllPoints())
        {
            NodeJson nodeJson = new NodeJson(point);

            List<Neighbour> neighbourList = new ArrayList<Neighbour>();

            for(Link link: linkEntityList)
            {
                if((link.getStartId().getPid()) == (nodeJson.getPointEntity().getPid()))
                {
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
