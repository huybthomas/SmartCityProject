package be.uantwerpen.sc.services.sockets;

import java.io.*;
import java.net.Socket;

/**
 * Created by Thomas on 27/05/2016.
 */
public class SimSocket
{
    private Socket socket;

    private SimSocket()
    {
        this.socket = null;
    }

    public SimSocket(Socket socket)
    {
        this.socket = socket;
    }

    public void close() throws IOException
    {
        this.socket.close();
    }

    public int getPort()
    {
        return this.socket.getLocalPort();
    }

    public int getRemotePort()
    {
        return this.socket.getPort();
    }

    public boolean isClosed()
    {
        return this.socket.isClosed();
    }

    public String getMessage()
    {
        BufferedReader reader = null;
        String message = null;

        if(this.socket.isClosed())
        {
            //Socket is closed
            return null;
        }

        try
        {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(IOException e)
        {
            //Could not open input stream
            System.err.println("Could not open input stream!");

            return null;
        }

        try
        {
            message = reader.readLine();
        }
        catch(IOException e)
        {
            //Could not read input stream
            System.err.println("Could not read input stream!");
        }

        try
        {
            reader.close();
        }
        catch(IOException e)
        {
            //Could not close input stream
            System.err.println("Could not close input stream!");
        }

        return message;
    }

    public boolean sendMessage(String message)
    {
        BufferedWriter writer = null;
        boolean success = true;

        if(this.socket.isClosed())
        {
            //Socket is closed
            return false;
        }

        try
        {
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        }
        catch(IOException e)
        {
            //Could not open output stream
            System.err.println("Could not open output stream!");

            return false;
        }

        try
        {
            writer.write(message);
        }
        catch(IOException e)
        {
            //Could not write to output stream
            System.err.println("Could not write to output stream!");

            success = false;
        }

        try
        {
            writer.close();
        }
        catch(IOException e)
        {
            //Could not close output stream
            System.err.println("Could not close output stream!");
        }

        return success;
    }
}
