package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.map.MapJson;
import be.uantwerpen.sc.models.map.Neighbour;
import be.uantwerpen.sc.models.map.NodeJson;
import be.uantwerpen.sc.tools.pathplanning.Dijkstra;
import be.uantwerpen.sc.tools.Edge;
import be.uantwerpen.sc.tools.Vertex;
import be.uantwerpen.sc.tools.pathplanning.IPathplanning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 27/02/2016.
 */
@Service
public class PathPlanningService implements IPathplanning
{

    @Autowired
    private LinkControlService linkControlService;
    @Autowired
    private MapControlService mapControlService;

    private Dijkstra dijkstra;

    public PathPlanningService()
    {
        this.dijkstra = new Dijkstra();
    }

    public List<Vertex> CalculatepathNonInterface(int start,int stop){
        MapJson mapJson = mapControlService.buildMapJson();
        List<Vertex> vertexes = new ArrayList<>();
        for (NodeJson nj : mapJson.getNodeJsons()){
            vertexes.add(new Vertex(nj));
        }

        ArrayList<Edge> edges;
        List<ArrayList<Edge>> edgeslistinlist = new ArrayList<>();
        int i = 0;
        for (NodeJson nj : mapJson.getNodeJsons()){
            edges = new ArrayList<>();
            for (Neighbour neighbour : nj.getNeighbours()){
                for (Vertex v : vertexes){
                    if(v.getId() == neighbour.getPointEntity().getPid()){
                        edges.add(new Edge(v.getId(),neighbour.getWeight(),linkControlService.getLink(neighbour.getPointEntity().getPid())));

                    }
                }
            }
            edgeslistinlist.add(i, (edges));
            i++;
        }

         for (int j = 0; j < vertexes.size();j++){
            vertexes.get(j).setAdjacencies(edgeslistinlist.get(j));
        }

        Vertex v = vertexes.get(start-1);

        dijkstra.computePaths(v,vertexes); // run Dijkstra
        System.out.println("Distance to " + vertexes.get(stop-1) + ": " + vertexes.get(stop-1).getMinDistance());
        List<Vertex> path = dijkstra.getShortestPathTo(vertexes.get(stop-1),vertexes);
        System.out.println("Path: " + path);
        //return ("Distance to " + vertexes.get(stop-1) + ": " + vertexes.get(stop-1).minDistance) + ( "Path: " + path);
        return path;
    }

    @Override
    public List<Vertex> Calculatepath(MapJson mapJson, int start, int stop) {
        MapJson mapJsonServer = mapControlService.buildMapJson();
        List<Vertex> vertexes = new ArrayList<>();
        for (NodeJson nj : mapJsonServer.getNodeJsons()){
            vertexes.add(new Vertex(nj));
        }

        ArrayList<Edge> edges;
        List<ArrayList<Edge>> edgeslistinlist = new ArrayList<>();
        int i = 0;
        for (NodeJson nj : mapJsonServer.getNodeJsons()){
            edges = new ArrayList<>();
            for (Neighbour neighbour : nj.getNeighbours()){
                for (Vertex v : vertexes){
                    if(v.getId() == neighbour.getPointEntity().getPid()){
                        edges.add(new Edge(v.getId(),neighbour.getWeight(),linkControlService.getLink(neighbour.getPointEntity().getPid())));

                    }
                }
            }
            edgeslistinlist.add(i, (edges));
            i++;
        }

        for (int j = 0; j < vertexes.size();j++){
            vertexes.get(j).setAdjacencies(edgeslistinlist.get(j));
        }

        Vertex v = vertexes.get(start-1);

        dijkstra.computePaths(v,vertexes); // run Dijkstra
        System.out.println("Distance to " + vertexes.get(stop-1) + ": " + vertexes.get(stop-1).getMinDistance());
        List<Vertex> path = dijkstra.getShortestPathTo(vertexes.get(stop-1),vertexes);
        System.out.println("Path: " + path);
        //return ("Distance to " + vertexes.get(stop-1) + ": " + vertexes.get(stop-1).minDistance) + ( "Path: " + path);
        return path;
    }
}
