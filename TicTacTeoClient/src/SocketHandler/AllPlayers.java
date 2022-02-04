/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketHandler;

/**
 *
 * @author ahmed
 */
public class AllPlayers {

    String username;
    int score;
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    

    public AllPlayers(String username, int score) {
        this.username = username;
        this.score = score;
    }
    
}
