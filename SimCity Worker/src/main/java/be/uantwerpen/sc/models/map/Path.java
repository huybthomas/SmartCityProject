package be.uantwerpen.sc.models.map;

import be.uantwerpen.sc.tools.Vertex;

import java.util.List;

/**
 * Created by Niels on 10/05/2016.
 */
public class Path {

    private List<Vertex> path;

    public List<Vertex> getPath() {
        return path;
    }

    public void setPath(List<Vertex> path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Path{" +
                "path=" + path +
                '}';
    }
}
