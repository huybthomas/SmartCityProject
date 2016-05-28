package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.map.Map;
import be.uantwerpen.sc.models.map.MapJson;
import be.uantwerpen.sc.models.map.Node;
import be.uantwerpen.sc.models.map.Path;
import be.uantwerpen.sc.services.*;
import be.uantwerpen.sc.tools.Vertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */

@RestController
@RequestMapping(value = "/map/")
public class MapController
{
    @Autowired
    private MapControlService mapControlService;
    @Autowired
    private PathPlanningService pathPlanningService;
    @Autowired
    private LinkControlService linkControlService;


    private Map myMap;
    private Node node;

    @RequestMapping(method = RequestMethod.GET)
    public Map getMap(){
        return mapControlService.buildMap();
    }

    MapJson mapJson = null;
    @RequestMapping(value = "json", method = RequestMethod.GET)
    public MapJson getMapJson(){
        mapJson = mapControlService.buildMapJson();
        return mapJson;
    }

    @RequestMapping(value = "{start}/path/{stop}", method = RequestMethod.GET)
    public Path PathPlanning(@PathVariable("start") int start, @PathVariable("stop") int stop){
        List<Vertex> path = pathPlanningService.Calculatepath(null,start,stop);
        return new Path(path);
    }

    @RequestMapping(value = "testpath/{start}/path/{stop}", method = RequestMethod.GET)
    public Path PathPlanning2(@PathVariable("start") int start, @PathVariable("stop") int stop){
        List<Vertex> path = pathPlanningService.CalculatepathNonInterface(start,stop);
        return new Path(path);
    }

    @RequestMapping(value = "stringmapjson", method = RequestMethod.GET)
    public String mapStringJson(){
        return mapControlService.buildMapJson().toString();
    }

    @RequestMapping(value = "stringmap", method = RequestMethod.GET)
    public String mapString(){
        return mapControlService.buildMap().toString();
    }

    @RequestMapping(value = "random/{start}", method = RequestMethod.GET)
    public Path randomPath(@PathVariable("start") int start){
        List<Vertex> vertexes = pathPlanningService.nextRandomPath(null,start);
        Path pathClass = new Path(vertexes);
        return pathClass;
    }
}
