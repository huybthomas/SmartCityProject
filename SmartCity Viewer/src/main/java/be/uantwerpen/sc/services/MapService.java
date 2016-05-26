package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.tools.MapBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arthur on 3/04/2016.
 */
@Service
public class MapService {

    //TODO set correct IP
    String coreIP = "http://146.175.140.118:1994";

    public MapBuilder mapBuilder;

    LinkEntity[] linkList;
    PointEntity[] pointList;

    public MapService(){
        //Disabled for testing
        getMap();

        /*
        //Create Test Map
        PointEntity point1 = new PointEntity();
        point1.setPid(1);
        PointEntity point2 = new PointEntity();
        point2.setPid(2);
        PointEntity point3 = new PointEntity();
        point3.setPid(3);
        PointEntity point4 = new PointEntity();
        point4.setPid(4);
        
        pointList = new PointEntity[]{point1, point2, point3, point4};
        
        LinkEntity link = new LinkEntity();
        link.setLength(3);
        link.setLid(1);
        link.setStartDirection("E");
        link.setStopDirection("W");
        link.setStartId(point1);
        link.setStopId(point2);
        LinkEntity link2 = new LinkEntity();
        link2.setLength(2);
        link2.setLid(2);
        link2.setStartDirection("E");
        link2.setStopDirection("W");
        link2.setStartId(point2);
        link2.setStopId(point3);
        LinkEntity link3 = new LinkEntity();
        link3.setLength(4);
        link3.setLid(3);
        link3.setStartDirection("S");
        link3.setStopDirection("W");
        link3.setStartId(point2);
        link3.setStopId(point4);
        LinkEntity link4 = new LinkEntity();
        link4.setLength(2);
        link4.setLid(4);
        link4.setStartDirection("S");
        link4.setStopDirection("N");
        link4.setStartId(point3);
        link4.setStopId(point4);
        

        linkList = new LinkEntity[]{link, link2, link3, link4};
        */

        //build Sim Map
        mapBuilder = new MapBuilder(linkList, pointList);
        System.out.println("Map Created");
        mapBuilder.logMap();

    }

    private void getMap(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PointEntity[]> responseList;
        responseList = restTemplate.getForEntity(coreIP.toString()+"/point/", PointEntity[].class);
        pointList = responseList.getBody();

        restTemplate = new RestTemplate();
        ResponseEntity<LinkEntity[]> responseList2;
        responseList2 = restTemplate.getForEntity(coreIP.toString()+"/link/", LinkEntity[].class);
        linkList = responseList2.getBody();
    }
}
