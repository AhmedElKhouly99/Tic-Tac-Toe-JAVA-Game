/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerHandler;

import Database.Database;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import player.AllPlayers;
import player.Players;

/**
 * FXML Controller class
 *
 * @author Abanoub Kamal
 */
public class MainController implements Initializable {

    private int lastNumberOfOnlinePlayers;
//    private int lastNumberOfOfflinePlayers;
    static boolean startStatusFlag;
    static boolean stopStatusFlag;
    private int SERVER_SOCKET_PORT;    
    
    static ServerSocket myServerSocket;
    static Vector<ClientHandler> clietnsVector;
    
    @FXML
    private Button activateBtn;
    @FXML
    private Label serverStatus;
    @FXML
    private Label activateDeactivateLabel;
    @FXML
    private ImageView activateDeactivateImage;
    @FXML
    private Tab tabViewOnlineName;
    @FXML
    private Tab tabViewAllNames;
    @FXML
    private TableColumn<AllPlayers, String> onlinePlayerName;
    @FXML
    private TableColumn<AllPlayers, Integer> onlinePlayerScore;
    @FXML
    private TableColumn<AllPlayers, String> offlinePlayerName;
    @FXML
    private TableColumn<AllPlayers, Integer> offlinePlayerScore;
    @FXML
    private TableColumn<AllPlayers, Integer> onlinePlayerRank;
    @FXML
    private TableColumn<AllPlayers, Integer> offlinePlayerRank;
    @FXML
    private TableView<AllPlayers> tabViewOnlinePlayers;
    @FXML
    private TableView<AllPlayers> tabViewAllPlayers;
    

    
    List<AllPlayers> listOfAllPlayers = new ArrayList<AllPlayers>();  
    ObservableList<AllPlayers> listOfAllPlayersForTable;
    // online
    Vector<Players> onlinePlayersVector;
    List<AllPlayers> onlinePlayersList = new ArrayList<AllPlayers>();  
    ObservableList<AllPlayers> listOfOnlinePlayersForTable;
    // offline
//    ObservableList<AllPlayers> listOfOfflinePlayersForTable;
    
   
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        SERVER_SOCKET_PORT = 5005;
        lastNumberOfOnlinePlayers = 0;
//        lastNumberOfOfflinePlayers = 0;
        startStatusFlag = false;
        stopStatusFlag = true;
        
