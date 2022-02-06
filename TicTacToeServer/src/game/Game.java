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
    
    String username1_x;
    String username2_o;
    char one;
    char two;
    char three;
    char four;
    char five;
    char six;
    char seven;
    char eight;
    char nine;
    int turn;
    public String getUsername1_x() {
        return username1_x;
    }

    public void setUsername1_x(String username1_x) {
        this.username1_x = username1_x;
    }

    public String getUsername2_o() {
        return username2_o;
    }

    public void setUsername2_o(String username2_o) {
        this.username2_o = username2_o;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
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
    
    public Game(String username1_x, String username2_o, char one, char two, char three, char four, char five, char six, char seven, char eight, char nine) {
        this.username1_x = username1_x;
        this.username2_o = username2_o;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
        this.turn = 1;
    }
    
    public Game(String username1_x, String username2_o, char one, char two, char three, char four, char five, char six, char seven, char eight, char nine, int turn) {
        this.username1_x = username1_x;
        this.username2_o = username2_o;
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.five = five;
        this.six = six;
        this.seven = seven;
        this.eight = eight;
        this.nine = nine;
        this.turn = turn;
    }
    
}
