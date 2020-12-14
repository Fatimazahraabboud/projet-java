/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package station;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Fatima zahra
 */
/*
- stations/station_X/temp   
- stations/station_X/vent  
- alertes/tempete         
- stations/moyenne         
- stations/options/duree   
- stations/options/arret    */
public class Station {
    public static Integer sleepTime = 1000;
    public static boolean stop = false;
    
    
    public static void main(String[] args) throws InterruptedException, MqttException {
      Random rand = new Random();
        Integer n = rand.nextInt(10000);
        
        String nomStation = "station_"+n.toString();
       
        MqttClient client = new MqttClient("tcp://localhost:1883",nomStation);
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {  
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals("stations/options/duree"))
                {
                    System.out.println("Interval d'envoi modifié à " + message.toString() + " secondes.");
                    sleepTime = new Integer(message.toString());
                    sleepTime *= 1000;
                }
                else if(topic.equals("stations/options/arret"))
                {
                    System.out.println("Une tempête a été détectée. On arrête les stations.");
                    //stop = true;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        client.connect();
        
        client.subscribe("stations/options/duree");
        client.subscribe("stations/options/arret");
        
        for (int i = 0; i < 10000 && !stop; i++) {
            MqttMessage message = new MqttMessage();
            
            Integer temp = (int)(Math.random()*31 - 10);
            message.setPayload(temp.toString().getBytes());
            client.publish("stations/"+nomStation+"/temp", message);
            System.out.println("temp="+message);
            
            message = new MqttMessage();
            Integer vent = (int)(Math.random()*4);
            message.setPayload(vent.toString().getBytes());
            client.publish("stations/"+nomStation+"/vent", message);
            System.out.println("vent="+message);
            
            if(vent==3)
            {
                message = new MqttMessage();
                message.setPayload("STOP des stations".getBytes());
                client.publish("stations/options/arret", message);
                System.out.println("On arrête les stations pour cause de tempête.");
            }
            
            Thread.sleep(sleepTime);
        }
        
        client.disconnect();
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package station;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Fatima zahra
 */
/*
- stations/station_X/temp   
- stations/station_X/vent  
- alertes/tempete         
- stations/moyenne         
- stations/options/duree   
- stations/options/arret    */
public class Station {
    public static Integer sleepTime = 1000;
    public static boolean stop = false;
    
    
    public static void main(String[] args) throws InterruptedException, MqttException {
      Random rand = new Random();
        Integer n = rand.nextInt(10000);
        
        String nomStation = "station_"+n.toString();
       
        MqttClient client = new MqttClient("tcp://localhost:1883",nomStation);
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {  
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals("stations/options/duree"))
                {
                    System.out.println("Interval d'envoi modifié à " + message.toString() + " secondes.");
                    sleepTime = new Integer(message.toString());
                    sleepTime *= 1000;
                }
                else if(topic.equals("stations/options/arret"))
                {
                    System.out.println("Une tempête a été détectée. On arrête les stations.");
                    //stop = true;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        client.connect();
        
        client.subscribe("stations/options/duree");
        client.subscribe("stations/options/arret");
        
        for (int i = 0; i < 10000 && !stop; i++) {
            MqttMessage message = new MqttMessage();
            
            Integer temp = (int)(Math.random()*31 - 10);
            message.setPayload(temp.toString().getBytes());
            client.publish("stations/"+nomStation+"/temp", message);
            System.out.println("temp="+message);
            
            message = new MqttMessage();
            Integer vent = (int)(Math.random()*4);
            message.setPayload(vent.toString().getBytes());
            client.publish("stations/"+nomStation+"/vent", message);
            System.out.println("vent="+message);
            
            if(vent==3)
            {
                message = new MqttMessage();
                message.setPayload("STOP des stations".getBytes());
                client.publish("stations/options/arret", message);
                System.out.println("On arrête les stations pour cause de tempête.");
            }
            
            Thread.sleep(sleepTime);
        }
        
        client.disconnect();
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package station;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 *
 * @author Fatima zahra
 */
/*
- stations/station_X/temp   
- stations/station_X/vent  
- alertes/tempete         
- stations/moyenne         
- stations/options/duree   
- stations/options/arret    */
public class Station {
    public static Integer sleepTime = 1000;
    public static boolean stop = false;
    
    
    public static void main(String[] args) throws InterruptedException, MqttException {
      Random rand = new Random();
        Integer n = rand.nextInt(10000);
        
        String nomStation = "station_"+n.toString();
       
        MqttClient client = new MqttClient("tcp://localhost:1883",nomStation);
        
        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable thrwbl) {  
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if(topic.equals("stations/options/duree"))
                {
                    System.out.println("Interval d'envoi modifié à " + message.toString() + " secondes.");
                    sleepTime = new Integer(message.toString());
                    sleepTime *= 1000;
                }
                else if(topic.equals("stations/options/arret"))
                {
                    System.out.println("Une tempête a été détectée. On arrête les stations.");
                    //stop = true;
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken imdt) {
            }
        });
        
        client.connect();
        
        client.subscribe("stations/options/duree");
        client.subscribe("stations/options/arret");
        
        for (int i = 0; i < 10000 && !stop; i++) {
            MqttMessage message = new MqttMessage();
            
            Integer temp = (int)(Math.random()*31 - 10);
            message.setPayload(temp.toString().getBytes());
            client.publish("stations/"+nomStation+"/temp", message);
            System.out.println("temp="+message);
            
            message = new MqttMessage();
            Integer vent = (int)(Math.random()*4);
            message.setPayload(vent.toString().getBytes());
            client.publish("stations/"+nomStation+"/vent", message);
            System.out.println("vent="+message);
            
            if(vent==3)
            {
                message = new MqttMessage();
                message.setPayload("STOP des stations".getBytes());
                client.publish("stations/options/arret", message);
                System.out.println("On arrête les stations pour cause de tempête.");
            }
            
            Thread.sleep(sleepTime);
        }
        
        client.disconnect();
    }
    
}
