/**
 * ConnectFour.java
 * 
 * This program implements a simplified Connect Four game.
 * Players take turns dropping discs into a grid, aiming to connect four in a row.
 * 
 * Constants:
 * - ROWS, COLS: Size of the game board.
 * - EMPTY: Symbol for an empty cell.
 * 
 * Main Method:
 * - Initiates and displays the game board.
 * - Alternates player turns, prompting disc placement.
 * - Checks for wins or draws after each move.
 * - Ends the game and declares the winner or draw.
 * 
 * initBoard Method: Initializes the game board.
 * 
 * printBoard Method: Displays the current game board.
 * 
 * getColumn Method: Takes user input for disc placement column.
 * 
 * dropDisk Method: Places a disc in the chosen column.
 * 
 * isBoardFull Method: Checks if the board is full (draw).
 * 
 * isWinningMove Method: Checks if the last move is a winning combination.
 * 
 * checkHorizontal, checkVertical, checkDiagonal1, checkDiagonal2 Methods:
 * Helper methods for isWinningMove, checking different directions.
 */
import java.util.Scanner;

/**
 * The ConnectFour class represents a simple implementation of the Connect Four game.
 */
public class ConnectFour {

    // Constants for the board size and symbols
    public static final int ROWS = 6;
    public static final int COLS = 7;
    public static final char EMPTY = '|';

    // 2D array to represent the game board
    public static char[][] board = new char[ROWS][COLS];

    // Scanner for user input
    public static Scanner input = new Scanner(System.in);

    /**
     * The main method that initializes the game, handles player turns, and checks for a winner.
     */
    public static void main(String[] args) {
        // Initialize the game board
        initBoard();
        // Display the initial board
        printBoard();

        // Game loop
        boolean playing = true;
        char currentPlayer = 'R'; // Red player starts

        while (playing) {
            // Get the column where the player wants to drop a disk
            int column = getColumn(currentPlayer);
            // Drop the disk and get the row where it lands
            int row = dropDisk(currentPlayer, column);
            // Display the updated board
            printBoard();

            // Check if the current player has won
            if (isWinningMove(currentPlayer, row, column)) {
                // Print the winning message based on the current player
                if (currentPlayer == 'R') {
                    System.out.println("The red player won");
                } else {
                    System.out.println("The yellow player won");
                }
                playing = false; // End the game loop
            } else if (isBoardFull()) {
                System.out.println("It's a draw!"); // Board is full, it's a draw
                playing = false; // End the game loop
            } else if (currentPlayer == 'R') {
                currentPlayer = 'Y';
            } else {
                currentPlayer = 'R';
            }

        }

        // Close the scanner
        input.close();
    }

    /**
     * Initializes the game board with empty cells.
     */
    public static void initBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = EMPTY;
            }
        }
    }

    /**
     * Prints the current state of the game board.
     */
    public static void printBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                // Print either an empty cell or the player's disk
                if (board[i][j] == EMPTY) {
                    System.out.printf("|%3s", " "); // Use printf to ensure fixed width
                } else {
                    System.out.printf("|%3s", " " + board[i][j] + " ");
                }
            }
            System.out.println("|"); // End the row with a separator
        }
        for (int i = 0; i < COLS; i++) {
            System.out.print("----"); // Print the bottom border
        }
        System.out.println("-"); // End the bottom border with a separator
    }
    
    
    /**
     * Gets the column where the player wants to drop a disk.
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @return The chosen column.
     */
    public static int getColumn(char player) {
        int column = -1;
        boolean valid = false;

        while (true) {
            if (player == 'R') {
                System.out.print("Drop a red disk at column (0-6): ");
            } else {
                System.out.print("Drop a yellow disk at column (0-6): ");
            }
            try {
                column = Integer.parseInt(input.nextLine());
                if (column >= 0 && column < COLS && board[0][column] == EMPTY) {
                    break; // exit the loop if the input is valid
                } else {
                    System.out.println("Invalid column. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Try again.");
            }
        }

        return column;
    }

    /**
     * Drops a disk of the given player in the specified column and returns the row.
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @parameter column The chosen column to drop the disk.
     * @return The row where the disk lands.
     */
    public static int dropDisk(char player, int column) {
        for (int i = ROWS - 1; i >= 0; i--) {
            if (board[i][column] == EMPTY) {
                board[i][column] = player;
                return i;
            }
        }
        return -1; // This should not happen
    }

    /**
     * Checks if the board is full.
     * @return True if the board is full, false otherwise.
     */
    public static boolean isBoardFull() {
        for (int j = 0; j < COLS; j++) {
            if (board[0][j] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the last move by the given player is a winning move.
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @parameter row The row where the last disk was placed.
     * @parameter column The column where the last disk was placed.
     * @return True if the move is a winning move, false otherwise.
     */
    public static boolean isWinningMove(char player, int row, int column) {
        return checkHorizontal(player, row, column) ||
                checkVertical(player, row, column) ||
                checkDiagonal1(player, row, column) ||
                checkDiagonal2(player, row, column);
    }

    /**
     * Checks if the last move forms a horizontal line of four.
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @parameter row The row where the last disk was placed.
     * @parameter column The column where the last disk was placed.
     * @return True if a horizontal line of four is formed, false otherwise.
     */
    public static boolean checkHorizontal(char player, int row, int column) {
        int count = 1;
        for (int j = column - 1; j >= 0 && board[row][j] == player; j--) {
            count++;
        }
        for (int j = column + 1; j < COLS && board[row][j] == player; j++) {
            count++;
        }
        return count >= 4;
    }

    /**
     * Checks if the last move forms a vertical line of four.
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @parameter row The row where the last disk was placed.
     * @parameter column The column where the last disk was placed.
     * @return True if a vertical line of four is formed, false otherwise.
     */
    public static boolean checkVertical(char player, int row, int column) {
        int count = 1;
        for (int i = row + 1; i < ROWS && board[i][column] == player; i++) {
            count++;
        }
        return count >= 4;
    }

    /**
     * Checks if the last move forms a diagonal line of four (bottom-left to top-right).
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @parameter row The row where the last disk was placed.
     * @parameter column The column where the last disk was placed.
     * @return True if a diagonal line of four is formed, false otherwise.
     */
    public static boolean checkDiagonal1(char player, int row, int column) {
        int count = 1;
        for (int i = row + 1, j = column - 1; i < ROWS && j >= 0 && board[i][j] == player; i++, j--) {
            count++;
        }
        for (int i = row - 1, j = column + 1; i >= 0 && j < COLS && board[i][j] == player; i--, j++) {
            count++;
        }
        return count >= 4;
    }

    /**
     * Checks if the last move forms a diagonal line of four (top-left to bottom-right).
     * @parameter player The current player ('R' for red, 'Y' for yellow).
     * @parameter row The row where the last disk was placed.
     * @parameter column The column where the last disk was placed.
     * @return True if a diagonal line of four is formed, false otherwise.
     */
    public static boolean checkDiagonal2(char player, int row, int column) {
        int count = 1;
        for (int i = row - 1, j = column - 1; i >= 0 && j >= 0 && board[i][j] == player; i--, j--) {
            count++;
        }
        for (int i = row + 1, j = column + 1; i < ROWS && j < COLS && board[i][j] == player; i++, j++) {
            count++;
        }
        return count >= 4;
    }
}
