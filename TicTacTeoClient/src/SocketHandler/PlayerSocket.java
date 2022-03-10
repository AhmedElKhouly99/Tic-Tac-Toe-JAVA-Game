/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tictactoe.LoginController;
import static tictactoe.LoginController.BackToMainBtn;
import tictactoe.MainController;

/**
 *
 * @author START
 */
public class PlayerSocket implements Initializable{

    static public Socket clientSocket;
    static public ObjectOutputStream outObj;

    static public ObjectInputStream inObj;

    static final String SOCKETIP = "127.0.0.1";
    static final int SOCKETPORT = 5005;
    String serverStatus = null;

    public static void socketInit() {
        try {
            clientSocket = new Socket(SOCKETIP, SOCKETPORT);
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            closeSoket();
//            disconnectionAlert(s);
        }

    }

    public static void closeSoket() {
        try {
            outObj.close();
            inObj.close();
            clientSocket.close();
        } catch (IOException ex) {
//            disconnectionAlert(s);
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
//            disconnectionAlert(s);
        }
    }

    public static void sendMsg(Object mgs) {
        try {
            outObj.writeObject(mgs);
        } catch (IOException ex) {
            disconnectionAlert();
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            closeSoket();
            disconnectionAlert();
        } finally {
//            disconnectionAlert();
        }
    }

    public static Object receiveMsg() {
        Object res = null;
        try {
            res = (Object) inObj.readObject();
        } catch (IOException ex) {
            disconnectionAlert();
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            disconnectionAlert();
//            Logger.getLogger(PlayerSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            closeSoket();
            disconnectionAlert();
        } finally {
//            disconnectionAlert();
        }
        return res;
    }

    public static void disconnectionAlert() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                try {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setTitle("Network");
                    alert.setHeaderText("Disconnected!!");
                    DialogPane dialogPane = alert.getDialogPane();
                    //        dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.showAndWait();
            }
        });

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
