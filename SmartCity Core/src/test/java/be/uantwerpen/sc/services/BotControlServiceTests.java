package be.uantwerpen.sc.services;

import be.uantwerpen.sc.SmartCityCoreApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.BotEntity;
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
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Niels on 17/03/2016.
 */

@RunWith(MockitoJUnitRunner.class)
@SpringApplicationConfiguration(classes = SmartCityCoreApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
@WebAppConfiguration
public class BotControlServiceTests {

    @InjectMocks
    BotControlService botControlService;

    @Mock
    private BotRepository botRepository;

    List<BotEntity> botList;
    BotEntity bot1;

    @Before
    public void init() {

        PointEntity p1 = new PointEntity();
        PointEntity p2 = new PointEntity();
        LinkEntity l1 = new LinkEntity();
        bot1 = new BotEntity();
        botList = new ArrayList<>();
        botList.add(bot1);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void nonExistingBotTest() throws Exception
    {
        when(botRepository.findAll()).thenReturn(botList);
        botControlService.getBot(10L);
    }

    @Test
    public void loadBot()
    {
        when(botRepository.findAll()).thenReturn(botList);
        BotEntity testBot = botList.get(0);
        assertEquals(bot1.getRid(),testBot.getRid());

    }
}
