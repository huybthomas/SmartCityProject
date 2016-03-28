package be.uantwerpen.sc.repositories;

import be.uantwerpen.sc.SmartCityCoreApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.repositories.BotRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

/**
 * Created by Niels on 17/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmartCityCoreApplication.class)
@WebAppConfiguration
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
public class BotRepositoryTests {
    @Autowired
    BotRepository botRepository;

    @Test
    public void testSaveBot(){
        PointEntity p1 = new PointEntity();
        PointEntity p2 = new PointEntity();
        LinkEntity l1 = new LinkEntity();
        BotEntity bot = new BotEntity();
        bot.setState("Test");

        //save product, verify has ID value after save
        //assertNull(bot.getRid()); //null before save
        botRepository.save(bot);
        assertNotNull(bot.getRid()); //not null after save

        //fetch from DB
        BotEntity fetchBot = botRepository.findOne(bot.getRid());

        //should not be null
        assertNotNull(fetchBot);

        //should equal
        assertEquals(bot.getRid(), fetchBot.getRid());
        assertEquals(bot.getState(), fetchBot.getState());

        //update description and save
        fetchBot.setState("NewState");
        botRepository.save(fetchBot);

        //get from DB, should be updated
        BotEntity fetchedUpdatedBot = botRepository.findOne(fetchBot.getRid());
        assertEquals(fetchBot.getState(), fetchedUpdatedBot.getState());

        //verify count of products in DB
        long botCount = botRepository.count();
        assertEquals(botCount, 1);

        //get all products, list should only have one more then initial value
        Iterable<BotEntity> bots = botRepository.findAll();

        int count = 0;

        for(BotEntity p : bots){
            count++;
        }

        assertEquals(count, 1);// we starten reeds met 2 gebruikers in de database
    }
}


