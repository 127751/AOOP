import Controller.*;
import Model.*;

import java.util.Scanner;


public class CLIApp {
    /**
     * Main execution method for the Numberle CLI application.
     * Initializes the game model, takes user input from the command line, and processes guesses until the game ends.
     *
     * @param args command line arguments (not used)
     * @requires a functioning Numberle model and a command line input mechanism
     * @ensures the game continues until the user correctly guesses the equation or runs out of attempts
     * @ensures game state messages are printed to the console
     */
    public static void main(String[] args) {
        INumberleModel model = new NumberleModel();

        try (Scanner scanner = new Scanner(System.in)) {
            model.startNewGame();
            System.out.println("Welcome to Numberle - CLI Version");
            System.out.println("You have " + model.getRemainingAttempts() + " attempts to guess the right equation. Good luck!");

            while (!model.isGameOver()) {
                System.out.println("\nEnter your guess (7 characters long equation): ");
                String input = scanner.nextLine();

                model.processInput(input);

                if (model.isGameOver()) {
                    if (model.isGameWon()) {
                        System.out.println("Congratulations! You've guessed the equation correctly.");
                    } else {
                        System.out.println("Game Over! The correct equation was: " + model.getTargetNumber());
                    }
                } else {
                    System.out.println("Try again. You have " + model.getRemainingAttempts() + " attempts left.");
                }
            }
        }
    }
}
