package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar;

import be.uantwerpen.sc.services.sockets.SimSocket;
import be.uantwerpen.sc.services.sockets.SimSocketService;

/**
 * Created by Thomas on 28/05/2016.
 */
public class SmartCar
{
    private final static String version = "0.0.1";
    private SimSocket taskSocket;
    private SimSocket eventSocket;
    private String name;
    private DriveHandler driveHandler;
    private TaskHandler taskHandler;
    private EventHandler eventHandler;

    public SmartCar()
    {
        this.taskSocket = null;
        this.eventSocket = null;
        this.name = "SimBot";

        this.driveHandler = new DriveHandler();
        this.eventHandler = new EventHandler();
        this.taskHandler = new TaskHandler(driveHandler, eventHandler);
    }

    public SmartCar(String name, float speed)
    {
        this();
        this.name = name;

        this.driveHandler.setSpeed(speed);
    }

    public String getVersion()
    {
        return this.version;
    }

    public void checkConnections(SimSocketService taskSocketService, SimSocketService eventSocketService)
    {
        boolean socketReset = false;

        //Verify task socket
        if(taskSocket != null)
        {
            if(taskSocket.isClosed())
            {
                socketReset = true;
            }
        }
        else
        {
            socketReset = true;
        }

        if(socketReset)
        {
            //Get new connection
            taskSocket = taskSocketService.getConnection();

            if(taskSocket != null)
            {
                //Send init message
                taskSocket.sendMessage("SmartCity Car: " + this.name + " - Version: " + this.version + "\r\n#");
            }
        }

        //Verify event socket
        socketReset = false;

        if(eventSocket != null)
        {
            if(eventSocket.isClosed())
            {
                socketReset = true;
            }
        }
        else
        {
            socketReset = true;
        }

        if(socketReset)
        {
            //Get new connection
            eventSocket = eventSocketService.getConnection();

            if(eventSocket != null)
            {
                //Send init message
                eventSocket.sendMessage("SmartCity Car: " + this.name + " - Version: " + this.version + "\r\n");
            }
        }
    }

    public void updateSimulation(double elapsedTime)
    {
        //Update drive module
        if(driveHandler.updatePosition(elapsedTime))
        {
            //Target position reached, generate event
            if(eventSocket != null)
            {
                eventSocket.sendMessage("DRIVE EVENT: FINISHED\r\n");
            }
        }

        //Process available tasks
        if(this.taskSocket != null)
        {
            taskHandler.processMessage(this.taskSocket);
        }

        //Process available events
        if(this.eventSocket != null)
        {
            eventHandler.processEvents(this.eventSocket);
        }
        else
        {
            //Flush generated events to simulate the disabled event generator
            this.eventHandler.flushEvents();
        }

        //Start next drive task if available and not driving
        driveHandler.startNextDriveTask();
    }
}
