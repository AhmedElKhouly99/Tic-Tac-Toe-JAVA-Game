package tictactoe;

import SocketHandler.PlayerSocket;
import game.Game;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Players;
import static tictactoe.MenuController.ConnectedPlayers;

/**
 * FXML Controller class
 *
 * @author shorook
 */
public class MultiPlayerModeController implements Initializable {

    @FXML
    private Button btn1 = new Button();
    @FXML
    private Button btn2 = new Button();
    @FXML
    private Button btn3 = new Button();
    @FXML
    private Button btn4 = new Button();
    @FXML
    private Button btn5 = new Button();
    @FXML
    private Button btn6 = new Button();
    @FXML
    private Button btn7 = new Button();
    @FXML
    private Button btn8 = new Button();
    @FXML
    private Button btn9 = new Button();
    @FXML
    private Button btnplayagain;
    @FXML
    private Label playerOneName;
    @FXML
    private Label playerTwoName;
    @FXML
    private Label playerTwoScore;
    @FXML
    private Label playerOneScore;
    @FXML
    private Button returnBtn;

    private String symbol = new String();

    char[] arrPlays = new char[9];

    boolean playerWins = false;
    private Thread myGameTh;
    Button[] buttonsArr;

    String index;
    String[] msg;

    boolean player1_turn = true;
    byte gameCOunter = 0;
    static public boolean gameTh;

