/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import game.Game;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import player.Player;

/**
 *
 * @author ahmed
 */
public class Database {
    
    final static String CONSTR = "jdbc:mysql://:3306/xogame?useSSL=true";
    final static String INSERTPLAYER = "insert into player (username,password,gender,score) values(?,?,?,?)";
    //final static String SEARCHPLAYER = "select * from player where username=? and password=?";
    final static String INSERTGAME = "insert into game values(?,?,?,?,?,?,?,?,?,?,?)";
    final static String SEARCHGAME = "select * from game where (player1_idx=? and player2_ido=?) OR (player1_idx=? and player2_ido=?)";
    final static String EDITSCORE = "update player set score=? where player_id=?";
    final static String GETPALYER = "select * from player where username=? and password=?";
    final static String DELETEGAME = "delete from game where player1_idx=? and player2_ido=?";
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
    public static boolean addPlayer(Player p){
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(INSERTPLAYER);
            preparedStmt.setString (1, p.getUsername());
            preparedStmt.setString (2, p.getPassword());
            preparedStmt.setString(3, p.getGender()+"");
            preparedStmt.setInt(4, 0);
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
    public static Player isPlayer(String uname, String pass){
        Player p;
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(GETPALYER);
            preparedStmt.setString (1, uname);
            preparedStmt.setString (2, pass);
            rs = preparedStmt.executeQuery();
            if(rs.next() && rs.getString(1).equals(uname) && rs.getString(2).equals(pass)){
                p = new Player(rs.getInt(5), rs.getString(1), rs.getString(2), rs.getString(3).charAt(0), rs.getInt(4));
            }else{
                p = null;
            }
            rs.close();
            preparedStmt.close();
            con.close();
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @SuppressWarnings("empty-statement")
    public static boolean addGame(Game g){
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(INSERTGAME);
            preparedStmt.setInt(1, g.getPlayer1_id());
            preparedStmt.setInt(2, g.getPlayer2_id());
            preparedStmt.setString(3,g.getOne()+"");
            preparedStmt.setString(4, g.getTwo()+"");
            preparedStmt.setString(5, g.getThree()+"");
            preparedStmt.setString(6, g.getFour()+"");
            preparedStmt.setString(7, g.getFive()+"");
            preparedStmt.setString(8, g.getSix()+"");
            preparedStmt.setString(9, g.getSeven()+"");
            preparedStmt.setString(10, g.getEight()+"");
            preparedStmt.setString(11, g.getNine()+"");
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
    public static Game getGame(int player1_id, int player2_id){
        try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(SEARCHGAME);
            preparedStmt.setInt(1, player1_id);
            preparedStmt.setInt(2, player2_id);
            preparedStmt.setInt(3, player2_id);
            preparedStmt.setInt(4, player1_id);
            rs = preparedStmt.executeQuery();
            if(rs.next() && (rs.getInt(1) == player1_id || rs.getInt(1) == player2_id)){
                Game g = new Game(rs.getInt(1), rs.getInt(2), rs.getString(3).charAt(0),rs.getString(4).charAt(0), rs.getString(5).charAt(0), rs.getString(6).charAt(0), rs.getString(7).charAt(0), rs.getString(8).charAt(0), rs.getString(9).charAt(0), rs.getString(10).charAt(0), rs.getString(11).charAt(0));
                preparedStmt = con.prepareStatement(DELETEGAME);
                preparedStmt.setInt(1, g.getPlayer1_id());
                preparedStmt.setInt(2, g.getPlayer2_id());
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
    public static boolean editPlayer(Player p){
         try {
            while(!startConnection());
            preparedStmt = con.prepareStatement(EDITSCORE);
            preparedStmt.setInt(1, p.getScore());
            preparedStmt.setInt(2, p.getId());
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
    
}
