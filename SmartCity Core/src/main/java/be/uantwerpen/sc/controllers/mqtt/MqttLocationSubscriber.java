package be.uantwerpen.sc.controllers.mqtt;

import be.uantwerpen.sc.controllers.BotController;
import be.uantwerpen.sc.tools.Terminal;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * Created by Arthur on 9/05/2016.
 */
@Service
public class MqttLocationSubscriber
{
    @Autowired
    private BotController botController;

    @Autowired
    private Environment environment;

    @Value("${mqtt.ip:localhost}")
    private String mqttIP;

    @Value("#{new Integer(${mqtt.port}) ?: 1883}")
    private int mqttPort;

    @Value("${mqtt.username:default}")
    private String mqttUsername;

    @Value("${mqtt.password:default}")
    private String mqttPassword;

    @Value("${mqtt.disabled:false}")
    private boolean mqttDisable;

    private String brokerURL;

    private MqttClient mqttSubscribeClient;

    public MqttLocationSubscriber()
    {

    }

    @PostConstruct
    private void postConstruct()
    {
        //Check to disable MQTT service
        for(String profile : environment.getActiveProfiles())
        {
            if(profile.equals("dev") && mqttDisable)
            {
                //Disable MQTT
                Terminal.printTerminalInfo("MQTT will not be available! System will not be operational.");
                return;
            }
        }

        //IP / port-values are initialised at the end of the constructor
        brokerURL = "tcp://" + mqttIP + ":" + mqttPort;

        Terminal.printTerminalInfo("Connecting to MQTT service");

        try
        {
            //Generate unique client ID
            mqttSubscribeClient = new MqttClient(brokerURL, "SmartCity_Core_Subscriber_" + new Random().nextLong());
        }
        catch(MqttException e)
        {
            Terminal.printTerminalError("Could not connect to MQTT Broker!");
            e.printStackTrace();

            Terminal.printTerminalError("System will exit!");
            System.exit(1);
        }

        try
        {
            start();
        }
        catch(Exception e)
        {
            Terminal.printTerminalError(e.getMessage());
            e.printStackTrace();

            boolean devMode = false;
            for(String profile : environment.getActiveProfiles())
            {
                if(profile.equals("dev"))
                {
                    devMode = true;
                }
            }

            if(devMode)
            {
                Terminal.printTerminalInfo("MQTT will not be available! System will not be operational.");
            }
            else
            {
                Terminal.printTerminalError("System will exit!");
                System.exit(1);
            }
        }
    }

    public void updateLocation()
    {

    }

    private void start() throws Exception
    {
        try
        {
            mqttSubscribeClient.setCallback(new MqttLocationSubscriberCallback(botController));
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName(mqttUsername);
            connOpts.setPassword(mqttPassword.toCharArray());
            mqttSubscribeClient.connect(connOpts);

            //Subscribe to all subtopics of bots
            mqttSubscribeClient.subscribe("BOT/#");
        }
        catch(MqttException e)
        {
            throw new Exception("Could not subscribe to topics of MQTT service!");
        }
    }
}
