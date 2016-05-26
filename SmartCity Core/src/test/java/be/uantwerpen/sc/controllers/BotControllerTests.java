package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.SmartCityCoreApplication;
import be.uantwerpen.sc.configurations.SystemPropertyActiveProfileResolver;
import be.uantwerpen.sc.models.LinkEntity;
import be.uantwerpen.sc.models.PointEntity;
import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.services.BotControlService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.google.gson.Gson;

/**
 * Created by Niels on 17/03/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SmartCityCoreApplication.class)
@ActiveProfiles(profiles = {"dev"}, resolver = SystemPropertyActiveProfileResolver.class)
public class BotControllerTests {

    @Mock
    private BotControlService botControlService;

    @InjectMocks
    private BotController botController;

    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private BotEntity bot;
    private Gson gson = new Gson();
    String json;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(botController).build();
    }

    @Test
    public void testBotGetInterface() throws Exception
    {
        mockMvc.perform(get("/bot/test"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveBot() throws Exception
    {
        PointEntity p1 = new PointEntity();
        PointEntity p2 = new PointEntity();
        LinkEntity l1 = new LinkEntity();
        bot = new BotEntity();

        json = gson.toJson(bot);
        System.out.println(json);

        mockMvc
            .perform(
                    post("/api/researcher/").content(json)
                            .contentType(APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated());
    }

}


