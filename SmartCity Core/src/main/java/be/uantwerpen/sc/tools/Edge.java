package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.Link;

/**
 * Created by Niels on 17/04/2016.
 */
public class Edge
{
    private Long target;
    private int weight;
    private Link linkEntity;
    public Edge(Long argTarget, int argWeight, Link linkEntity)
    {
        target = argTarget;
        weight = argWeight;
        this.linkEntity = linkEntity;
    }

    public Edge(Long argTarget, int argWeight)
    {
        target = argTarget;
        weight = argWeight;
    }

    public Long getTarget()
    {
        return target;
    }

    public void setTarget(Long target)
    {
        this.target = target;
    }

    public int getWeight()
    {
        return weight;
    }

    public void setWeight(int weight)
    {
        this.weight = weight;
    }

    public Link getLinkEntity()
    {
        return linkEntity;
    }

    public void setLinkEntity(Link linkEntity)
    {
        this.linkEntity = linkEntity;
    }
}
