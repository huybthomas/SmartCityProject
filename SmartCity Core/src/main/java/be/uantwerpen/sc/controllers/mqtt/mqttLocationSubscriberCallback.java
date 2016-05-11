package be.uantwerpen.sc.controllers.mqtt;

import be.uantwerpen.sc.controllers.BotController;
import be.uantwerpen.sc.models.BotEntity;
import be.uantwerpen.sc.repositories.BotRepository;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Arthur on 9/05/2016.
 */
public class mqttLocationSubscriberCallback implements MqttCallback {


    private BotController botController;

    mqttLocationSubscriber subscriber;
    boolean on = false;

    public mqttLocationSubscriberCallback(mqttLocationSubscriber subscriber, BotController botController){
        this.subscriber = subscriber;
        this.botController = botController;
    }

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

        System.out.println("Message arrived. String: " + topic + "  Message: " + mqttMessage.toString());

        //TODO Process message
        String botIDString = topic.split("/")[1];
        Long botID = Long.parseLong(botIDString);

        String payploadString =  new String(mqttMessage.getPayload());
        int tussenint = Integer.parseInt(payploadString);
        botController.updateLocation(botID, tussenint);

        System.out.println("Message processing finished");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
