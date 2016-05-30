package be.uantwerpen.sc.models.sim;

import be.uantwerpen.sc.models.sim.messages.SimBotStatus;
import be.uantwerpen.sc.services.SimCoresService;
import be.uantwerpen.sc.services.sockets.SimSocketService;
import be.uantwerpen.sc.tools.Terminal;
import be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.SmartCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 26/02/2016.
 */
public class SimCar extends SimVehicle
{
    private SimCore carCore;
    private SimSocketService taskSocketService;
    private SimSocketService eventSocketService;

    public SimCar()
    {
        super("bot", 0, 70);

        this.taskSocketService = new SimSocketService();
        this.eventSocketService = new SimSocketService();
        this.type = "car";
        this.carCore = null;
    }

    public SimCar(String name, int startPoint, long simSpeed)
    {
        super(name, startPoint, simSpeed);

        this.taskSocketService = new SimSocketService();
        this.eventSocketService = new SimSocketService();
        this.type = "car";
        this.carCore = null;
    }

    @Override
    public SimBotStatus getBotStatus()
    {
        String status;

        if(carCore != null)
        {
            status = carCore.getStatus().toString();
        }
        else
        {
            status = "Off";
        }

        SimBotStatus simBotStatus = new SimBotStatus(this.id, this.type, this.name, status);

        return simBotStatus;
    }

    @Override
    public String getLog()
    {
        String log;

        if(carCore != null)
        {
            log = carCore.getLog();
        }
        else
        {
            log = "No log available yet! Please, start the bot first.";
        }

        return log;
    }

    @Override
    public boolean parseProperty(String property, String value) throws Exception
    {
        if(super.parseProperty(property, value))
        {
            return true;
        }

        switch(property.toLowerCase().trim())
        {
            default:
                return false;
        }
    }

    @Override
    protected void simulationProcess()
    {
        Thread commandSocketServiceThread = new Thread(this.taskSocketService);
        Thread eventSocketServiceThread = new Thread(this.eventSocketService);
        commandSocketServiceThread.start();
        eventSocketServiceThread.start();

        //Wait for server sockets to initialise
        while((this.taskSocketService.getListeningPort() == 0 || this.eventSocketService.getListeningPort() == 0) && this.isRunning());

        List<String> coreArguments = new ArrayList<String>();

        //Create core process arguments
        //Setup ports to simulated C-Core
        coreArguments.add("-Dcar.ccore.taskport=" + this.taskSocketService.getListeningPort());
        coreArguments.add("-Dcar.ccore.eventport=" + this.eventSocketService.getListeningPort());
        //Select random free port
        coreArguments.add("-Dserver.port=0");

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

        //Simulation process of SimCar
        this.simulateCar();

        //Stop simulation
        this.carCore.stop();

        commandSocketServiceThread.interrupt();
        eventSocketServiceThread.interrupt();

        //Wait for socket service to terminate
        while(commandSocketServiceThread.isAlive() || eventSocketServiceThread.isAlive());
    }

    private void simulateCar()
    {
        SmartCar carSimulation = new SmartCar(this.name, this.simSpeed, this.serverCoreIP, this.serverCorePort);

        boolean commandSocketReset = true;
        boolean eventSocketReset = true;

        long lastSimulationTime = System.currentTimeMillis();

        //Initialise simulation
        if(!carSimulation.initSimulation(this.startPoint))
        {
            System.err.println("Could not initialise SmartCar simulation!");
            System.err.println("Simulation will abort...");

            this.running = false;
        }

        while(this.isRunning())
        {
            //Calculated simulation time
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastSimulationTime;
            lastSimulationTime = currentTime;

            //Verify sockets
            carSimulation.checkConnections(taskSocketService, eventSocketService);

            //Update simulation
            carSimulation.updateSimulation(elapsedTime);

            try
            {
                //Sleep simulation for 10 ms (simulation resolution > 10 ms)
                Thread.sleep(10);
            }
            catch(Exception e)
            {
                //Thread is interrupted
            }
        }

        if(!carSimulation.stopSimulation())
        {
            System.err.println("Simulation layer is not stopped properly!");
        }
    }
}
