package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;

import java.util.List;

/**
 * Created by Niels on 3/04/2016.
 */
public class Node {

    private int nodeId;
    private PointEntity pointEntity;
    private List<LinkEntity> neighbours;

    public Node(PointEntity pointEntity) {
        this.pointEntity = pointEntity;
        this.nodeId = pointEntity.getPid();
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public PointEntity getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(PointEntity pointEntity) {
        this.pointEntity = pointEntity;
    }

    public List<LinkEntity> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<LinkEntity> neighbours) {
        this.neighbours = neighbours;
    }
}
