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
public class Player {

    public Player(String username, int score) {
        this.username = username;
        this.score = score;
        this.inGame = false;
    }
    String username;
    int score;
    boolean inGame;

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

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
