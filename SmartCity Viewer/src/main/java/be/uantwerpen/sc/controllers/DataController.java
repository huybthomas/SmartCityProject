package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.sim.SimBot;
import be.uantwerpen.sc.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by Arthur on 23/03/2016.
 */
@RestController
public class DataController {

    /*@RequestMapping("/update")
    public SimBot returnSimBot(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }*/
    @Autowired
    CarService carService;
    SimBot simBot;

    @RequestMapping("/update")
    public ArrayList<SimBot> returnSimBot() {

        if(simBot == null){
            simBot = new SimBot();
        }
        else{
            double[] loc = simBot.getLoc();
            loc[0]++;
            loc[1]++;
            simBot.setLoc(loc);
        }
        //return simBot;
        return carService.simBots;
    }
}
