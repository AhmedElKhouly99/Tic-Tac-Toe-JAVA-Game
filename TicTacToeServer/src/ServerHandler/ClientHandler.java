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
public class ClientHandler extends Thread{
  

    long player2Vid;

    DataInputStream  inS;
    PrintStream outS;
    static Vector<ClientHandler> clientsVector = new Vector<ClientHandler>();
    String clientStatus;

    int id;
    static int counter_id=0;

    String thisUname;

    ClientHandler player2Handler;

    
    public ClientHandler(Socket s) {
        try{
            inS = new DataInputStream(s.getInputStream());
            outS = new PrintStream(s.getOutputStream());
            clientStatus = "Online";
            clientsVector.add(this);
            id =counter_id++;
            start();
        }catch(Exception ex){
             System.out.println("server.ChatHandler.<init>()");
        }
    }

    public void run(){
        try{
            /////////////////////////////////////////////////////////////////////
//            String username = inS.readLine();
//            String password = inS.readLine();
//            
//            ifUserExist(username);
            
            ////////////////////////////////////////////////////////////////////
            
            while(true){
                
                clientStatus = inS.readLine(); // for client status // give an exception error if there is no client
                outS.println("Online"); // for server status // give an exception error if there is no client
                //System.out.println(clientStatus);
                if(clientStatus == null) // done at the client fallen
                {
                    System.out.println(clientStatus);
                    System.out.println("tic.tac.toe_server.ClientHandler.run() from outside exception");
                    inS.close();
                    outS.close();
                    clientsVector.removeElement(this);
                    this.currentThread().stop();
                }
                else
                {

                    
//                    String respond=MessageParser.checkClientMsg(clientStatus,id);
//                    outS.println(respond);

                   MessageParser.checkClientMsg(clientStatus, this); 

                }
                
                
                
                this.currentThread().sleep(50);
            }
        }catch(Exception ex){       // i don't know why this exception is not fire at the client is fallen
            try{
                System.out.println(clientStatus);
                System.out.println("tic.tac.toe_server.ClientHandler.run() from iside exception");
                inS.close();
                outS.close();
                clientsVector.removeElement(this);
                this.currentThread().stop();
            }catch(IOException e){}
        }
        
    }

    public static Vector<ClientHandler> getClientsVector()
    {
        return clientsVector;
    }
    
    
    
        
        
        
        
        
//        if("login".equals(arrString[0]))
//        {
//            if("Username from database".equals(arrString[1]))
//            {
//                
//                if("Password from database".equals(arrString[2]))
//                {
//                    System.out.println("My test succeded");
//                }
//                
//            }
//        }
//        
//        
//        /*------------Insert user data from database----------*/
//        
//        if("signup".equals(arrString[0]))   
//        {
//            
//        }
        
/* handling the user existness */        
        
}
    
    
    
    
    


