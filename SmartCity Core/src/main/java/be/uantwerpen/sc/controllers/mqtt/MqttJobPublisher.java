package be.uantwerpen.sc.controllers.mqtt;

import be.uantwerpen.sc.models.Job;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Thomas on 01/06/2016.
 */
@Service
public class MqttJobPublisher
{
    @Value("${mqtt.ip:localhost}")
    private String mqttIP;

    @Value("#{new Integer(${mqtt.port}) ?: 1883}")
    private int mqttPort;

    @Value("${mqtt.username:default}")
    private String mqttUsername;

    @Value("${mqtt.password:default}")
    private String mqttPassword;

    public boolean publishJob(Job job, long robotID)
    {
        String content  = job.toString();
        int qos         = 2;
        String topic    = "BOT/" + robotID + "/Job";
        String broker   = "tcp://" + mqttIP + ":" + mqttPort;

        MemoryPersistence persistence = new MemoryPersistence();

        try
        {
            //Generate unique client ID
            MqttClient client = new MqttClient(broker, "SmartCity_Core_Publisher_" + new Random().nextLong(), persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            connectOptions.setUserName(mqttUsername);
            connectOptions.setPassword(mqttPassword.toCharArray());
            client.connect(connectOptions);

            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            client.publish(topic, message);

            client.disconnect();
        }
        catch(MqttException e)
        {
            System.err.println("Could not publish topic: " + topic + " to mqtt service!");
            System.err.println("Reason: " + e.getReasonCode());
            System.err.println("Message: " + e.getMessage());
            System.err.println(e.getLocalizedMessage());
            System.err.println("Cause: " + e.getCause());
            System.err.println("Exception: " + e);

            return false;
        }

        return true;
    }
}
