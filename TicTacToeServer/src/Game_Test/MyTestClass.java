package Game_Test;


//***************************************
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MyTestClass implements ActionListener {

    Random random = new Random();
    JFrame frame = new JFrame();
    JPanel title_panel = new JPanel();
    JPanel button_panel = new JPanel();
    JLabel textfield = new JLabel();
    JButton[] buttons = new JButton[9];
    boolean player1_turn;
    char[] arrPlays = new char[9];
    boolean playerWins = false;

    public MyTestClass() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        textfield.setBackground(new Color(25, 25, 25));
        textfield.setForeground(new Color(25, 255, 0));
        textfield.setFont(new Font("Ink Free", Font.BOLD, 75));
        textfield.setHorizontalAlignment(JLabel.CENTER);
        textfield.setText("Tic-Tac-Toe");
        textfield.setOpaque(true);

        for (int i = 0; i < arrPlays.length; i++) {
            arrPlays[i] = (char) i;
        }

        title_panel.setLayout(new BorderLayout());
        title_panel.setBounds(0, 0, 800, 100);

        button_panel.setLayout(new GridLayout(3, 3));
        button_panel.setBackground(new Color(150, 150, 150));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            button_panel.add(buttons[i]);
            buttons[i].setFont(new Font("MV Boli", Font.BOLD, 120));
            buttons[i].setFocusable(false);
            buttons[i].addActionListener(this);
            buttons[i].setEnabled(false);
        }

        title_panel.add(textfield);
        frame.add(title_panel, BorderLayout.NORTH);
        frame.add(button_panel);

        firstTurn();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for (int i = 0; i < 9; i++) {
            if (e.getSource() == buttons[i]) {
                if (player1_turn) {
                    if (buttons[i].getText() == "") {
                        buttons[i].setForeground(new Color(255, 0, 0));
                        buttons[i].setText("X");
                        player1_turn = false;
                        arrPlays[i] = buttons[i].getText().charAt(0);
                        textfield.setText("O turn");
                        
                        check();
                        if (!playerWins) {
                            //gamePlay();
                            check();
                        }

                    }
                }
                //else {
//                    if (buttons[i].getText() == "") {
//                        buttons[i].setForeground(new Color(0, 0, 255));
//                        buttons[i].setText("O");
//                        player1_turn = true;
//                        arrPlays[i] = buttons[i].getText().charAt(0);
//                        textfield.setText("X turn");
//                        check();
//                    }
//                }
            }
        }
    }

    public void firstTurn() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(true);
        }

        player1_turn = true;
        textfield.setText("X turn");
    }

    public void gamePlay() {
        
        
        if(arrPlays[4] != 'X' && arrPlays[4] != 'O')
        {
            buttons[4].setText("O");
            arrPlays[4] = 'O';
            buttons[4].setForeground(new Color(0, 0, 255));
            player1_turn = true;
            return;
        }
        
        for (int i = 0; i < 9; i++) {
            if (arrPlays[i] != 'X' && arrPlays[i] != 'O') {
                buttons[i].setText("O");
                arrPlays[i] = 'O';
                buttons[i].setForeground(new Color(0, 0, 255));
                player1_turn = true;
                return;
            }
        }
    }
    
    public void gamePlayMultiplayer() {
        
        for (int i = 0; i < 9; i++) {
            if (arrPlays[i] != 'X' && arrPlays[i] != 'O') {
                buttons[i].setText("O");
                arrPlays[i] = 'O';
                buttons[i].setForeground(new Color(0, 0, 255));
                player1_turn = true;
                return;
            }
        }
    }
    
    
    

    public void check() {
        //check X win conditions
        int j;

        for (int i = 0; i < 3; i++) {
            j = i * 3;

            if (i != 1) {
                if ((arrPlays[i] == arrPlays[4]) && (arrPlays[4] == arrPlays[8 - i])) {
                    if (arrPlays[i] == 'X') {
                        xWins(i, 4, 8 - i);
                    } else {
                        oWins(i, 4, 8 - i);
                    }
                    return;
                }
            }
            if ((arrPlays[i] == arrPlays[i + 3]) && (arrPlays[i + 3] == arrPlays[i + 6])) // Check Columns 
            {
                if (arrPlays[i] == 'X') {
                    xWins(i, i + 3, i + 6);
                } else {
                    oWins(i, i + 3, i + 6);
                }
                return;
            } else if ((arrPlays[j] == arrPlays[j + 1]) && (arrPlays[j + 1] == arrPlays[j + 2])) ///Check Rows
            {
                if (arrPlays[j] == 'X') {
                    xWins(j, j + 1, j + 2);
                } else {
                    oWins(j, j + 1, j + 2);
                }
                return;
            }

        }


    }

    public void xWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("X wins");
        playerWins = true;
    }

    public void oWins(int a, int b, int c) {
        buttons[a].setBackground(Color.GREEN);
        buttons[b].setBackground(Color.GREEN);
        buttons[c].setBackground(Color.GREEN);

        for (int i = 0; i < 9; i++) {
            buttons[i].setEnabled(false);
        }
        textfield.setText("O wins");
    }
}
