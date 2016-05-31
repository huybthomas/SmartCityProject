package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.services.LinkControlService;
import be.uantwerpen.sc.services.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Niels on 26/03/2016.
 */
@RestController
@RequestMapping("/graph/")
public class GraphController
{
    @Autowired
    private LinkControlService linkControlService;

    @Autowired
    private GraphService graphService;

    private List<Link> linkEntityList;

    @RequestMapping(method = RequestMethod.GET)
    public void getMap()
    {
        if(!graphService.isFlag())
        {
            buildGraph();
        }
    }


    @RequestMapping(value ="/path/{start}+{stop}",method = RequestMethod.GET)
    public void getTest(@PathVariable("start") String start,@PathVariable("stop") String stop)
    {
        if(!graphService.isFlag())
        {
            buildGraph();
        }

        graphService.shortestPath(start,stop);
    }

    @RequestMapping(value ="/path",method = RequestMethod.GET)
    public void getTest()
    {
        if(!graphService.isFlag())
        {
            buildGraph();
        }

        graphService.shortestPath("1","20");
    }

    public void buildGraph()
    {
        linkEntityList = linkControlService.getAllLinks();
        // de punten toevoegen
        for(int i = 1; i <= linkEntityList.size(); i++){
            graphService.addPointToGraph(String.valueOf(i));
        }

        //de linken toevoegen
        for(Link link : linkEntityList){
            //System.out.println("Start: " + link.getStartId().getPid());
            //System.out.println("Stop: " + link.getStopId().getPid());
            graphService.addEdgeToGraph(link);
        }
        graphService.setFlag(true);
        graphService.strongConnectivity();
    }
}
