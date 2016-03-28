package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.Map;
import be.uantwerpen.sc.repositories.LinkRepository;
import be.uantwerpen.sc.repositories.PointRepository;
import be.uantwerpen.sc.repositories.TrafficLightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Niels on 26/03/2016.
 */
@Service
public class MapService {

    @Autowired
    private PointRepository puntRepository;
    @Autowired
    private LinkRepository linkRepository;
    @Autowired
    private TrafficLightRepository trafficLightRepository;

    private Map map;
    private Character linkchar = '-';
    private Character knooppunt = '+';
    private Character parking = 'P';
    private Character verkeerslicht = 'L';
    private List<Character> characterList;

    public MapService() {
    }

    public Map getMap() {
        return this.map;
    }

    public void setMap(Map map) {
        this.map = map;
        characterList = new ArrayList<>();
        this.map.setLinkList(linkRepository.findAll());
        this.map.setPuntList(puntRepository.findAll());
        this.map.setTrafficLightList(trafficLightRepository.findAll());
    }

    public void generateMap(){

        //characterList.add(parking);
        for(LinkEntity link : map.getLinkList()) {
           /* if(link.getStopId().getType().equals("KNOOPPUNT")){
                characterList.add(knooppunt);
            }
            if(link.getStopId().getType().equals("PARKING")){
                characterList.add(parking);
            }
            if(link.getStopId().getType().equals("TRAFFICLIGHT")){
                characterList.add(verkeerslicht);
            }*/
            characterList.add(linkchar);
        }
    }

    public String printMap(){
        StringBuilder builder = new StringBuilder(characterList.size());
        for(Character ch: characterList)
        {
            builder.append(ch);
        }
        return builder.toString();
    }
}
