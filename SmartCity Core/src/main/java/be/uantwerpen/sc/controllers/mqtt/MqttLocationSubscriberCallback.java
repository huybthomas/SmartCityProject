package be.uantwerpen.sc.controllers.mqtt;

import be.uantwerpen.sc.controllers.BotController;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Arthur on 9/05/2016.
 */
public class MqttLocationSubscriberCallback implements MqttCallback
{
    BotController botController;

    public MqttLocationSubscriberCallback(BotController botController)
    {
        this.botController = botController;
    }

    @Override
    public void connectionLost(Throwable cause)
    {
        //This is called when the connection is lost. We could reconnect here.
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception
    {
        String botIDString = topic.split("/")[1];
        Long botID = Long.parseLong(botIDString);

        String payloadString = new String(mqttMessage.getPayload());

        if(!payloadString.endsWith("Location"))
        {
            //No topic of interest, drop message
            return;
        }

        try
        {
            int parsedInt = Integer.parseInt(payloadString);
            botController.updateLocation(botID, parsedInt);
        }
        catch(Exception e)
        {
            System.err.println("Could not parse integer from payloadString: " + payloadString);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {

    }
}
