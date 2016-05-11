package be.uantwerpen.sc.controllers.mqtt;

import be.uantwerpen.sc.controllers.BotController;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Arthur on 9/05/2016.
 */


@Service
public class mqttLocationSubscriber {

    @Autowired
    private BotController botController;

    public static final String BROKER_URL = "tcp://146.175.139.66:1883";
    //public static final String BROKER_URL = "tcp://test.mosquitto.org:1883";

    //We have to generate a unique Client id.
    public MqttClient mqttSubscribeClient, mqttSendClient;

    public mqttLocationSubscriber() {

        try {
            mqttSubscribeClient = new MqttClient(BROKER_URL, "SmartCity_Core_Receiver");
            start();

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void start() {
        try {
            mqttSubscribeClient.setCallback(new mqttLocationSubscriberCallback(this,botController));
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            connOpts.setUserName("arthur");
            connOpts.setPassword("arthur".toCharArray());
            mqttSubscribeClient.connect(connOpts);

            //Subscribe to all subtopics of Sensor
            mqttSubscribeClient.subscribe("BOT/#");

        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
