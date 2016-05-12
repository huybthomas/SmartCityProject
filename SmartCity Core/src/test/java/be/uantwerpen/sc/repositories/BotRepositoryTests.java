package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.SmartCityCoreApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.BotEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by Niels on 17/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmartCityCoreApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class BotRepositoryTests
{
    @Autowired
    private BotRepository botRepository;

    @Test
    @Transactional
    public void testSaveBot()
    {
        //Get repository size before test
        int origBotRepositorySize = (int)botRepository.count();

        //Setup bot
        BotEntity bot = new BotEntity();
        bot.setState("Test");

        //Save bot, verify has ID value after save
        assertNull(bot.getRid());       //Null before save
        botRepository.save(bot);
        assertNotNull(bot.getRid());    //Not null after save

        //Fetch from database
        BotEntity fetchedBot = botRepository.findOne(bot.getRid());

        //Should not be null
        assertNotNull(fetchedBot);

        //Should equal
        assertEquals(bot.getRid(), fetchedBot.getRid());
        assertEquals(bot.getState(), fetchedBot.getState());

        //Update description and save
        fetchedBot.setState("NewState");
        botRepository.save(fetchedBot);

        //Get from database, should be updated
        BotEntity fetchedUpdatedBot = botRepository.findOne(fetchedBot.getRid());
        assertEquals(fetchedBot.getState(), fetchedUpdatedBot.getState());

        //Verify count of bots in database
        long botCount = botRepository.count();
        assertEquals(botCount, origBotRepositorySize + 1);      //One bot has been added to the database

        //Get all bots, list should only have one more then initial value
        Iterable<BotEntity> bots = botRepository.findAll();

        int count = 0;

        for(BotEntity p : bots)
        {
            count++;
        }

        //There are originally 'origBotRepositorySize' bots declared in the database (+1 has been added in this test)
        assertEquals(count, origBotRepositorySize + 1);
    }

    @Test
    @Transactional
    public void testDeleteBot()
    {
        //Get repository size before test
        int origBotRepositorySize = (int)botRepository.count();

        //Setup bot
        BotEntity bot = new BotEntity();

        //Save bot, verify if it has ID value after save
        assertNull(bot.getRid());           //Null before save
        botRepository.save(bot);
        assertNotNull(bot.getRid());        //Not null after save

        //Verify count of bots in database
        long botCount = botRepository.count();
        assertEquals(botCount, origBotRepositorySize + 1);      //One bot has been added to the database

        //Fetch from database
        BotEntity fetchedBot = botRepository.findOne(bot.getRid());

        //Should not be null
        assertNotNull(fetchedBot);

        //Delete bot from database
        botRepository.delete(fetchedBot.getRid());

        //Fetch from database (should not exist anymore
        fetchedBot = botRepository.findOne(fetchedBot.getRid());

        //Should be null
        assertNull(fetchedBot);

        //Verify count of bots in database
        botCount = botRepository.count();
        assertEquals(botCount, origBotRepositorySize);          //One bot has been deleted to the database

        //Get all bots, list should have the same amount than the initial value
        Iterable<BotEntity> bots = botRepository.findAll();

        int count = 0;

        for(BotEntity p : bots)
        {
            count++;
        }

        //There are originally 'origBotRepositorySize' bots declared in the database
        assertEquals(count, origBotRepositorySize);
    }
}


