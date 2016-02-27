package be.uantwerpen.sc.models.sim;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimMap
{
    private int sizeX, sizeY;
    private List<SimLink> links;

    public SimMap()
    {
        this.sizeX = 0;
        this.sizeY = 0;
        this.links = new ArrayList<SimLink>();
    }

    public int getSizeX()
    {
        return this.sizeX;
    }

    public void setSizeX(int size)
    {
        this.sizeX = size;
    }

    public int getSizeY()
    {
        return this.sizeY;
    }

    public void setSizeY(int size)
    {
        this.sizeY = size;
    }

    public void addLinks(List<SimLink> links)
    {
        this.links.addAll(links);
    }

    public void setLinks(List<SimLink> links)
    {
        this.links = links;
    }

    public List<SimLink> getLinks()
    {
        return this.links;
    }
}
