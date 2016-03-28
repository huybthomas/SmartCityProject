package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.services.BotControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Thomas on 25/02/2016.
 */
@RestController
@RequestMapping("/bot/")
public class BotController
{
    @Autowired
    private BotControlService botControlService;

    @RequestMapping(method = RequestMethod.GET)
    public List<BotEntity> allBots(){
        List<BotEntity> robotEntityList = botControlService.getAllBots();
        return robotEntityList;
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public BotEntity getBot(@PathVariable("id") Long id){return  botControlService.getBot(id);}

    @RequestMapping(value = "{id}",method = RequestMethod.POST)
    public void saveBot(@PathVariable("id") Long id,@RequestBody BotEntity bot){
        botControlService.saveBot(bot);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public void updateBot(@PathVariable("id") Long id,@RequestBody BotEntity bot){
        botControlService.updateBot(bot);
    }

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public BotEntity testRestBot(){
        return new BotEntity();
    }

    @RequestMapping(value = "savetest",method = RequestMethod.GET)
    public void saveBotTest(){
        BotEntity bot = new BotEntity();
        bot.setState("test");
        botControlService.saveBot(bot);
    }
}
