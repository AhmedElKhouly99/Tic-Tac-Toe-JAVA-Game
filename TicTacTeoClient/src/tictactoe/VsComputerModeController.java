/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shorook
 */
public class VsComputerModeController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Label textwin;
    @FXML
    private Button btnplayagain;
    @FXML
    private Button returnBtn;

    boolean player1_turn = true;
    char[] arrPlays = new char[9];
    boolean playerWins = false;
    byte gameCOunter = 0;
    Button[] buttonsArr;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        buttonsArr = new Button[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9};
        for (int i = 0; i < 9; i++) {
            buttonsArr[i].setFont(new Font("MV Boli", 50));
        }
        for (int i = 0; i < arrPlays.length; i++) {
            arrPlays[i] = (char) i;
        }

    }

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) returnBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    @FXML
    private void btnPressed(ActionEvent event) {

        for (int i = 0; i < 9; i++) {
            if (event.getSource() == buttonsArr[i]) {
                if (player1_turn) {
                    if (buttonsArr[i].getText() == "") {

                        buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #ff0303;");
                        buttonsArr[i].setText("X");
                        player1_turn = false;
                        gameCOunter++;
                        arrPlays[i] = buttonsArr[i].getText().charAt(0);

                        check();
                        if (!playerWins && (gameCOunter != 9)) {
                            if (LevelsController.hardAI) {
                                hardGamePlay();
                            } else {
                                easyGamePlay();
                            }
                            gameCOunter++;
                            check();
                        }

                    }
                }

            }
        }
    }

    public void check() {
        //check X win conditions
        int j;

        for (int i = 0; i < 3; i++) {
            j = i * 3;

            if (i != 1) {
                if ((arrPlays[i] == arrPlays[4]) && (arrPlays[4] == arrPlays[8 - i])) {
                    if (arrPlays[i] == 'X') {
                        xWins(i, 4, 8 - i);
                        playerWins = true;
                    } else {
                        oWins(i, 4, 8 - i);

                        player1_turn = false;
                    }
                    return;
                }
            }
            if ((arrPlays[i] == arrPlays[i + 3]) && (arrPlays[i + 3] == arrPlays[i + 6])) // Check Columns 
            {
                if (arrPlays[i] == 'X') {
                    playerWins = true;
                    xWins(i, i + 3, i + 6);
                } else {
                    oWins(i, i + 3, i + 6);

                    player1_turn = false;
                }
                return;
            } else if ((arrPlays[j] == arrPlays[j + 1]) && (arrPlays[j + 1] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == 'X') {
                    xWins(j, j + 1, j + 2);
                    playerWins = true;
                } else {
                    oWins(j, j + 1, j + 2);

                    player1_turn = false;

                }
                return;
            }

        }

        if (gameCOunter == 9) {
            textwin.setText("Tie");
        }

    }

    public void easyGamePlay() {
        int index;

        index = (int) Math.floor(Math.random() * (9));

        while (!((arrPlays[index] != 'X') && (arrPlays[index] != 'O'))) {

            index = (int) Math.floor(Math.random() * (9));
        }

        buttonsArr[index].setText("O");
        buttonsArr[index].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #00b100;");
        arrPlays[index] = 'O';

        player1_turn = true;

    }

    public void hardGamePlay() {

        if (arrPlays[4] != 'X' && arrPlays[4] != 'O') {
            buttonsArr[4].setText("O");
            buttonsArr[4].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
            arrPlays[4] = 'O';

            player1_turn = true;
            return;
        }
        int j = 0;
        for (int i = 0; i < 3; i++) {
            j = i * 3;

            if (i != 1) {
                if ((arrPlays[i] == arrPlays[4]) || (arrPlays[4] == arrPlays[8 - i]) || (arrPlays[i] == arrPlays[8 - i])) {

                    if (arrPlays[i] == 'X') {

                        if (arrPlays[4] == 'X') {
                            if (arrPlays[8 - i] != 'O') {
                                arrPlays[8 - i] = 'O';
                                buttonsArr[8 - i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                                buttonsArr[8 - i].setText("O");
                                player1_turn = true;

                                return;
                            }
                        } else if (arrPlays[8 - i] == 'X') {
                            if (arrPlays[4] != 'O') {
                                arrPlays[4] = 'O';
                                buttonsArr[4].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                                buttonsArr[4].setText("O");
                                player1_turn = true;

                                return;
                            }
                        }
                    }
                    if (arrPlays[4] == 'X') {
                        if ((arrPlays[i] != 'O') && (arrPlays[i] != 'X')) {
                            arrPlays[i] = 'O';
                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i].setText("O");
                            player1_turn = true;

                            return;
                        }
                    }
                    if ((arrPlays[i] != 'X') && (arrPlays[4] != 'X')) {
                        if (arrPlays[4] == 'O') {
                            if (arrPlays[i] != 'O') {
                                arrPlays[i] = 'O';
                                buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                                buttonsArr[i].setText("O");
                                player1_turn = true;

                                return;
                            } else if ((arrPlays[8 - i] != 'O') && (arrPlays[8 - i] != 'X')) {
                                arrPlays[8 - i] = 'O';
                                buttonsArr[8 - i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                                buttonsArr[8 - i].setText("O");
                                player1_turn = true;

                                return;
                            }
                        } else if (arrPlays[i] == 'O') {
                            if (arrPlays[4] != 'O') {
                                arrPlays[4] = 'O';
                                buttonsArr[4].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                                buttonsArr[4].setText("O");
                                player1_turn = true;

                                return;
                            } else if ((arrPlays[8 - i] != 'O') && (arrPlays[8 - i] != 'X')) {
                                arrPlays[8 - i] = 'O';
                                buttonsArr[8 - i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                                buttonsArr[8 - i].setText("O");
                                player1_turn = true;

                                return;
                            }

                        }
                    }

                }
            }
            if ((arrPlays[i] == arrPlays[i + 3]) || (arrPlays[i + 3] == arrPlays[i + 6]) || (arrPlays[i] == arrPlays[i + 6])) // Check Columns 
            {
                if (arrPlays[i] == 'X') {
                    if (arrPlays[i + 3] == 'X') {
                        if (arrPlays[i + 6] != 'O') {

                            arrPlays[i + 6] = 'O';
                            buttonsArr[i + 6].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i + 6].setText("O");
                            player1_turn = true;

                            return;

                        }
                    } else if (arrPlays[i + 6] == 'X') {
                        if (arrPlays[i + 3] != 'O') {
                            arrPlays[i + 3] = 'O';
                            buttonsArr[i + 3].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i + 3].setText("O");
                            player1_turn = true;

                            return;
                        }
                    }
                }
                if (arrPlays[i + 3] == 'X') {
                    if ((arrPlays[i] != 'O') && (arrPlays[i] != 'X')) {
                        arrPlays[i] = 'O';
                        buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                        buttonsArr[i].setText("O");
                        player1_turn = true;

                        return;
                    }
                }
                if ((arrPlays[i] != 'X') && (arrPlays[i + 3] != 'X')) {
                    if (arrPlays[i + 3] == 'O') {
                        if (arrPlays[i] != 'O') {
                            arrPlays[i] = 'O';
                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i].setText("O");
                            player1_turn = true;

                            return;
                        } else if ((arrPlays[i + 6] != 'O') && (arrPlays[i + 6] != 'X')) {
                            arrPlays[i + 6] = 'O';
                            buttonsArr[i + 6].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i + 6].setText("O");
                            player1_turn = true;

                            return;
                        }
                    } else if (arrPlays[i] == 'O') {
                        if (arrPlays[i + 3] != 'O') {
                            arrPlays[i + 3] = 'O';
                            buttonsArr[i + 3].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i + 3].setText("O");
                            player1_turn = true;

                            return;
                        } else if ((arrPlays[i + 6] != 'O') && (arrPlays[i + 6] != 'X')) {
                            arrPlays[i + 6] = 'O';
                            buttonsArr[i + 6].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[i + 6].setText("O");
                            player1_turn = true;

                            return;
                        }

                    }
                }

            }
            if ((arrPlays[j] == arrPlays[j + 1]) || (arrPlays[j + 1] == arrPlays[j + 2]) || (arrPlays[j] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == 'X') {
                    if (arrPlays[j + 1] == 'X') {
                        if (arrPlays[j + 2] != 'O') {
                            arrPlays[j + 2] = 'O';
                            buttonsArr[j + 2].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[j + 2].setText("O");
                            player1_turn = true;

                            return;
                        }
                    } else if (arrPlays[j + 2] == 'X') {
                        if (arrPlays[j + 1] != 'O') {
                            arrPlays[j + 1] = 'O';
                            buttonsArr[j + 1].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[j + 1].setText("O");
                            player1_turn = true;

                            return;
                        }
                    }
                }
                if (arrPlays[j + 1] == 'X') {
                    if ((arrPlays[j] != 'O') && (arrPlays[j] != 'X')) {
                        arrPlays[j] = 'O';
                        buttonsArr[j].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                        buttonsArr[j].setText("O");
                        player1_turn = true;

                        return;
                    }
                }
                if ((arrPlays[j] != 'X') && (arrPlays[j + 1] != 'X')) {
                    if (arrPlays[j + 1] == 'O') {
                        if (arrPlays[j] != 'O') {
                            arrPlays[j] = 'O';
                            buttonsArr[j].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[j].setText("O");
                            player1_turn = true;

                            return;
                        } else if ((arrPlays[j + 2] != 'O') && (arrPlays[j + 2] != 'X')) {
                            arrPlays[j + 2] = 'O';
                            buttonsArr[j + 2].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[j + 2].setText("O");
                            player1_turn = true;

                            return;
                        }
                    } else if (arrPlays[j] == 'O') {
                        if (arrPlays[j + 1] != 'O') {
                            arrPlays[j + 1] = 'O';
                            buttonsArr[j + 1].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[j + 1].setText("O");
                            player1_turn = true;

                            return;
                        } else if ((arrPlays[j + 2] != 'O') && (arrPlays[j + 2] != 'X')) {
                            arrPlays[j + 2] = 'O';
                            buttonsArr[j + 2].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                            buttonsArr[j + 2].setText("O");
                            player1_turn = true;

                            return;
                        }

                    }
                }

            }

        }

        for (int i = 0; i < 9; i++) {
            if (arrPlays[i] != 'X' && arrPlays[i] != 'O') {
                buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #0342ff;");
                buttonsArr[i].setText("O");
                arrPlays[i] = 'O';

                player1_turn = true;
                return;
            }
        }
    }

    public void xWins(int a, int b, int c) {

        buttonsArr[a].setStyle("-fx-background-color: #0342ff;-fx-text-fill: #ff0303;");
        buttonsArr[b].setStyle("-fx-background-color: #0342ff;-fx-text-fill: #ff0303;");
        buttonsArr[c].setStyle("-fx-background-color: #0342ff;-fx-text-fill: #ff0303;");
        textwin.setStyle("-fx-text-fill: #4adeed;");
        textwin.setText("X won");

    }

    public void oWins(int a, int b, int c) {
        buttonsArr[a].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        buttonsArr[b].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        buttonsArr[c].setStyle("-fx-background-color: #ff0303;-fx-text-fill: #0342ff;");
        textwin.setStyle("-fx-text-fill: #4adeed;");
        textwin.setText("O won");

    }

    @FXML
    private void replayAgain(ActionEvent event) {

        for (int i = 0; i < 9; i++) {
            buttonsArr[i].setText("");
            buttonsArr[i].setStyle("-fx-background-color: #4adeed;");
        }
        for (int i = 0; i < arrPlays.length; i++) {
            arrPlays[i] = (char) i;
        }
        textwin.setText("");
        player1_turn = true;
        playerWins = false;
        gameCOunter = 0;
    }

}
