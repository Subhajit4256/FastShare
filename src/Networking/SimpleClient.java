/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Utils.IPFinder;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

/**
 *
 * @author BIT2
 */
public class SimpleClient {
    
    
    
    
    
    
 
    
    
    public static void main(String[] args) throws IOException {

        System.out.println("My IP: "+IPFinder.getLocalHostLANAddress());
        System.out.println("Enter IP: ");
        String addr = IPFinder.getLocalHostLANAddress().toString();
        
        if (addr == null) {
             System.out.println("Usage: java QuoteClient <hostname>");
             return;
        }

            // get a datagram socket
        DatagramSocket socket = new DatagramSocket();

            // send request
        byte[] buf = new byte[256];
        
        InetAddress address = IPFinder.getLocalHostLANAddress();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
    
            // get response
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

	    // display response
        String received = new String(packet.getData(), 0, packet.getLength());
        System.out.println("Quote of the Moment: " + received);
    
        socket.close();
    }
}
