package be.uantwerpen.sc.services.javaCoreServices;

import be.uantwerpen.sc.models.Job;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Niels on 4/05/2016.
 */
@Service
public class QueueService {

    BlockingQueue<String> jobQueue = new ArrayBlockingQueue<>(100);

    public QueueService() {
    }

    public String getJob(){
        try {
            return jobQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertJob(String job){
        try {
            jobQueue.put(job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public BlockingQueue<String> getContentQueue(){

        return this.jobQueue;
    }

}
