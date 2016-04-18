package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.LinkEntity;

/**
 * Created by Niels on 17/04/2016.
 */
public class Edge {
    public final Vertex target;
    public final int weight;
    public LinkEntity linkEntity;
    public Edge(Vertex argTarget, int argWeight, LinkEntity linkEntity)
    { target = argTarget; weight = argWeight; this.linkEntity = linkEntity;}
}
