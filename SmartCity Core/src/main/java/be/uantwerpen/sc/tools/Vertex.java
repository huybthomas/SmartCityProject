package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.map.NodeJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Niels on 17/04/2016.
 */
public class Vertex implements Comparable<Vertex> {

    public final int id;
    public List<Edge> adjacencies = new ArrayList<>();
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;

    public Vertex(NodeJson nodeJson) {
        this.id = nodeJson.getPointEntity().getPid();
    }

    public int getId() {
        return id;
    }

    public List<Edge> getAdjacencies() {
        return adjacencies;
    }

    public void setAdjacencies(List<Edge> adjacencies) {
        this.adjacencies = adjacencies;
    }

    @Override
    public int compareTo(Vertex o) {
        return 0;
    }

    @Override
    public String toString() {
        return "Vertex{" + "id=" + id + '}';
    }
}
