package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.models.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
public class Node
{
    private int nodeId;
    private Point pointEntity;
    private List<Link> neighbours;

    public Node()
    {
        this.nodeId = 0;
        this.pointEntity = null;
        this.neighbours = new ArrayList<Link>();
    }

    public Node(Point pointEntity)
    {
        this.pointEntity = pointEntity;
        this.nodeId = pointEntity.getPid();
        this.neighbours = new ArrayList<Link>();
    }

    public int getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(int nodeId)
    {
        this.nodeId = nodeId;
    }

    public Point getPointEntity()
    {
        return pointEntity;
    }

    public void setPointEntity(Point pointEntity)
    {
        this.pointEntity = pointEntity;
    }

    public List<Link> getNeighbours()
    {
        return neighbours;
    }

    public void setNeighbours(List<Link> neighbours)
    {
        this.neighbours = neighbours;
    }
}
