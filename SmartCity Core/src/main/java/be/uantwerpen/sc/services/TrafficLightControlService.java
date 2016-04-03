package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.TrafficlightEntity;
import be.uantwerpen.sc.repositories.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Niels on 2/04/2016.
 */
@Service
public class TrafficLightControlService {

    @Autowired
    private TrafficLightRepository trafficLightRepository;

    public List<TrafficlightEntity> getAlTrafficLights(){
        return trafficLightRepository.findAll();
    }

    public TrafficlightEntity getTrafficLight(int id){
        return trafficLightRepository.findOne(id);
    }

}
