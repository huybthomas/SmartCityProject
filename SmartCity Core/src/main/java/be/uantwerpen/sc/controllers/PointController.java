package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<PointEntity> allLinks(){
        List<PointEntity> points = pointRepository.findAll();
        return points;
    }
}
