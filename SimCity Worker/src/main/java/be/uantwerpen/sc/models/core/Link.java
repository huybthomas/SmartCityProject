package be.uantwerpen.sc.models.core;

/**
 * Created by Thomas on 26/02/2016.
 */
public class Link
{
    private final Point start;
    private final Point end;
    private final long length;

    public Link(Point start, Point end, long length)
    {
        this.start = start;
        this.end = end;
        this.length = length;
    }

    public Point getStart()
    {
        return this.start;
    }

    public Point getEnd()
    {
        return this.end;
    }

    public long getLength()
    {
        return this.length;
    }
}
