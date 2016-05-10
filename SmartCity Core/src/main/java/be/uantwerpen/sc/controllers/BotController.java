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

    @RequestMapping(value = "updateBotTest/{id}",method = RequestMethod.GET)
    public void updateBotTest(@PathVariable("id") Long id){
        BotEntity botEntity = new BotEntity();
        botEntity.setRid(id);
        botEntity.setState("Updated");
        botControlService.updateBot(botEntity);
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

    @RequestMapping(value = "goto/{id}/{rid}",method = RequestMethod.GET)
    public String goTo(@PathVariable("id") Long id, @PathVariable("rid") Long rid){

        BotEntity botEntity = botControlService.getBot(rid);
        /*if (!pointEntities.contains(botEntity.getLinkId().getStopId())){
            pointEntities.add(botEntity.getLinkId().getStopId());
        }*/
        if (botEntity != null) {
            if (botEntity.getPercentageCompleted() >= 50) {
               // stack.push(botEntity.getLinkId().getStopId());
            }
        }
        else{System.out.println("Robot doesnt exist");}

        return "Something";
    }

    @RequestMapping(value = "newRobot",method = RequestMethod.GET)
    public Long newRobot(){
        Long newID;
        if(botControlService.getAllBots() != null &&!botControlService.getAllBots().isEmpty()) {
            //Get new ID
            Long lastID = botControlService.getAllBots().get(botControlService.getAllBots().size()).getRid();
            newID = ++lastID;
        }else{
            newID = (long)0;
        }

        //Save Robot
        BotEntity bot = new BotEntity();
        bot.setRid(newID);
        botControlService.saveBot(bot);

        return newID;
    }

    public void updateLocation(Long id, int mm){
        BotEntity botEntity = this.getBot(id);
        botEntity.setPercentageCompleted(mm);
        botEntity.setState("Updated");
        botControlService.updateBot(botEntity);
    }
}
