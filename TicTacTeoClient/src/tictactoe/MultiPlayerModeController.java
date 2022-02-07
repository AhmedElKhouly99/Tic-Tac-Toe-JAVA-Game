
 
package tictactoe;

import SocketHandler.PlayerSocket;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author shorook
 */
public class MultiPlayerModeController implements Initializable {

    @FXML
    private Button btn1=new Button();
    @FXML
    private Button btn2=new Button();
    @FXML
    private Button btn3=new Button();
    @FXML
    private Button btn4=new Button();
    @FXML
    private Button btn5=new Button();
    @FXML
    private Button btn6=new Button();
    @FXML
    private Button btn7=new Button();
    @FXML
    private Button btn8=new Button();
    @FXML
    private Button btn9=new Button();
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
    
    private String symbol;
    
    char[] arrPlays = new char[9];
    
    boolean playerWins = false;
    private Thread myGameTh;
    Button[] buttonsArr;
    
    String index;
    String[] msg;
    
    boolean player1_turn=true;
    /**
     * Initializes the controller class.
     */
    
    private void gameplay()
    {
        myGameTh=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                try {
                    msg= (String[])(((String)PlayerSocket.inObj.readObject()).split("::"));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            putMove(Integer.parseInt(msg[1])); //To change body of generated methods, choose Tools | Templates.
                        }
                    });
                    
                } catch (Exception ex) {
                    Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }
        });
        myGameTh.start();
    }
    
    
    
    private void putMove(int index)
    {
        
        if(symbol.equals("X")){
            buttonsArr[index].setText("O");
            arrPlays[index]='O';
        }else{
            buttonsArr[index].setText("X");
            arrPlays[index]='X';
        }
        
        player1_turn=true;
        check();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            symbol = (String)PlayerSocket.inObj.readObject();
            if(symbol.equals("X")){
                player1_turn = true;
            }else{
                player1_turn = false;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(MultiPlayerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        buttonsArr=new Button[]{btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9};
        for (int i = 0; i < 9; i++) {
            buttonsArr[i].setFont(new Font("MV Boli", 50));
        }
         for (int i = 0; i < arrPlays.length; i++) {
            arrPlays[i] = (char) i;
        }    
        gameplay();
    }    

    @FXML
    private void backToMainPage(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
         Stage window = (Stage) returnBtn.getScene().getWindow();
         window.setScene(new Scene(root));
    }

    @FXML
    private void btnPressed(ActionEvent event) throws IOException {
//        System.out.println(buttonsArr[0]);
//        System.out.println(event.getSource());
        for (int i = 0; i < 9; i++) {
            if (event.getTarget() == buttonsArr[i]) {
                if (player1_turn) {
                    if (buttonsArr[i].getText() == "") {
//                      buttonsArr[i].setForeground(new Color(255, 0, 0));
                        System.out.println("Hiiii");
                        //buttonsArr[i].setText("X");
                        buttonsArr[i].setText(symbol);
                        player1_turn = false;
                        arrPlays[i] = buttonsArr[i].getText().charAt(0);
//<<<<<<< HEAD
////                      textfield.setText("O turn");
//                        PlayerSocket.outS.println("play::"+i);
//=======
////                        textfield.setText("O turn");
                        PlayerSocket.outObj.writeObject("play::"+i);
//>>>>>>> e5fe6c013339234420bc07e1af0e79e2545e6aff
                            check();
                            
//                        if (playerWins) {
////                            myGameTh.stop();
////                            playerOneName.setText("You win");
////                            
////                        }

                    }
                }
                
    }
   }
    }
    @FXML
    private void replayAgain(ActionEvent event) {
        
    }
    
    
    
     public void check() {
        //check X win conditions
        int j;

        for (int i = 0; i < 3; i++) {
            j = i * 3;

            if (i != 1) {
                if ((arrPlays[i] == arrPlays[4]) && (arrPlays[4] == arrPlays[8 - i])) {
                    if (arrPlays[i] == symbol.charAt(0)) {
//                        xWins(i, 4, 8 - i);
                          playerWins=true;     
                          playerOneName.setText("You Win");
                    } else {
//                        oWins(i, 4, 8 - i);
                            player1_turn=false;
                            playerOneName.setText("You lose");
                            
                    }
                    return;
                }
            }
            if ((arrPlays[i] == arrPlays[i + 3]) && (arrPlays[i + 3] == arrPlays[i + 6])) // Check Columns 
            {
                if (arrPlays[i] == symbol.charAt(0)) {
                    playerWins=true;
               
                    playerOneName.setText("You Win");
                } else {
//                    oWins(i, i + 3, i + 6);
                    //myGameTh.stop();
                     player1_turn=false;
                     playerOneName.setText("You lose");
                }
                return;
            } else if ((arrPlays[j] == arrPlays[j + 1]) && (arrPlays[j + 1] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == symbol.charAt(0)) {
//                    xWins(j, j + 1, j + 2);
                    playerWins=true;
                    playerOneName.setText("You Win");
                } else {
//                    oWins(j, j + 1, j + 2);
                    //myGameTh.stop();
                    player1_turn=false;
                     playerOneName.setText("You lose");
                      
                }
                return;
            }

        }


    }
     
     
//    public void xWins(int a, int b, int c) {
////        buttons[a].setBackground(Color.GREEN);
////        buttons[b].setBackground(Color.GREEN);
////        buttons[c].setBackground(Color.GREEN);
//
//        for (int i = 0; i < 9; i++) {
//            buttonsArr[i].s
//        }
////        textfield.setText("X wins");
//        playerWins = true;
//    }

//    public void oWins(int a, int b, int c) {
////        buttons[a].setBackground(Color.GREEN);
////        buttons[b].setBackground(Color.GREEN);
////        buttons[c].setBackground(Color.GREEN);
//
//        for (int i = 0; i < 9; i++) {
//            buttons[i].setEnabled(false);
//        }
////        textfield.setText("O wins");
//    } 
     
}

