/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Frames.HomeWindow;
import InternalFrames.Receiver_Loader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDesktopPane;

/**
 *
 * @author BIT2
 */
public class MulticastClient {

    public static boolean isRunning = false;
    Receiver_Loader receiver_Loader;
    ArrayList<String> ServersIP = new ArrayList();

    public MulticastClient(Receiver_Loader loader) {
        receiver_Loader = loader;
    }

    
    public static void main(String[] args) {
        //new MulticastClient(new Receiver_Loader(new JDesktopPane(),new HomeWindow());
    }
    
    public void Entry() throws IOException {

        MulticastSocket socket = new MulticastSocket(4446);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);
        socket.setSoTimeout(5000);
        isRunning = true;

        DatagramPacket packet;

        // get a few quotes
        try {
            while (isRunning) {
                byte[] buf = new byte[256];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String rec = new String(packet.getData(), 0, packet.getLength());
                System.out.println(" " + (ServersIP.contains(rec)));
                if (!(ServersIP.contains(rec)) && ServersIP.size() <= 4) {
                    System.out.println(" " + (ServersIP.contains(rec)));
                    ServersIP.add(rec);
                    System.out.println("Server IP: " + rec);
                }
            }
        } catch (Exception e) {
            isRunning = false;
            System.out.println("TimeOut");
            receiver_Loader.jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/131.gif")));
            receiver_Loader.jLabel2.setText("Connect to Server");
            try {                
                receiver_Loader.server1Logo.setText(ServersIP.get(0).substring(1));
                receiver_Loader.server1Logo.setVisible(true); 
                
                receiver_Loader.server2Logo.setText(ServersIP.get(1).substring(1));
                receiver_Loader.server2Logo.setVisible(true);
                
                receiver_Loader.server4Logo.setText(ServersIP.get(2).substring(1));
                receiver_Loader.server4Logo.setVisible(true);
                
                receiver_Loader.server3Logo.setText(ServersIP.get(3).substring(1));
                receiver_Loader.server3Logo.setVisible(true);
            } catch (Exception ee) {
                ee.printStackTrace();
            }

            System.out.println(" " + ServersIP.get(0));

        } finally {
            isRunning = false;
            socket.leaveGroup(address);
            socket.close();
        }
    }
}
