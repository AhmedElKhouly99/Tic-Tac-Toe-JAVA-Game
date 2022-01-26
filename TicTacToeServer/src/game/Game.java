/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

/**
 *
 * @author ahmed
 */
public class Game {
    
    int player1_id;
    int player2_id;
    char one;
    char two;
    char three;
    char four;
    char five;
    char six;
    char seven;
    char eight;
    char nine;
    char turn;

    public int getPlayer1_id() {
        return player1_id;
    }

    public void setPlayer1_id(int player1_id) {
        this.player1_id = player1_id;
    }

    public int getPlayer2_id() {
        return player2_id;
    }

    public void setPlayer2_id(int player2_id) {
        this.player2_id = player2_id;
    }

    public char getOne() {
        return one;
    }

    public void setOne(char one) {
        this.one = one;
    }

    public char getTwo() {
        return two;
    }

    public void setTwo(char two) {
        this.two = two;
    }

    public char getThree() {
        return three;
    }

    public void setThree(char three) {
        this.three = three;
    }

    public char getFour() {
        return four;
    }

    public void setFour(char four) {
        this.four = four;
    }

    public char getFive() {
        return five;
    }

    public void setFive(char five) {
        this.five = five;
    }

    public char getSix() {
        return six;
    }

    public void setSix(char six) {
        this.six = six;
    }

    public char getSeven() {
        return seven;
    }

    public void setSeven(char seven) {
        this.seven = seven;
    }

    public char getEight() {
        return eight;
    }

    public void setEight(char eight) {
        this.eight = eight;
    }

    public char getNine() {
        return nine;
    }

    public void setNine(char nine) {
        this.nine = nine;
    }
    
    public Game(int player1_id, int player2_id, char one, char two, char three, char four, char five, char six, char seven, char eight, char nine) {
        this.player1_id = player1_id;
        this.player2_id = player2_id;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
    }
    
}
