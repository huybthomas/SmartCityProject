package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.map.Map;
import be.uantwerpen.sc.models.map.MapJson;
import be.uantwerpen.sc.models.map.Node;
import be.uantwerpen.sc.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Niels on 3/04/2016.
 */

@RestController
@RequestMapping(value = "/map/")
public class MapController {

    @Autowired
    private MapControlService mapControlService;


    private Map myMap;
    private Node node;

    @RequestMapping(method = RequestMethod.GET)
    public Map getMap(){
        System.out.println("DoSomething");

        return mapControlService.buildMap();
    }

    @RequestMapping(value = "json", method = RequestMethod.GET)
    public MapJson getMapJson(){
        System.out.println("DoSomething");

        return mapControlService.buildMapJson();
    }

}
