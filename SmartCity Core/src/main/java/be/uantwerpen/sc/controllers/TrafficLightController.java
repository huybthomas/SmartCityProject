package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.TrafficLightEntity;
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
public class TrafficLightController
{
    @Autowired
    private TrafficLightControlService trafficLightControlService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TrafficLightEntity> allBots(){
        return trafficLightControlService.getAlTrafficLights();
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public TrafficLightEntity getBot(@PathVariable("id") int id){return  trafficLightControlService.getTrafficLight(id);}

    @RequestMapping(value = "savetest",method = RequestMethod.GET)
    public void saveTlTest(){
        TrafficLightEntity tl = new TrafficLightEntity();
        tl.setState("Test");
        trafficLightControlService.saveTl(tl);
    }
}
