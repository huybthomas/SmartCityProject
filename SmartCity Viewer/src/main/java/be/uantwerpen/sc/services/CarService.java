package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.sim.SimBot;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Arthur on 24/03/2016.
 */
@Service
public class CarService {

    BotEntity[] botList;
    ArrayList<SimBot> simBots;

    public CarService(){
        //getBots();

        simBots = new ArrayList<>();

        //For testing
        addTestBots();


    }

    //TODO Test this method
    private void getBots(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BotEntity[]> responseList;
        responseList = restTemplate.getForEntity("coreIP"+"/bot", BotEntity[].class);
        botList = responseList.getBody();
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
