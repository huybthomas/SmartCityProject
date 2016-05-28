package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar;

import be.uantwerpen.sc.services.sockets.SimSocket;

/**
 * Created by Thomas on 28/05/2016.
 */
public class EventHandler
{
    private QueueHandler events = new QueueHandler();

    public EventHandler()
    {
        this.events = new QueueHandler();
    }

    public void addEvent(String event)
    {
        this.events.addElement(event + "\r\n");
    }

    public void flushEvents()
    {
        this.events.flush();
    }

    public void processEvents(SimSocket socket)
    {
        while(!this.events.isEmpty())
        {
            String event = (String)this.events.getNextElement();

            socket.sendMessage(event);
        }
    }
}
