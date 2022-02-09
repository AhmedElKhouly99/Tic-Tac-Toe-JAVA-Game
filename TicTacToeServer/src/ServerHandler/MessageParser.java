
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import Database.Database;
import static ServerHandler.ClientHandler.clientsVector;
import game.Game;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import player.AllPlayers;
import player.Players;
import static player.Players.playersVector;

//invite::player2_id"abanoub", id="soly"
//accept::player1_id"soly"::abanoub
/**
 *
 * @author START
 */
public class MessageParser {


    public static void checkClientMsg(String msg, ClientHandler ch) throws IOException {

        String[] arrString = msg.split("::");

        ch.outObj.flush();
        switch (arrString[0]) {
            case "login":
                playersVector.forEach(e -> {
                    if (e.getUsername().equals(arrString[1])) {
                        try {
                            ch.outObj.writeObject("login::exist");
                            return;
                        } catch (IOException ex) {
                            Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });


                /*-------------------login::username::password----------------------*/
                if (isUser(arrString[1], arrString[2], ch.p)) {

                    playersVector.add(ch.p);
                    ch.outObj.writeObject("login::done");

                } else {
                    ch.outObj.writeObject("login::failed");

                    clientsVector.removeElement(ch);
                }

                break;

            case "signup":/*-------------------signup::username::password----------------------*/

                if (addUser(arrString[1], arrString[2], ch.p)) {
                    ch.outObj.writeObject("signup::done");
                } else {
                    ch.outObj.writeObject("signup::failed");
                }
                /*------------Insert user data from database----------*/

                break;

            case "onlinePlayers":

                ch.outObj.writeObject(new Vector<Players>(playersVector));
                break;

            case "rankings":

                ch.outObj.writeObject(new ArrayList<AllPlayers>(Database.getAllPlayers()));
                break;

           

            case "invite"://invite::khouly

                clientsVector.forEach((e) -> {
                    if (e.p.getUsername().equals(arrString[1])) {
                        try {
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
                            ch.player2Handler = e;
                            e.player2Handler = ch;
                            ch.p.setInGame(true);
                            e.p.setInGame(true);
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

            case "reject":
                clientsVector.forEach((e) -> {
                    if (e.p.getUsername().equals(arrString[1])) {
                        try {
                            e.outObj.writeObject("inviteRejected");
                            return;
                        } catch (IOException ex) {
                            Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                break;

            case "play"://play::index                
                /*-------------------playing::username::turn::indexPlaymove----------------------*/

                ch.player2Handler.outObj.writeObject("put::" + arrString[1]);

                break;

            case "winner"://winner                
                /*-------------------playing::username::turn::indexPlaymove----------------------*/

                ch.p.setScore(ch.p.getScore() + 10);
                Database.editPlayer(ch.p);

                break;

            case "recordrequest":
                ch.player2Handler.outObj.writeObject("record");

                break;

            case "recordaccept":
                ch.player2Handler.outObj.writeObject("recordaccepted");

                break;

            case "finishgame":
                ch.player2Handler.player2Handler = null;
                ch.player2Handler = null;

                break;

            case "exited":

                ch.player2Handler.outObj.writeObject("exitgame");

                break;

            case "getGame":
                Game g = Database.getGame(arrString[1], arrString[2]);
                if (g != null) {
                    ch.outObj.writeObject(g);

                    clientsVector.forEach((e) -> {
                        if (e.p.getUsername().equals(arrString[2])) {
                            try {
                                e.outObj.writeObject(g);//invitedyou::Soly
                            } catch (IOException ex) {
                                Logger.getLogger(MessageParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            return;
                        }
                    });

                } else {
                    ch.outObj.writeObject(null);
                }

                break;

      
            case "tie"://winner                
                /*-------------------playing::username::turn::indexPlaymove----------------------*/

                ch.p.setScore(ch.p.getScore() + 5);
                Database.editPlayer(ch.p);

                break;
        }
    }

    private static boolean isUser(String uname, String password, Players p) {
        return Database.isPlayer(uname, password, p);
    }

    private static boolean addUser(String uname, String pass, Players p) {
        if (Database.isPlayer(uname, pass, p)) {
            return false;
        }
        return Database.addPlayer(uname, pass);
    }

}
