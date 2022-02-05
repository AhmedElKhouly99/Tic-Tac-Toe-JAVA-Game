/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import SocketHandler.PlayerSocket;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import player.Players;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class MenuController implements Initializable {

    @FXML
    private Button RankBtn;
    
    @FXML
    private Button StartGameBtn;

      @FXML
    private Button LogoutBtn;
         static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button newGame = new Button();
        Button resume=new Button();
        String lastItem;
        String names[]=new String[3];
        int score[]=new int[3];
      
        public XCell(){
            super();
          
            label.setFont(new Font(20));
            label.setTranslateX(10);
            newGame.setPrefSize(50, 50);
//            newGame.getStyleClass().add("btn");
            resume.setPrefSize(50, 50);
            //newGame.setTranslateX(-30);
           
             ImageView newGameImg = new ImageView("file:/C:/Users/shorook/Desktop/Tic-Tac-Toe-JAVA-Game/TicTacTeoClient/src/tictactoe/images/newgame.png");
              newGameImg.setFitHeight(20);
              newGameImg.setFitWidth(20);
              //newGameImg.setPreserveRatio(true);
              newGame.setGraphic(newGameImg);
              ImageView resumeImg = new ImageView("file:/C:/Users/shorook/Desktop/Tic-Tac-Toe-JAVA-Game/TicTacTeoClient/src/tictactoe/images/resum.png");
              resumeImg.setFitHeight(35);
              resumeImg.setFitWidth(35);
              //newGameImg.setPreserveRatio(true);
              resume.setGraphic(resumeImg);
              
              ImageView onlineImg = new ImageView("file:/C:/Users/shorook/Desktop/Tic-Tac-Toe-JAVA-Game/TicTacTeoClient/src/tictactoe/images/online.png");
              onlineImg.setTranslateY(10);
              onlineImg.setTranslateX(-3);
              onlineImg.setFitHeight(10);
              onlineImg.setFitWidth(10);
            
            hbox.getChildren().addAll(onlineImg,label,pane,newGame,resume);
            HBox.setHgrow(pane, Priority.ALWAYS);
            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                        System.out.println(label.getText());
                        
                       Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("MultiPlayersMode.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                      Stage window = (Stage) newGame.getScene().getWindow();
                       window.setScene(new Scene(root));
        
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
                label.setText(item!=null ? item : "<null>");
              
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
    }    

    @FXML
    private void GoToRanks(ActionEvent event) throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("ScoreList.fxml"));
        Stage window = (Stage) RankBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }
    
    @FXML
    void StartGame(ActionEvent event) throws IOException {
        StackPane pane = new StackPane();
       
        
        
        Vector<Players> ConnectedPlayers = new Vector<Players>();
        ConnectedPlayers.add(new Players("ahmed",100));
        ConnectedPlayers.add(new Players("Shorook",100));
        String [] info = new String[ConnectedPlayers.size()];
        
        int i = 0;
        for(Players p : ConnectedPlayers){
            info[i] = p.getUsername()+"\t"+p.getScore();
            i++;
        }
         ObservableList<String> list = FXCollections.observableArrayList(info);
        ListView<String> lv = new ListView<>(list);
        //lv.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth()-200);
        lv.setStyle("-fx-control-inner-background: #edcef1; -fx-background-radius: 5; -fx-border-color: #b023c1; -fx-border-style: solid; -fx-border-width: 2; -fx-border-radius: 5;");
        
        lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });

        
        pane.getChildren().addAll(lv);

        
        
       
        Stage window = (Stage) StartGameBtn.getScene().getWindow();
        window.setScene(new Scene(pane,500,500));
        

       }
//        Parent root = FXMLLoader.load(getClass().getResource("MultiPlayerMode.fxml"));
//        Stage window = (Stage) StartGameBtn.getScene().getWindow();
//        window.setScene(new Scene(root));
    @FXML
      void Logout(ActionEvent event) throws IOException {
        PlayerSocket.closeSoket();
        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Stage window = (Stage) LogoutBtn.getScene().getWindow();
        window.setScene(new Scene(root));
    }

    }
    
       





