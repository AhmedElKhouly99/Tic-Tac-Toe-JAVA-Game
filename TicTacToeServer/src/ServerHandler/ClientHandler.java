/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ServerHandler;

//import static ServerHandler.MessageParser.LoggedinPlayers;
import game.Game;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import player.Players;
import static player.Players.playersVector;

/**
 *
 * @author Abanoub Kamal
 */
public class ClientHandler extends Thread {

    ObjectOutputStream outObj;
    ObjectInputStream inObj;

    static Vector<ClientHandler> clientsVector = new Vector<ClientHandler>();

    String clientStatus;

    ClientHandler player2Handler;

    public boolean status;

    public Players p;

    public ClientHandler(Socket s) {
        try {
            inObj = new ObjectInputStream(s.getInputStream());
            outObj = new ObjectOutputStream(s.getOutputStream());
            clientStatus = "Online";
            clientsVector.add(this);
            this.p = new Players();
            status = true;
            start();
        } catch (Exception ex) {
            System.out.println("server.ChatHandler.<init>()");
        }
    }

    public void run() {
        try {

            while (status) {
                Object res = (Object) inObj.readObject();
                if (!(res.getClass() == clientStatus.getClass())) {
                    Database.Database.addGame((Game) res);
                } else {
                    clientStatus = (String) res;
                }

                if (clientStatus == null) // done at the client fallen
                {
                    clientsVector.removeElement(this);
                    playersVector.removeElement(this.p);
                    this.currentThread().stop();
                } else {

                    MessageParser.checkClientMsg(clientStatus, this);
                }
                this.currentThread().sleep(50);
            }
        } catch (Exception ex) {
            try {
                clientsVector.removeElement(this);
                playersVector.removeElement(this.p);
                this.currentThread().stop();
            } catch (Exception e) {
            }
        }

    }

    public static Vector<ClientHandler> getClientsVector() {
        return clientsVector;
    }

}
