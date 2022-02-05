/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import ServerHandler.ClientHandler;
import game.Game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.AllPlayers;
import player.Players;

/**
 *
 * @author ahmed
 */
public class Database {
    
    final static String CONSTR = "jdbc:mysql://:3306/xogame?useSSL=true";
    final static String INSERTPLAYER = "insert into player (username,password,score) values(?,?,?)";
    //final static String SEARCHPLAYER = "select * from player where username=? and password=?";
    final static String INSERTGAME = "insert into game values(?,?,?,?,?,?,?,?,?,?,?,?)";
    final static String SEARCHGAME = "select * from game where (username1_x=? and username2_o=?) OR (username2_o=? and username1_x=?)";
    final static String EDITSCORE = "update player set score=? where username=?";
    final static String GETPLAYER = "select * from player where username=? and password=?";
    final static String DELETEGAME = "delete from game where username1_x=? and username2_o=?";
    final static String GETALLPLAYERS = "select username, score from player order by score desc";
    static Connection con;
    static Statement stm;
    static PreparedStatement preparedStmt;
    static ResultSet rs;
    
    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean startConnection(){
        try {
            con = DriverManager.getConnection(CONSTR, "javagame", "123");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @SuppressWarnings("empty-statement")
    public static boolean addPlayer(String uname, String pass){
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(INSERTPLAYER);
            preparedStmt.setString (1, uname);
            preparedStmt.setString (2, pass);
            preparedStmt.setInt(3, 0);
            preparedStmt.execute();
            preparedStmt.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @SuppressWarnings("empty-statement")
    public static boolean isPlayer(String uname, String pass, Players p){
//        p = new ClientHandler.Player();
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(GETPLAYER);
            preparedStmt.setString (1, uname);
            preparedStmt.setString (2, pass);
            rs = preparedStmt.executeQuery();
            if(rs.next() && rs.getString(1).equals(uname) && rs.getString(2).equals(pass)){
//                p = new Players(rs.getInt(5), rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4));
//                p = new Players();
                p.setUsername(rs.getString(1));
                p.setScore(rs.getInt(3));
                p.setInGame(false);
//                return true;
            }else{
                p = null;
            }
            rs.close();
            preparedStmt.close();
            con.close();
            //return p;
            return true;
       } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        //p = null;
        return false;
    }
    
    @SuppressWarnings("empty-statement")
    public static boolean addGame(Game g){
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(INSERTGAME);
            preparedStmt.setString(1, g.getUsername1_x());
            preparedStmt.setString(2, g.getUsername2_o());
            preparedStmt.setString(3,g.getOne()+"");
            preparedStmt.setString(4, g.getTwo()+"");
            preparedStmt.setString(5, g.getThree()+"");
            preparedStmt.setString(6, g.getFour()+"");
            preparedStmt.setString(7, g.getFive()+"");
            preparedStmt.setString(8, g.getSix()+"");
            preparedStmt.setString(9, g.getSeven()+"");
            preparedStmt.setString(10, g.getEight()+"");
            preparedStmt.setString(11, g.getNine()+"");
             preparedStmt.setString(12, g.getTurn()+"");
            preparedStmt.execute();
            preparedStmt.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    @SuppressWarnings("empty-statement")
    public static Game getGame(String player1, String player2){
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(SEARCHGAME);
            preparedStmt.setString(1, player1);
            preparedStmt.setString(2, player2);
            preparedStmt.setString(3, player2);
            preparedStmt.setString(4, player1);
            rs = preparedStmt.executeQuery();
            if(rs.next() && (rs.getString(1).equals(player1) || rs.getString(1).equals(player2))){
                Game g = new Game(rs.getString(1), rs.getString(2), rs.getString(3).charAt(0),rs.getString(4).charAt(0), rs.getString(5).charAt(0), rs.getString(6).charAt(0), rs.getString(7).charAt(0), rs.getString(8).charAt(0), rs.getString(9).charAt(0), rs.getString(10).charAt(0), rs.getString(11).charAt(0), rs.getInt(12));
                preparedStmt = con.prepareStatement(DELETEGAME);
                preparedStmt.setString(1, g.getUsername1_x());
                preparedStmt.setString(2, g.getUsername2_o());
                preparedStmt.execute();
                rs.close();
                preparedStmt.close();
                con.close();
                return g;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @SuppressWarnings("empty-statement")
    public static boolean editPlayer(AllPlayers p){
         try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(EDITSCORE);
            preparedStmt.setInt(1, p.getScore());
            preparedStmt.setString(2, p.getUsername());
             System.err.println("Done");
            preparedStmt.execute();
            preparedStmt.close();
            con.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public static ObservableList<AllPlayers> getAllPlayers()
    {       
        ObservableList<AllPlayers> list = FXCollections.observableArrayList();
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(GETALLPLAYERS);
            rs = preparedStmt.executeQuery();
            while(rs.next()){
                list.add(new AllPlayers(rs.getString(1), rs.getInt(2)));
            }
            rs.close();
            preparedStmt.close();
            con.close();
            return    FXCollections.observableList(list);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }   
}
