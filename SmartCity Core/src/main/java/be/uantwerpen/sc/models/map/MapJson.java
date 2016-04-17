package be.uantwerpen.sc.models.map;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niels on 14/04/2016.
 */
public class MapJson {

    private List<NodeJson> nodeJsons;
    private int size;

    public MapJson() {
        this.nodeJsons = new ArrayList<>();
    }

    public void addNodeJson(NodeJson nodeJson){
        nodeJsons.add(nodeJson);
    }

    public List<NodeJson> getNodeJsons() {
        return nodeJsons;
    }

    public void setNodeJsons(List<NodeJson> nodeJsons) {
        this.nodeJsons = nodeJsons;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
