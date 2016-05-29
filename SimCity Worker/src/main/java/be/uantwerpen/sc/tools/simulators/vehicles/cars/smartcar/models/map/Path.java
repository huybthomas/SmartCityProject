package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map;

import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 10/05/2016.
 */
public class Path
{
    private List<Vertex> path;

    public Path()
    {
        this.path = new ArrayList<Vertex>();
    }

    public List<Vertex> getPath()
    {
        return path;
    }

    public void setPath(List<Vertex> path)
    {
        this.path = path;
    }

    @Override
    public String toString()
    {
        return "Path{" +
                "path=" + path +
                '}';
    }
}
