package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.sim.SimBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
@Deprecated
public class SimDispatchService
{
    @Autowired
    SimSupervisorService supervisorService;

    public SimDispatchService()
    {

    }

    public SimBot instantiateBot(SimBot type)
    {
        SimBot bot = null;

       /* try
        {*/
            bot = type;
       /* }
       /* catch(InstantiationException e)
        {
            System.err.println("Could not instantiate bot of type: " + type.getName());
            e.printStackTrace();

            return null;
        }
        catch(IllegalAccessException e)
        {
            System.err.println("Could not instantiate bot of type: " + type.getName());
            e.printStackTrace();

            return null;
        }*/

        if(supervisorService.addNewBot(bot))
        {
            return bot;
        }
        else
        {
            return null;
        }
    }
}
