package be.uantwerpen.sc.models.sim;

/**
 * Created by Thomas on 25/02/2016.
 */
public abstract class SimBot implements Runnable
{
    private Thread simulationThread;
    private boolean running;
    protected String type;
    protected String name;

    public SimBot(String name)
    {
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

        if(simulationThread == null)
        {
            this.simulationThread = new Thread(this);
        }

        this.simulationThread.start();

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

    @Override
    public void run()
    {
        while(this.running)
        {
            this.simulationProcess();
        }
    }

    abstract protected void simulationProcess();
}
