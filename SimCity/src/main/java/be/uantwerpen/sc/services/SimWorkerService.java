package be.uantwerpen.sc.services;

import be.uantwerpen.sc.repositories.SimWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 03/04/2016.
 */
@Service
public class SimWorkerService
{
    @Autowired
    private SimWorkerRepository simWorkerRepository;
}
