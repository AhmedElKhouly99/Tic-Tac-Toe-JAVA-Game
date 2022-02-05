/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerHandler;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;
import player.AllPlayers;

/**
 *
 * @author Abanoub Kamal
 */
public class ClientHandler extends Thread{
  

    long player2Vid;
    ObjectOutputStream outObj;
    ObjectInputStream inObj;
//    DataInputStream  inS;
//    PrintStream outS;
    static Vector<ClientHandler> clientsVector = new Vector<ClientHandler>();
    static Vector<Player> playersVector = new Vector<Player>();
    static Vector<AllPlayers> AllplayersVector = new Vector<AllPlayers>();
    String clientStatus;

    int id;
    static int counter_id=0;

    public Player p;

    ClientHandler player2Handler;
    
    public class Player{
        String username;
        int score;
        boolean inGame;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public boolean isInGame() {
            return inGame;
        }

        public void setInGame(boolean inGame) {
            this.inGame = inGame;
        }
    }

    
    public ClientHandler(Socket s) {
        try{
            //inS = new DataInputStream(s.getInputStream());
            //outS = new PrintStream(s.getOutputStream());
            inObj = new ObjectInputStream(s.getInputStream());
            outObj = new ObjectOutputStream(s.getOutputStream());
            clientStatus = "Online";
            clientsVector.add(this);
            id =counter_id++;
            this.p = new Player();
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
                clientStatus = (String)inObj.readObject();
                //clientStatus = inS.readLine(); // for client status // give an exception error if there is no client
                //outS.println("Online"); // for server status // give an exception error if there is no client
                //System.out.println(clientStatus);
                if(clientStatus == null) // done at the client fallen
                {
//                    System.out.println(clientStatus);
//                    System.out.println("tic.tac.toe_server.ClientHandler.run() from outside exception");
//                    inS.close();
//                    outS.close();
                    clientsVector.removeElement(this);
                    playersVector.removeElement(this.p);
                    this.currentThread().stop();
                }
                else
                {

                    
//                    String respond=MessageParser.checkClientMsg(clientStatus,id);
//                    outS.println(respond);
                        System.out.println("before msg");
                        System.out.println(clientStatus);
                   MessageParser.checkClientMsg(clientStatus, this);
                   //this.outS.println("done");

                }
                
                
                
                this.currentThread().sleep(50);
            }
        }catch(Exception ex){       // i don't know why this exception is not fire at the client is fallen
            try{
//                System.out.println(clientStatus);
//                System.out.println("tic.tac.toe_server.ClientHandler.run() from iside exception");
//                inS.close();
//                outS.close();
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
    
    
    
    
    


