package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.RobotEntity;
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
    public List<RobotEntity> allBots(){
        List<RobotEntity> robotEntityList = botControlService.getAllBots();
        return robotEntityList;
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public RobotEntity getBot(@PathVariable("id") int id){return  botControlService.getBot(id);}

    @RequestMapping(value = "{id}",method = RequestMethod.POST)
    public void saveBot(@PathVariable("id") int id,@RequestBody RobotEntity bot){
        botControlService.saveBot(bot);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public void updateBot(@PathVariable("id") int id,@RequestBody RobotEntity bot){
        botControlService.updateBot(bot);
    }

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public RobotEntity testRestBot(){
        return new RobotEntity();
    }

    @RequestMapping(value = "savetest",method = RequestMethod.GET)
    public void saveBotTest(){
        RobotEntity bot = new RobotEntity();
        bot.setState("test");
        botControlService.saveBot(bot);
    }



}
