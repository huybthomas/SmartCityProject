package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.TrafficlightEntity;
import be.uantwerpen.sc.services.TrafficLightControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 2/04/2016.
 */
@RestController
@RequestMapping("/trafficlight/")
public class TrafficLightController {

    @Autowired
    private TrafficLightControlService trafficLightControlService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TrafficlightEntity> allBots(){
        return trafficLightControlService.getAlTrafficLights();
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public TrafficlightEntity getBot(@PathVariable("id") int id){return  trafficLightControlService.getTrafficLight(id);}
}