    /**
     * Initializes the controller class.
     */
    private void gameplay() {
        myGameTh = new Thread(new Runnable() {
            @Override
            public void run() {
                while (gameTh) {
                    try {
                        msg = (String[]) (((String) PlayerSocket.inObj.readObject()).split("::"));
                        System.err.println(msg[0]);
                        if (msg[0].equals("record")) {

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.initModality(Modality.APPLICATION_MODAL);
                                    ButtonType buttonSave = new ButtonType("Accept");
                                    ButtonType buttonDontSave = new ButtonType("Refuse");

                                    alert.setTitle("Record request");
                                    alert.setHeaderText(Players.vsPlayer.getUsername() + " wants to record session and complete in another round");
                                    DialogPane dialogPane = alert.getDialogPane();
                                    dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                    dialogPane.getStyleClass().add("myDialog");

                                    alert.getButtonTypes().setAll(buttonSave, buttonDontSave);

                                    Optional<ButtonType> result = alert.showAndWait();

                                    if (result.get() == buttonSave) {

                                        try {
                                            gameTh = false;
                                            myGameTh.stop();
                                            PlayerSocket.outObj.writeObject("recordaccept");
                                            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                            Stage window = (Stage) returnBtn.getScene().getWindow();
                                            PlayerSocket.closeSoket();
                                            window.setScene(new Scene(root));
                                            //myGameTh.stop();
                                        } catch (IOException ex) {
                                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else {

                                        try {
                                            PlayerSocket.outObj.writeObject("recordreject");
                                        } catch (IOException ex) {
                                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            });

                        } else if (msg[0].equals("recordaccepted")) {

                            try {
                                String xplayer = null;
                                String oplayer = null;

                                if (symbol.equals("X")) {
                                    xplayer = Players.myPlayer.getUsername();
                                    oplayer = Players.vsPlayer.getUsername();

                                } else {
                                    oplayer = Players.myPlayer.getUsername();
                                    xplayer = Players.vsPlayer.getUsername();

                                }

                                Game cuurentGame = new Game(xplayer, oplayer, arrPlays[0], arrPlays[1], arrPlays[2], arrPlays[3], arrPlays[4], arrPlays[5], arrPlays[6], arrPlays[7], arrPlays[8], gameCOunter);

                                try {
                                    PlayerSocket.outObj.writeObject(cuurentGame);
                                } catch (IOException ex) {
                                    Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                PlayerSocket.outObj.writeObject("finishgame");

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            gameTh = false;
                                            myGameTh.stop();
                                            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                            Stage window = (Stage) returnBtn.getScene().getWindow();
                                            PlayerSocket.closeSoket();
                                            window.setScene(new Scene(root));
                                            //myGameTh.stop();
                                        } catch (IOException ex) {
                                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                });

                            } catch (IOException ex) {
                                Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }else if(msg[0].equals("inviteAccepted"))
                        {
                            for (int i = 0; i < 9; i++) {
                                buttonsArr[i].setText("");
                                buttonsArr[i].setStyle("-fx-background-color: #4adeed;");
                            }
                            for (int i = 0; i < arrPlays.length; i++) {
                                arrPlays[i] = (char) i;
                            }

                            playerWins = false;
                            gameCOunter = 0;
                            if (symbol.equals("X")) {
                                player1_turn = true;
                                playerOneName.setStyle("-fx-text-fill: green;");
                                playerTwoName.setStyle("-fx-text-fill: red;");
                            } else {
                                playerOneName.setStyle("-fx-text-fill: red;");
                                playerTwoName.setStyle("-fx-text-fill: green;");
                                player1_turn = false;
                            }
                            playerOneName.setText(Players.myPlayer.getUsername());
                            playerTwoName.setText(Players.vsPlayer.getUsername());
                        }
                        else if (msg[0].equals("requestrejected")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                    alert1.setTitle("Recording request issue");
                                    alert1.setHeaderText("Recording request refused by other player");
                                    DialogPane dialogPane1 = alert1.getDialogPane();
                                    dialogPane1.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                    dialogPane1.getStyleClass().add("myDialog");
                                }
                            });

                        } else if (msg[0].equals("exitgame")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert1.setTitle("Your Opponent has quit the game");
<<<<<<< HEAD
                                        alert1.setHeaderText("You have won!!\tScore:" + Players.myPlayer.getScore() + "+10");
=======
                                        alert1.setHeaderText("You have won!!\tScore:"+Players.myPlayer.getScore()+" +10");
>>>>>>> 10e537ea9b75fc3f70ddd63b4617c967752b82a1
                                        DialogPane dialogPane1 = alert1.getDialogPane();
                                        dialogPane1.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                        dialogPane1.getStyleClass().add("myDialog");
                                        alert1.show();
//                                        try {
//                                            Thread.sleep(500);
//                                        } catch (InterruptedException ex) {
//                                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
//                                        }
                                        PlayerSocket.outObj.writeObject("winner");

                                        gameTh = false;
                                        myGameTh.stop();
                                        PlayerSocket.outObj.writeObject("finishgame");
                                        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                        PlayerSocket.closeSoket();
                                        Stage window = (Stage) returnBtn.getScene().getWindow();
                                        window.setScene(new Scene(root));
                                        //myGameTh.stop();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });

                        } else if (msg[0].equals("invitedyou")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.initModality(Modality.APPLICATION_MODAL);
                                    ButtonType buttonSave = new ButtonType("Accept");
                                    ButtonType buttonDontSave = new ButtonType("Reject");
                                    alert.setTitle("Invitation");
                                    alert.setHeaderText("Do you want to again play with " + Players.vsPlayer.getUsername() + "?");
                                    DialogPane dialogPane = alert.getDialogPane();
                                    dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                    dialogPane.getStyleClass().add("myDialog");
                                    alert.getButtonTypes().setAll(buttonSave, buttonDontSave);

                                    Optional<ButtonType> result = alert.showAndWait();

                                    if (result.get() == buttonSave) {

                                        try {
                                            PlayerSocket.outObj.writeObject("accept::" + Players.vsPlayer.getUsername());
                                            //PlayerSocket.inObj.readObject();
                                        } catch (IOException ex) {
                                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                        Players.vsPlayer.setInGame(true);
                                        Players.myPlayer.setInGame(true);
                                        for (int i = 0; i < 9; i++) {
                                            buttonsArr[i].setText("");
                                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;");
                                        }
                                        for (int i = 0; i < arrPlays.length; i++) {
                                            arrPlays[i] = (char) i;
                                        }

                                        playerWins = false;
                                        gameCOunter = 0;
                                        if (symbol.equals("X")) {
                                            player1_turn = true;
                                            playerOneName.setStyle("-fx-text-fill: green;");
                                            playerTwoName.setStyle("-fx-text-fill: red;");
                                        } else {
                                            playerOneName.setStyle("-fx-text-fill: red;");
                                            playerTwoName.setStyle("-fx-text-fill: green;");
                                            player1_turn = false;
                                        }
                                        playerOneName.setText(Players.myPlayer.getUsername());
                                        playerTwoName.setText(Players.vsPlayer.getUsername());
                                    }
                                }
                            });

                        } else if (msg[0].equals("put")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    putMove(Integer.parseInt(msg[1])); //To change body of generated methods, choose Tools | Templates.
                                }
                            });
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        });

        myGameTh.start();

    }

    private void putMove(int index) {

        if (symbol.equals("X")) {
            buttonsArr[index].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #00b100;");
            buttonsArr[index].setText("O");
            arrPlays[index] = 'O';
        } else {
            buttonsArr[index].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #ff0303;");
            buttonsArr[index].setText("X");
            arrPlays[index] = 'X';
        }
        playerOneName.setStyle("-fx-text-fill: green;");
        playerTwoName.setStyle("-fx-text-fill: red;");

        gameCOunter++;
        player1_turn = true;

        check();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
//<<<<<<< HEAD
//            Object res = PlayerSocket.inObj.readObject();
//            if(res.getClass() == symbol.getClass()){
//                symbol = (String)res;
////            symbol = (String)PlayerSocket.inObj.readObject();
//=======
            Object response;
            do {

                response = PlayerSocket.inObj.readObject();

            } while (response.getClass() != symbol.getClass());
            symbol = (String) response;
            System.out.println(symbol);
            buttonsArr = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
            for (int i = 0; i < 9; i++) {
                buttonsArr[i].setFont(new Font("MV Boli", 50));
            }
            for (int i = 0; i < arrPlays.length; i++) {
                arrPlays[i] = (char) i;
            }

            //System.out.println(Game.myGame.getUsername1_x());
            System.out.println(Game.myGame.getUsername2_o());
            if ((Game.myGame.getUsername1_x()) != null) {
                gameCOunter = (byte) Game.myGame.getTurn();
                if (Players.myPlayer.getUsername().equals(Game.myGame.getUsername1_x())) {

                    symbol = "X";
                    if ((gameCOunter % 2) == 0) {
                        player1_turn = true;
                    } else {
                        player1_turn = false;
                    }
                } else {
                    symbol = "O";
                    if ((gameCOunter % 2) == 1) {
                        player1_turn = true;
                    } else {
                        player1_turn = false;
                    }

                }
                System.out.println(player1_turn);
                arrPlays[0] = Game.myGame.getOne();
                arrPlays[1] = Game.myGame.getTwo();
                arrPlays[2] = Game.myGame.getThree();
                arrPlays[3] = Game.myGame.getFour();
                arrPlays[4] = Game.myGame.getFive();
                arrPlays[5] = Game.myGame.getSix();
                arrPlays[6] = Game.myGame.getSeven();
                arrPlays[7] = Game.myGame.getEight();
                arrPlays[8] = Game.myGame.getNine();

                for (int i = 0; i < 9; i++) {
                    if (arrPlays[i] == 'X') {
                        buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #ff0303;");
                        buttonsArr[i].setText("X");
                    } else if (arrPlays[i] == 'O') {
                        buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #00b100;");
                        buttonsArr[i].setText("O");
                    } else {
                        arrPlays[i] = (char) i;
                    }
                }

                if (symbol.equals("X")) {
                    playerOneName.setStyle("-fx-text-fill: green;");
                    playerTwoName.setStyle("-fx-text-fill: red;");
                } else {
                    playerOneName.setStyle("-fx-text-fill: red;");
                    playerTwoName.setStyle("-fx-text-fill: green;");
                }
<<<<<<< HEAD

            } else if (symbol.equals("X")) {
=======
                
                
                
                
            }
            
            else
//>>>>>>> 756c6ec035dfa3d0059270be825de9364bb53a87
            if(symbol.equals("X")){
>>>>>>> 10e537ea9b75fc3f70ddd63b4617c967752b82a1
                player1_turn = true;
                playerOneName.setStyle("-fx-text-fill: green;");
                playerTwoName.setStyle("-fx-text-fill: red;");
            } else {
                playerOneName.setStyle("-fx-text-fill: red;");
                playerTwoName.setStyle("-fx-text-fill: green;");
                player1_turn = false;
<<<<<<< HEAD
            }

        } catch (Exception ex) {
            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }

