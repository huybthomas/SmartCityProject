package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.SimWorker;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 27/02/2016.
 */
@Service
public class SimWorkerService
{
    private SimWorker simWorker;

    public SimWorkerService()
    {
        this.simWorker = new SimWorker();
    }

    public void setId(int id)
    {
        this.simWorker.setId(id);
    }

    public int getId()
    {
        return this.simWorker.getId();
    }

    public void setName(String name)
    {
        this.simWorker.setName(name);
    }

    public String getName()
    {
        return this.simWorker.getName();
    }
}
