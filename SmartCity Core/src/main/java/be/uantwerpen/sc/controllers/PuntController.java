package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.PuntEntity;
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
@RequestMapping("/punt/")
public class PuntController {

    @Autowired
    private PointRepository pointRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<PuntEntity> allLinks(){
        List<PuntEntity> punts = pointRepository.findAll();
        return punts;
    }
}
