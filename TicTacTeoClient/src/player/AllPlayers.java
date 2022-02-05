/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import java.io.Serializable;

/**
 *
 * @author ahmed
 */
public class AllPlayers implements Serializable{

    Integer rank;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
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
    
    

    public AllPlayers(Integer rank, String username, int score) {
        this.rank = rank;
        this.username = username;
        this.score = score;
    }
    
}
