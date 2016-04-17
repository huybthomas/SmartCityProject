package be.uantwerpen.sc.tools;

/**
 * Created by Niels on 17/04/2016.
 */
public class Edge {
    public final Vertex target;
    public final int weight;
    public Edge(Vertex argTarget, int argWeight)
    { target = argTarget; weight = argWeight; }
}
