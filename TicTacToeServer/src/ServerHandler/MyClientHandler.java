/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

/**
 *
 * @author Abanoub Kamal
 */
public class MyClientHandler extends Thread{
  

    long player2Vid;

    DataInputStream  inS;
    PrintStream outS;
    static Vector<MyClientHandler> clientsVector = new Vector<MyClientHandler>();
    String clientStatus;

    int id;
    static int counter_id=0;

    String thisUname;
    
    int score;

    ClientHandler player2Handler;

    
    public MyClientHandler(Socket s) {
        try{
            inS = new DataInputStream(s.getInputStream());
            outS = new PrintStream(s.getOutputStream());
            clientStatus = "Online";
            clientsVector.add(this);
            ////////////
            thisUname = "abanoub " ;
            score = 500;
            System.out.println("adding player.");
            ////\\\\\\\\\\\\\\\
            id =counter_id++;
            start();
        }catch(Exception ex){
             System.out.println("ServerHandler.MyClientHandler.<init>()");
        }
    }

    public void run(){
        try{            
            while(true){
                
                clientStatus = inS.readLine(); // for client status // give an exception error if there is no client
                //outS.println("Online"); // for server status // give an exception error if there is no client
                //System.out.println(clientStatus);
                if(clientStatus == null) // done at the client fallen
                {
                    System.out.println(clientStatus);
                    System.out.println("ServerHandler.MyClientHandler.run()");
                    inS.close();
                    outS.close();
                    clientsVector.removeElement(this);
                    this.currentThread().stop();
                }
                else
                {
//                    String respond=MessageParser.checkClientMsg(clientStatus,id);
//                    outS.println(respond);
//                    System.out.println("before msg");
//                    MessageParser.checkClientMsg(clientStatus, this);
                    //this.outS.println("done");
                }  
                
                this.currentThread().sleep(50);
            }
        }catch(Exception ex){       // i don't know why this exception is not fire at the client is fallen
            try{
                System.out.println(clientStatus);
                System.out.println("ServerHandler.MyClientHandler.run().Exception");
                inS.close();
                outS.close();
                clientsVector.removeElement(this);
                this.currentThread().stop();
            }catch(IOException e){
                System.out.println("ServerHandler.MyClientHandler.run().IOException");
            }
        }
        
    }

    public static Vector<MyClientHandler> getClientsVector()
    {
        return clientsVector;
    }
      
        
}
    
    
    
    
    


