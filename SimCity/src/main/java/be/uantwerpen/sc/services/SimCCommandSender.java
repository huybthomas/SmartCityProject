package be.uantwerpen.sc.services;

import be.uantwerpen.sc.tools.Terminal;
import org.springframework.stereotype.Service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Niels on 11/05/2016.
 */
@Service
public class SimCCommandSender {

    private Socket socket;
    private DataOutputStream dOut;
    private DataInputStream dIn;

    public SimCCommandSender(){
        try{
            socket = new Socket("localhost", 1314);
            socket.setSoTimeout(500);
            dOut = new DataOutputStream(socket.getOutputStream());
            dIn = new DataInputStream(socket.getInputStream());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public SimCCommandSender(String ip){
        try{
            socket = new Socket(ip, 1313);
            socket.setSoTimeout(500);
            dOut = new DataOutputStream(socket.getOutputStream());
            dIn = new DataInputStream(socket.getInputStream());
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean sendCommand(String str){
        try {
            //byte[] message = str.getBytes();
            //System.out.println(message.toString());
            int attempts = 0;

            str = str.concat("\n");
            byte[] bytes = str.getBytes();

            //while(attempts <5) {
            //Send message
            //dOut.writeInt(message.length); // write length of the message
            dOut.flush();
            dOut.write(bytes);
                /*
                //Receive Message
                try {
                    //Check if acknowledged
                    byte[] ackBytes = new byte[4];
                    dIn.readFully(ackBytes);
                    String response = new String(ackBytes);
                    Terminal.printTerminal("Response:" + response);
                    if(response.startsWith("ACK")  || response.startsWith("Smar")){
                        //Message acknowledged
                        return true;
                    }if(response.startsWith("NACK")){
                        return false;
                    }

                    //clear buffer
                    if(dIn.available() > 0) {
                        byte[] removed = new byte[dIn.available()];
                        dIn.readFully(removed);
                    }
                }catch(SocketTimeoutException e){
                    Terminal.printTerminalInfo("SocketTimeout");
                    e.printStackTrace();
                }
                attempts++;
                */
            //}

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            Terminal.printTerminalInfo("IOException");
            return false;
        }
    }

    public boolean close(){
        try{
            socket.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }


    }
}

