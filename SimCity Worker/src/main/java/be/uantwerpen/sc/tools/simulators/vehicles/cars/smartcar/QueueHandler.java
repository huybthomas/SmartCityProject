package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Niels on 4/05/2016.
 */
public class QueueHandler
{
    private Queue<Object> queue;

    public QueueHandler()
    {
        this.queue = new LinkedBlockingQueue<>();
    }

    public boolean addElement(Object element)
    {
        return this.queue.offer(element);
    }

    public boolean isEmpty()
    {
        return this.queue.isEmpty();
    }

    public Object getNextElement()
    {
        return this.queue.poll();
    }

    public void flush()
    {
        this.queue.clear();
    }
}
