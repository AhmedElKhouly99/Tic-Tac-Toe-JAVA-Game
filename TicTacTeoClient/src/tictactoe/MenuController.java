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
import java.security.Provider.Service;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
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
import javafx.stage.Window;
import javafx.stage.WindowEvent;
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
//    public static Stage homeStage;

    static boolean turnThread = true;
    static boolean waitTh = false;
//    Timer time;
//    InterruptTimerTask interruptTimerTask;

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
//            File file = new File();
            newGame.getStyleClass().add("BtnLive");
            resume.getStyleClass().add("BtnLive");
            newGame.setStyle("-fx-background-color: #4adeed ");
            resume.setStyle("-fx-background-color: #4adeed ");

//Image image = new Image(new FileInputStream("file:images/newgame.png"));
//        Image image = new Image("file:images/newgame.png");
            ImageView newGameImg = new ImageView("newgame.png");
//        newGameImg.setImage();
//             ImageView newGameImg = new ImageView("tictactoe.images/newgame.png");
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
                    System.out.println(label.getText());

//                       Parent root = null;
//                    try {
//                        PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);
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
//                                    PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);
                                    PlayerSocket.outObj.writeObject("invite::" + invitePlay[0]);

                                    String respond = (String) PlayerSocket.inObj.readObject();
                                    System.out.println(respond);
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
                                        turnThread = true;

                                    }
                                } catch (IOException ex) {
                                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                }

                            }

                        });
//                        PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);
//                        PlayerSocket.outObj.writeObject("invite::"+invitePlay[0]);
//
//                        String respond = (String)PlayerSocket.inObj.readObject();
//                        System.out.println(respond);
//                        if(respond.equals("inviteAccepted")){
//                            waitTh = false;
//                            turnThread = false;
//                            Players.vsPlayer=new Players();
//                            Players.vsPlayer.setScore(Integer.parseInt(invitePlay[1]));
//                            Players.vsPlayer.setUsername(invitePlay[0]);
//                            Players.vsPlayer.setInGame(true);
//                            Players.myPlayer.setInGame(true);
//                            root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
//                            Stage window = (Stage) newGame.getScene().getWindow();
//                            window.setScene(new Scene(root));
//                            
//                        }else{
//                            turnThread = true;
//                            
//                        }
                    }

