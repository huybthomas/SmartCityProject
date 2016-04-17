package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;

/**
 * Created by Niels on 14/04/2016.
 */
public class Neighbour {

    private PointEntity pointEntity;
    private int length;
    private int weight;

    public Neighbour(LinkEntity linkEntity) {
        this.pointEntity = linkEntity.getStopId();
        this.length = linkEntity.getLength();
        this.weight = linkEntity.getWeight();
    }

    public PointEntity getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(PointEntity pointEntity) {
        this.pointEntity = pointEntity;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
