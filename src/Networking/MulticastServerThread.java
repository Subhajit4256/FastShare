/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Utils.IPFinder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BIT2
 */
public class MulticastServerThread extends SimpleServerThread {

    private long THREE_SECONDS = 5000;

    public MulticastServerThread() throws IOException {
        super("MulticastServerThread");
        socket.setSoTimeout(5000);
    }

    public void run() {
        int count = 0;
        try {
            String ip = IPFinder.getLocalHostLANAddress().toString();
            while (true) {
                count++;
                try {
                    byte[] buf = new byte[256];

                    // construct quote
                    String dString = "";
                    dString = ip;                                                                                                 
                    System.out.println("Sending......" + dString+" Count="+count);

                    buf = ip.getBytes();

                    // send it
                    InetAddress group = InetAddress.getByName("230.0.0.1");
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                    socket.send(packet);
                    count++;
                    // sleep for a while
                    try {
                        sleep((long) (THREE_SECONDS));
                    } catch (InterruptedException e) {
                    }
                } catch (IOException e) {
                    e.printStackTrace();                  
                }
            }
           
        } catch (UnknownHostException ex) {
             socket.close();
            Logger.getLogger(MulticastServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
