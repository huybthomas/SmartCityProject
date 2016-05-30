package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.Point;

import java.util.List;

/**
 * Created by Niels on 14/04/2016.
 */
public class NodeJson {
    private Point pointEntity;
    private List<Neighbour> neighbours;

    public NodeJson(Point pointEntity) {
        this.pointEntity = pointEntity;
    }

    public Point getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(Point pointEntity) {
        this.pointEntity = pointEntity;
    }

    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }

    @Override
    public String toString() {
        return "NodeJson{" +
                "pointEntity=" + pointEntity +
                ", neighbours=" + neighbours +
                '}';
    }
}
