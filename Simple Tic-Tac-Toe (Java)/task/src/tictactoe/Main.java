package tictactoe;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        GameMachine gameMachine = new GameMachine();

        while (!gameMachine.getGamestate().equals(GameState.DONE)) {
            gameMachine.takeSingleInput(scanner.nextLine());
        }

    }
}


