package be.uantwerpen.sc.services;

import be.uantwerpen.sc.controllers.mqtt.MqttJobPublisher;
import be.uantwerpen.sc.models.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Thomas on 01/06/2016.
 */
@Service
public class JobService
{
    @Autowired
    MqttJobPublisher mqttJobPublisher;

    public boolean sendJob(int botId, String command)
    {
        Job job = new Job(0, command);

        return mqttJobPublisher.publishJob(job, botId);
    }
}
