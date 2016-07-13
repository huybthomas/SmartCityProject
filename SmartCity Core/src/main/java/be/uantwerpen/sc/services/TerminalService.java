package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.Bot;
import be.uantwerpen.sc.models.Link;
import be.uantwerpen.sc.tools.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
public class TerminalService
{
    @Autowired
    private JobService jobService;
    
    @Autowired
    private BotControlService botControlService;
    
    private Terminal terminal;

    public TerminalService()
    {
        terminal = new Terminal()
        {
            @Override
            public void executeCommand(String commandString)
            {
                parseCommand(commandString);
            }
        };
    }

    public void systemReady()
    {
        terminal.printTerminal("\nSmartCity Core [Version " + getClass().getPackage().getImplementationVersion() + "]\n(c) 2015-2017 University of Antwerp. All rights reserved.");
        terminal.printTerminal("Type 'help' to display the possible commands.");

        terminal.activateTerminal();
    }

    private void parseCommand(String commandString)
    {
        String command = commandString.split(" ", 2)[0].toLowerCase();

        switch(command)
        {
            case "job":
                if(commandString.split(" ", 3).length <= 2)
                {
                    terminal.printTerminalInfo("Missing arguments! 'job {botId} {command}");
                }
                else
                {
                    int parsedInt;

                    try
                    {
                        parsedInt = this.parseInteger(commandString.split(" ", 3)[1]);

                        this.sendJob(parsedInt, commandString.split(" ", 3)[2]);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }
                }
                break;
            case "show":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'show {bots}'");
                }
                else
                {
                    if(commandString.split(" ", 2)[1].equals("bots"))
                    {
                        this.printAllBots();
                    }
                    else
                    {
                        terminal.printTerminalInfo("Unknown arguments! 'show {bots}'");
                    }
                }
                break;
            case "reset":
                this.resetBots();
                break;
            case "delete":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'delete {botId}'");
                }
                else
                {
                    int parsedInt;

                    try
                    {
                        parsedInt = this.parseInteger(commandString.split(" ", 2)[1]);

                        this.deleteBot(parsedInt);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }
                }
                break;
            case "exit":
                exitSystem();
                break;
            case "help":
            case "?":
                printHelp("");
                break;
            default:
                terminal.printTerminalInfo("Command: '" + command + "' is not recognized.");
                break;
        }
    }

    private void exitSystem()
    {
        System.exit(0);
    }

    private void printHelp(String command)
    {
        switch(command)
        {
            default:
                terminal.printTerminal("Available commands:");
                terminal.printTerminal("-------------------");
                terminal.printTerminal("'job {botId} {command}' : send a job to the bot with the given id.");
                terminal.printTerminal("'show {bots}' : show all bots in the database.");
                terminal.printTerminal("'reset' : remove all bots from the database.");
                terminal.printTerminal("'delete {botId}' : remove the bot with the given id from the database.");
                terminal.printTerminal("'exit' : shutdown the server.");
                terminal.printTerminal("'help' / '?' : show all available commands.\n");
                break;
        }
    }

    private void printAllBots()
    {
        List<Bot> bots = botControlService.getAllBots();

        if(bots.isEmpty())
        {
            terminal.printTerminalInfo("There are no bots available to list.");
        }
        else
        {
            terminal.printTerminal("Bot-id\t\tLink-id\t\tStatus");
            terminal.printTerminal("------------------------------");

            for(Bot bot : bots)
            {
                int linkId = -1;
                Link link = bot.getLinkId();

                if(link != null)
                {
                    linkId = link.getLid();
                }

                terminal.printTerminal("\t" + bot.getRid() + "\t\t" + linkId + "\t\t\t" + bot.getState());
            }
        }
    }

    private void deleteBot(int botID)
    {
        if(botControlService.deleteBot(botID))
        {
            terminal.printTerminalInfo("Bot deleted with id: " + botID + ".");
        }
        else
        {
            terminal.printTerminalError("Could not delete bot with id: " + botID + "!");
        }
    }

    private void resetBots()
    {
        if(botControlService.resetBots())
        {
            terminal.printTerminalInfo("All bot entries cleared from database.");
        }
        else
        {
            terminal.printTerminalError("Could not clear all robots from the database.");
        }
    }

    private void sendJob(int botId, String command)
    {
        if(botControlService.getBot((long)botId) == null)
        {
            //Could not find bot in database
            terminal.printTerminalError("Could not find bot with id: " + botId + "!");
        }

        if(jobService.sendJob(botId, command))
        {
            terminal.printTerminalInfo("Job send to bot with id: " + botId + ".");
        }
        else
        {
            terminal.printTerminalError("Could not send job to bot with id: " + botId + "!");
        }
    }

    private int parseInteger(String value) throws Exception
    {
        int parsedInt;

        try
        {
            parsedInt = Integer.parseInt(value);
        }
        catch(NumberFormatException e)
        {
            throw new Exception("'" + value + "' is not an integer value!");
        }

        return parsedInt;
    }
}
