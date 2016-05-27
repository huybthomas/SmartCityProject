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
    private boolean running;
    protected SimSocketService simSocketService;
    protected int id;
    protected String type;
    protected String name;

    public SimBot(String name)
    {
        this.simSocketService = new SimSocketService();
        this.running = false;
        this.type = "bot";

        this.name = name;
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

        while(simulationThread.isAlive())
        {
            //Wait for thread to stop
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
        SimBotStatus simBotStatus = new SimBotStatus(this.id, this.type, this.name, this.running);

        return simBotStatus;
    }

    @Override
    public void run()
    {
        Thread socketServiceThread = new Thread(this.simSocketService);
        socketServiceThread.start();

        while(this.running)
        {
            this.simulationProcess();
        }

        socketServiceThread.interrupt();

        //Wait for socket service to terminate
        while(socketServiceThread.isAlive());
    }

    abstract protected void simulationProcess();
}
