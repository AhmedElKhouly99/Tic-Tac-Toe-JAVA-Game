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
            //inS = new DataInputStream(clientSocket.getInputStream());
            //outS = new PrintStream(clientSocket.getOutputStream());
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
        }catch(Exception e){
            
            System.out.println("clientapp.ClientApp.init().init client socket and streams");
        }
  
        
    }
    
    public static void refresh() throws IOException{
//        outObj.close();
        inObj.close();
//        outObj = new ObjectOutputStream(clientSocket.getOutputStream());
//        inObj = new ObjectInputStream(clientSocket.getInputStream());
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
    
//    Thread updatingClientGuiThread;
//    private void startThreadToUpdateClientGui()
//    {
//            Runnable runnable = new Runnable(){
//            @Override
//            public void run(){
//    
//                while(true){
//                    try{
//                        outS.println("Online"); // for client status // give an exception error if there is no server
//                        serverStatus = inS.readLine(); // for server status // give an exception error if there is no server
//                        
//                        Platform.runLater(new Runnable(){
//                        @Override
//                            public void run(){
//                                 // write the serverStatus on client GUI  
//                                            
//                            }
//                        }); 
//                    }catch(Exception e){
//                        e.getStackTrace();
//                        System.out.println("TicTacToe.run().threadUpdateGui");
//                        /* in case the server isn't exist */
//                        Platform.runLater(new Runnable(){
//                            @Override
//                            public void run(){
//                                  
//                            }
//
//                        }); 
//                    }
//                    
//                    try {
//                        updatingClientGuiThread.sleep(50);
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(TicTacToe.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        };
//        updatingClientGuiThread = new Thread(runnable);
//        updatingClientGuiThread.start();
//    }
//    
    
    
    
    
    
}
