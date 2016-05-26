package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.LinkEntity;

/**
 * Created by Niels on 17/04/2016.
 */
public class Edge {
    private int target;
    private int weight;
    private LinkEntity linkEntity;
    public Edge(int argTarget, int argWeight, LinkEntity linkEntity)
    { target = argTarget; weight = argWeight; this.linkEntity = linkEntity;}

    public Edge(int argTarget, int argWeight)
    { target = argTarget; weight = argWeight;}


    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public LinkEntity getLinkEntity() {
        return linkEntity;
    }

    public void setLinkEntity(LinkEntity linkEntity) {
        this.linkEntity = linkEntity;
    }
}
