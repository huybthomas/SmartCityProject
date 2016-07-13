package be.uantwerpen.sc.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Thomas on 25/02/2016.
 */
public abstract class Terminal
{
    private TerminalReader terminalReader;

    public Terminal()
    {
        terminalReader = new TerminalReader();

        terminalReader.getObserver().addObserver(new Observer()
        {
            @Override
            public void update(Observable source, Object object)
            {
                if(object != null)
                {
                    String command = ((String)object).trim();

                    if(!command.equals(""))
                    {
                        executeCommand((String) object);
                    }
                }

                activateTerminal();
            }
        });
    }

    public static void printTerminal(String message)
    {
        System.out.println(message);
    }

    public static void printTerminalInfo(String message)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.println("[INFO - " + timeFormat.format(calendar.getTime()) + "] " + message);
    }

    public static void printTerminalError(String message)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        System.out.println("[ERROR - " + timeFormat.format(calendar.getTime()) + "] " + message);
    }

    public void activateTerminal()
    {
        new Thread(terminalReader).start();
    }

    abstract public void executeCommand(String commandString);

    private class TerminalReader implements Runnable
    {
        private TerminalObserver observer;

        public TerminalReader()
        {
            this.observer = new TerminalObserver();
        }

        public TerminalObserver getObserver()
        {
            return this.observer;
        }

        @Override
        public void run()
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("# ");

            try
            {
                String command = input.readLine();
                this.observer.setChanged();
                this.observer.notifyObservers(command);
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private class TerminalObserver extends Observable
    {
        public void clearChanged()
        {
            super.clearChanged();
        }

        public void setChanged()
        {
            super.setChanged();
        }
    }
}
