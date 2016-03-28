package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.Map;
import be.uantwerpen.sc.services.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 26/03/2016.
 */
@RestController
@RequestMapping("/map/")
public class MapController {

    @Autowired
    private MapService mapService;
    private Map map;

    @RequestMapping(method = RequestMethod.GET)
    public Map getMap(){
        map = new Map();
        mapService.setMap(map);
        mapService.generateMap();
        System.out.println(mapService.printMap());
        return mapService.getMap();
    }

}
