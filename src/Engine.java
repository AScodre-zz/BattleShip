/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Author: 348983139 Class: ICS3U
 *
 * Program: Assignment X Question Y Input: (explain what is being input into the
 * program) Processing: (explain what is being done) Output: (Result of the
 * program)
 *
 */
//Import Statements Listed Alphabetically
import java.io.*;           //used for any type of input or output
import java.util.*;         //useful utilities like Scanner
import hsa.Console;
import hsa.*;
import java.util.Scanner;

/**
 *
 * @author 348983139
 */
public class Engine {

    /**
     * * MAIN METHOD
     *
     **
     * @param args
     */
    static char[][] board = new char[10][10];
    static int[] aircraftX = new int[6], aircraftY = new int[6];
    static int[] destroyerX = new int[3], destroyerY = new int[3];
    static int[] patrolX = new int[2], patrolY = new int[2];
    static int[] battleshipX = new int[4], battleshipY = new int[4];
    static int[] submarineX = new int[3], submarineY = new int[3];
    static int submarineHP = 3, aircraftHP = 6, destroyerHP = 3, patrolHP = 2, battleshipHP = 4;
    static int submarine = 3, aircraft = 6, destroyer = 3, patrol = 2, battleship = 4;
    static boolean overlap = true;

    static int moves;

    static Console c = new Console();

    public static void main(String[] args) {
        //FIRST WRITE YOUR PSEUDOCODE USING COMMENTS, THEN FILL IN WITH CODE
        System.out.println("Starting...");

        c.println("aircraftHP: " + aircraftHP);
        c.println("battleshipHP: " + battleshipHP);
        c.println("SubmarineHP: " + submarineHP);
        c.println("destroyerHP: " + destroyerHP);
        c.println("patrolHP: " + patrolHP);
        c.print("  0 1 2 3 4 5 6 7 8 9\n");

        for (int i = 0; i < 10; i++) {
            c.print(i + " ");
            //c.println(i);
            for (int j = 0; j < 10; j++) {
                board[i][j] = '-';
                c.print(board[i][j]);
                c.print(" ");
            }
            c.println();

        }
        shipLocation();

        while (!win()) {
            move();
        }
        if (win() != true) {
            c.println("You lose... Don't feel bad lets win this time");
        } else {
            c.println("Good job! You win");
        }

    }

    //Places the ship vertically

    public static void verticalPlacement(int[] x, int[] y, int ship) {
        int falseNum = 0;
        for (int i = 0; i < ship; i++) {
            x[i] = x[0] + i;
            y[i] = y[0];
            if (isOverlap(x[i], y[i]) == true) {
                overlap = true;
                System.out.println("overlapped");

            } else {
                falseNum = falseNum + 1;
            }
        }
        if (falseNum == ship) {
            overlap = false;
        }
        if (overlap == false) {
            for (int i = 0; i < ship; i++) {
                board[x[i]][y[i]] = '*';
                System.out.println(x[i]);
                overlap = false;
            }
        }

    }

