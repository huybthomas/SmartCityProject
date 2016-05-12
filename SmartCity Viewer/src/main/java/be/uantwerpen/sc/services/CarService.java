package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.sim.SimBot;
import be.uantwerpen.sc.models.sim.SimPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Arthur on 24/03/2016.
 */
@Service
public class CarService {

    @Autowired
    MapService mapService;

    private String coreIP = "146.175.140.118:1994";

    BotEntity[] botList;
    public ArrayList<SimBot> simBots;

    public CarService(){
        //getBots();

        simBots = new ArrayList<>();

        //For testing
        //addTestBots();

        getBots();


    }

    //TODO Test this method
    public void getBots(){
        simBots.clear();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BotEntity[]> responseList;
        responseList = restTemplate.getForEntity("http://" + coreIP+"/bot/", BotEntity[].class);
        botList = responseList.getBody();
        for(BotEntity bot : botList){
            SimBot simBot = new SimBot(bot);
            if(mapService != null) {
                SimPath path = null;
                if(simBot.getLinkID() != -1){
                    ArrayList<SimPath> path2 = mapService.mapBuilder.getSimPaths();
                    for(SimPath simPath : path2){
                        if(simPath.getLinkID() == simBot.getLinkID()){
                            path = simPath;
                        }
                    }
                    if(path != null){
                        simBot.update(path);
                    }
                }

            }
            simBots.add(simBot);
        }
    }

    private void addTestBots(){
        SimBot simBot = new SimBot();
        simBots.add(simBot);
    }

}
