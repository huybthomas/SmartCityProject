package be.uantwerpen.sc.services.sockets;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Created by Thomas on 27/05/2016.
 */
public class SimSocket
{
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

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
        this.reader.close();
        this.writer.close();

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
        String message = null;

        if(this.socket.isClosed())
        {
            //Socket is closed
            return null;
        }

        try
        {
            openReader();
        }
        catch(IOException e)
        {
            //Could not open input stream
            System.err.println("Could not open input stream!");

            return null;
        }

        try
        {
            try
            {
                message = this.reader.readLine();

                if(message == null)
                {
                    //Socket is closed by the client
                    this.close();
                }
            }
            catch(SocketTimeoutException e)
            {
                //Socket timed-out
            }
        }
        catch(IOException e)
        {
            //Could not read input stream
            System.err.println("Could not read input stream!");
        }

        return message;
    }

    public boolean sendMessage(String message)
    {
        boolean success = true;

        if(this.socket.isClosed())
        {
            //Socket is closed
            return false;
        }

        try
        {
            openWriter();
        }
        catch(IOException e)
        {
            //Could not open output stream
            System.err.println("Could not open output stream!");

            return false;
        }

        try
        {
            this.writer.write(message);
            this.writer.flush();
        }
        catch(IOException e)
        {
            //Could not write to output stream
            System.err.println("Could not write to output stream!");

            success = false;
        }

        return success;
    }

    private void openReader() throws IOException
    {
        if(this.reader == null && this.socket != null)
        {
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        }
    }

    private void openWriter() throws IOException
    {
        if(this.writer == null && this.socket != null)
        {
            writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        }
    }
}
