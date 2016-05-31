package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.Bot;
import be.uantwerpen.sc.repositories.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
public class BotControlService
{
    @Autowired
    private BotRepository botRepository;

    public Bot saveBot(Bot bot)
    {
        return botRepository.save(bot);
    }

    public Bot getBot(Long id)
    {
        return botRepository.findOne(id);
    }

    public List<Bot> getAllBots()
    {
        return botRepository.findAll();
    }

    public  void updateBot(Bot bot)
    {
        Bot dbBot = botRepository.findOne(bot.getRid());
        dbBot = bot;
        //dbBot.setLinkId(bot.getLinkId());
        botRepository.save(dbBot);
    }

    public boolean deleteBot(long rid)
    {
        if(this.getBot(rid) == null)
        {
            //Could not find bot with rid
            return false;
        }
        else
        {
            botRepository.delete(rid);
            return true;
        }
    }

    public boolean resetBots()
    {
        botRepository.deleteAll();

        return true;
    }
}
