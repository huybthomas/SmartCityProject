package be.uantwerpen.sc.repository;

import be.uantwerpen.sc.SmartCityCoreApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PuntEntity;
import be.uantwerpen.sc.models.RobotEntity;
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
public class BotRepositoryTest {
    @Autowired
    BotRepository botRepository;

    @Test
    public void testSaveBot(){
        PuntEntity p1 = new PuntEntity();
        PuntEntity p2 = new PuntEntity();
        LinkEntity l1 = new LinkEntity();
        RobotEntity bot = new RobotEntity();
        bot.setState("Test");

        //save product, verify has ID value after save
        //assertNull(bot.getRid()); //null before save
        botRepository.save(bot);
        assertNotNull(bot.getRid()); //not null after save

        //fetch from DB
        RobotEntity fetchBot = botRepository.findOne(bot.getRid());

        //should not be null
        assertNotNull(fetchBot);

        //should equal
        assertEquals(bot.getRid(), fetchBot.getRid());
        assertEquals(bot.getState(), fetchBot.getState());

        //update description and save
        fetchBot.setState("NewState");
        botRepository.save(fetchBot);

        //get from DB, should be updated
        RobotEntity fetchedUpdatedBot = botRepository.findOne(fetchBot.getRid());
        assertEquals(fetchBot.getState(), fetchedUpdatedBot.getState());

        //verify count of products in DB
        long botCount = botRepository.count();
        assertEquals(botCount, 1);

        //get all products, list should only have one more then initial value
        Iterable<RobotEntity> bots = botRepository.findAll();

        int count = 0;

        for(RobotEntity p : bots){
            count++;
        }

        assertEquals(count, 1);// we starten reeds met 2 gebruikers in de database
    }
}


