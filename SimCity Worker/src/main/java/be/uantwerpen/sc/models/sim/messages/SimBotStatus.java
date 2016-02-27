package be.uantwerpen.sc.models.sim.messages;

/**
 * Created by Thomas on 26/02/2016.
 */
public class SimBotStatus
{
    private final int id;
    private final String type;

    public SimBotStatus(int id, String type)
    {
        this.id = id;
        this.type = type;
    }

    public int getId()
    {
        return this.id;
    }

    public String getType()
    {
        return this.type;
    }
}
