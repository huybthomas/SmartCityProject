package be.uantwerpen.sc.services.sim;

import be.uantwerpen.sc.models.security.User;
import be.uantwerpen.sc.models.sim.SimWorker;
import be.uantwerpen.sc.repositories.sim.SimWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 03/04/2016.
 */
@Service
public class SimWorkerService
{
    @Autowired
    private SimWorkerRepository simWorkerRepository;

    public Iterable<SimWorker> findAll()
    {
        return this.simWorkerRepository.findAll();
    }

    public boolean delete(String workerName)
    {
        SimWorker w = findByWorkerName(workerName);

        if(w != null)
        {
            this.simWorkerRepository.delete(w.getId());

            return true;
        }

        return false;
    }

    public int getNumberOfWorkers()
    {
        return this.simWorkerRepository.findAll().size();
    }

    public SimWorker findByWorkerName(String workerName)
    {
        return simWorkerRepository.findByWorkerName(workerName);
    }
}
