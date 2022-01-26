/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package player;

import game.Game;

/**
 *
 * @author ahmed
 */
public class Player {

    public Player(int id, String username, String password, char gender, int score) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.score = score;
        this.status = 1;
    }
    
    int id;
    String username;
    String password;
    char gender;
    //char x_o;
    int score = 0;
    //boolean turn;
    int stage;
    int status;
    Game []gs;
    
    public Player(String username, String password, char gender) {
        //this.id = id;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.stage = 0;
//        this.x_o = x_o;
//        this.score = score;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

//    public char getX_o() {
//        return x_o;
//    }

//    public void setX_o(char x_o) {
//        this.x_o = x_o;
//    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }       
}
