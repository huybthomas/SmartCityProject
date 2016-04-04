package be.uantwerpen.sc.services.sim;

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
        //return this.simWorkerRepository.findAll();

        List<SimWorker> workers = new ArrayList<SimWorker>();

        SimWorker worker1 = new SimWorker();
        worker1.setId(1L);
        worker1.setWorkerName("Worker1");

        SimWorker worker2 = new SimWorker();
        worker2.setId(2L);
        worker2.setWorkerName("Worker2");

        workers.add(worker1);
        workers.add(worker2);

        return workers;
    }

    public int getNumberOfWorkers()
    {
        return this.simWorkerRepository.findAll().size();
    }
}
