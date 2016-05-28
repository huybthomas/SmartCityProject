package be.uantwerpen.sc.tools;



import be.uantwerpen.sc.services.sockets.SimCCommandSender;
import be.uantwerpen.sc.services.javaCoreServices.QueueService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Niels on 4/05/2016.
 */
public class QueueConsumer implements Runnable
{
    private QueueService queueService;
    private SimCCommandSender simCCommandSender;

    private BlockingQueue<String> jobQueue;

    public QueueConsumer(QueueService queueService, SimCCommandSender simCCommandSender)
    {
        this.queueService = queueService;
        this.simCCommandSender = simCCommandSender;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                //System.out.println("Consimer wants to consume");
                Thread.sleep(10);
                if(queueService.getContentQueue().size() == 0){
                    //System.out.println("queue is empty");
                }else{
                    //System.out.println(queueService.getContentQueue().toString());
                    //Wait until robot not busy
                    synchronized (this) {
                        //if(!dataService.robotBusy) {
                                String s = queueService.getJob();
                                simCCommandSender.sendCommand(s);
                            }
                        //}
                    }
                //System.out.println("CrunchifyBlockingConsumer: Message - " + queueService.getJob() + " consumed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
