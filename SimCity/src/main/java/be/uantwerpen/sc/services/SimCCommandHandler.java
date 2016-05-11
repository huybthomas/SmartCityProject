package be.uantwerpen.sc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Niels on 11/05/2016.
 */
@Service
public class SimCCommandHandler implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;
    @Autowired
    DataService dataService;

    public SimCCommandHandler() {
        try {
            serverSocket = new ServerSocket(1314);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {

                socket = serverSocket.accept();/*
                DataInputStream dIn = new DataInputStream(socket.getInputStream());
                byte[] bytes = new byte[1024];
                dIn.readFully(bytes);
                String s = new String(bytes);
                System.out.println(s);
                //TODO Continue this method
                if (s.startsWith("DRIVE EVENT: FINISHED")){
                    synchronized (this){
                        dataService.robotBusy = false;
                    }
                }if (s.startsWith("TRAFFICLIGHT DETECTION EVENT")){
                    String statusString = s.split(":", 2)[1];
                    String status = "";
                    if(statusString.contains("GREEN")){
                        status = "GREEN";
                    }
                    if(statusString.contains("RED")){
                        status = "RED";
                    }
                    if(statusString.contains("NONE")){
                        status = "NONE";
                    }
                    synchronized (this) {
                        dataService.trafficLightStatus = status;
                    }
                }
                if (s.startsWith("MILLIMETERS")){
                    String millisString = s.split(":", 2)[1];
                    int millis = Integer.parseInt(millisString);
                    synchronized (this) {
                        dataService.setMillis(millis);
                    }
                }if (s.startsWith("TAG READ UID EVENT")){
                    String tag = s.split(":", 2)[1];
                    synchronized (this){
                        dataService.setTag(tag);
                    }
                }if (s.startsWith("TRAFFIC_LIGHT")){
                    String trafficlightStatus = s.split(" ", 2)[1];
                    synchronized (this){
                        dataService.trafficLightStatus = trafficlightStatus;
                    }
                }*/
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        try{
            socket.close();
            serverSocket.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
