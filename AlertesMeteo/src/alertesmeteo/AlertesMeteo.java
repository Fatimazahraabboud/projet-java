/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alertesmeteo;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import  org.eclipse.paho.client.mqttv3.MqttException ;
import  org.eclipse.paho.client.mqttv3.MqttMessage ;

/**
 *
 * @author Fatima zahra
 */
public class AlertesMeteo {

    
    public static void main(String[] args) throws MqttException, InterruptedException {
      MqttClient client = new MqttClient("tcp://localhost:1883","alerteMeteorologique");
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {  
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String s = topic.replace("stations/station_","");
                s = s.replace("/vent","");
                Integer station = new Integer(s);
                Integer vent = new Integer(message.toString());
                if(vent==3)
                {
                    MqttMessage message2 = new MqttMessage();       
                    message2.setPayload("Alerte : tempete !".getBytes());    
                    client.publish("alertes/tempete", message2);
                    System.out.println("Début tempete...");
                    Thread.sleep(3000);
                    message2 = new MqttMessage();
                    message2.setPayload("Fin de tempête...".getBytes());
                    client.publish("alertes/tempete", message2);
                    System.out.println("Fin tempete...");
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
          
        
        client.connect();
        
        client.subscribe("stations/+/vent");
        
        for (int i = 0; i < 10000; i++) {
            Thread.sleep(1000);
        }
        
        client.disconnect();
    }
}
