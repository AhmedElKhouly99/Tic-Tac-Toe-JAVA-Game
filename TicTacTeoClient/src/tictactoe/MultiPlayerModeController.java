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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Players;

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

    byte playerWins = 2;
    private Thread myGameTh;
    Button[] buttonsArr = new Button[9];

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
                        Object res = PlayerSocket.receiveMsg();
                        if (res == null) {
                            gameTh = false;
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                        Stage window = (Stage) btn1.getScene().getWindow();
                                        window.setScene(new Scene(root));
                                    } catch (IOException ex) {
                                        Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            myGameTh.stop();
                            break;
                        }
                        msg = (String[]) (((String) res).split("::"));
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
                                            PlayerSocket.sendMsg("recordaccept");
                                            Game.myGame.setUsername1_x(null);
                                            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                            Stage window = (Stage) returnBtn.getScene().getWindow();
                                            PlayerSocket.closeSoket();
                                            window.setScene(new Scene(root));
                                        } catch (IOException ex) {
                                            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    } else {
                                        PlayerSocket.sendMsg("recordreject");
                                    }
                                }
                            });

                        } else if (msg[0].equals("recordaccepted")) {
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
                            PlayerSocket.sendMsg(cuurentGame);
                            PlayerSocket.sendMsg("finishgame");
                            Game.myGame.setUsername1_x(null);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        gameTh = false;
                                        myGameTh.stop();
                                        returnBtn = new Button();
                                        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                        Stage window = (Stage) btn1.getScene().getWindow();
                                        PlayerSocket.closeSoket();
                                        window.setScene(new Scene(root));

                                    } catch (IOException ex) {
                                        Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });

                        } else if (msg[0].equals("inviteAccepted")) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < 9; i++) {
                                        buttonsArr[i].setText("");
                                        buttonsArr[i].setStyle("-fx-background-color: #4adeed;");
                                    }
                                }
                            });
                            for (int i = 0; i < arrPlays.length; i++) {
                                arrPlays[i] = (char) i;
                            }
                            playerWins = 2;
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
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    playerOneName.setText(Players.myPlayer.getUsername());
                                    playerTwoName.setText(Players.vsPlayer.getUsername());
                                }
                            });
                        } else if (msg[0].equals("requestrejected")) {
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
                                        if(playerWins == 2){
                                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                                        alert1.setTitle("Your Opponent has quit the game");

                                        alert1.setHeaderText("You have won!!\tScore:" + Players.myPlayer.getScore() + "+10");

                                        DialogPane dialogPane1 = alert1.getDialogPane();
                                        dialogPane1.getStylesheets().add(getClass().getResource("myDialogs.css").toExternalForm());
                                        dialogPane1.getStyleClass().add("myDialog");
                                        alert1.show();

                                        PlayerSocket.sendMsg("winner");}
                                        Game.myGame.setUsername1_x(null);
                                        gameTh = false;
                                        myGameTh.stop();
                                        PlayerSocket.sendMsg("finishgame");
                                        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                        PlayerSocket.closeSoket();
                                        Stage window = (Stage) returnBtn.getScene().getWindow();
                                        window.setScene(new Scene(root));

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
                                        PlayerSocket.sendMsg("accept::" + Players.vsPlayer.getUsername());
                                        Players.vsPlayer.setInGame(true);
                                        Players.myPlayer.setInGame(true);
                                        for (int i = 0; i < 9; i++) {
                                            buttonsArr[i].setText("");
                                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;");
                                        }
                                        for (int i = 0; i < arrPlays.length; i++) {
                                            arrPlays[i] = (char) i;
                                        }

                                        playerWins = 2;
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

                        } 
                        else if (msg[0].equals("put")) {
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
            Object response;
            do {

                response = PlayerSocket.receiveMsg();
                if (response == null) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                Stage window = (Stage) btn1.getScene().getWindow();
                                window.setScene(new Scene(root));
                            } catch (IOException ex) {
                                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    break;
                }

            } while (response.getClass() != symbol.getClass());
            symbol = (String) response;

            buttonsArr = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
            for (int i = 0; i < 9; i++) {
                buttonsArr[i].setFont(new Font("MV Boli", 50));
            }
            for (int i = 0; i < arrPlays.length; i++) {
                arrPlays[i] = (char) i;
            }

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

            } else if (symbol.equals("X")) {

                player1_turn = true;
                playerOneName.setStyle("-fx-text-fill: green;");
                playerTwoName.setStyle("-fx-text-fill: red;");
            } else {
                playerOneName.setStyle("-fx-text-fill: red;");
                playerTwoName.setStyle("-fx-text-fill: green;");
                player1_turn = false;

            }

        } catch (Exception ex) {
            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        if (playerWins == 2) {
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
                        PlayerSocket.sendMsg("recordrequest");
                        String recordResponse = null;
                        Game.myGame.setUsername1_x(null);

                    } else if (result.get() == buttonDontSave) {
                        PlayerSocket.sendMsg("exited");
                        Game.myGame.setUsername1_x(null);
                        PlayerSocket.closeSoket();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    gameTh = false;
                                    myGameTh.stop();
                                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));

                                    Stage window = (Stage) returnBtn.getScene().getWindow();
                                    window.setScene(new Scene(root));

                                } catch (IOException ex) {
                                    Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        });
                    }
                }

            });
        } else {
            PlayerSocket.sendMsg("exited");
            Game.myGame.setUsername1_x(null);
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

                    } catch (IOException ex) {
                        Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

    }

    @FXML
    private void btnPressed(ActionEvent event) throws IOException {

        for (int i = 0; i < 9; i++) {
            if (event.getTarget() == buttonsArr[i]) {
                if (player1_turn) {
                    if (buttonsArr[i].getText().equals("")) {

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

                        PlayerSocket.sendMsg("play::" + i);

                        check();

                    }
                }

            }
        }
    }

    @FXML
    private void replayAgain(ActionEvent event) {

        if (playerWins != 2 || gameCOunter == 9) {
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
                        PlayerSocket.sendMsg("invite::" + Players.vsPlayer.getUsername());

                    } else {

                    }
                }
            });
        }

    }

    public void check() {
        int j;
        for (int i = 0; i < 3; i++) {
            j = i * 3;

            if (i != 1) {
                if ((arrPlays[i] == arrPlays[4]) && (arrPlays[4] == arrPlays[8 - i])) {
                    if (arrPlays[i] == symbol.charAt(0)) {

                        youWin(i, 4, 8 - i);

                        playerWins = 1;
                        playerOneName.setText("You Win");
                        playerOneScore.setText("");
                    } else {

                        youLose(i, 4, 8 - i);
                        playerWins = 0;
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

                    playerWins = 1;

                    playerOneName.setText("You Win");
                    playerOneScore.setText("");
                } else {

                    youLose(i, i + 3, i + 6);
                    playerWins = 0;
                    player1_turn = false;
                    playerOneName.setText("You lose");
                    playerOneScore.setText("");
                }
                return;
            } else if ((arrPlays[j] == arrPlays[j + 1]) && (arrPlays[j + 1] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == symbol.charAt(0)) {

                    youWin(j, j + 1, j + 2);

                    playerWins = 1;
                    playerOneName.setText("You Win");
                    playerOneScore.setText("");
                } else {

                    youLose(j, j + 1, j + 2);
                    playerWins = 0;
                    player1_turn = false;
                    playerOneName.setText("You lose");
                    playerOneScore.setText("");
                }
                return;
            }

        }
        if (gameCOunter == 9) {
            playerOneName.setText("Tie");
            PlayerSocket.sendMsg("tie");
            return;
        }

    }

    public void youWin(int a, int b, int c) {

        buttonsArr[a].setStyle("-fx-background-color: #00b100;-fx-text-fill: #ff0303;");
        buttonsArr[b].setStyle("-fx-background-color: #00b100;-fx-text-fill: #ff0303;");
        buttonsArr[c].setStyle("-fx-background-color: #00b100;-fx-text-fill: #ff0303;");
        PlayerSocket.sendMsg("winner");

    }

    public void youLose(int a, int b, int c) {
        buttonsArr[a].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        buttonsArr[b].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        buttonsArr[c].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");

    }
}
