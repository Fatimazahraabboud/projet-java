/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartphone;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
/**
 *
 * @author Fatima zahra
 */
public class SmartPhone {

    
     public static void getTemp() throws MalformedURLException, IOException
    {
        String url = "http://localhost:4567/stations/moyenne";
        String charset = "UTF-8";
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response;
        response = connection.getInputStream();
        try(Scanner scanner = new Scanner(response))
        {
            Integer responseBody = scanner.useDelimiter("\\A").nextInt();
            System.out.println("Température moyenne : " + responseBody + "°C");
        }
    }
    
    public static void getTempete() throws MalformedURLException, IOException
    {
        String url = "http://localhost:4567/stations/moyenne";
        String charset = "UTF-8";
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response;
        response = connection.getInputStream();
        try(Scanner scanner = new Scanner(response))
        {
            Integer responseBody = scanner.useDelimiter("\\A").nextInt();
            if(responseBody==1)
                System.out.println("Tempête en cours");
            else
                System.out.println("Pas de tempête actuellement");
        }
    }
    
    public static void postTime() throws MalformedURLException, IOException
    {
        Integer duree;
        do
        {
            System.out.println("Entrer le nouvel interval en secondes.");
            Scanner in = new Scanner(System.in);
            duree = in.nextInt();
        }   while(duree < 1);
        String url = "http://localhost:4567/stations";
        String charset = "UTF-8";
        String query = String.format("time=%d", duree);
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Accept-Charset", charset);
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
        try(OutputStream output = connection.getOutputStream()) {
            output.write(query.getBytes(charset));
        }
        InputStream response;
        response = connection.getInputStream();
        try(Scanner scanner = new Scanner(response))
        {
            Integer responseBody = scanner.useDelimiter("\\A").nextInt();
            System.out.println("Modification prise en compte.");
        }
        
        
    }
    public static void main(String[] args) throws IOException {
          Integer choix;
        do
        {  
            System.out.println("Voulez-vous...");
            System.out.println("Afficher la température moyenne ?   (1)");
            System.out.println("Afficher l'avis de tempête ?        (2)");
            System.out.println("Modifier l'intervalle de temps ?    (3)");
            System.out.println("Quitter ?                           (0)");
            Scanner in = new Scanner(System.in);
            choix = in.nextInt();
            
            switch (choix) {
                case 1:
                    getTemp();
                    break;
                case 2:
                    getTempete();
                    break;
                case 3:
                    postTime();
                    break;
                default:
                    break;
            }
  
        }   while(choix>0);
        
    }
    
}
