/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import Database.Database;
import static ServerHandler.ClientHandler.clientsVector;
import java.io.IOException;
import java.util.Vector;
import player.Player;

//invite::player2_id"abanoub", id="soly"
//accept::player1_id"soly"::abanoub

/**
 *
 * @author START
 */
public class MessageParser {
    
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
                }
                
                break;
                
            case "signup":/*-------------------signup::username::password----------------------*/
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
    }
       
    
    
}    
