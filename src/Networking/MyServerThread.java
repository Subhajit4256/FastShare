/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import Frames.HomeWindow;
import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author BIT2
 */
public class MyServerThread {

    Socket socket;
    HomeWindow window;

    public MyServerThread(Socket s, HomeWindow homeWindow) {
        window = homeWindow;
        socket = s;
    }

    public void listen() {
        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    if (isTrying()) {
                        receive();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MyServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();
    }

    public boolean isTrying() {

        System.out.println("Trying...............");
        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());

        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
            Logger.getLogger(MyServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            String flag = "";

            System.out.println("Loooop");
            flag = dataInputStream.readUTF();
            System.out.println("Flaaaaaaaaaaaaaaag--------------" + flag);
            if (flag.equals("Start")) {
                return true;
            } else {
                return false;
            }

        } catch (IOException ex) {

            System.out.println("flag pr");
            return false;

            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendTo(File file) {
        window.jTabbedPane1.setSelectedIndex(1);
        window.jProgressBar1.setValue(0);
        window.jLabel1.setText("Sending File: " + file.getName());
        window.jLabel1.setVisible(true);
        window.jList1.add(new JLabel("Name : " + file.getName() + "\t" + "Type : Send"));
        InputStream in = null;
        OutputStream os = null;
        DataOutputStream dataOutputStream = null;
        try {
            in = new FileInputStream(file);
            os = socket.getOutputStream();
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            // Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Stream Problem");
        }
        try {
            dataOutputStream.writeUTF("Start");
            dataOutputStream.writeInt((int) file.length());
            dataOutputStream.writeUTF(file.getAbsolutePath().substring(file.getAbsoluteFile().toString().lastIndexOf(".")));
            dataOutputStream.writeUTF(file.getName());
        } catch (IOException ex) {
            Logger.getLogger(MyClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        int count = 0, len = (int) file.length();
        float total = 0;
        float prog = 0;
        byte bytes[] = new byte[16 * 1024];
        try {
            while ((count = in.read(bytes)) > 0) {
                total += count;
                prog = (total / len) * 100;
                window.jProgressBar1.setValue((int) prog);
                System.out.println("Sending");
                os.write(bytes, 0, count);
            }
            window.jProgressBar1.setValue(0);
            Toolkit.getDefaultToolkit().beep();
            os.flush();
            count = 0;
        } catch (IOException ex) {
            System.out.println("Problem at Sending");
        }
    }

    public void receive() {
        window.jTabbedPane1.setSelectedIndex(1);
        window.jProgressBar1.setValue(0);
        InputStream inputStream = null;
        OutputStream outputStream = null;
        int len = 0;
        String extension = "";
        String fileName = "";
        try {
            inputStream = socket.getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(MyServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

        DataInputStream dataInputStream = null;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            len = dataInputStream.readInt();

        } catch (IOException ex) {
            System.out.println("Can't get socket input stream. ");
            //Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            extension = dataInputStream.readUTF();
            fileName = dataInputStream.readUTF();
            System.out.println("Set");
            System.out.println("File Name=" + fileName);
        } catch (IOException ex) {
            System.out.println("Not Set");
            Logger.getLogger(MyServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        window.jLabel1.setText("Receiving File: " + fileName);
        window.jLabel1.setVisible(true);
        window.jList1.add(new JLabel("Name : " + fileName + "\t" + "Type : Receive"));
        try {
            outputStream = new FileOutputStream("D:\\" + fileName + extension);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[16 * 1024];
        int count = 0;
        int total = 0;
        float a = 0,ll=len;        
        try {
            while ((count = inputStream.read(bytes)) > 0) {
                total += count;
                System.out.println("Total------" + total + " & Length---" + len);
                a = (total / ll) * 100;               
                window.jProgressBar1.setValue((int) a);
                System.out.println("Progress====" + a);
                System.out.println("Receiving");
                outputStream.write(bytes, 0, count);
                if (len == total) {
                    break;
                }
            }
            window.jProgressBar1.setValue(0);
            Toolkit.getDefaultToolkit().beep();
            System.out.println("Valuess = " + window.jProgressBar1.getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
