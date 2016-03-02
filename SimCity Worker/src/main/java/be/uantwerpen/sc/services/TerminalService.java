package be.uantwerpen.sc.services;

import be.uantwerpen.sc.models.sim.SimBot;
import be.uantwerpen.sc.models.sim.SimCar;
import be.uantwerpen.sc.models.sim.messages.SimBotStatus;
import be.uantwerpen.sc.tools.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Thomas on 26/02/2016.
 */
@Service
public class TerminalService
{
    @Autowired
    SimDispatchService dispatchService;

    @Autowired
    SimSupervisorService supervisorService;

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
        terminal.printTerminal(" :: SimCity Worker - 2016 ::  -  Developed by: Huybrechts T., Janssens A., Joosens D., Vervliet N.");
        terminal.printTerminal("Type 'help' to display the possible commands.");

        terminal.activateTerminal();
    }

    private void parseCommand(String commandString)
    {
        String command = commandString.split(" ", 2)[0].toLowerCase();

        switch(command)
        {
            case "exit":
                exitSystem();
                break;
            case "create":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'create {type}'");
                }
                else
                {
                    this.instantiateBot(commandString.split(" ", 2)[1]);
                }
                break;
            case "run":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'run {botId}'");
                }
                else
                {
                    int parsedInt;

                    try
                    {
                        parsedInt = this.parseInteger(commandString.split(" ", 2)[1]);

                        this.startBot(parsedInt);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }
                }
                break;
            case "stop":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'stop {botId}'");
                }
                else
                {
                    int parsedInt;

                    try
                    {
                        parsedInt = this.parseInteger(commandString.split(" ", 2)[1]);

                        this.stopBot(parsedInt);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }
                }
                break;
            case "restart":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'restart {botId}'");
                }
                else
                {
                    int parsedInt;

                    try
                    {
                        parsedInt = this.parseInteger(commandString.split(" ", 2)[1]);

                        this.restartBot(parsedInt);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }
                }
                break;
            case "kill":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'kill {botId}'");
                }
                else
                {
                    int parsedInt;

                    try
                    {
                        parsedInt = this.parseInteger(commandString.split(" ", 2)[1]);

                        this.killBot(parsedInt);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }
                }
                break;
            case "set":
                if(commandString.split(" ", 4).length <= 3)
                {
                    if(commandString.contains("help") || commandString.contains("?"))
                    {
                        this.printHelp("set");
                    }
                    else
                    {
                        terminal.printTerminalInfo("Missing arguments! 'set {botId} {property} {value}'");

                    }
                }
                else
                {
                    int botId;
                    String property = commandString.split(" ", 4)[2];
                    String value = commandString.split(" ", 4)[3];

                    try
                    {
                        this.parseInteger(commandString.split(" ", 4)[1]);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }

                    terminal.printTerminalInfo("METHOD NOT IMPLEMENTED YET!");
                }
                break;
            case "get":
                if(commandString.split(" ", 3).length <= 2)
                {
                    if(commandString.contains("help") || commandString.contains("?"))
                    {
                        this.printHelp("get");
                    }
                    else
                    {
                        terminal.printTerminalInfo("Missing arguments! 'get {botId} {property}'");

                    }
                }
                else
                {
                    int botId;
                    String property = commandString.split(" ", 4)[2];

                    try
                    {
                        botId = this.parseInteger(commandString.split(" ", 4)[1]);
                    }
                    catch(Exception e)
                    {
                        terminal.printTerminalError(e.getMessage());
                    }

                    terminal.printTerminalInfo("METHOD NOT IMPLEMENTED YET!");
                }
                break;
            case "setconfig":
                if(commandString.split(" ", 3).length <= 2)
                {
                    if(commandString.contains("help") || commandString.contains("?"))
                    {
                        printHelp("setconfig");
                    }
                    else
                    {
                        terminal.printTerminalInfo("Missing arguments! 'setconfig {property} {value}'");
                    }
                }
                else
                {
                    String property = commandString.split(" ", 3)[1];
                    String value = commandString.split(" ", 3)[2];

                    terminal.printTerminalInfo("METHOD NOT IMPLEMENTED YET!");
                }
                break;
            case "getconfig":
                if(commandString.split(" ", 2).length <= 1)
                {
                    if(commandString.contains("help") || commandString.contains("?"))
                    {
                        printHelp("getconfig");
                    }
                    else
                    {
                        terminal.printTerminalInfo("Missing arguments! 'getconfig {property}'");
                    }
                }
                else
                {
                    String property = commandString.split(" ", 3)[1];
                    String value = commandString.split(" ", 3)[2];

                    terminal.printTerminalInfo("METHOD NOT IMPLEMENTED YET!");
                }
                break;
            case "show":
                if(commandString.split(" ", 2).length <= 1)
                {
                    terminal.printTerminalInfo("Missing arguments! 'show {botId | all}'");
                }
                else
                {
                    if(commandString.split(" ", 2)[1].equals("all"))
                    {
                        this.printAllBots();
                    }
                    else
                    {
                        int botId;

                        try
                        {
                            botId = this.parseInteger(commandString.split(" ", 2)[1]);

                            this.printBotStatus(botId);
                        }
                        catch(Exception e)
                        {
                            terminal.printTerminalError(e.getMessage());
                        }
                    }
                }
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

    private void instantiateBot(String type)
    {
        SimBot bot;

        switch(type.toLowerCase().trim())
        {
            case "car":
                bot = dispatchService.instantiateBot(type);
                break;
            default:
                terminal.printTerminalInfo("Bottype: '" + type + "' is unknown!");
                terminal.printTerminalInfo("Known types: {car}");
                return;
        }

        if(bot != null)
        {
            terminal.printTerminalInfo("New bot of type: '" + bot.getType() + "' and name: '" + bot.getName() + "' instantiated.");
        }
        else
        {
            terminal.printTerminalError("Could not instantiate bot of type: " + type + "!");
        }
    }

    private void startBot(int botId)
    {
        if(supervisorService.startBot(botId))
        {
            terminal.printTerminalInfo("Bot started with id: " + botId + ".");
        }
        else
        {
            terminal.printTerminalError("Could not start bot with id: " + botId + "!");
        }
    }

    private void stopBot(int botId)
    {
        if(supervisorService.stopBot(botId))
        {
            terminal.printTerminalInfo("Bot stopped with id: " + botId + ".");
        }
        else
        {
            terminal.printTerminalError("Could not stop bot with id: " + botId + "!");
        }
    }

    private void restartBot(int botId)
    {
        if(supervisorService.restartBot(botId))
        {
            terminal.printTerminalInfo("Bot restarted with id: " + botId + ".");
        }
        else
        {
            terminal.printTerminalError("Could not restart bot with id: " + botId + "!");
        }
    }

    private void killBot(int botId)
    {
        if(supervisorService.removeBot(botId))
        {
            terminal.printTerminalInfo("Bot killed with id: " + botId + ".");
        }
        else
        {
            terminal.printTerminalError("Could not kill bot with id: " + botId + "!");
        }
    }

    private void printBotStatus(int botId)
    {
        SimBotStatus status = supervisorService.getBotStatus(botId);

        if(status == null)
        {
            terminal.printTerminalError("Could not find bot with id: " + botId + "!");
        }
        else
        {
            terminal.printTerminal("Bot-id:\t\t" + status.getId());
            terminal.printTerminal("Name:\t\t" + status.getName());
            terminal.printTerminal("Type:\t\t" + status.getType());
            terminal.printTerminal("Running:\t" + status.getRunningState());
        }
    }

    private void printAllBots()
    {
        List<SimBotStatus> stats = supervisorService.getAllBotStats();

        if(stats.isEmpty())
        {
            terminal.printTerminalInfo("There are no bots available to list.");
        }
        else
        {
            terminal.printTerminal("Bot-id\t\tType\t\tName\t\tRunning");
            terminal.printTerminal("-----------------------------------------------");

            for (SimBotStatus status : stats)
            {
                terminal.printTerminal("\t" + status.getId() + "\t\t" + status.getType() + "\t\t\t" + status.getName() + "\t\t" + status.getRunningState());
            }
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
            case "set":
                terminal.printTerminal("SET {botId} {property} {value}");
                terminal.printTerminal("------------------------------");
                terminal.printTerminal("'help' / '?' : show all configurable properties.\n");
                break;
            case "get":
                terminal.printTerminal("GET {botId} {property}");
                terminal.printTerminal("----------------------");
                terminal.printTerminal("'help' / '?' : show all configurable properties.\n");
                break;
            case "setconfig":
                terminal.printTerminal("SETCONFIG {property} {value}");
                terminal.printTerminal("----------------------------");
                terminal.printTerminal("'help' / '?' : show all configurable properties.\n");
                break;
            case "getconfig":
                terminal.printTerminal("GETCONFIG {property}");
                terminal.printTerminal("--------------------");
                terminal.printTerminal("'help' / '?' : show all configurable properties.\n");
                break;
            default:
                terminal.printTerminal("Available commands:");
                terminal.printTerminal("-------------------");
                terminal.printTerminal("'create {type}' : instantiate a bot of the given type.");
                terminal.printTerminal("'run {botId}' : start the bot with the given id.");
                terminal.printTerminal("'stop {botId}' : stop the bot with the given id.");
                terminal.printTerminal("'restart {botId}' : restart the bot with the given id.");
                terminal.printTerminal("'kill {botId}' : destroy the bot with the given id.");
                terminal.printTerminal("'set {botId} {property} {value}' : set the property of the bot with the given id.");
                terminal.printTerminal("'get {botId} {property}' : get the value of the property for the bot with the given id.");
                terminal.printTerminal("'setconfig {property} {value}' : set the system property of the worker node.");
                terminal.printTerminal("'getconfig {property}' : get the value of the property for the bot with the given id.");
                terminal.printTerminal("'show all' : give a list of all created bots.");
                terminal.printTerminal("'show {botId}' : display the details of the bot with the given id.");
                terminal.printTerminal("'exit' : shutdown the server.");
                terminal.printTerminal("'help' / '?' : show all available commands.\n");
                break;
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