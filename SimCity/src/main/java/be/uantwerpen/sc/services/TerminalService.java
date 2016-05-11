package be.uantwerpen.sc.services;


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
    SimCCommandSender cCommandSender;

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
                    String message = command2.split(" ", 2)[0].toLowerCase();
                    System.out.println("Robot do " + message);
                    sendCommand(message);

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
                terminal.printTerminal("'sendcommand {Something}': send a command to the robot c-core");
                terminal.printTerminal("'exit' : shutdown the server.");
                terminal.printTerminal("'help' / '?' : show all available commands.\n");
                break;
        }
    }

    private void sendCommand(String message){
        cCommandSender.sendCommand(message);
    }
}
