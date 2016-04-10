package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.models.RobotThread;
import be.uantwerpen.sc.services.BotControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        bot.setRobotState("test");
        botControlService.saveBot(bot);
    }

    @RequestMapping(value = "goto/{id}/{rid}",method = RequestMethod.GET)
    public String goTo(@PathVariable("id") Long id, @PathVariable("rid") Long rid){

        BotEntity botEntity = botControlService.getBot(rid);
        if (botEntity != null){
           // RobotThread robotThread = new RobotThread(botEntity);
            //robotThread.start();
            try {
                //Release location
                // Acquire Lock for next point
                if(botEntity.getPercentageCompleted() >= 50){
                    botEntity.getLinkId().getStopId().getPointLock().acquire();
                    botEntity.getLinkId().getStartId().getPointLock().release();
                }
            } catch (InterruptedException e) {
                System.out.println("received InterruptedException");
            }
        }
        else{System.out.println("Robot doesnt exist");}


        return "Something";
    }
}
