package be.uantwerpen.sc.controllers;

import be.uantwerpen.sc.models.Bot;
import be.uantwerpen.sc.services.BotControlService;
import be.uantwerpen.sc.services.LinkControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private LinkControlService linkControlService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Bot> allBots()
    {
        List<Bot> robotEntityList = botControlService.getAllBots();

        return robotEntityList;
    }

    @RequestMapping(value = "{id}",method = RequestMethod.GET)
    public Bot getBot(@PathVariable("id") Long id)
    {
        return botControlService.getBot(id);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.POST)
    public void saveBot(@PathVariable("id") Long id, @RequestBody Bot bot)
    {
        botControlService.saveBot(bot);
    }

    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public void updateBot(@PathVariable("id") Long id, @RequestBody Bot bot)
    {
        botControlService.updateBot(bot);
    }

    @RequestMapping(value = "updateBotTest/{id}",method = RequestMethod.GET)
    public void updateBotTest(@PathVariable("id") Long id)
    {
        Bot botEntity = new Bot();
        botEntity.setRid(id);
        botEntity.setState("Updated");
        botControlService.updateBot(botEntity);
    }

    @RequestMapping(value = "test",method = RequestMethod.GET)
    public Bot testRestBot()
    {
        return new Bot();
    }

    @RequestMapping(value = "savetest",method = RequestMethod.GET)
    public void saveBotTest()
    {
        Bot bot = new Bot();
        bot.setState("test");
        botControlService.saveBot(bot);
    }

    @RequestMapping(value = "goto/{id}/{rid}",method = RequestMethod.GET)
    public String goTo(@PathVariable("id") Long id, @PathVariable("rid") Long rid)
    {
        Bot botEntity = botControlService.getBot(rid);
        /*if (!pointEntities.contains(botEntity.getLinkId().getStopId())){
            pointEntities.add(botEntity.getLinkId().getStopId());
        }*/
        if (botEntity != null)
        {
            if (botEntity.getPercentageCompleted() >= 50)
            {
               // stack.push(botEntity.getLinkId().getStopId());
            }
        }
        else
        {
            System.out.println("Robot does not exist");
        }

        return "Something";
    }

    @RequestMapping(value = "newRobot", method = RequestMethod.GET)
    public Long newRobot()
    {
        Bot bot = new Bot();

        //Save bot in database and get bot new rid
        bot = botControlService.saveBot(bot);

        Date date = new Date();
        System.out.println("New robot created!!" + date.toString());

        return bot.getRid();
    }

    @RequestMapping(value = "{id}/lid/{lid}", method = RequestMethod.GET)
    public void locationLink(@PathVariable("id") Long id, @PathVariable("lid") int lid)
    {
        Bot bot = this.getBot(id);

        if(bot != null)
        {
            bot.setLinkId(linkControlService.getLink(lid));
            botControlService.updateBot(bot);
        }
        else
        {
            System.out.println("Bot with id:" + id + " not found!");
        }
    }

    public void updateLocation(Long id, int mm)
    {
        Bot botEntity = this.getBot(id);
        botEntity.setPercentageCompleted(mm);
        botEntity.setState("Updated");
        botControlService.updateBot(botEntity);
    }

    @RequestMapping(value = "/delete/{rid}",method = RequestMethod.GET)
    public void deleteBot(@PathVariable("rid") Long rid)
    {
        botControlService.deleteBot(rid);
        System.out.println("Bot with id: " + rid + " deleted from DB");
    }

    @RequestMapping(value = "/resetbots}",method = RequestMethod.GET)
    public void resetBots()
    {
        botControlService.resetBots();
    }
}
