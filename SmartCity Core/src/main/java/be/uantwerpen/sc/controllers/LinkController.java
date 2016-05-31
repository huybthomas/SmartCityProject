package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.services.LinkControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 30/03/2016.
 */
@RestController
@RequestMapping("/link/")
public class LinkController
{
    @Autowired
    private LinkControlService linkControlService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Link> allBots()
    {
        List<Link> linkEntityList = linkControlService.getAllLinks();
        return linkEntityList;
    }
}

