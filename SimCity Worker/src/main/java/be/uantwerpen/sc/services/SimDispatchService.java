package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.sim.SimBot;
import be.uantwerpen.sc.models.sim.SimCar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
public class SimDispatchService
{
    @Autowired
    SimSupervisorService supervisorService;

    public SimDispatchService()
    {

    }

    public SimBot instantiateBot(String type)
    {
        SimBot bot = this.parseBot(type);

        if(bot == null)
        {
            return null;
        }

        if(supervisorService.addNewBot(bot))
        {
            return bot;
        }
        else
        {
            return null;
        }
    }

    private SimBot parseBot(String botType)
    {
        SimBot simBot;

        switch(botType.toLowerCase().trim())
        {
            case "car":
                simBot = new SimCar();
                break;
            default:
                simBot = null;
                break;
        }

        return simBot;
    }
}
