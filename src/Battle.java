/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 348983139
 */

import java.util.Random;
import java.awt.*;
import java.io.*;           //used for any type of input or output
import java.util.*;         //useful utilities like Scanner
import hsa.Console;
import hsa.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.awt.Graphics;

public class Battle extends javax.swing.JPanel {

    // static Console c = new Console();

    static char[][] board = new char[10][10];
    static int[] aircraftX = new int[6], aircraftY = new int[6];
    static int[] destroyerX = new int[3], destroyerY = new int[3];
    static int[] patrolX = new int[2], patrolY = new int[2];
    static int[] battleshipX = new int[4], battleshipY = new int[4];
    static int[] submarineX = new int[3], submarineY = new int[3];
    static int submarineHP = 3, aircraftHP = 6, destroyerHP = 3, patrolHP = 2, battleshipHP = 4;
    static int submarine = 3, aircraft = 6, destroyer = 3, patrol = 2, battleship = 4;
    static boolean overlap = true;
    boolean[][] shipHit = new boolean[10][10];

    Graphics g;

    static int moves;

    Random rn = new Random();

    /**
     * Creates new form Battle
     */
    public Battle() {
        initComponents();

    }

    public void paintComponent(Graphics g) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = '-';
                shipHit[i][j] = false;

            }

        }
        //Generates random ship location
        shipLocation();
        //Draws the initial board
        drawBoard(g);

    }
    //Draws the board with filled squares
    public void drawBoard(Graphics g) {
        //A nested loop that draws the board with squares 
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                //Draw blue squares at the start of the game
                if (board[i][j] == '-') {
                    g.setColor(Color.blue);
                    g.fillRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                    g.setColor(Color.yellow);
                    g.drawRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                } 
                //Draw green squares if a hit is missed
                else if (board[i][j] == 'x') {
                    g.setColor(Color.green);
                    g.fillRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                    g.setColor(Color.yellow);
                    g.drawRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);

                } 
                //If it is marked with a *, it means that the ship is spawning there
                else if (board[i][j] == '*') {
                    //If it is not hit yet, stay hidden
                    if (shipHit[i][j] == false) {
                        g.setColor(Color.blue);
                        g.fillRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                        g.setColor(Color.yellow);
                        g.drawRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                    } 
                    //If it is hit, draw it in red
                    else {
                        g.setColor(Color.red);
                        g.fillRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                        g.setColor(Color.yellow);
                        g.drawRect((getWidth() / 10) * i, (getHeight() / 10) * j, getWidth() / 10, getHeight() / 10);
                        g.drawLine((getWidth() / 10) * i, (getHeight() / 10) * j, (getWidth() / 10) * i + getWidth() / 10, (getHeight() / 10) * j + getHeight() / 10);
                        g.drawLine((getWidth() / 10) * i, (getHeight() / 10) * j + getHeight() / 10, (getWidth() / 10) * i + getWidth() / 10, (getHeight() / 10) * j);

                    }
                }
            }
        }
    }

    //  Places the ship horizontally
    public static void horizontalPlacement(int[] x, int[] y, int ship) {
        //falseNumber keeps track of the number of non overlapping coordinates
        int falseNum = 0;
        for (int i = 0; i < ship; i++) {
            x[i] = x[0] + i;
            y[i] = y[0];
            if (isOverlap(x[i], y[i]) == true) {
                overlap = true;
            } else {
                falseNum = falseNum + 1;
            }
        }
        //If falseNum is equal to the ship length
        //Then they do not overlap
        if (falseNum == ship) {
            overlap = false;
        }
        //After confirming they do not overlap
        //Will store its value into the board[][]
        if (overlap == false) {
            for (int i = 0; i < ship; i++) {
                board[x[i]][y[i]] = '*';
                //The false state will help to exit out of the loop that is 
                //responisble for generating the coordinates for that particular ship
                //So the program can continue to the next ship and generate the locaiton
                overlap = false;
            }
        }

    }

    //Places the ship vertically
    public static void verticalPlacement(int[] x, int[] y, int ship) {
        //Integer that records the number of coordinates
        //that do not overlap with any ship on the board
        int falseNum = 0;
        for (int i = 0; i < ship; i++) {
            //Row number does not change
            x[i] = x[0];
            //Adds one more column to the right everytime it goes through the loop
            y[i] = y[0] + i;
            if (isOverlap(x[i], y[i]) == true) {
                overlap = true;

            } else {
                falseNum++;

            }
        }
        if (falseNum == ship) {
            overlap = false;
        }
        if (overlap == false) {
            for (int i = 0; i < ship; i++) {
                board[x[i]][y[i]] = '*';
                overlap = false;
            }
        }
    }

    //Generate ship location and mark them

    public void shipLocation() {
        //Aircraft location
        Random rn = new Random();
        //Decide the direction of the ship by generating a 0 or 1. Whether vertically or horizontally
        int vertOrHoriz = rn.nextInt(2);

        //0 will mean vertical direction
        if (vertOrHoriz == 0) {
            //The x coordinate is the column number
            //Because it is vertical, it can be placed in any column
            aircraftX[0] = rn.nextInt(10);
            //But it cannot go above the 4th row because it is 6 units long
            aircraftY[0] = rn.nextInt(5);
            //Generate the rest of the coordinates of the ship
            verticalPlacement(aircraftX, aircraftY, aircraft);
            //Set overlap to be true so the ship coordinates generation will repeat
            //Until a unique set of coordinates is generated
            overlap = true;
        } 
        
        //Horizontally
        else if (vertOrHoriz == 1) {
            aircraftX[0] = rn.nextInt(5);
            aircraftY[0] = rn.nextInt(10);
            horizontalPlacement(aircraftX, aircraftY, aircraft);
            overlap = true;
        }

        while (overlap == true) {
            //Battleship locaiton
            vertOrHoriz = rn.nextInt(2);
            //Vertically
            if (vertOrHoriz == 0) {
                 //Ship can be on row 0 - 9, but only on 0 - 6 column 
                //So it does not extend out of the board
                battleshipX[0] = rn.nextInt(10);
                battleshipY[0] = rn.nextInt(7);

            //Extends the placement horizontally
                verticalPlacement(battleshipX, battleshipY, battleship);

            } //Horizontally
            else if (vertOrHoriz == 1) {
                battleshipX[0] = rn.nextInt(7);
                battleshipY[0] = rn.nextInt(10);
                //Extends the placement Horizontally
                horizontalPlacement(battleshipX, battleshipY, battleship);
            }
        }
        overlap = true;

        while (overlap == true) {

            //Submarine location
            vertOrHoriz = rn.nextInt(2);
            //Vertically
            if (vertOrHoriz == 0) {
                 //Ship can be on row 0 - 9, but only on 0 - 7 column 
                //So it does not extend out of the board
                submarineX[0] = rn.nextInt(10);
                submarineY[0] = rn.nextInt(8);
                verticalPlacement(submarineX, submarineY, submarine);

            } //Horizontally
            else if (vertOrHoriz == 1) {
                submarineX[0] = rn.nextInt(8);
                submarineY[0] = rn.nextInt(10);
                //Extends the placement horizontally
                horizontalPlacement(submarineX, submarineY, submarine);

            }
        }
        overlap = true;

        //destroyer location
        while (overlap == true) {
            vertOrHoriz = rn.nextInt(2);
            //Vertically
            if (vertOrHoriz == 0) {
                 //Ship can be on row 0 - 9, but only on 0 - 7 column 
                //So it does not extend out of the board
                destroyerX[0] = rn.nextInt(10);
                destroyerY[0] = rn.nextInt(8);
                verticalPlacement(destroyerX, destroyerY, destroyer);
            } else if (vertOrHoriz == 1) {
                destroyerX[0] = rn.nextInt(8);
                destroyerY[0] = rn.nextInt(10);
                //Extends the placement Horizontally
                horizontalPlacement(destroyerX, destroyerY, destroyer);

            }

        }
         //Patrol location

        overlap = true;
        while (overlap == true) {

            //Patrol location
            vertOrHoriz = rn.nextInt(2);
            //Vertically
            if (vertOrHoriz == 0) {
                 //Ship can be on row 0 - 9, but only on 0 - 7 column 
                //So it does not extend out of the board
                patrolX[0] = rn.nextInt(10);
                patrolY[0] = rn.nextInt(9);
                verticalPlacement(patrolX, patrolY, patrol);

            } //Horizontally
            else if (vertOrHoriz == 1) {
                patrolX[0] = rn.nextInt(9);
                patrolY[0] = rn.nextInt(10);
                //Extends the placement Horizontal
                horizontalPlacement(patrolX, patrolY, patrol);

            }
        }
        overlap = true;

    }

    //Check if the spot already has a ship marked there
    public static boolean isOverlap(int x, int y) {
        if (board[x][y] == '*') {
            return true;
        }
        return false;
    }

    //Gets the player to make a move and mark it
    public void moveShip(int row, int col, Graphics g) {
        //Check for hit and track hp
        aircraftHP = hp(row, col, aircraftX, aircraftY, aircraftHP, aircraft);
        battleshipHP = hp(row, col, battleshipX, battleshipY, battleshipHP, battleship);
        submarineHP = hp(row, col, submarineX, submarineY, submarineHP, submarine);
        destroyerHP = hp(row, col, destroyerX, destroyerY, destroyerHP, destroyer);
        patrolHP = hp(row, col, patrolX, patrolY, patrolHP, patrol);
        //If the user's input matches with a ship's coordinate, mark that coordinate to be true
        if (board[col][row] == '*') {
            shipHit[col][row] = true;
        } else {
            board[col][row] = 'x';

        }
        //Calls the drawBoard method to update the colours and squares
        drawBoard(g);

    }
    //Keep track of the ship's hp
    public static int hp(int row, int col, int[] x, int[] y, int shipHP, int ship) {
        for (int i = 0; i < ship; i++) {
            if (x[i] == col && y[i] == row) {
                //Hp goes down by 1 
                shipHP--;
            }

        }
        return shipHP;

    }
    //Wins upon sinking all ships by dropping all their hp's to zero
    public boolean win() {
        if ((submarineHP == 0) && (battleshipHP == 0) && (aircraftHP == 0) && (destroyerHP == 0) && (patrolHP == 0)) {
            return true;
        }
        return false;

    }
    
    //Check if the user already took the spot
    public boolean isOccupied(int row, int col) {
        if (board[col][row] == 'x' || shipHit[col][row] == true) {
            return true;
        }
        return false;

    }

//   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 348, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 292, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
