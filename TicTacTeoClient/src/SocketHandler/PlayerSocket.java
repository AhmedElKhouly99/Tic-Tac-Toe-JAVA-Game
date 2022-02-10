/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author START
 */
public class PlayerSocket {

    static public Socket clientSocket;
    static public ObjectOutputStream outObj;

    static public ObjectInputStream inObj;

    static final String SOCKETIP = "127.0.0.1";
    static final int SOCKETPORT = 5005;
    String serverStatus = null;

    public static void socketInit() {
        try {
            clientSocket = new Socket(SOCKETIP, SOCKETPORT);
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {

        }

    }

    public static void closeSoket() {
        try {
            outObj.close();
            inObj.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendMsg(Object mgs){
        try {
            outObj.writeObject(mgs);
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Object receiveMsg(){
        Object res = null;
        try {
            res  = (Object)inObj.readObject();
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
}
