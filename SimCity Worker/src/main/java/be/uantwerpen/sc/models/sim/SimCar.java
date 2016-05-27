package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.core.Point;
import be.uantwerpen.sc.services.SimCoresService;
import be.uantwerpen.sc.tools.Terminal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 26/02/2016.
 */
public class SimCar extends SimVehicle
{
    private SimCore carCore;

    public SimCar()
    {
        super("bot", null, 0);

        this.type = "car";
        this.carCore = null;
    }

    public SimCar(String name, Point startPoint, long simSpeed)
    {
        super(name, startPoint, simSpeed);

        this.type = "car";
        this.carCore = null;
    }

    @Override
    protected void simulationProcess()
    {
        List<String> coreArguments = new ArrayList<String>();

        if(this.carCore == null)
        {
            this.carCore = SimCoresService.getSimulationCore(this.type);
        }

        if(this.carCore != null)
        {
            this.carCore.start(coreArguments);
        }
        else
        {
            //No core available
            Terminal.printTerminalError("Could not run Core for Car simulation!");

            this.stop();

            return;
        }

        while(this.isRunning())
        {
            System.out.println("Simulation running...");
            System.out.println("STATUS: " + this.carCore.getStatus());

            try
            {
                Thread.sleep(2500);
            }
            catch(Exception e)
            {

            }
        }

        this.carCore.stop();
    }
}
