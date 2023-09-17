package tictactoe;

import static tictactoe.GameState.*;

public class GameMachine {

    private GameState gamestate;


    private boolean correctInput = false;
    private boolean correctSingleInput = false;
    private final int[] gridCoordinates;
    private final char[][] inputPattern;

    private char currentPlayer;

    public GameMachine() {
        this.inputPattern = new char[3][3];
        this.gridCoordinates = new int[2];
        this.gamestate = NOT_FINISHED;
        this.currentPlayer = 'X';

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inputPattern[i][j] = '_';
            }
        }
        printArray();
//        System.out.print("Enter a 9-character string for the Tic Tac Toe board:");
    }

    public boolean isCorrectInput() {
        return correctInput;
    }

    public boolean isCorrectSingleInput() {
        return correctSingleInput;
    }

    public GameState getGamestate() {return gamestate;}

    public void reportGameResult() {

        switch (gamestate) {
            case NOT_FINISHED:
                // Maybe prompt the next player for a move
                /*System.out.print("Game not finished");*/
                break;
            case DRAW:
                System.out.print("Draw");
                gamestate = DONE;
                // Handle draw game logic
                break;
            case X_WINS:
                System.out.print("X wins");
                // Handle post-game actions for X win
                gamestate = DONE;
                break;
            case O_WINS:
                System.out.print("O wins");
                // Handle post-game actions for O win
                gamestate = DONE;
                break;
            case IMPOSSIBLE:
                System.out.print("Impossible");
                // Handle this state as needed
                gamestate = DONE;
                break;

        }

    }

    /*public void receiveInput(String input) {


        if (input.length() != 9) {
            System.out.println("Invalid input! Please enter exactly 9 characters.");
            return;
        }

        // Check for valid characters
        for (char c : input.toCharArray()) {
            if (c != 'X' && c != 'O' && c != '_') {
                System.out.println("Invalid characters! Only 'X', 'O', or '_' are allowed.");
                correctInput = false;
                return;
            }
        }

        correctInput = true;

        int inputIndex = 0; //used to keep track of input

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inputPattern[i][j] = input.charAt(inputIndex);
                inputIndex++;
            }
        }

    }*/

    public void printArray() {
        System.out.println("---------");

        // Displaying the Tic Tac Toe board
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (j == 0) {
                    System.out.print("| ");
                }
                System.out.print(inputPattern[i][j] + " ");

                if (j == 2) {
                    System.out.println("|");
                }
            }
//            System.out.println(); // Move to the next line after printing each row
        }

        System.out.println("---------");
    }


    public void analyzeGame() {
        int xCount = 0;
        int oCount = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (inputPattern[i][j] == 'X') {
                    xCount++;

                } else if (inputPattern[i][j] == 'O') {
                    oCount++;
                }
            }
        }

        boolean xWins = checkWinner(inputPattern, 'X');
        boolean oWins = checkWinner(inputPattern, 'O');

        if (Math.abs(xCount - oCount) >= 2 || (xWins && oWins)) {
            gamestate = IMPOSSIBLE;
        } else if (xWins) {
            gamestate = X_WINS;
        } else if (oWins) {
            gamestate = O_WINS;
        } else if (xCount + oCount == 9) {
            gamestate = DRAW;

        } else {
            gamestate = NOT_FINISHED;
        }

        reportGameResult();
    }

    private boolean checkWinner(char[][] inputPattern, char player) {

        // Check all rows
        for (int i = 0; i < 3; i++) {
            if (inputPattern[i][0] == player && inputPattern[i][1] == player && inputPattern[i][2] == player) {
                return true;  // This means that the player has all of his symbols in one of the rows.
            }

            // Check all columns
            if (inputPattern[0][i] == player && inputPattern[1][i] == player && inputPattern[2][i] == player) {
                return true;  // This means that the player has all of his symbols in one of the columns.
            }
        }

        // Check the diagonal from top-left to bottom-right
        if (inputPattern[0][0] == player && inputPattern[1][1] == player && inputPattern[2][2] == player) {
            return true;  // Player has all of his symbols in this diagonal.
        }

        // Check the diagonal from top-right to bottom-left
        return inputPattern[0][2] == player && inputPattern[1][1] == player && inputPattern[2][0] == player;
        // If the player has all of his symbols in this diagonal, true will be returned, otherwise false.
    }

    public void takeSingleInput(String input) {
        String[] parts = input.split(" ");


        if (!(isNumeric(parts[0]) && isNumeric(parts[1]))) {
            System.out.println("You should enter numbers!");
            return;
        } else if (!((isValidCoordinate(parts[0]) && isValidCoordinate(parts[1])))) {
            System.out.println("Coordinates should be from 1 to 3!");
            return;

        }

        // Adjust the user-provided coordinates to match the 0-based index pattern
        gridCoordinates[0] = Integer.parseInt(parts[0]) - 1;
        gridCoordinates[1] = Integer.parseInt(parts[1]) - 1;

        if (!cellOccupancyCheck()) {
            System.out.println("This cell is occupied! Choose another one!");
            return;
        } else {

            inputPattern[gridCoordinates[0]][gridCoordinates[1]] = currentPlayer; // Assuming 'X' is always the next move. Adjust as per your game logic.
        }
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        printArray();
        analyzeGame();
        /*correctSingleInput = true;*/


        //replace the value in inputPattern from grid coordinates with 'X'

    }




    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

    private boolean isValidCoordinate(String str) {
        try {
            int value = Integer.parseInt(str);
            return value >= 1 && value <= 3;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean cellOccupancyCheck() {
        return inputPattern[gridCoordinates[0]][gridCoordinates[1]] == '_';
    }

}


