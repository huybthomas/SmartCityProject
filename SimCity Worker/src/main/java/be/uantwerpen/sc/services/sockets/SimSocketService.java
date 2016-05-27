package be.uantwerpen.sc.services.sockets;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Thomas on 27/05/2016.
 */
@Service
public class SimSocketService implements Runnable
{
    private int socketPort;
    private List<SimSocket> sockets;
    private ServerSocket serverSocket;

    public SimSocketService()
    {
        this.socketPort = 0;
        this.sockets = new ArrayList<SimSocket>();
        this.serverSocket = null;
    }

    public SimSocketService(int listeningPort)
    {
        if(listeningPort >= 0)
        {
            this.socketPort = listeningPort;
        }
        else
        {
            //Invalid listening port given
            this.socketPort = 0;
        }

        this.sockets = new ArrayList<SimSocket>();
        this.serverSocket = null;
    }

    public int getListeningPort()
    {
        if(serverSocket != null)
        {
            return this.serverSocket.getLocalPort();
        }
        else
        {
            return this.socketPort;
        }
    }

    synchronized public SimSocket getConnection()
    {
        synchronized(this.sockets)
        {
            if (this.sockets.size() != 0)
            {
                //Get next waiting connection
                SimSocket socket = this.sockets.get(0);
                this.sockets.remove(0);

                return socket;
            }
            else
            {
                //No waiting connection
                return null;
            }
        }
    }

    @Override
    public void run()
    {
        Socket socket = null;
        SimSocket simSocket = null;

        try
        {
            this.serverSocket = new ServerSocket(socketPort);
            this.serverSocket.setSoTimeout(500);

            System.out.println("Simulator listening on port: " + this.serverSocket.getLocalPort());
        }
        catch(Exception e)
        {
            System.err.println("Could not open port: " + this.socketPort + " for simulator!");

            return;
        }

        while(!Thread.currentThread().isInterrupted())
        {
            try
            {
                socket = this.serverSocket.accept();
            }
            catch(SocketTimeoutException timeEx)
            {
                //Ignore exception to allow interrupting thread
                continue;
            }
            catch(Exception e)
            {
                System.err.println("Error, could not accept new connection!");
                e.printStackTrace();

                continue;
            }

            if(socket != null)
            {
                simSocket = new SimSocket(socket);
            }

            //Add socket to waiting list
            synchronized(this.sockets)
            {
                this.sockets.add(simSocket);
            }

            //Clear socket pointer
            socket = null;
        }

        //Close remaining waiting connection
        synchronized(this.sockets)
        {
            for(SimSocket waitingSocket : this.sockets)
            {
                try
                {
                    waitingSocket.close();
                }
                catch(IOException e)
                {
                    System.err.println("Could not close waiting connection!");
                }
            }

            this.sockets.clear();
        }

        try
        {
            //Close server socket
            this.serverSocket.close();
        }
        catch(IOException e)
        {
            //Could not close server socket
            System.err.println("Could not close server on port: " + socketPort + "!");
        }

        //Clear server socket pointer
        this.serverSocket = null;
    }
}
