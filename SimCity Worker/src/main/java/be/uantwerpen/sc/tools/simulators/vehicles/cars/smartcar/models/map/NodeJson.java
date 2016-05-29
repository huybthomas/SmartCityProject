package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map;

import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.PointEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 14/04/2016.
 */
public class NodeJson
{
    private PointEntity pointEntity;
    private List<Neighbour> neighbours;

    public NodeJson()
    {
        this.pointEntity = null;
        this.neighbours = new ArrayList<Neighbour>();
    }

    public NodeJson(PointEntity pointEntity)
    {
        this.pointEntity = pointEntity;
    }

    public PointEntity getPointEntity()
    {
        return pointEntity;
    }

    public void setPointEntity(PointEntity pointEntity)
    {
        this.pointEntity = pointEntity;
    }

    public List<Neighbour> getNeighbours()
    {
        return neighbours;
    }

    public void setNeighbours(List<Neighbour> neighbours)
    {
        this.neighbours = neighbours;
    }
}
