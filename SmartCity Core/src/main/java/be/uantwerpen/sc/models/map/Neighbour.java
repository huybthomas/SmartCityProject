package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.models.Point;

/**
 * Created by Niels on 14/04/2016.
 */
public class Neighbour {

    private Point pointEntity;
    private Long length;
    private int weight;

    public Neighbour(Link linkEntity) {
        this.pointEntity = linkEntity.getStopPoint();
        this.length = linkEntity.getLength();
        this.weight = linkEntity.getWeight();
    }

    public Point getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(Point pointEntity) {
        this.pointEntity = pointEntity;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Neighbour{" +
                "pointEntity=" + pointEntity +
                ", length=" + length +
                ", weight=" + weight +
                '}';
    }
}
