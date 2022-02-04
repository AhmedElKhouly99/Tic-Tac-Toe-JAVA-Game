/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

/**
 *
 * @author shorook
 */
public class Player {
    String name;
    int score;
     boolean inGame;

    public Player(String name, int score, boolean inGame) {
        this.name = name;
        this.score = score;
        this.inGame = inGame;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public boolean isInGame() {
        return inGame;
    }
   
    
}

