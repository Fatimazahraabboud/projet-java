/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passerelle;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

/**
 *
 * @author Fatima zahra
 */
public class Passerelle {
    public static Integer tempMoyenne = 0;
    public static String lastMessage;
  
    public static void main(String[] args) throws InterruptedException, MqttException {
        MqttClient client = new MqttClient("tcp://localhost:1883","passerelle");
        client.connect();
        
        get("/stations/moyenne", (request, response) ->
        {
            return tempMoyenne;
        });
        
        get("/alertes/tempete", (request, response) ->
        {
            if("Alerte : tempete !".equals(lastMessage))
                return 1;
            else
                return 0;
        });
        
        post("/stations", (Request request, Response response) ->
        {
            try {
                String val = request.queryParams("time");
                MqttMessage message = new MqttMessage();
                message.setPayload(val.getBytes());
                client.publish("stations/options/duree", message);
                System.out.println("Changement de durée : " + message + "sec");
                return val;
            } catch (MqttException ex) {
               // Logger.getLogger(PasserelleHTTP_MQTT.class.getName()).log(Level.SEVERE, null, ex);
            }
            return "";
        });
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {  
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals("stations/moyenne"))
                {
                    tempMoyenne = new Integer(message.toString());
                    System.out.println("tempmoy="+tempMoyenne);
                }
                else if(topic.equals("alertes/tempete"))
                {
                    lastMessage = message.toString();
                    System.out.println("lastMessage="+lastMessage);
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        client.subscribe("stations/moyenne");
        client.subscribe("alertes/tempete");
        
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000);
        }
        
        client.disconnect();
    }
    
}
