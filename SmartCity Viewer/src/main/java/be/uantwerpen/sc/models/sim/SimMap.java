package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.tools.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 27/02/2016.
 */
public class SimMap
{
    private int sizeX, sizeY;
    public ArrayList<ArrayList<Tile> > mapTiles;

    public SimMap()
    {
        this.sizeX = 0;
        this.sizeY = 0;
        mapTiles = new ArrayList<>();
        mapTiles.add(new ArrayList<Tile>());
        mapTiles.get(0).add(null);
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
}
