/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import Database.Database;
//import ServerHandler.ClientHandler.Player;
import static ServerHandler.ClientHandler.clientsVector;
import static ServerHandler.ClientHandler.playersVector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.AllPlayers;
import player.Players;

//invite::player2_id"abanoub", id="soly"
//accept::player1_id"soly"::abanoub

/**
 *
 * @author START
 */
public class MessageParser {
    
<<<<<<< HEAD
    static Vector<Player> LoggedinPlayers = new Vector<Player>();
    
    
    public static void checkClientMsg(String msg, ClientHandler ch) throws IOException
    {
        String[] arrString=msg.split("::");
        //System.out.println(msg);
      //  System.out.println(arrString[0]);
        //System.out.println(arrString[1]);
        
        if("status".equals(arrString[0]))
        {
            if("Online".equals(arrString[1]))
            {
                System.out.println("My test succeded");
            }
        }
        
        
        switch (arrString[0])
        {
            case "login": /*-------------------login::username::password----------------------*/
                Player p = null;
                if(isUser(p, arrString[1], arrString[2])){
                    System.out.println("correct user!!");
                    ch.thisUname = p.getUsername();
                    LoggedinPlayers.add(p);
                    ch.outS.println("login::done");
                }else{
                    ch.outS.println("login::failed");
                    ///Incorrect username or password
=======
    
    

    //static Vector<Player> LoggedinPlayers = new Vector<Player>();
    public static void checkClientMsg(String msg, ClientHandler ch) throws IOException {
        Vector<Players> p11 = new Vector<Players>();
        p11.add(new Players("ahmed", 100));
        p11.add(new Players("ahmed", 100));
        p11.add(new Players("ahmed", 100));
        
        String[] arrString = msg.split("::");
        
        Vector<Integer> test = new Vector<>();
        test.add(10);
        test.add(20);
        test.add(30);
      
        switch (arrString[0]) {
            case "login":
                //ch.outS.println("3abat");
                /*-------------------login::username::password----------------------*/
                if(isUser(arrString[1], arrString[2], ch.p)){
                //if (ch.p != null) {

                    System.out.println("correct user!!");
                    
                    //LoggedinPlayers.add(p);
                    //ch.thisUname = ch.p.getUsername();
                    System.out.println(ch.p.getUsername());
                    
                    System.out.println(ch.p.getUsername());
                    playersVector.add(ch.p);
                    ch.outObj.writeObject("login::done");
                    //ch.outS.println("login::done");
                } else {
                    ch.outObj.writeObject("login::failed");
                    //ch.outS.println("login::failed");
                    clientsVector.removeElement(ch);
>>>>>>> 0940e864bda16540a6381ebcce2f727e396bffa6
                }
                
                break;
                
            case "signup":/*-------------------signup::username::password----------------------*/
<<<<<<< HEAD
                Player p1 = new Player(arrString[1], arrString[2], arrString[3].charAt(0));
                if(addUser(p1)){
                    ch.outS.println("signup::done");
                }else{
                    ch.outS.println("signup::failed");
                }    
         /*------------Insert user data from database----------*/
                
                break; 
                
                
           case "playing"://play::x|o::index  this
               
               /*-------------------playing::username::turn::indexPlaymove----------------------*/
                
         /*------------In a game----------*/
               
                break;
                
           case "finished_playing": /*-------------------playing::username::winnerStatus----------------------*/
                
         /*------------In a game----------*/
               
                break; 
           
           case "paused_playing": /*-------------------playing::username::winnerStatus----------------------*/
                
         /*------------In a game----------*/
               
                break;     
                
                
           
           case "notplaying": /*-------------------notplaying::username::waitinginqueue/notwaiting--------------------*/
                
         /*------------Not doing anything----------*/
               
                break;  
                
                                                            //sender1
           case "message":  /*-------------------message::username1::username2/all chat----------------------*/
    
               
         /*------------Writting a message----------*/
               
                break;     
              
           case "invite"://invite::abanoub_id
               clientsVector.forEach((e) -> {
                   if(e.thisUname == arrString[1]){
                       e.outS.println("invitedyou::"+ch.thisUname);//invitedyou::soly_id
                       return;
                   }
               });
               break;
           case "accept"://accept::soly
               clientsVector.forEach((e) -> {
                   if(e.thisUname == arrString[1]){
//                       e.player2Vid = ch.getId();
//                       ch.player2Vid = e.getId();
                        ch.player2Handler = e;
                        e.player2Handler = ch;
                       e.outS.println("inviteAccepted");
                       return;
                   }
               });
                    // if accepted start game
               break;
                
        }
    }
    
    private static boolean isUser(Player p, String uname, String password)
    {
        p = Database.isPlayer(uname, password);
        if(p != null){
            return true;
        }
        return false;
    }
    
    private static boolean addUser(Player p){
        return Database.addPlayer(p);
=======
                //Player p1 = new Players(arrString[1], arrString[2], arrString[3].charAt(0));
                if (addUser(arrString[1], arrString[2])) {
                    ch.outObj.writeObject("signup::done");
                } else {
                    ch.outObj.writeObject("signup::failed");
                }

                /*------------Insert user data from database----------*/
                break;
                
            case "onlinePlayers":
                
                //ch.outObj.writeObject(playersVector);
                break;
                
            case "rankings":
                System.out.println("rankings");
                System.out.println(Database.getAllPlayers());
//                ObservableList<AllPlayers> list = Database.getAllPlayers();
//                System.out.println(list);
//                ch.outObj.writeObject(list);
                ch.outObj.writeObject(new ArrayList<AllPlayers>(Database.getAllPlayers()));
//                ch.outObj.writeObject(p11);
                //ch.outObj.writeObject(p11);
//                ch.outObj.writeObject(test);
//               ch.outObj.writeObject(Database.getAllPlayers());
                break;

            case "finished_playing":
                /*-------------------playing::username::winnerStatus----------------------*/

 /*------------In a game----------*/
                break;

            case "paused_playing":
                /*-------------------playing::username::winnerStatus----------------------*/

 /*------------In a game----------*/
                break;

            case "notplaying":
                /*-------------------notplaying::username::waitinginqueue/notwaiting--------------------*/

 /*------------Not doing anything----------*/
                break;

            //sender1
            case "message":
                /*-------------------message::username1::username2/all chat----------------------*/


 /*------------Writting a message----------*/
                break;

            case "invite"://invite::khouly
                System.out.println(msg);
                clientsVector.forEach((e) -> {
                    System.out.println(e.p.getUsername());
                    if (e.p.getUsername().equals(arrString[1])) {
                        try {
                            //                        System.out.println(ch.thisUname+"***");
                            e.outObj.writeObject("invitedyou::" + ch.p.getUsername());//invitedyou::Soly
                        } catch (IOException ex) {
                            Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        return;
                    }
                });

                break;
            case "accept"://accept::Soly
                clientsVector.forEach((e) -> {
                    if (e.p.getUsername().equals(arrString[1])) {
                        try {
                            System.out.println(e.p.getUsername());
                            ch.player2Handler = e;
                            e.player2Handler = ch;
                            
                            e.outObj.writeObject("inviteAccepted");
                            e.outObj.writeObject("X");
                            ch.outObj.writeObject("O");
                            return;
                        } catch (IOException ex) {
                            Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                // if accepted start game
                break;

            case "play"://play::index                
                /*-------------------playing::username::turn::indexPlaymove----------------------*/

                ch.player2Handler.outObj.writeObject("put::" + arrString[1]);

                break;

//            case "winner"://winner                
//                /*-------------------playing::username::turn::indexPlaymove----------------------*/
//
//                ch.println("youlose");
//
//                break;  
////            
//            case "tie"://winner                
//                /*-------------------playing::username::turn::indexPlaymove----------------------*/
//
//                ch.player2Handler.outS.println("tie");
//
//                break;    
        }
    }

    private static boolean isUser(String uname, String password, Players p) {
        return Database.isPlayer(uname, password, p);
    }

    private static boolean addUser(String uname, String pass) {
        return Database.addPlayer(uname, pass);
>>>>>>> 0940e864bda16540a6381ebcce2f727e396bffa6
    }
       
    
    
}    
