package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.repositories.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 23/03/2016.
 */
@RestController
@RequestMapping("/link/")
public class LinkController {

    @Autowired
    private LinkRepository linkRepository;

    @RequestMapping(method = RequestMethod.GET)
    public List<LinkEntity> allLinks(){
        List<LinkEntity> links = linkRepository.findAll();
        return links;
    }
}
