package be.uantwerpen.sc.controllers.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Arthur on 9/05/2016.
 */
public class mqttLocationSubscriberCallback implements MqttCallback
{
    mqttLocationSubscriber subscriber;
    boolean on = false;

    public mqttLocationSubscriberCallback(mqttLocationSubscriber subscriber)
    {
        this.subscriber = subscriber;
    }

    @Override
    public void connectionLost(Throwable cause)
    {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
        System.out.println("Message arrived. String: " + topic + "  Message: " + mqttMessage.toString());

        //TODO Process message
        String botIDString = topic.split("/")[1];
        Long botID = Long.parseLong(botIDString);

        String payploadString = new String(mqttMessage.getPayload());
        int tussenint = Integer.parseInt(payploadString);
        subscriber.botController.updateLocation(botID, tussenint);

        System.out.println("Message processing finished");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {

    }
}
