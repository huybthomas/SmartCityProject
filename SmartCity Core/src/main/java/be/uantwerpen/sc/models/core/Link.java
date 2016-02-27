package be.uantwerpen.sc.models.core;

import be.uantwerpen.sc.models.MyAbstractPersistable;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * Created by Thomas on 27/02/2016.
 */
@Entity
public class Link extends MyAbstractPersistable<Long>
{
    @NotNull
    private Point start;

    @NotNull
    private Point end;

    private long length;

    protected Link()
    {
        this.start = new Point();
        this.end = new Point();
        this.length = 0;
    }

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

    public void setStart(Point start)
    {
        this.start = start;
    }

    public Point getEnd()
    {
        return this.end;
    }

    public void setEnd(Point end)
    {
        this.end = end;
    }

    public long getLength()
    {
        return this.length;
    }

    public void setLength(long length)
    {
        this.length = length;
    }
}
