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
    
    static Vector<Players> LoggedinPlayers = new Vector<Players>();
    public static void checkClientMsg(String msg, ClientHandler ch) throws IOException {
        
        String[] arrString = msg.split("::");
        
        Vector<Integer> test = new Vector<>();
        test.add(10);
        test.add(20);
        test.add(30);
      
        switch (arrString[0]) {
            case "login":
               LoggedinPlayers.forEach(e->{
                    if(e.getUsername().equals(arrString[1])){
                        try {
                            ch.outObj.writeObject("login::exist");
                            return;
                        } catch (IOException ex) {
                            Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                
                //ch.outS.println("3abat");
                /*-------------------login::username::password----------------------*/
                if(isUser(arrString[1], arrString[2], ch.p)){
                //if (ch.p != null) {

                    System.out.println("correct user!!");
                    
                    //LoggedinPlayers.add(p);
                    //ch.thisUname = ch.p.getUsername();
                    System.out.println(ch.p.getUsername());
                    
                    System.out.println(ch.p.getUsername());
                    LoggedinPlayers.add(ch.p);
                    ch.outObj.writeObject("login::done");
                    //ch.outS.println("login::done");
                } else {
                    ch.outObj.writeObject("login::failed");
                    //ch.outS.println("login::failed");
                    clientsVector.removeElement(ch);
                }
                
                break;
                
            case "signup":/*-------------------signup::username::password----------------------*/

//                Players p1 = new Players(arrString[1], arrString[2]);
                if(addUser(arrString[1], arrString[2])){
                    ch.outS.println("signup::done");
                }else{
                    ch.outS.println("signup::failed");
                }    
         /*------------Insert user data from database----------*/
                
                break; 
                
            case "onlinePlayers":
                
                //ch.outObj.writeObject(playersVector);
                break;
                
            case "rankings":
                System.out.println("rankings");
//                System.out.println(Database.getAllPlayers());
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
    }
       
    
    
}    
