package be.uantwerpen.sc.tools;

import be.uantwerpen.sc.controllers.CCommandSender;
import be.uantwerpen.sc.controllers.SimCCommandSender;
import be.uantwerpen.sc.services.DataService;
import be.uantwerpen.sc.services.QueueService;
import be.uantwerpen.sc.services.SimulationService;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Niels on 4/05/2016.
 */
public class QueueConsumer implements Runnable
{
    private CCommandSender sender;
    private QueueService queueService;
    private DataService dataService;
    private SimulationService simulationService;
    private SimCCommandSender simCCommandSender;

    private BlockingQueue<String> jobQueue;

    public QueueConsumer(QueueService queueService, CCommandSender sender, DataService dataService, SimCCommandSender simCCommandSender, SimulationService simulationService)
    {
        this.queueService = queueService;
        this.sender = sender;
        this.dataService = dataService;
        this.simCCommandSender = simCCommandSender;
        this.simulationService = simulationService;
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
                        if(!dataService.robotBusy) {
                            if(simulationService.isActiveSimulator()){
                                String s = queueService.getJob();
                                simCCommandSender.sendCommand(s);
                            }else{
                                String s = queueService.getJob();
                                sender.sendCommand(s);
                                if(!s.contains("DRIVE DISTANCE")) {
                                    dataService.robotBusy = true;
                                }
                            }
                        }
                    }
                }
                //System.out.println("CrunchifyBlockingConsumer: Message - " + queueService.getJob() + " consumed.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
