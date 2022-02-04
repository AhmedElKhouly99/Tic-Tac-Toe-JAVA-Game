/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author shorook
 */
public class MainController implements Initializable {

    @FXML
    protected Button MultiPlayerBtn;
    @FXML
    private Button singlePlayerBtn;

    /**
     * Initializes the controller class.
     */
     static class XCell extends ListCell<String> {
        HBox hbox = new HBox();
        Label label = new Label("(empty)");
        Pane pane = new Pane();
        Button newGame = new Button("+");
        Button resume=new Button(">");
        String lastItem;
          String names[]=new String[3];
          int score[]=new int[3];
      
        public XCell(){
            super();
          
            label.setFont(new Font(20));
             
            hbox.getChildren().addAll(label, pane,newGame,resume);
            HBox.setHgrow(pane, Priority.ALWAYS);
            newGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(lastItem + " : " + event);
                }
            });
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void MultiPlayer(ActionEvent event) throws IOException {
//         Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
  StackPane pane = new StackPane();
//        Scene scene = new Scene(pane, 300, 150);
//        primaryStage.setScene(scene);
        Vector<Player> ConnectedPlayers = new Vector<Player>();
        ConnectedPlayers.add(new Player("ahmed",100, false));
        ConnectedPlayers.add(new Player("Shorook",100, false));
        String [] info = new String[ConnectedPlayers.size()];
        
        int i = 0;
        for(Player p : ConnectedPlayers){
            info[i] = p.getName()+"\t"+p.getScore();
            i++;
        }
         ObservableList<String> list = FXCollections.observableArrayList(info);
        ListView<String> lv = new ListView<>(list);
        lv.setStyle("-fx-control-inner-background: #2E2E2E;");
        lv.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new XCell();
            }
        });
        pane.getChildren().add(lv);
        Stage window = (Stage) MultiPlayerBtn.getScene().getWindow();
        window.setScene(new Scene(pane));

    }

    @FXML
    private void singleplayer(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("VsComputerMode.fxml"));
        Stage window = (Stage) singlePlayerBtn.getScene().getWindow();
        window.setScene(new Scene(root));
        
    }
    
}
