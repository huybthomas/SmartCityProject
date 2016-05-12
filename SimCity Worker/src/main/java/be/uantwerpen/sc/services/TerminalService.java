package be.uantwerpen.sc.services;


import be.uantwerpen.sc.controllers.SimCommandController;
import be.uantwerpen.sc.services.sockets.SimCCommandHandler;
import be.uantwerpen.sc.tools.Terminal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 25/02/2016.
 */
@Service
public class TerminalService
{
    private Terminal terminal;
    @Autowired
    SimCCommandHandler simCCommandHandler;
    @Autowired
    SimCommandController simCommandController;

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
        terminal.printTerminal(" :: SmartCity Core - 2016 ::  -  Developed by: Huybrechts T., Janssens A., Joosens D., Vervliet N.");
        terminal.printTerminal("Type 'help' to display the possible commands.");

        terminal.activateTerminal();
    }

    private void parseCommand(String commandString)
    {
        String command = commandString.split(" ", 2)[0].toLowerCase();

        switch(command)
        {
            case "sendcommand":
                try {
                    String command2 = commandString.split(" ", 2)[1].toLowerCase();
                    String robot = command2.split(" ", 2)[0].toLowerCase();
                    String job = command2.split(" ", 2)[1].toLowerCase();
                    System.out.println(robot + " do " + job);
                    try {
                        int robotId = Integer.parseInt(robot);
                        sendCommand(robotId, job);
                    } catch (NumberFormatException e) {
                        terminal.printTerminalError(e.getMessage());
                        terminal.printTerminal("Usage: command id message");
                    }
                }catch (Exception e){
                    terminal.printTerminal(e.getMessage());
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
                terminal.printTerminal("'addrobot': add new simulation robot");
                terminal.printTerminal("'sendcommand {robotID} {Something}': send a command to the robot c-core");
                terminal.printTerminal("'exit' : shutdown the server.");
                terminal.printTerminal("'help' / '?' : show all available commands.\n");
                break;
        }
    }

    private void sendCommand(int robotID, String message){
        switch (robotID)
        {
            case 1:
                simCommandController.sendCommand("http://146.175.140.119:8080/command/", message);
                break;
            default:
                System.out.println("Robot not found");
                break;
        }
        //cCommandSender.sendCommand(message);
    }


}
