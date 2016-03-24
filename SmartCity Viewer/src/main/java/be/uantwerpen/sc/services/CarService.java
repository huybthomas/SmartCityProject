package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.sim.SimBot;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by Arthur on 24/03/2016.
 */
@Service
public class CarService {

    public ArrayList<SimBot> simBots;

    public CarService(){
        simBots = new ArrayList<>();
        //For testing
        addTestBots();
    }

    private void addTestBots(){
        SimBot simBot = new SimBot();
        double[] loc = {50,50};
        simBot.setLoc(loc);
        simBots.add(simBot);
        simBot = new SimBot();
        loc = new double[]{100, 100};
        simBot.setLoc(loc);
        simBots.add(simBot);
    }

}
