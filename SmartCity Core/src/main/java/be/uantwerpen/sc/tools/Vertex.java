package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.map.Node;
import be.uantwerpen.sc.models.map.NodeJson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Niels on 17/04/2016.
 */
public class Vertex implements Comparable<Vertex> {

    private int id;
    private List<Edge> adjacencies = new ArrayList<>();
    private double minDistance = Double.POSITIVE_INFINITY;
    private Vertex previous;

    public Vertex(NodeJson nodeJson) {
        this.id = nodeJson.getPointEntity().getPid();
    }
    public Vertex(Node node){this.id = node.getNodeId();}
    public Vertex(int id){this.id=id;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Edge> getAdjacencies() {
        return adjacencies;
    }

    public void setAdjacencies(List<Edge> adjacencies) {
        this.adjacencies = adjacencies;
    }


    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
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
