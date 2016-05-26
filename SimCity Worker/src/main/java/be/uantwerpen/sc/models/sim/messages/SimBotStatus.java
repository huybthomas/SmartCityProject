package be.uantwerpen.sc.models.sim.messages;

/**
 * Created by Thomas on 26/02/2016.
 */
public class SimBotStatus
{
    private final int id;
    private final String type;
    private final String name;
    private final boolean running;

    public SimBotStatus(int id, String type, String name, boolean running)
    {
        this.id = id;
        this.type = type;
        this.name = name;
        this.running = running;
    }

    public int getId()
    {
        return this.id;
    }

    public String getType()
    {
        return this.type;
    }

    public String getName()
    {
        return this.name;
    }

    public boolean getRunningState()
    {
        return this.running;
    }
}
