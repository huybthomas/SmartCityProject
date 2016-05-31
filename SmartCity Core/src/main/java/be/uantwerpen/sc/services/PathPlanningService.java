package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.models.map.*;
import be.uantwerpen.sc.tools.pathplanning.Dijkstra;
import be.uantwerpen.sc.tools.Edge;
import be.uantwerpen.sc.tools.Vertex;
import be.uantwerpen.sc.tools.pathplanning.IPathplanning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Map map = mapControlService.buildMap();
        List<Link> linkEntityList = new ArrayList<>();
        List<Vertex> vertexes = new ArrayList<>();
        for (Node nj : map.getNodeList()){
            vertexes.add(new Vertex(nj));
            for(Link linkEntity : nj.getNeighbours()){
                linkEntityList.add(linkEntity);
            }
        }

        ArrayList<Edge> edges;
        List<ArrayList<Edge>> edgeslistinlist = new ArrayList<>();
        Link realLink = new Link();
        int i = 0;
        for (Node nj : map.getNodeList()){
            edges = new ArrayList<>();
            for (Link neighbour : nj.getNeighbours()){
                for (Vertex v : vertexes){
                    if(v.getId() == neighbour.getStopId().getPid()){
                        for(Link linkEntity: linkEntityList){
                            if(linkEntity.getStopId().getPid() == v.getId() && linkEntity.getStartId().getPid() == nj.getPointEntity().getPid()){
                                System.out.println(linkEntity.toString() +" " + linkEntity);
                                realLink = linkEntity;
                            }
                        }
                        //edges.add(new Edge(v.getId(),neighbour.getWeight(),linkControlService.getLink(neighbour.getPointEntity().getPid())));
                        edges.add(new Edge(v.getId(),neighbour.getWeight(),realLink));
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
    private List<Link> linkEntityList;

    @Override
    public List<Vertex> Calculatepath(MapJson mapJson, int start, int stop) {

        List<Vertex> vertexes = mapToVertexes();

        Vertex v = vertexes.get(start-1);

        dijkstra.computePaths(v,vertexes); // run Dijkstra
        System.out.println("Distance to " + vertexes.get(stop-1) + ": " + vertexes.get(stop-1).getMinDistance());
        List<Vertex> path = dijkstra.getShortestPathTo(vertexes.get(stop-1),vertexes);
        System.out.println("Path: " + path);
        //return ("Distance to " + vertexes.get(stop-1) + ": " + vertexes.get(stop-1).minDistance) + ( "Path: " + path);
        return path;
    }

    @Override
    public List<Vertex> nextRandomPath(Map map, int start) {
        List<Vertex> vertexes = mapToVertexes();

        Random random = new Random();
        Vertex currentVertex = null;
        for(Vertex v: vertexes){
            if(v.getId()==start) {
                currentVertex = v;
            }
        }
        int i = currentVertex.getAdjacencies().size();
        int index = random.nextInt(i);
        Vertex nextVertex = new Vertex(new Node(currentVertex.getAdjacencies().get(index).getLinkEntity().getStopId()));

        List<Vertex> vertexList = new ArrayList<>();
        vertexList.add(currentVertex);
        vertexList.add(nextVertex);
        return vertexList;
    }

    private List<Vertex> mapToVertexes(){
        MapJson mapJsonServer = mapControlService.buildMapJson();
        List<Vertex> vertexes = new ArrayList<>();
        for (NodeJson nj : mapJsonServer.getNodeJsons()){
            vertexes.add(new Vertex(nj));
        }

        ArrayList<Edge> edges;
        List<ArrayList<Edge>> edgeslistinlist = new ArrayList<>();
        Link realLink = new Link();
        int i = 0;
        for (NodeJson nj : mapJsonServer.getNodeJsons()){
            edges = new ArrayList<>();
            for (Neighbour neighbour : nj.getNeighbours()){
                for (Vertex v : vertexes){
                    if(v.getId() == neighbour.getPointEntity().getPid()){
                        for(Link linkEntity: linkControlService.getAllLinks()){
                            if(linkEntity.getStopId().getPid() == v.getId() && linkEntity.getStartId().getPid() == nj.getPointEntity().getPid()){
                                System.out.println(linkEntity.toString() +" " + linkEntity);
                                realLink = linkEntity;
                            }
                        }
                        //edges.add(new Edge(v.getId(),neighbour.getWeight(),linkControlService.getLink(neighbour.getPointEntity().getPid())));
                        edges.add(new Edge(v.getId(),neighbour.getWeight(),realLink));
                    }
                }
            }
            edgeslistinlist.add(i, (edges));
            i++;
        }

        for (int j = 0; j < vertexes.size();j++){
            vertexes.get(j).setAdjacencies(edgeslistinlist.get(j));
        }

        return vertexes;
    }
}
