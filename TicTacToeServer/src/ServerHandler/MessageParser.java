/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import Database.Database;
import static ServerHandler.ClientHandler.clientsVector;
import static ServerHandler.ClientHandler.playersVector;
import java.io.IOException;
import java.util.Vector;
//import player.Player;

//invite::player2_id"abanoub", id="soly"
//accept::player1_id"soly"::abanoub
/**
 *
 * @author START
 */
public class MessageParser {

    //static Vector<Player> LoggedinPlayers = new Vector<Player>();
    public static void checkClientMsg(String msg, ClientHandler ch) throws IOException {
        String[] arrString = msg.split("::");
      
        switch (arrString[0]) {
            case "login":
                /*-------------------login::username::password----------------------*/
                isUser(arrString[1], arrString[2], ch.p);
                if (ch.p != null) {

                    System.out.println("correct user!!");
                    
                    //LoggedinPlayers.add(p);
                    //ch.thisUname = ch.p.getUsername();
                    //System.out.println(ch.thisUname);
                    ch.outS.println("login::done");
                    System.out.println(ch.p.getUsername());
                    playersVector.add(ch.p);
                } else {
                    ch.outS.println("login::failed");
                    clientsVector.removeElement(ch);
                }

                break;

            case "signup":/*-------------------signup::username::password----------------------*/
                //Player p1 = new Player(arrString[1], arrString[2], arrString[3].charAt(0));
                if (addUser(arrString[1], arrString[2])) {
                    ch.outS.println("signup::done");
                } else {
                    ch.outS.println("signup::failed");
                }

                /*------------Insert user data from database----------*/
                break;
                
            case "onlinePlayers":
                
                ch.outObj.writeObject(playersVector);
                break;
                
            case "rankings":
                
                ch.outObj.writeObject(Database.getAllPlayers());
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
//                        System.out.println(ch.thisUname+"***");
                        e.outS.println("invitedyou::" + ch.p.getUsername());//invitedyou::Soly
                        return;
                    }
                });

                break;
            case "accept"://accept::Soly
                clientsVector.forEach((e) -> {
                    if (e.p.getUsername().equals(arrString[1])) {
                        System.out.println(e.p.getUsername());
                        ch.player2Handler = e;
                        e.player2Handler = ch;

                        e.outS.println("inviteAccepted");
                        e.outS.println("X");
                        ch.outS.println("O");
                        return;
                    }
                });
                // if accepted start game
                break;

            case "play"://play::index                
                /*-------------------playing::username::turn::indexPlaymove----------------------*/

                ch.player2Handler.outS.println("put::" + arrString[1]);

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

    private static void isUser(String uname, String password, ClientHandler.Player p) {
        Database.isPlayer(uname, password, p);
    }

    private static boolean addUser(String uname, String pass) {
        return Database.addPlayer(uname, pass);
    }

}
