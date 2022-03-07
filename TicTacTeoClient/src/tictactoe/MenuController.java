/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import SocketHandler.PlayerSocket;
import game.Game;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.ImageIcon;
import player.Players;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MenuController extends Thread implements Initializable {

    ImageIcon imageCursorGomme = new ImageIcon("cursor.png");
    @FXML
    private Button RankBtn;

    @FXML
    private Button StartGameBtn = new Button();

    static StackPane pane;
    Stage window;


    static boolean turnThread = true;
    static boolean waitTh = false;


    @FXML
    private Button LogoutBtn;

    static class XCell extends ListCell<String> {

        HBox hbox = new HBox();

        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button newGame = new Button();
        Button resume = new Button();
        String lastItem;
        String names[] = new String[3];
        int score[] = new int[3];

        public XCell() {
            super();

            label.setFont(new Font(20));
            label.setTranslateX(10);
            newGame.setPrefSize(50, 50);
            newGame.getStyleClass().add("btn");
            resume.setPrefSize(50, 50);
            newGame.setTranslateX(-30);

            newGame.getStyleClass().add("BtnLive");
            resume.getStyleClass().add("BtnLive");
            newGame.setStyle("-fx-background-color: #4adeed ");
            resume.setStyle("-fx-background-color: #4adeed ");


            ImageView newGameImg = new ImageView("newgame.png");

            newGameImg.setFitHeight(20);
            newGameImg.setFitWidth(20);
            newGameImg.setPreserveRatio(true);
            newGame.setGraphic(newGameImg);
            ImageView resumeImg = new ImageView("resum.png");
            resumeImg.setFitHeight(35);
            resumeImg.setFitWidth(35);
            newGameImg.setPreserveRatio(true);
            resume.setGraphic(resumeImg);

            ImageView onlineImg = new ImageView("online.png");
            onlineImg.setTranslateY(10);
            onlineImg.setTranslateX(-3);
            onlineImg.setFitHeight(10);
            onlineImg.setFitWidth(10);

            hbox.getChildren().addAll(onlineImg, label, pane, newGame, resume);
            HBox.setHgrow(pane, Priority.ALWAYS);
            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String[] invitePlay = label.getText().split("\t");

                    turnThread = false;



                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    ButtonType buttonSave = new ButtonType("Invite");
                    ButtonType buttonDontSave = new ButtonType("Cancel");
                    alert.setTitle("Invitation");
                    alert.setHeaderText("Do you want to send an invite to " + invitePlay[0] + "?");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.getButtonTypes().setAll(buttonSave, buttonDontSave);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == buttonSave) {
                        ConnectedPlayers.forEach(p
                                -> {
                            Parent root = null;
                            if (p.getUsername().equals(invitePlay[0]) && !p.isInGame()) {
                                try {

                                    PlayerSocket.outObj.writeObject("invite::" + invitePlay[0]);

                                    String respond = (String) PlayerSocket.inObj.readObject();

                                    if (respond.equals("inviteAccepted")) {
                                        try {
                                            waitTh = false;
                                            turnThread = false;
                                            Players.vsPlayer = new Players();
                                            Players.vsPlayer.setScore(Integer.parseInt(invitePlay[1]));
                                            Players.vsPlayer.setUsername(invitePlay[0]);
                                            Players.vsPlayer.setInGame(true);
                                            Players.myPlayer.setInGame(true);
                                            root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
                                            Stage window = (Stage) newGame.getScene().getWindow();
                                            window.setScene(new Scene(root));
                                        } catch (IOException ex) {
                                            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else {
                                        Alert alert1 = new Alert(AlertType.INFORMATION);
                                        alert1.initModality(Modality.APPLICATION_MODAL);
                                        alert1.setTitle("Invitation");
                                        alert1.setHeaderText("Invite rejected");
                                        DialogPane dialogPane1 = alert1.getDialogPane();
                                        dialogPane1.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                        dialogPane1.getStyleClass().add("myDialog");
                                        alert1.show();
                                        turnThread = true;

                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }else{
                                Alert alert1 = new Alert(AlertType.INFORMATION);
                                alert1.initModality(Modality.APPLICATION_MODAL);
                                alert1.setTitle("Invitation");
                                alert1.setHeaderText(invitePlay[0]+"in another game!");
                                DialogPane dialogPane1 = alert.getDialogPane();
                                dialogPane1.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                dialogPane1.getStyleClass().add("myDialog");
                            }

                        });

                    }else{
                        Alert alert1 = new Alert(AlertType.INFORMATION);
                        alert1.initModality(Modality.APPLICATION_MODAL);
                        alert1.setTitle("Invitation");
                        alert1.setHeaderText("Invite rejected!!");
                        DialogPane dialogPane1 = alert1.getDialogPane();
                        dialogPane1.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                        dialogPane1.getStyleClass().add("myDialog");
                        alert1.show();
                    }
                }
            });
            //----------Resume Button Event Handler ----------------------------//
            resume.setOnAction(new EventHandler<ActionEvent>() {
//
                     @Override
                     public void handle(ActionEvent event) {
                         String[] invitePlay = label.getText().split("\t");

                         turnThread = false;


                         Alert alert = new Alert(AlertType.CONFIRMATION);
                         alert.initModality(Modality.APPLICATION_MODAL);
                         ButtonType buttonSave = new ButtonType("resume");
                         ButtonType buttonDontSave = new ButtonType("Cancel");
                         alert.setTitle("Invitation");
                         alert.setHeaderText("Do you want to resume the game with "+invitePlay[0]+"?");
                         DialogPane dialogPane = alert.getDialogPane();
                         dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                         dialogPane.getStyleClass().add("myDialog");
                         alert.getButtonTypes().setAll(buttonSave, buttonDontSave);

                         Optional<ButtonType> result = alert.showAndWait();

                         if (result.get() == buttonSave) {
                             ConnectedPlayers.forEach(p
                                     -> {
                                 Parent root = null;
                                 if (p.getUsername().equals(invitePlay[0]) && !p.isInGame()) {
                                     try {

                                         PlayerSocket.outObj.writeObject("getGame::"+Players.myPlayer.getUsername()+"::"+ invitePlay[0]);
                                         Object res = (Object)PlayerSocket.inObj.readObject();

                                         Game.myGame= null;

                                         if(res != null){
                                             Game.myGame = (Game)res;
                                             PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);


                                         String respond = (String) PlayerSocket.inObj.readObject();

                                         if (respond.equals("inviteAccepted")) {
                                             try {
                                                 waitTh = false;
                                                 turnThread = false;
                                                 Players.vsPlayer = new Players();
                                                 Players.vsPlayer.setScore(Integer.parseInt(invitePlay[1]));
                                                 Players.vsPlayer.setUsername(invitePlay[0]);
                                                 Players.vsPlayer.setInGame(true);
                                                 Players.myPlayer.setInGame(true);
                                                 root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
                                                 Stage window = (Stage) newGame.getScene().getWindow();
                                                 window.setScene(new Scene(root));
                                             } catch (IOException ex) {
                                                 Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                             }
                                         }else{
                                             PlayerSocket.outObj.writeObject(Game.myGame);
                                         }
                                         } else {
                                             turnThread = true;

                                         }
                                     } catch (IOException ex) {
                                         Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                     } catch (ClassNotFoundException ex) {
                                         Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                     }

                                 }

                             });
                         }
                     }
                 });
             }
        


        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;
                label.setText(item != null ? item : "<null>");

                setGraphic(hbox);
            }
        }
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

          waitTh=false;
          turnThread=true;
     
    }

    @FXML
    private void GoToRanks(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("ScoreList.fxml"));
        Stage window = (Stage) RankBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    void StartGame(ActionEvent event) throws IOException {
        Button returnBtn = new Button();
        ImageView returnImg = new ImageView("returnn.png");
        returnImg.setFitHeight(30);
        returnImg.setFitWidth(50);
        returnImg.setPreserveRatio(true);
        returnBtn.setGraphic(returnImg);
        Label onlinePlayers = new Label("List of Online Players , send a game request to start a game !");
        onlinePlayers.setStyle("-fx-text-fill: purple; -fx-font-size: 16px;");
        HBox hboxheader = new HBox();
        hboxheader.setStyle("-fx-background-image: url('images/bg2.jpg');");
        waitTh = true;

        returnBtn.getStyleClass().add("BtnLive");

        pane = new StackPane();
        BorderPane borderpane = new BorderPane();
        hboxheader.setPrefHeight(70);

        onlinePlayers.setTranslateX(-35);
        onlinePlayers.setTranslateY(35);
        hboxheader.getChildren().addAll(returnBtn, onlinePlayers);
        borderpane.setTop(hboxheader);
        borderpane.setCenter(pane);
        returnBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                waitTh = false;
                stop();
                PlayerSocket.closeSoket();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Stage window = (Stage) returnBtn.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        });

        start();

        Stage window = (Stage) StartGameBtn.getScene().getWindow();
        Scene scene = new Scene(borderpane, 500, 500);
        scene.getStylesheets().add(getClass().getResource("BackGround.css").toString());
        window.setScene(scene);

    }


    @FXML
    void Logout(ActionEvent event) throws IOException {
        PlayerSocket.closeSoket();
        stop();
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) LogoutBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    static Game anyGame=new Game();
    
    private void checkInvite(String user) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initModality(Modality.APPLICATION_MODAL);
                ButtonType buttonSave = new ButtonType("Accept");
                ButtonType buttonDontSave = new ButtonType("Reject");
                alert.setTitle("Invitation");
                alert.setHeaderText("Do you want to play with " + user + "?");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                alert.getButtonTypes().setAll(buttonSave, buttonDontSave);

                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == buttonSave) {
                    try {
                        PlayerSocket.outObj.writeObject("accept::" + user);

                        Players.vsPlayer = new Players();
                        Players.vsPlayer.setUsername(user);
                        ConnectedPlayers.forEach(p
                                -> {
                            if (p.getUsername().equals(user)) {
                                Players.vsPlayer.setScore(p.getScore());
                            }
                        });
                        
                        Players.vsPlayer.setInGame(true);

                        Players.myPlayer.setInGame(true);


                        Parent root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
                        Stage window = (Stage) pane.getScene().getWindow();
                        window.setScene(new Scene(root));
                        waitTh=false;
                        stop();

                    } catch (IOException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (result.get() == buttonDontSave) {

                    Game.myGame.setUsername1_x(null);
                    try {
                        PlayerSocket.outObj.writeObject("reject::" + user);
                        PlayerSocket.inObj.readObject();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }
//    public static ObservableList<String> list;
    public static Vector<Players> ConnectedPlayers=new Vector<>();
//    public static ListView<String> lv = null;
    @Override
    public void run() {
        while (waitTh) {
            if (!turnThread) {
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                    
                    PlayerSocket.sendMsg("onlinePlayers");
                    Object checkType = PlayerSocket.receiveMsg();
                    if(checkType == null){
                         Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                    Stage window = (Stage) pane.getScene().getWindow();
                                    window.setScene(new Scene(root));
                                } catch (IOException ex) {
                                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                         });
                         break;
                    }
                    String msg = new String();

                    if (checkType.getClass() == msg.getClass()) {

                        String[] arrString = ((String) checkType).split("::");

                        checkInvite(arrString[1]);

                    } else //                
                    if(checkType.getClass() == anyGame.getClass()){
                        Game.myGame = (Game)checkType;
                    }
                    else if(checkType.getClass() == ConnectedPlayers.getClass()){ConnectedPlayers.clear();
                        ConnectedPlayers = (Vector<Players>) checkType;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pane.getChildren().removeAll();
                            String[] info = new String[ConnectedPlayers.size() - 1];
                            int i = 0;
                            for (Players p : ConnectedPlayers) {
                                if (p.getUsername().equals(Players.myPlayer.getUsername())) {
                                    Players.myPlayer.setScore(p.getScore());
                                    continue;
                                }

                                if(p.isInGame()){
                                    info[i] = p.getUsername() + "\t" + p.getScore()+"\tin a game!";
                                }else{
                                    info[i] = p.getUsername() + "\t" + p.getScore();
                                }
                                i++;
                            }

                            ObservableList<String> list = FXCollections.observableArrayList(info);
                            ListView<String> lv = new ListView<>(list);
                            lv.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() - 200);
                            lv.setStyle("-fx-control-inner-background: #edcef1; -fx-background-radius: 5; -fx-border-color: #b023c1; -fx-border-style: solid; -fx-border-width: 2; -fx-border-radius: 5;");
                            
                            lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                                @Override
                                public ListCell<String> call(ListView<String> param) {
                                    XCell c = new XCell();
                                     return c;
                                }
                            });
                            pane.getChildren().removeAll();
                            pane.getChildren().addAll(lv);
                        }
                    }); 
                try {
                    sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        stop();
    }

}
