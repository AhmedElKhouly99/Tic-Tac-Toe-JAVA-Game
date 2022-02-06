/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerHandler;

//import static ServerHandler.MessageParser.LoggedinPlayers;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import player.AllPlayers;
import player.Players;
import static player.Players.playersVector;

/**
 *
 * @author Abanoub Kamal
 */
public class ClientHandler extends Thread{
  
    ObjectOutputStream outObj;
    ObjectInputStream inObj;

    static Vector<ClientHandler> clientsVector = new Vector<ClientHandler>();

    String clientStatus;

    ClientHandler player2Handler;

    public boolean status;

    public Players p;
    
    public ClientHandler(Socket s) {
        try{
            inObj = new ObjectInputStream(s.getInputStream());
            outObj = new ObjectOutputStream(s.getOutputStream());
            clientStatus = "Online";
            clientsVector.add(this); 
            this.p = new Players();
            status = true;
            start();
        }catch(Exception ex){
             System.out.println("server.ChatHandler.<init>()");
        }
    }

    public void run(){
        try{
            
            while(true){
                clientStatus = (String)inObj.readObject();
                if(clientStatus == null) // done at the client fallen
                {
                    clientsVector.removeElement(this);
                    playersVector.removeElement(this.p);
                    this.currentThread().stop();
                }
                else
                {
                    System.out.println("before msg");
                    System.out.println(clientStatus);
                   MessageParser.checkClientMsg(clientStatus, this);
                } 
                this.currentThread().sleep(50);
            }
        }catch(Exception ex){       // i don't know why this exception is not fire at the client is fallen
            try{
                clientsVector.removeElement(this);
                playersVector.removeElement(this.p);
                this.currentThread().stop();
            }catch(Exception e){}
        }
        
    }

    public static Vector<ClientHandler> getClientsVector()
    {
        return clientsVector;
    }       
}