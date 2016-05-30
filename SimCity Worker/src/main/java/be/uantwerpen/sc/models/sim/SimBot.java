package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.sim.messages.SimBotStatus;
import be.uantwerpen.sc.services.sockets.SimSocketService;

import javax.annotation.PostConstruct;

/**
 * Created by Thomas on 25/02/2016.
 */
public abstract class SimBot implements Runnable
{
    private Thread simulationThread;
    protected boolean running;
    protected int id;
    protected String type;
    protected String name;
    protected String serverCoreIP;
    protected int serverCorePort;

    protected SimBot()
    {
        this.running = false;
        this.type = "bot";
        this.serverCoreIP = "localhost";
        this.serverCorePort = 1994;

        this.name = "SimBot";
    }

    public SimBot(String name)
    {
        this();

        this.name = name;
    }

    public void setServerCoreAddress(String serverIP, int serverPort)
    {
        this.serverCoreIP = serverIP;
        this.serverCorePort = serverPort;
    }

    public boolean start()
    {
        if(this.running)
        {
            return false;
        }

        this.simulationThread = new Thread(this);

        this.simulationThread.start();

        this.running = true;

        return true;
    }

    public boolean restart()
    {
        if(simulationThread != null)
        {
            if(this.running)
            {
                this.running = false;
            }

            while(simulationThread.isAlive())
            {
                //Wait for thread to stop
            }
        }

        return this.start();
    }

    public boolean stop()
    {
        if(!this.running || this.simulationThread == null)
        {
            return false;
        }

        if(this.running)
        {
            this.running = false;
        }

        return true;
    }

    public boolean interrupt()
    {
        if(this.simulationThread == null)
        {
            return false;
        }

        if(this.simulationThread.isAlive())
        {
            this.simulationThread.interrupt();
            this.running = false;
        }

        return true;
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getType()
    {
        return this.type;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public SimBotStatus getBotStatus()
    {
        String status;

        if(this.running)
        {
            status = "Running";
        }
        else
        {
            status = "Off";
        }

        SimBotStatus simBotStatus = new SimBotStatus(this.id, this.type, this.name, status);

        return simBotStatus;
    }

    public String getLog()
    {
        return "No logging available for this bot!";
    }

    @Override
    public void run()
    {
        while(this.running)
        {
            this.simulationProcess();
        }
    }

    public boolean parseProperty(String property, String value) throws Exception
    {
        switch(property.toLowerCase().trim())
        {
            case "name":
                setName(value);
                return true;
            default:
                return false;
        }
    }

    abstract protected void simulationProcess();
}
