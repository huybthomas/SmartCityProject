package be.uantwerpen.sc.models.core;

/**
 * Created by Thomas on 26/02/2016.
 */
public class Position
{
    private final Link link;
    private final long posInPerc;

    public Position(Link link, long posInPerc)
    {
        this.link = link;
        this.posInPerc = posInPerc;
    }

    public Link getLink()
    {
        return this.link;
    }

    public long getPosInPerc()
    {
        return this.posInPerc;
    }
}