=======
            }} catch (IOException ex) {
            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
//        } catch (Exception ex) {
//            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
        
        
>>>>>>> 10e537ea9b75fc3f70ddd63b4617c967752b82a1
        playerOneScore.setStyle("-fx-text-fill:#4adeed");
        playerTwoScore.setStyle("-fx-text-fill:#4adeed");

        playerOneName.setText(Players.myPlayer.getUsername());
        playerTwoName.setText(Players.vsPlayer.getUsername());

        playerOneScore.setText(String.valueOf(Players.myPlayer.getScore()));

        playerTwoScore.setText(String.valueOf(Players.vsPlayer.getScore()));
        gameTh = true;
        gameplay();
    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        if (gameCOunter != 9) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    ButtonType buttonSave = new ButtonType("Save and Exit");
                    ButtonType buttonDontSave = new ButtonType("Exit");
                    ButtonType buttonCancel = new ButtonType("Cancel");
                    alert.setTitle("Exit");
                    alert.setHeaderText("Do you want to record game and exit?");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.getButtonTypes().setAll(buttonSave, buttonDontSave, buttonCancel);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == buttonSave) {

                        try {

                            PlayerSocket.outObj.writeObject("recordrequest");
                            String recordResponse = null;

                        } catch (IOException ex) {
                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else if (result.get() == buttonDontSave) {
                        try {
                            PlayerSocket.outObj.writeObject("exited");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        gameTh = false;
                                        myGameTh.stop();
                                        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                        PlayerSocket.closeSoket();
                                        Stage window = (Stage) returnBtn.getScene().getWindow();
                                        window.setScene(new Scene(root));

                                        //myGameTh.stop();
                                    } catch (IOException ex) {
                                        Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });

                        } catch (IOException ex) {
                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            });
        } else {
            try {
                PlayerSocket.outObj.writeObject("finishgame");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            gameTh = false;
                            myGameTh.stop();
                            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                            PlayerSocket.closeSoket();
                            Stage window = (Stage) returnBtn.getScene().getWindow();
                            window.setScene(new Scene(root));
                            //myGameTh.stop();
                        } catch (IOException ex) {
                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });

            } //To change body of generated methods, choose Tools | Templates.
            catch (IOException ex) {
                Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @FXML
    private void btnPressed(ActionEvent event) throws IOException {

        for (int i = 0; i < 9; i++) {
            if (event.getTarget() == buttonsArr[i]) {
                if (player1_turn) {
                    if (buttonsArr[i].getText().equals("")) {

//                        System.out.println("Hiiii");
                        if (symbol.equals("X")) {
                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #ff0303;");
                        } else {
                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #00b100;");
                        }
                        buttonsArr[i].setText(symbol);
                        player1_turn = false;
                        arrPlays[i] = buttonsArr[i].getText().charAt(0);
                        playerOneName.setStyle("-fx-text-fill: red;");
                        playerTwoName.setStyle("-fx-text-fill: green;");

                        gameCOunter++;

                        PlayerSocket.outObj.writeObject("play::" + i);

                        check();

                    }
                }

            }
        }
    }

    @FXML
    private void replayAgain(ActionEvent event) {

        
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    ButtonType buttonSave = new ButtonType("Invite");
                    ButtonType buttonDontSave = new ButtonType("Cancel");
                    alert.setTitle("Invitation");
                    alert.setHeaderText("Do you want to send an invite to " + Players.vsPlayer.getUsername() + "?");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                    dialogPane.getStyleClass().add("myDialog");
                    alert.getButtonTypes().setAll(buttonSave, buttonDontSave);

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get() == buttonSave) {
                        try {
                            PlayerSocket.outObj.writeObject("invite::" + Players.vsPlayer.getUsername());    

                        } catch (IOException ex) {
                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (ClassNotFoundException ex) {
//                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {

                    }
                }
            });


    }

    public void check() {
        //check X win conditions
        int j;

        if (gameCOunter == 9) {
            playerOneName.setText("Tie");
            try {
                PlayerSocket.outObj.writeObject("tie");
            } catch (IOException ex) {
                Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }

        for (int i = 0; i < 3; i++) {
            j = i * 3;

            if (i != 1) {
                if ((arrPlays[i] == arrPlays[4]) && (arrPlays[4] == arrPlays[8 - i])) {
                    if (arrPlays[i] == symbol.charAt(0)) {

                        youWin(i, 4, 8 - i);

                        playerWins = true;
                        playerOneName.setText("You Win");
                        playerOneScore.setText("");
                    } else {

                        youLose(i, 4, 8 - i);
                        player1_turn = false;
                        playerOneName.setText("You lose");
                        playerOneScore.setText("");
                    }
                    return;
                }
            }
            if ((arrPlays[i] == arrPlays[i + 3]) && (arrPlays[i + 3] == arrPlays[i + 6])) // Check Columns 
            {
                if (arrPlays[i] == symbol.charAt(0)) {
                    youWin(i, i + 3, i + 6);

                    playerWins = true;

                    playerOneName.setText("You Win");
                    playerOneScore.setText("");
                } else {

                    youLose(i, i + 3, i + 6);

                    player1_turn = false;
                    playerOneName.setText("You lose");
                    playerOneScore.setText("");
                }
                return;
            } else if ((arrPlays[j] == arrPlays[j + 1]) && (arrPlays[j + 1] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == symbol.charAt(0)) {

                    youWin(j, j + 1, j + 2);

                    playerWins = true;
                    playerOneName.setText("You Win");
                    playerOneScore.setText("");
                } else {

                    youLose(j, j + 1, j + 2);

                    player1_turn = false;
                    playerOneName.setText("You lose");
                    playerOneScore.setText("");
                }
                return;
            }

        }

    }

    public void youWin(int a, int b, int c) {

        buttonsArr[a].setStyle("-fx-background-color: #00b100;-fx-text-fill: #ff0303;");
        buttonsArr[b].setStyle("-fx-background-color: #00b100;-fx-text-fill: #ff0303;");
        buttonsArr[c].setStyle("-fx-background-color: #00b100;-fx-text-fill: #ff0303;");
        try {
            PlayerSocket.outObj.writeObject("winner");
        } catch (IOException ex) {
            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void youLose(int a, int b, int c) {
        buttonsArr[a].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        buttonsArr[b].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        buttonsArr[c].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");

    }
}
