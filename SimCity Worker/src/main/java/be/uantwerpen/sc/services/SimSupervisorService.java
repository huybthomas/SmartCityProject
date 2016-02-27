package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.sim.SimBot;
import be.uantwerpen.sc.models.sim.messages.SimBotStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 26/02/2016.
 */
@Service
public class SimSupervisorService
{
    private List<SimBot> bots;
    private int nextId;

    public SimSupervisorService()
    {
        this.bots = new ArrayList<SimBot>();

        this.nextId = 0;
    }

    public SimBotStatus getBotStatus(int botId)
    {
        return new SimBotStatus(0, "");
    }
}
