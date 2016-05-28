package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.TrafficLightEntity;
import be.uantwerpen.sc.repositories.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Niels on 2/04/2016.
 */
@Service
public class TrafficLightControlService
{

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    public List<TrafficLightEntity> getAlTrafficLights(){
        return trafficLightRepository.findAll();
    }

    public TrafficLightEntity getTrafficLight(int id){
        return trafficLightRepository.findOne(id);
    }

    public void updateTL(TrafficLightEntity trafficlightEntity){
        TrafficLightEntity dbTL = trafficLightRepository.findOne(trafficlightEntity.getTlid());
        dbTL = trafficlightEntity;
        trafficLightRepository.save(dbTL);
    }

    public void saveTl(TrafficLightEntity tl){
        trafficLightRepository.save(tl);
    }



}
