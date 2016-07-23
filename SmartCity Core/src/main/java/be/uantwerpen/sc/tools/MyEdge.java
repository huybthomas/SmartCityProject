package be.uantwerpen.sc.tools;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * Created by Niels on 1/04/2016.
 */
@Deprecated
public class MyEdge extends DefaultWeightedEdge
{
    @Override
    public String toString()
    {
        return String.valueOf(getWeight());
    }
}
