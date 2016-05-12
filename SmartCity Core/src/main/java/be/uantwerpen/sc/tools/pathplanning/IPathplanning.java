package be.uantwerpen.sc.tools.pathplanning;

import be.uantwerpen.sc.models.map.MapJson;
import be.uantwerpen.sc.tools.Vertex;

import java.util.List;

/**
 * Created by Niels on 27/04/2016.
 */

public interface IPathplanning {

    List<Vertex> Calculatepath(MapJson mapJson, int start, int stop);

}
