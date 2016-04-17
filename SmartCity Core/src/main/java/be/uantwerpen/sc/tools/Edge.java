package be.uantwerpen.sc.tools;

/**
 * Created by Niels on 17/04/2016.
 */
public class Edge {
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}
