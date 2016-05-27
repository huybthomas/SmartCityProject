package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.sim.messages.SimBotStatus;
import be.uantwerpen.sc.services.SimSupervisorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Thomas on 25/02/2016.
 */
@RestController
public class SimConfigController
{
    @Autowired
    private SimSupervisorService simSupervisor;

    @RequestMapping(value = {"/bots/{botid}/"}, method = RequestMethod.GET)
    public ResponseEntity<SimBotStatus> getBotStatus(@PathVariable String botid)
    {
        int parsedId = Integer.parseInt(botid);
        SimBotStatus status = simSupervisor.getBotStatus(parsedId);

        if(status != null)
        {
            //Status request succeed
            return new ResponseEntity<SimBotStatus>(status, HttpStatus.OK);
        }
        else
        {
            //Status request failed
            return new ResponseEntity<SimBotStatus>(HttpStatus.NOT_FOUND);
        }
    }
}