        /* for list of players */
        onlinePlayerRank.setCellValueFactory(new PropertyValueFactory<AllPlayers,Integer>("rank"));
        onlinePlayerName.setCellValueFactory(new PropertyValueFactory<AllPlayers,String>("username"));
        onlinePlayerScore.setCellValueFactory(new PropertyValueFactory<AllPlayers,Integer>("score"));
        offlinePlayerRank.setCellValueFactory(new PropertyValueFactory<AllPlayers,Integer>("rank"));
        offlinePlayerName.setCellValueFactory(new PropertyValueFactory<AllPlayers,String>("username"));
        offlinePlayerScore.setCellValueFactory(new PropertyValueFactory<AllPlayers,Integer>("score"));
        
    } 
    
    
    @FXML
    private void handleActivateAndDeactivateAction(ActionEvent event) {
        if(event.getSource() == activateBtn && startStatusFlag == false && stopStatusFlag == true)
        {
            startStatusFlag = true;
            stopStatusFlag = false;
            StartThreadToAcceptClients();
            startThreedToUpdateServerGui();
            
            /* change the button image to deactive next time (execute in UpdateThread)*/ 
            ImageView imageView = new ImageView(getClass().getResource("images/power-off.jpg").toExternalForm());
            imageView.setFitWidth(56);
            imageView.setFitHeight(56);
            activateBtn.setGraphic(imageView);
            /* change label */
            activateDeactivateLabel.setText("Deactivate");
            /* server status */
            serverStatus.setText("ON");
            activateDeactivateImage.setStyle("-fx-background-radius: 2em;");
            activateBtn.setStyle("-fx-background-color: #FA5353; -fx-background-radius: 2em;");
             
            /////////       /* for test */
            testFunc();
            System.out.println("ServerHandler.MainController.handleStartButtonAction()");
        }else {
            
            startStatusFlag = false;
            stopStatusFlag = true;
            try {
                actionAtServerAppClose();
            } catch (IOException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ServerHandler.MainController.handleActivateAndDeactivateAction().IOException");
            }
            
            /* change the button image to active next time */ 
            ImageView imageView = new ImageView(getClass().getResource("images/power-on-button.png").toExternalForm());
            imageView.setFitWidth(56);
            imageView.setFitHeight(56);
            activateBtn.setGraphic(imageView);
            /* change label */
            activateDeactivateLabel.setText("Activate");
            /* server status */
            serverStatus.setText("OFF");
            activateDeactivateImage.setStyle("-fx-background-radius: 2em;");
            activateBtn.setStyle("-fx-background-color: #71c213; -fx-background-radius: 2em; ");
           
          //////  /* for test list*/
            tabViewOnlinePlayers.getItems().clear();
            tabViewAllPlayers.getItems().clear();
            onlinePlayersList.clear();
            listOfAllPlayers.clear();
            System.out.println("ServerHandler.MainController.handleStopButtonAction()");
        }    
    }


    
 
    /**************************************************************************/
    /***************** thread for the server to accept clients ****************/
    
    private static Thread acceptClientsThread;
    private void StartThreadToAcceptClients()
    {
        Runnable runnable = new Runnable(){
            @Override
            public void run(){
                
                try{
                    myServerSocket = new ServerSocket(SERVER_SOCKET_PORT);
                    while(true){
                        Socket internalSocket = myServerSocket.accept();
                        
                        new ClientHandler(internalSocket);
//                        System.out.println("A player accepted");
                    }    
                }catch(Exception e){
                    System.out.println("ServerHandler.MainController.StartThreadToAcceptClients().Exception");
                }   
            }
        };
        
        acceptClientsThread = new Thread(runnable);
        acceptClientsThread.start();
    }
    
    private static void endThreadThatAcceptClients()
    {
        acceptClientsThread.stop();
    }
    
    
    /**************************************************************************/
    /**************** thread to renew the data of server screen ***************/
    private static Thread updateServerGuiThread;
    private void startThreedToUpdateServerGui(){
        Runnable task = new Runnable() {
            public void run(){
                runTask();
            }
        };
        updateServerGuiThread = new Thread(task);
        updateServerGuiThread.start();
    }
    
    private void runTask(){     
        while(true)
        { 
            try
            {
                /* get all players */
//                listOfAllPlayers = Database.getAllPlayers();
//                listOfAllPlayersForTable = FXCollections.observableArrayList(listOfAllPlayers);
                /* get the online players */
                onlinePlayersVector = Players.playersVector;
                if(lastNumberOfOnlinePlayers != onlinePlayersVector.size())
                {
                    lastNumberOfOnlinePlayers = onlinePlayersVector.size();
                    listOfOnlinePlayersForTable.clear();
                    for(Players player: onlinePlayersVector)
                    {
                        onlinePlayersList.add(new AllPlayers(0 , player.getUsername(), player.getScore()));
                    }
                    listOfOnlinePlayersForTable = FXCollections.observableArrayList(onlinePlayersList);
                }

                Platform.runLater( new Runnable(){
                    public void run(){    
                        /* for online players */
                        tabViewOnlinePlayers.setItems(listOfOnlinePlayersForTable);

                        /* for ofline players */
                        tabViewAllPlayers.setItems(listOfAllPlayersForTable);
                    }
                });
                
                updateServerGuiThread.sleep(500);
            }catch(InterruptedException e){
                System.out.println("ServerHandler.MainController.runTask().InterruptedException");
            }
        }
    }
    
    private static void endThreadThatUpdateServerGui()
    {
        updateServerGuiThread.stop();
    }
    
    /**************************************************************************/
    /******************* action should take at close the server ***************/
    static void actionAtServerAppClose() throws IOException
    {
        clietnsVector = ClientHandler.getClientsVector();
        
        /* end all internal sockets (threads that stands against) */
        for(ClientHandler ch: clietnsVector)
        {
            try {
                ch.currentThread().wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(TicTacToeServer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ServerHandler.MainController.actionAtServerAppClose().InterruptedException");
            }
        }
        /* close the thread resposible for accept clients */
        endThreadThatAcceptClients();
        /* close the socket of the server */
        myServerSocket.close();
        /* close the thread resposible for make changes on the GUI */
        endThreadThatUpdateServerGui();
    }

    
    private void testFunc()
    {
        listOfAllPlayers.add(new AllPlayers(0 , "soly", 100));
        listOfOnlinePlayersForTable = FXCollections.observableArrayList(listOfAllPlayers);
        onlinePlayersList.add(new AllPlayers(0 , "khouly", 100));
        onlinePlayersList.add(new AllPlayers(0 , "abanoub", 90));
        onlinePlayersList.add(new AllPlayers(0 , "soly", 100));
        listOfAllPlayersForTable = FXCollections.observableArrayList(onlinePlayersList);
    }
    
}
