package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.Bot;
import be.uantwerpen.sc.repositories.BotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
public class BotControlService
{
    @Autowired
    private BotRepository botRepository;

    public void saveBot(Bot bot){
        botRepository.save(bot);
    }

    public Bot getBot(Long id){
        return botRepository.findOne(id);
    }

    public List<Bot> getAllBots(){
        return botRepository.findAll();
    }

    public  void updateBot(Bot bot)
    {
        Bot dbBot = botRepository.findOne(bot.getRid());
        dbBot = bot;
        //dbBot.setLinkId(bot.getLinkId());
        botRepository.save(dbBot);
    }

    public void deleteBot(Long rid){
        botRepository.delete(rid);
    }

    public void resetBots(){
        botRepository.deleteAll();
    }
}
