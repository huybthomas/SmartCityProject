package be.uantwerpen.sc.services;

import be.uantwerpen.sc.tools.Dijkstra;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 27/02/2016.
 */
@Service
public class PathPlanningService
{
    private Dijkstra dijkstra;

    public PathPlanningService()
    {
        this.dijkstra = new Dijkstra();
    }
}
