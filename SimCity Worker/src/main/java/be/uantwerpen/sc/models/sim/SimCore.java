package be.uantwerpen.sc.models.sim;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Thomas on 27/05/2016.
 */
public class SimCore
{
    private String coreLocation;
    private String version;
    private SimStatus status;
    private String log;
    private boolean running;
    private List<String> runArguments;
    private Thread coreThread;

    private SimCore()
    {
        this.coreLocation = "";
        this.version = "0.0.0";
        this.status = SimStatus.OFF;
        this.running = false;
        this.log = "";
    }

    public SimCore(String coreLocation, String version)
    {
        this.coreLocation = coreLocation;
        this.version = version;
        this.status = SimStatus.OFF;
        this.running = false;
        this.log = "";
    }

    public String getCoreLocation()
    {
        return this.coreLocation;
    }

    public String getVersion()
    {
        return this.version;
    }

    public SimStatus getStatus()
    {
        return this.status;
    }

    public String getLog()
    {
        return this.log;
    }

    public void clearLog()
    {
        this.log = "";
    }

    public boolean start(List<String> arguments)
    {
        if(!running)
        {
            this.runArguments = arguments;

            coreThread = new Thread(new CoreProcess());
            coreThread.start();

            running = true;

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean stop()
    {
        if(running)
        {
            coreThread.interrupt();

            return true;
        }
        else
        {
            return false;
        }
    }

    private class CoreProcess implements Runnable
    {
        @Override
        public void run()
        {
            //Create process
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", coreLocation);

            //Add core boot arguments
            List<String> processCommands = processBuilder.command();
            processCommands.addAll(runArguments);
            processBuilder.command(processCommands);

            status = SimStatus.BOOT;

            Process process;
            try
            {
                process = processBuilder.start();
            }
            catch(Exception e)
            {
                System.err.println("Could not create Core process!");
                e.printStackTrace();

                status = SimStatus.ERROR;

                running = false;

                return;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String logLine = "";
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            //Start new log section
            log = log.concat("\n------------------- New log - " + dateFormat.format(new Date()) + " -------------------\n");

            while(!Thread.currentThread().isInterrupted() && !logLine.startsWith("#"))
            {
                try
                {
                    if(reader.ready())
                    {
                        logLine = reader.readLine();

                        if(logLine == null)
                        {
                            //Input stream closed, exit boot status
                            break;
                        }

                        //Add line to log
                        log = log.concat(logLine + "\n");
                    }
                }
                catch(IOException e)
                {
                    if(!Thread.currentThread().isInterrupted())
                    {
                        System.err.println("Could not open input stream!");
                        e.printStackTrace();

                        status = SimStatus.ERROR;

                        process.destroy();

                        running = false;

                        return;
                    }
                    else
                    {
                        //System interrupted
                        break;
                    }
                }
            }

            status = SimStatus.RUNNING;

            try
            {
                while(!Thread.currentThread().isInterrupted() && logLine != null)
                {
                    if(reader.ready())
                    {
                        logLine = reader.readLine();

                        //Add line to log
                        if(logLine != null)
                        {
                            log = log.concat(logLine + "\n");
                        }
                    }
                }
            }
            catch(IOException e)
            {
                if(!Thread.currentThread().isInterrupted())
                {
                    System.err.println("Could not read input stream!");
                    e.printStackTrace();

                    status = SimStatus.ERROR;

                    process.destroy();

                    running = false;

                    return;
                }
                else
                {
                    //System interrupted
                }
            }

            //Send shutdown signal to process
            status = SimStatus.SHUTDOWN;

            process.destroy();

            //Wait for core process to shutdown
            try
            {
                while(!Thread.currentThread().isInterrupted() && logLine != null)
                {
                    if(reader.ready())
                    {
                        logLine = reader.readLine();

                        //Add line to log
                        if(logLine != null)
                        {
                            log = log.concat(logLine + "\n");
                        }
                    }
                }
            }
            catch(IOException e)
            {
                if(!Thread.currentThread().isInterrupted())
                {
                    System.err.println("Could not read input stream!");
                    e.printStackTrace();

                    status = SimStatus.ERROR;

                    process.destroy();

                    running = false;

                    return;
                }
                else
                {
                    //System interrupted
                }
            }

            try
            {
                reader.close();
            }
            catch(IOException e)
            {
                System.err.println("Could not close input stream!");
            }

            status = SimStatus.OFF;

            running = false;
        }
    }
}