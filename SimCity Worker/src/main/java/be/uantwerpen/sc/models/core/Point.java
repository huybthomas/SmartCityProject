package be.uantwerpen.sc.models.core;

/**
 * Created by Thomas on 26/02/2016.
 */
public class Point
{
    private final int id;
    private final String rfId;

    public Point(int id, String rfId)
    {
        this.id = id;
        this.rfId = rfId;
    }

    public int getId()
    {
        return this.id;
    }

    public String getRfId()
    {
        return this.rfId;
    }
}
