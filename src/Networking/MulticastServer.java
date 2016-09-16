/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Networking;

import java.io.IOException;

/**
 *
 * @author BIT2
 */
public class MulticastServer{
    public static void main(String[] args) throws IOException {
        new MulticastServerThread().start();
    }
}
