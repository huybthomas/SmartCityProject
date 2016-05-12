package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 23/03/2016.
 */
@RestController
@RequestMapping("/point/")
public class PointController {

    @Autowired
    private PointRepository pointRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<PointEntity> allPoints(){
        List<PointEntity> points = pointRepository.findAll();
        return points;
    }

    @RequestMapping(value = "getlock/{id}", method = RequestMethod.GET)
    public boolean getPointStatus(@PathVariable("id") int id){
        switch(pointRepository.findOne(id).getPointlock()){
            case 1:
                return true;
            case 0:
                return false;
            default:
        }
        return true;
    }

    @RequestMapping(value = "setlock/{id}/{value}", method = RequestMethod.PUT)
    public void getPointStatus(@PathVariable("id") int id, @PathVariable("value") int value){
        synchronized (this){
            pointRepository.findOne(id).setPointlock(value);
        }
    }
}
