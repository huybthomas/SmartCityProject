package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.BotEntity;
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

    public void saveBot(BotEntity bot){
        botRepository.save(bot);
    }

    public BotEntity getBot(Long id){
        return botRepository.findOne(id);
    }

    public List<BotEntity> getAllBots(){
        return botRepository.findAll();
    }

    public  void updateBot(BotEntity bot){
        BotEntity dbBot = botRepository.findOne(bot.getRid());
        dbBot.setState(bot.getState());
        dbBot.setPercentageCompleted(bot.getPercentageCompleted());
        //dbBot.setLinkId(bot.getLinkId());
        botRepository.save(dbBot);
    }

}