//                    } catch (IOException ex) {
//                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (ClassNotFoundException ex) {
//                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
            });
            //----------Resume Button Event Handler ----------------------------//
            resume.setOnAction(new EventHandler<ActionEvent>() {
//
                     @Override
                     public void handle(ActionEvent event) {
                         String[] invitePlay = label.getText().split("\t");

                         turnThread = false;
                         System.out.println(label.getText());

//                       Parent root = null;
//                    try {
//                        PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);
                         Alert alert = new Alert(AlertType.CONFIRMATION);
                         alert.initModality(Modality.APPLICATION_MODAL);
                         ButtonType buttonSave = new ButtonType("resume");
                         ButtonType buttonDontSave = new ButtonType("Cancel");
                         alert.setTitle("Invitation");
                         alert.setHeaderText("Do you want to resume the game with ?");
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
//                                    PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);
                                         PlayerSocket.outObj.writeObject("getGame::"+Players.myPlayer.getUsername()+"::"+ invitePlay[0]);
                                         Object res = (Object)PlayerSocket.inObj.readObject();
                                         Game.myGame = null;
                                         if(res != null){
                                             Game.myGame = (Game)res;
                                             PlayerSocket.outObj.writeObject("invite::"+label.getText().split("\t")[0]);
                                             System.out.println(Game.myGame);
//                                         }
//!(res.getClass() == myGame.getClass())
                                         String respond = (String) PlayerSocket.inObj.readObject();
                                         System.out.println(respond);
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
        

//        public void setStage(){
//            Parent root = null;
//            root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
//            Stage window = (Stage) newGame.getScene().getWindow();
//            window.setScene(new Scene(root));
//        }
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
//Vector<Players> ConnectedPlayers;
//Thread onlineUsrs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        onlineUsrs.start();
          waitTh=false;
     
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
        returnImg.setFitHeight(25);
        returnImg.setFitWidth(25);
        returnImg.setPreserveRatio(true);
        returnBtn.setGraphic(returnImg);
        Label onlinePlayers = new Label("List of Online Players , send a game request to start a game !");
        onlinePlayers.setStyle("-fx-text-fill: purple; -fx-font-size: 16px;");
        HBox hboxheader = new HBox();

        waitTh = true;

        returnBtn.getStyleClass().add("BtnLive");
        hboxheader.setStyle("-fx-background-color: #dcf5f5; ");
        //lv.setStyle("-fx-control-inner-background: #edcef1; -fx-background-radius: 5; -fx-border-color: #b023c1; -fx-border-style: solid; -fx-border-width: 2; -fx-border-radius: 5;");
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
//>>>>>>> ed9b659c5d76bfab01ae03af81a6cba7db975c83
        Stage window = (Stage) StartGameBtn.getScene().getWindow();
        Scene scene = new Scene(borderpane, 500, 500);
        scene.getStylesheets().add(getClass().getResource("BackGround.css").toString());
        window.setScene(scene);

    }
//        Parent root = FXMLLoader.load(getClass().getResource("MultiPlayerMode.fxml"));
//        Stage window = (Stage) StartGameBtn.getScene().getWindow();
//        window.setScene(new Scene(root));

    @FXML
    void Logout(ActionEvent event) throws IOException {
        PlayerSocket.closeSoket();
        stop();
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) LogoutBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    private void checkInvite(String user) {
//          boolean ok = false;
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
                        PlayerSocket.inObj.readObject();
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
                        if(Game.myGame.getUsername1_x() != null){
                            if(Game.myGame.getUsername1_x().equals(user) || Game.myGame.getUsername2_o().equals(user))
                                PlayerSocket.inObj.readObject();}
                        Parent root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
                        Stage window = (Stage) pane.getScene().getWindow();
                        window.setScene(new Scene(root));
                        stop();

                    } catch (IOException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (result.get() == buttonDontSave) {
                    //System.out.println("reject********************************************");
                    Game.myGame = null;
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
//          return ok;
    }

    public static Vector<Players> ConnectedPlayers;

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
                try {
                    PlayerSocket.outObj.writeObject("onlinePlayers");
                    Object checkType = PlayerSocket.inObj.readObject();
                    System.out.println(checkType.getClass());
                    String msg = new String();
                    Game.myGame = new Game();
                    if (checkType.getClass() == msg.getClass()) {
                        System.out.println((String) checkType);
                        String[] arrString = ((String) checkType).split("::");

                        checkInvite(arrString[1]);

//                    PlayerSocket.outObj.writeObject("accept::"+arrString[1]);
//                    PlayerSocket.inObj.readObject();
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                Parent root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
//                                Stage window = (Stage) pane.getScene().getWindow();
//                                window.setScene(new Scene(root));
//                            } catch (IOException ex) {
//                                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
//                            }
//                        }
//                    });
//                    stop();
//                    waitTh = false;
//                    turnThread = true;
                    } else //                ConnectedPlayers = (Vector<Players>)PlayerSocket.inObj.readObject();    
                    if(checkType.getClass() == Game.myGame.getClass()){
                        Game.myGame = (Game)checkType;
//                        turnThread = true;
                    }
                    else{
                        ConnectedPlayers = (Vector<Players>) checkType;
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            pane.getChildren().removeAll();
                            String[] info = new String[ConnectedPlayers.size() - 1];
                            System.out.println(ConnectedPlayers);
                            int i = 0;
                            for (Players p : ConnectedPlayers) {
                                if (p.getUsername().equals(Players.myPlayer.getUsername())) {
                                    Players.myPlayer.setScore(p.getScore());
                                    continue;
                                }
                                info[i] = p.getUsername() + "\t" + p.getScore();
                                i++;
                            }
                            ObservableList<String> list = FXCollections.observableArrayList(info);
                            ListView<String> lv = new ListView<>(list);
                            lv.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth() - 200);
                            lv.setStyle("-fx-control-inner-background: #edcef1; -fx-background-radius: 5; -fx-border-color: #b023c1; -fx-border-style: solid; -fx-border-width: 2; -fx-border-radius: 5;");

                            lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
                                @Override
                                public ListCell<String> call(ListView<String> param) {
                                    return new XCell();
                                }
                            });
                            pane.getChildren().addAll(lv);
                        }
                    });
                    sleep(2000);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        stop();
    }

}
