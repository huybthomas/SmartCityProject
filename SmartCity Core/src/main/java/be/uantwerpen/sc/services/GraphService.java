package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.LinkEntity;
import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.StrongConnectivityInspector;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Niels on 26/03/2016.
 */
@Service
public class GraphService
{
    private DirectedGraph<String, DefaultEdge> directedGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    private StrongConnectivityInspector sci;
    private List stronglyConnectedSubgraphs;
    private boolean flag = false;

    public GraphService()
    {
    }

    public DirectedGraph<String, DefaultEdge> getDirectedGraph() {
        return directedGraph;
    }

    public void addPointToGraph(String pointIdString) {directedGraph.addVertex(pointIdString);}

    public void addEdgeToGraph(LinkEntity link){directedGraph.addEdge(String.valueOf(link.getStartId().getPid()),String.valueOf(link.getStopId().getPid()));}

    public StrongConnectivityInspector strongConnectivity(){
        sci = new StrongConnectivityInspector(directedGraph);
        stronglyConnectedSubgraphs = sci.stronglyConnectedSubgraphs();
        System.out.println("Strongly connected components:");
        for (int i = 0; i < stronglyConnectedSubgraphs.size(); i++) {
            System.out.println(stronglyConnectedSubgraphs.get(i));
        }
        System.out.println();
        return sci;
    }

    public void shortestPath(String start, String end){
        System.out.println("Shortest path from: " + start + " to: " + end);
        List path = DijkstraShortestPath.findPathBetween(directedGraph, start, end);
        System.out.println(path + "\n");
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
