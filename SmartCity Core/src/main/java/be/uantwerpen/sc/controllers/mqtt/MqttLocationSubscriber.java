package be.uantwerpen.sc.controllers.mqtt;

import be.uantwerpen.sc.controllers.BotController;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by Arthur on 9/05/2016.
 */
@Service
public class MqttLocationSubscriber
{
    //TODO: fix bad code - Public field!
    @Autowired
    public BotController botController;

    @Value("${mqtt.ip:localhost}")
    private String mqttIP;

    @Value("#{new Integer(${mqtt.port}) ?: 1883}")
    private int mqttPort;

    @Value("${mqtt.username:default}")
    private String mqttUsername;

    @Value("${mqtt.password:default}")
    private String mqttPassword;

    private String brokerURL;

    //We have to generate a unique Client id.
    private MqttClient mqttSubscribeClient;

    public MqttLocationSubscriber()
    {

    }

    @PostConstruct
    private void postConstruct()
    {
        //IP / port-values are initialised at the end of the constructor
        brokerURL = "tcp://" + mqttIP + ":" + mqttPort;

        try
        {
            mqttSubscribeClient = new MqttClient(brokerURL, "SmartCity_Core_Receiver");
            start();
        }
        catch(MqttException e)
        {
            System.err.println("Could not connect to MQTT Broker!");
            e.printStackTrace();
            System.err.println("System will exit!");
            System.exit(1);
        }
    }

    public void start()
    {
        try
        {
            mqttSubscribeClient.setCallback(new MqttLocationSubscriberCallback(this));
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(mqttUsername);
            connOpts.setPassword(mqttPassword.toCharArray());
            mqttSubscribeClient.connect(connOpts);

            //Subscribe to all subtopics of Sensor
            mqttSubscribeClient.subscribe("BOT/#");

        }
        catch(MqttException e)
        {
            e.printStackTrace();
            //System.exit(1);
        }
    }
}
