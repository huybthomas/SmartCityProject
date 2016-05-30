package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.sim.SimBot;
import be.uantwerpen.sc.models.sim.messages.SimBotStatus;
import be.uantwerpen.sc.tools.Terminal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Thomas on 26/02/2016.
 */
@Service
public class SimSupervisorService
{
    @Value("${sc.core.ip:localhost}")
    private String serverCoreIP;

    @Value("#{new Integer(${sc.core.port}) ?: 1994}")
    private int serverCorePort;

    private List<SimBot> bots;

    public SimSupervisorService()
    {
        this.bots = new ArrayList<SimBot>();
    }

    public SimBotStatus getBotStatus(int botId)
    {
        SimBot bot = this.getBot(botId);

        if(bot != null)
        {
            return bot.getBotStatus();
        }
        else
        {
            return null;
        }
    }

    public List<SimBotStatus> getAllBotStats()
    {
        List<SimBotStatus> stats = new ArrayList<SimBotStatus>();

        for(SimBot simBot : this.bots)
        {
            stats.add(simBot.getBotStatus());
        }

        return stats;
    }

    public String getBotLog(int botId)
    {
        SimBot bot = this.getBot(botId);

        if(bot != null)
        {
            return bot.getLog();
        }
        else
        {
            return null;
        }
    }

    public boolean addNewBot(SimBot bot)
    {
        boolean status;
        int nextId = this.getNextId();

        bot.setId(nextId);
        bot.setName("bot-" + nextId);

        return bots.add(bot);
    }

    public boolean removeBot(int botId)
    {
        SimBot bot = this.getBot(botId);

        return this.removeBot(bot);
    }

    public boolean removeBot(String botName)
    {
        SimBot bot = this.getBot(botName);

        return this.removeBot(bot);
    }

    public boolean startBot(int botId)
    {
        SimBot bot = this.getBot(botId);

        if(bot != null)
        {
            bot.setServerCoreAddress(this.serverCoreIP, this.serverCorePort);

            return bot.start();
        }
        else
        {
            return false;
        }
    }

    public boolean stopBot(int botId)
    {
        SimBot bot = this.getBot(botId);

        if(bot != null)
        {
            return bot.stop();
        }
        else
        {
            return false;
        }
    }

    public boolean restartBot(int botId)
    {
        SimBot bot = this.getBot(botId);

        if(bot != null)
        {
            return bot.restart();
        }
        else
        {
            return false;
        }
    }

    public boolean setBotProperty(int botId, String property, String value)
    {
        SimBot bot = this.getBot(botId);

        if(bot != null)
        {
            try
            {
                return bot.parseProperty(property, value);
            }
            catch(Exception e)
            {
                Terminal.printTerminalError(e.getMessage());

                return false;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean removeBot(SimBot bot)
    {
        if(bot != null)
        {
            bot.stop();

            return this.bots.remove(bot);
        }
        else
        {
            return false;
        }
    }

    private int getNextId()
    {
        int nextId = 0;
        Iterator<SimBot> it = this.bots.iterator();

        if(!this.bots.isEmpty())
        {
            while(it.hasNext())
            {
                SimBot bot = it.next();

                if(nextId < bot.getId())
                {
                    nextId = bot.getId();
                }
            }

            nextId++;
        }

        return nextId;
    }

    private SimBot getBot(int botId)
    {
        Iterator<SimBot> it = this.bots.iterator();

        while(it.hasNext())
        {
            SimBot bot = it.next();

            if(bot.getId() == botId)
            {
                return bot;
            }
        }

        return null;
    }

    private SimBot getBot(String botName)
    {
        Iterator<SimBot> it = this.bots.iterator();

        while(it.hasNext())
        {
            SimBot bot = it.next();

            if(bot.getName() == botName)
            {
                return bot;
            }
        }

        return null;
    }
}
