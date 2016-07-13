package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.models.Link;
import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.ListenableDirectedWeightedGraph;

import javax.swing.*;

/**
 * Created by Niels on 30/03/2016.
 */
@Deprecated
public class MapVisualization
{
    private ListenableDirectedWeightedGraph<String, MyEdge> g;
    private JGraphXAdapter<String, MyEdge > graphAdapter;
    private JFrame frame;
    private mxIGraphLayout layout;
    MyEdge e;

    public MapVisualization()
    {
        g = new ListenableDirectedWeightedGraph<String, MyEdge>(MyEdge.class);
        e = new MyEdge();
    }

    public void addVertex(String pointIdString)
    {
        g.addVertex(pointIdString);
    }

    public void addEdge(Link link)
    {
        e = g.addEdge(String.valueOf(link.getStartId().getPid()),String.valueOf(link.getStopId().getPid()));
        g.setEdgeWeight(e,link.getLength());
    }

    public void createAndShowGui()
    {
        frame = new JFrame("Graph");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        graphAdapter = new JGraphXAdapter<String, MyEdge>(g);
        layout = new mxCircleLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());
        frame.add(new mxGraphComponent(graphAdapter));

        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }


}