    //Places the ship horizontally
    public static void horizontalPlacement(int[] x, int[] y, int ship) {
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
                System.out.println("overlapped");

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
                System.out.println(x[i]);
                overlap = false;
            }
        }
    }

    //Generate ship location and mark them

    public static void shipLocation() {
        //Aircraft location
        Random rn = new Random();
        //Decide the direction of the ship. Whether vertically or horizontally
        int vertOrHoriz = rn.nextInt(2);

        //Horizontally
        if (vertOrHoriz == 0) {
            System.out.println("Horizontical");
            aircraftX[0] = rn.nextInt(10);
            aircraftY[0] = rn.nextInt(5);
            horizontalPlacement(aircraftX, aircraftY, aircraft);
            overlap = true;
            //Extends the placement horizontally
        } //Vertically
        else if (vertOrHoriz == 1) {
            System.out.println("Vertical");
            aircraftX[0] = rn.nextInt(5);
            aircraftY[0] = rn.nextInt(10);
            verticalPlacement(aircraftX, aircraftY, aircraft);
            overlap = true;
        }

        while (overlap == true) {
            //Battleship locaiton
            vertOrHoriz = rn.nextInt(2);
            //Horizontally
            if (vertOrHoriz == 0) {
                System.out.println("Horizontical");
         //Ship can be on row 0 - 9, but only on 0 - 6 column 
                //So it does not extend out of the board
                battleshipX[0] = rn.nextInt(10);
                battleshipY[0] = rn.nextInt(7);
                System.out.println("b");

                //Extends the placement horizontally
                horizontalPlacement(battleshipX, battleshipY, battleship);

            } //Vertically
            else if (vertOrHoriz == 1) {
                System.out.println("Vertical");
                battleshipX[0] = rn.nextInt(7);
                battleshipY[0] = rn.nextInt(10);
                //Extends the placement vertically
                System.out.println("b");
                verticalPlacement(battleshipX, battleshipY, battleship);
            }
        }//Second ship
        overlap = true;

        while (overlap == true) {

            //Submarine location
            vertOrHoriz = rn.nextInt(2);
            //Horizontally
            if (vertOrHoriz == 0) {
                System.out.println("Horizontical");
         //Ship can be on row 0 - 9, but only on 0 - 7 column 
                //So it does not extend out of the board
                submarineX[0] = rn.nextInt(10);
                submarineY[0] = rn.nextInt(8);
                System.out.println("s");
                horizontalPlacement(submarineX, submarineY, submarine);

            } else if (vertOrHoriz == 1) {
                System.out.println("Vertical");
                submarineX[0] = rn.nextInt(8);
                submarineY[0] = rn.nextInt(10);
                System.out.println("s");
                //Extends the placement vertically
                verticalPlacement(submarineX, submarineY, submarine);

            }
        }
        overlap = true;

        //destroyer location
        while (overlap == true) {
            vertOrHoriz = rn.nextInt(2);
            //Horizontally
            if (vertOrHoriz == 0) {
                System.out.println("Horizontical");
         //Ship can be on row 0 - 9, but only on 0 - 7 column 
                //So it does not extend out of the board
                destroyerX[0] = rn.nextInt(10);
                destroyerY[0] = rn.nextInt(8);
                System.out.println("d");
                horizontalPlacement(destroyerX, destroyerY, destroyer);
            } else if (vertOrHoriz == 1) {
                System.out.println("Vertical");
                destroyerX[0] = rn.nextInt(8);
                destroyerY[0] = rn.nextInt(10);
                System.out.println("d");
                //Extends the placement vertically
                verticalPlacement(destroyerX, destroyerY, destroyer);

            }

        }
         //Patrol location

        overlap = true;
        while (overlap == true) {

            //Patrol location
            vertOrHoriz = rn.nextInt(2);
            //Horizontally
            if (vertOrHoriz == 0) {
                System.out.println("Horizontical");
         //Ship can be on row 0 - 9, but only on 0 - 7 column 
                //So it does not extend out of the board
                patrolX[0] = rn.nextInt(10);
                patrolY[0] = rn.nextInt(9);
                System.out.println("p");
                horizontalPlacement(patrolX, patrolY, patrol);

            } else if (vertOrHoriz == 1) {
                System.out.println("Vertical");
                patrolX[0] = rn.nextInt(9);
                patrolY[0] = rn.nextInt(10);
                System.out.println("p");
                //Extends the placement vertically
                verticalPlacement(patrolX, patrolY, patrol);

            }
        }
        overlap = true;

    }

    public static boolean isOverlap(int x, int y) {
        if (board[x][y] == '*') {
            return true;
        }
        return false;
    }

    public static void markShip() {

    }

    public static void moves() {
        moves--;
    }

    public static void difficulty() {

    }

    public static void printMoves() {
        c.println("aircraftHP: " + aircraftHP);
        c.println("battleshipHP: " + battleshipHP);
        c.println("SubmarineHP: " + submarineHP);
        c.println("destroyerHP: " + destroyerHP);
        c.println("patrolHP: " + patrolHP);
        c.print("  0 1 2 3 4 5 6 7 8 9\n");
        for (int i = 0; i < 10; i++) {
            c.print(i + " ");
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == 'x') {
                    c.print(board[i][j] + " ");
                } else if (board[i][j] == '*') {
                    c.print(board[i][j] + " ");
                } else {
                    c.print(board[i][j] = '-');
                    c.print(" ");
                }
            }
            c.println(" ");
        }
    }

    //Print the initial board
    public static void printBoard() {
        c.print("  0 1 2 3 4 5 6 7 8 9\n");

        for (int i = 0; i < 10; i++) {
            c.print(i + " ");
            for (int j = 0; j < 10; j++) {
                board[i][j] = '-';
                c.print(board[i][j]);
                c.print(" ");
            }
            c.println();

        }

    }

    //Gets the player to make a move and mark it

    public static void move() {
        int row, col;
        do {
            c.println("Please enter a new move: ");
            row = c.readInt();
            col = c.readInt();
        } while (isOccupied(row, col));
        //Check for hit 
        aircraftHP = hit(row, col, aircraftX, aircraftY, aircraftHP, aircraft);
        battleshipHP = hit(row, col, battleshipX, battleshipY, battleshipHP, battleship);
        submarineHP = hit(row, col, submarineX, submarineY, submarineHP, submarine);
        destroyerHP = hit(row, col, destroyerX, destroyerY, destroyerHP, destroyer);
        patrolHP = hit(row, col, patrolX, patrolY, patrolHP, patrol);

        board[row][col] = 'x';

        c.clear();
        printMoves();
        moves();
    }

    public static int hit(int row, int col, int[] x, int[] y, int shipHP, int ship) {
        for (int i = 0; i < ship; i++) {
            if (x[i] == row && y[i] == col) {

                System.out.println(ship + " is hit");
                shipHP--;

                // }
            }

        }
        return shipHP;

    }

    public static boolean win() {
        if ((submarineHP == 0) && (battleshipHP == 0) && (aircraftHP == 0) && (destroyerHP == 0) && (patrolHP == 0)) {
            return true;
        }
        return false;

    }

    //Check if the user already took the spot
    public static boolean isOccupied(int row, int col) {
        if (board[row][col] == 'x') {
            return true;
        }
        return false;

    }

}
