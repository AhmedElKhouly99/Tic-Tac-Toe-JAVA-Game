/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import tictactoe.TicTacToe;

/**
 *
 * @author START
 */
public class PlayerSocket {
    static public Socket clientSocket;
    static public ObjectOutputStream outObj;
//    static public DataInputStream inS;
//    static public PrintStream outS;
    static public ObjectInputStream inObj;
    
    static final String SOCKETIP="127.0.0.1";
    static final int SOCKETPORT=5005;
    String serverStatus = null;
    
    
    
    public static void socketInit()
    {
        try{
            clientSocket = new Socket(SOCKETIP, SOCKETPORT);
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
        }catch(IOException e){
            System.out.println("clientapp.ClientApp.init().init client socket and streams");
        }
  
        
    }

    public static void closeSoket(){
        try {
            outObj.close();
            inObj.close();
            clientSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
