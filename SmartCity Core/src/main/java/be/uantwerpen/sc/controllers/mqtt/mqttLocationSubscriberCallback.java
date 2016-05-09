package be.uantwerpen.sc.controllers.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Arthur on 9/05/2016.
 */
public class mqttLocationSubscriberCallback implements MqttCallback {

    mqttLocationSubscriber subscriber;
    boolean on = false;

    public mqttLocationSubscriberCallback(mqttLocationSubscriber subscriber){
        this.subscriber = subscriber;
    }

    @Override
    public void connectionLost(Throwable cause) {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

        System.out.println("Message arrived. String: " + s + "  Message: " + mqttMessage.toString());

        //TODO Process message

        System.out.println("Message processing finished");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
