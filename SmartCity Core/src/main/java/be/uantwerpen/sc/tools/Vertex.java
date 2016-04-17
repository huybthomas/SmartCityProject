package be.uantwerpen.sc.tools;

import java.util.Arrays;

/**
 * Created by Niels on 17/04/2016.
 */
public class Vertex implements Comparable<Vertex> {

    public final int id;
    public Edge[] adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(int id) {
        this.id = id;
    }

    @Override
    public int compareTo(Vertex o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ", adjacencies=" + Arrays.toString(adjacencies) +
                ", minDistance=" + minDistance +
                ", previous=" + previous +
                '}';
    }
}
