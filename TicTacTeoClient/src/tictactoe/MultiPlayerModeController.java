
 
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
import player.Players;

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
    
    private String symbol = new String();
    
    char[] arrPlays = new char[9];
    
    boolean playerWins = false;
    private Thread myGameTh;
    Button[] buttonsArr;
    
    String index;
    String[] msg;
    
    boolean player1_turn=true;
    byte gameCOunter=0;
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
            buttonsArr[index].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #00b100;");
            buttonsArr[index].setText("O");
            arrPlays[index]='O';
        }else{
            buttonsArr[index].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #ff0303;");
            buttonsArr[index].setText("X");
            arrPlays[index]='X';
        }
        playerOneName.setStyle("-fx-text-fill: green;");
        playerTwoName.setStyle("-fx-text-fill: red;");
        
        gameCOunter++;
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
                playerOneName.setStyle("-fx-text-fill: green;");
                playerTwoName.setStyle("-fx-text-fill: red;");
            }else{
                playerOneName.setStyle("-fx-text-fill: red;");
                playerTwoName.setStyle("-fx-text-fill: green;");
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
        
        
        
        playerOneScore.setStyle("-fx-text-fill:#4adeed");
        playerTwoScore.setStyle("-fx-text-fill:#4adeed");
        
        playerOneName.setText(Players.myPlayer.getUsername());
        playerTwoName.setText(Players.vsPlayer.getUsername());
        
        playerOneScore.setText(String.valueOf(Players.myPlayer.getScore()));
        
        playerTwoScore.setText(String.valueOf(Players.vsPlayer.getScore()));
        
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


        for (int i = 0; i < 9; i++) {
            if (event.getTarget() == buttonsArr[i]) {
                if (player1_turn) {
                    if (buttonsArr[i].getText().equals("")) {

//                        System.out.println("Hiiii");
         
                        if(symbol.equals("X"))
                        {
                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #ff0303;");
                        }
                        else
                        {
                            buttonsArr[i].setStyle("-fx-background-color: #4adeed;-fx-text-fill: #00b100;");
                        }
                        buttonsArr[i].setText(symbol);
                        player1_turn = false;
                        arrPlays[i] = buttonsArr[i].getText().charAt(0);
                        playerOneName.setStyle("-fx-text-fill: red;");
                        playerTwoName.setStyle("-fx-text-fill: green;");
                        
                        gameCOunter++;

                        PlayerSocket.outObj.writeObject("play::"+i);

                        check();
                            
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
        
        if(gameCOunter==9)
        {
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
                          
                          
                          playerWins=true;     
                          playerOneName.setText("You Win");
                          playerOneScore.setText("");
                    } else {
                          
                            youLose(i, 4, 8 - i);
                            player1_turn=false;
                            playerOneName.setText("You lose");
                            playerOneScore.setText("");
                    }
                    return;
                }
            }
            if ((arrPlays[i] == arrPlays[i + 3]) && (arrPlays[i + 3] == arrPlays[i + 6])) // Check Columns 
            {
                if (arrPlays[i] == symbol.charAt(0)) {
                    youWin(i, i+3, i+6);
                    
                    playerWins=true;
               
                    playerOneName.setText("You Win");
                    playerOneScore.setText("");
                        } else {

                    youLose(i, i+3, i+6);
                    
                     player1_turn=false;
                     playerOneName.setText("You lose");
                     playerOneScore.setText("");
                }
                return;
            } else if ((arrPlays[j] == arrPlays[j + 1]) && (arrPlays[j + 1] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == symbol.charAt(0)) {

                    youWin(j, j+1,  j+2);

                    playerWins=true;
                    playerOneName.setText("You Win");
                    playerOneScore.setText("");
                } else {

                    youLose(j, j+1, j+2);
                    
                    player1_turn=false;
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
