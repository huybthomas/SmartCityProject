package be.uantwerpen.sc.services;

import be.uantwerpen.sc.SmartCityCoreApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.models.Point;
import be.uantwerpen.sc.models.Bot;
import be.uantwerpen.sc.repositories.BotRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by Niels on 17/03/2016.
 */

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = SmartCityCoreApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
public class BotControlServiceTests {

    @Mock
    private BotRepository botRepository;

    @InjectMocks
    private BotControlService botControlService;

    private List<Bot> botList;
    private Bot bot1;

    @Before
    public void init() {

        Point p1 = new Point();
        Point p2 = new Point();
        Link l1 = new Link();
        bot1 = new Bot();
        botList = new ArrayList<>();
        botList.add(bot1);

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testNonExistingBot() throws Exception
    {
        when(botRepository.findAll()).thenReturn(botList);

        botControlService.getBot(10L);

        //assertTrue("This test has no value! getBot(10) will return null which will not throw Exceptions. 'throws Exception' for a test is not a good test condition: use assert instead.", false);
    }

    @Test
    public void testLoadBot()
    {
        when(botRepository.findAll()).thenReturn(botList);

        Bot testBot = botList.get(0);

        assertEquals(bot1,testBot);

        //assertTrue("This test has no value! No reference to BotControlService.", false);
    }
}
