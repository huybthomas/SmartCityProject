package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.models.Point;

import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
public class Node {

    private int nodeId;
    private Point pointEntity;
    private List<Link> neighbours;

    public Node(Point pointEntity) {
        this.pointEntity = pointEntity;
        this.nodeId = pointEntity.getPid();
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public Point getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(Point pointEntity) {
        this.pointEntity = pointEntity;
    }

    public List<Link> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Link> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeId=" + nodeId +
                ", pointEntity=" + pointEntity +
                ", neighbours=" + neighbours +
                '}';
    }
}
