/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tempmoyenne;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Fatima zahra
 */
public class TempMoyenne {
    public static Integer station1 = -1;
    public static Integer temp1 = 0;
    public static Integer station2 = -1;
    public static Integer temp2 = 0;
    public static Integer station3 = -1;
    public static Integer temp3 = 0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws MqttException, InterruptedException {
        MqttClient client = new MqttClient("tcp://localhost:1883","temperatureMoyenne");
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {  
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String s = topic.replace("stations/station_","");
                s = s.replace("/temp","");
                Integer station = new Integer(s);
                Integer temp = new Integer(message.toString());
                if(station.equals(station1) || station1 == -1)
                {
                    station1 = station;
                    temp1 = temp;
                }
                else if(station.equals(station2) || station2 == -1)
                {
                    station2 = station;
                    temp2 = temp;
                }
                else if(station.equals(station3) || station3 == -1)
                {
                    station3 = station;
                    temp3 = temp;
                }   
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
          
        
        client.connect();
        
        client.subscribe("stations/+/temp");
        
        for (int i = 0; i < 10000; i++) {
            MqttMessage message2 = new MqttMessage();
            Integer moyenne = (temp1 + temp2 + temp3)/3;
            message2.setPayload(moyenne.toString().getBytes());
            client.publish("stations/moyenne", message2);
            System.out.println("moyenne="+moyenne);
            Thread.sleep(5000);
        }
        
        client.disconnect();
    }
    
}
