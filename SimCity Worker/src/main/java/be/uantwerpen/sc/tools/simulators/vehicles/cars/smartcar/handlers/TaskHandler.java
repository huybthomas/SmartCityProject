package be.uantwerpen.sc.tools.simulators.vehicles.cars.smartcar.handlers;

import be.uantwerpen.sc.services.sockets.SimSocket;

/**
 * Created by Thomas on 28/05/2016.
 */
public class TaskHandler
{
    private DriveHandler driveHandler;
    private EventHandler eventHandler;
    private LocationHandler locationHandler;
    private TagReaderHandler tagReaderHandler;

    private TaskHandler()
    {
        this.driveHandler = null;
        this.eventHandler = null;
        this.locationHandler = null;
        this.tagReaderHandler = null;
    }

    public TaskHandler(DriveHandler driveHandler, EventHandler eventHandler, LocationHandler locationHandler, TagReaderHandler tagReaderHandler)
    {
        this.driveHandler = driveHandler;
        this.eventHandler = eventHandler;
        this.locationHandler = locationHandler;
        this.tagReaderHandler = tagReaderHandler;
    }

    public void processMessage(SimSocket socket)
    {
        String message = socket.getMessage();
        String response = "NACK";

        if(message == null)
        {
            //No task to process
            return;
        }

        if(message.length() <= 0)
        {
            //Empty message
            return;
        }

        String task = message.split(" ")[0].trim();

        switch(task)
        {
            case "DRIVE":
                response = processDriveCommand(message);
                break;
            case "CAMERA":
                //Not implemented in simulation
                response = "NACK";
                break;
            case "TAG":
                response = processTagCommand(message);
                break;
            case "LIFT":
                //Not implemented in simulation
                response = "NACK";
                break;
            case "SPEAKER":
                //Not available in simulation
                response = "NACK";
                break;
            case "SHUTDOWN":
                //Not available in simulation
                response = "NACK";
                break;
            case "HELP":
            case "?":
                response = processHelpCommand();
                break;
            default:
                response = "UNKNOWN COMMAND";
                break;
        }

        //Send response
        socket.sendMessage(response + "\r\n# ");
    }

    private String processDriveCommand(String command)
    {
        String response = "NACK";

        if(command.startsWith("DRIVE ABORT"))
        {
            driveHandler.flushAllDriveTasks();
            driveHandler.abortDriving();
        }
        else if(command.startsWith("DRIVE FLUSH"))
        {
            driveHandler.flushAllDriveTasks();
        }
        else if(command.startsWith("DRIVE FOLLOWLINE"))
        {
            if(command.split(" ", 3).length == 2)
            {
                //Follow line until end of line
                locationHandler.startFollowLine();

                int distance = locationHandler.getDistanceTargetLocation();

                driveHandler.newDriveDistanceCommand(distance);

                return "ACK";
            }
            else
            {
                //Follow line for distance
                try
                {
                    int distance = parseInteger(command.split(" ", 3)[2]);

                    driveHandler.newDriveDistanceCommand(distance);

                    response = "ACK";
                }
                catch(Exception e)
                {
                    //Could not parse distance from command
                    response = "MALFORMED COMMAND";
                }
            }
        }
        else if(command.startsWith("DRIVE PAUSE"))
        {
            driveHandler.setPaused(true);

            response = "ACK";
        }
        else if(command.startsWith("DRIVE RESUME"))
        {
            driveHandler.setPaused(false);

            response = "ACK";
        }
        else if(command.startsWith("DRIVE FORWARD"))
        {
            try
            {
                int distance = parseInteger(command.split(" ", 3)[2]);

                driveHandler.newDriveDistanceCommand(distance);

                response = "ACK";
            }
            catch(Exception e)
            {
                //Could not parse distance from command
                response = "MALFORMED COMMAND";
            }
        }
        else if(command.startsWith("DRIVE BACKWARDS"))
        {
            try
            {
                int distance = parseInteger(command.split(" ", 3)[2]);

                driveHandler.newDriveDistanceCommand(-distance);

                response = "ACK";
            }
            catch(Exception e)
            {
                //Could not parse distance from command
                response = "MALFORMED COMMAND";
            }
        }
        else if(command.startsWith("DRIVE TURN"))
        {
            if(command.split(" ").length == 3)
            {
                if(command.split(" ")[2].equals("L"))
                {
                    driveHandler.newTurnAngleCommand(90);

                    response = "ACK";
                }
                else if(command.split(" ")[2].equals("R"))
                {
                    driveHandler.newTurnAngleCommand(-90);

                    response = "ACK";
                }
                else
                {
                    response = "MALFORMED COMMAND";
                }
            }
            else if(command.split(" ").length == 4)
            {
                if(command.split(" ")[2].equals("L"))
                {
                    try
                    {
                        int angle = parseInteger(command.split(" ")[3]);

                        driveHandler.newTurnAngleCommand(angle);

                        response = "ACK";
                    }
                    catch(Exception e)
                    {
                        //Could not parse distance from command
                        response = "MALFORMED COMMAND";
                    }
                }
                else if(command.split(" ")[2].equals("R"))
                {
                    try
                    {
                        int angle = parseInteger(command.split(" ")[3]);

                        driveHandler.newTurnAngleCommand(-angle);

                        response = "ACK";
                    }
                    catch(Exception e)
                    {
                        //Could not parse distance from command
                        response = "MALFORMED COMMAND";
                    }
                }
                else
                {
                    response = "MALFORMED COMMAND";
                }
            }
            else
            {
                response = "MALFORMED COMMAND";
            }
        }
        else if(command.startsWith("DRIVE ROTATE"))
        {
            if(command.split(" ").length == 4)
            {
                if(command.split(" ")[2].equals("L"))
                {
                    try
                    {
                        int angle = parseInteger(command.split(" ")[3]);

                        driveHandler.newTurnAngleCommand(angle);

                        response = "ACK";
                    }
                    catch(Exception e)
                    {
                        //Could not parse distance from command
                        response = "MALFORMED COMMAND";
                    }
                }
                else if(command.split(" ")[2].equals("R"))
                {
                    try
                    {
                        int angle = parseInteger(command.split(" ")[3]);

                        driveHandler.newTurnAngleCommand(-angle);

                        response = "ACK";
                    }
                    catch(Exception e)
                    {
                        //Could not parse distance from command
                        response = "MALFORMED COMMAND";
                    }
                }
                else
                {
                    response = "MALFORMED COMMAND";
                }
            }
            else
            {
                response = "MALFORMED COMMAND";
            }
        }
        else if(command.startsWith("DRIVE DISTANCE"))
        {
            eventHandler.addEvent("TRAVEL DISTANCE EVENT: " + (int)driveHandler.getTravelledDistance());

            response = "ACK";
        }
        else
        {
            response = "UNKNOWN COMMAND";
        }

        return response;
    }

    private String processTagCommand(String command)
    {
        if(command.equals("TAG READ UID"))
        {
            this.eventHandler.addEvent(this.tagReaderHandler.readTag());

            return "ACK";
        }
        else
        {
            return "UNKNOWN COMMAND";
        }
    }

    private String processHelpCommand()
    {
        return "KNOWN COMMANDS IN SIMULATION: DRIVE [ABORT, FLUSH, FOLLOWLINE, PAUSE, RESUME, FORWARD, BACKWARDS, TURN, ROTATE, DISTANCE], TAG [READ UID], HELP";
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
