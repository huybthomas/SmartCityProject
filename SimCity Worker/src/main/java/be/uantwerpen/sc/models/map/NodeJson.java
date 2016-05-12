package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.PointEntity;

import java.util.List;

/**
 * Created by Niels on 14/04/2016.
 */
public class NodeJson {
    private PointEntity pointEntity;
    private List<Neighbour> neighbours;

    public NodeJson() {
    }

    public NodeJson(PointEntity pointEntity) {
        this.pointEntity = pointEntity;
    }

    public PointEntity getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(PointEntity pointEntity) {
        this.pointEntity = pointEntity;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }
}
