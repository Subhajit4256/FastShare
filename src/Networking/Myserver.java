/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Frames.HomeWindow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BIT2
 */
public class Myserver {

    ServerSocket serverSocket;

    public Myserver(HomeWindow homeWindow) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                  try {
                serverSocket = new ServerSocket(8888);
                 } catch (IOException ex) {
                        Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                while (true) {
                  
                      try {
                          Socket socket = serverSocket.accept();
                          System.out.println("Connected");
                          MyServerThread mst = new MyServerThread(socket,homeWindow);
                          homeWindow.listofSocket.add(mst);
                          System.out.println("Size=====fff"+homeWindow.listofSocket.size());
                          mst.listen();
                      } catch (IOException ex) {
                          Logger.getLogger(Myserver.class.getName()).log(Level.SEVERE, null, ex);
                      }
                   
                }
            }
        }).start();

    }

}
