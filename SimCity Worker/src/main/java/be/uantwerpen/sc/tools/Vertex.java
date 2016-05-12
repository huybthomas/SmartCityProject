package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.map.Node;
import be.uantwerpen.sc.models.map.NodeJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 17/04/2016.
 */
public class Vertex implements Comparable<Vertex> {

    public Vertex(){}

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
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if(Vertex.class.isAssignableFrom(obj.getClass())) {
            final Vertex other = (Vertex) obj;
            if ((this.id == -1) ? (other.id != -1) : !(this.id == other.id)) {
                return false;
            }
            return true;
        }else if(Edge.class.isAssignableFrom(obj.getClass())){
            final Edge other = (Edge) obj;
            if ((this.id == -1) ? (other.getTarget() != -1) : !(this.id == other.getTarget())) {
                return false;
            }
            return true;
        }
        return false;
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
