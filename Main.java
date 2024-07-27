package SnakesAndLadders;

import SnakesAndLadders.Game.Game;
import SnakesAndLadders.components.Ladder;
import SnakesAndLadders.components.Player;
import SnakesAndLadders.components.Snake;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int env = -1;
        while (env < 0 ||  env>1) {
            System.out.println("Enter Environment (0 for Test & 1 for Production)");
            env = scanner.nextInt();
            if (env < 0 || env>1) {
                System.out.println("Enter valid values again.");
            }
        }

        int size = 0;
        while (size < 1) {
            System.out.println("Enter board size (minimum 1): ");
            size = scanner.nextInt();
            if (size < 1) {
                System.out.println("The board size must be at least 1. Please enter a valid size.");
            }
        }

        int numberOfDice = 0;
        while (numberOfDice < 1) {
            System.out.println("Enter number of dice (minimum 1): ");
            numberOfDice = scanner.nextInt();
            if (numberOfDice < 1) {
                System.out.println("The number of dice must be at least 1. Please enter a valid number.");
            }
        }

        Game game = new Game(size, numberOfDice,env);


        System.out.println("Enter number of snakes: ");
        int numberOfSnakes = scanner.nextInt();
        for (int i = 0; i < numberOfSnakes; i++) {
            System.out.println("Enter head and tail for snake " + (i + 1) + ": ");
            int head = scanner.nextInt();
            int tail = scanner.nextInt();
            try {
                game.addComponent(new Snake(head, tail));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Please try again.");
                i--;
            }
        }

        System.out.println("Enter number of ladders: ");
        int numberOfLadders = scanner.nextInt();
        for (int i = 0; i < numberOfLadders; i++) {
            System.out.println("Enter bottom and top for ladder " + (i + 1) + ": ");
            int bottom = scanner.nextInt();
            int top = scanner.nextInt();
            try {
                game.addComponent(new Ladder(bottom, top));
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Please try again.");
                i--;
            }
        }

        int numberOfPlayers = 0;
        while (numberOfPlayers < 2) {
            System.out.println("Enter number of players (minimum 2): ");
            numberOfPlayers = scanner.nextInt();
            if (numberOfPlayers < 2) {
                System.out.println("The game requires at least 2 players. Please enter a valid number.");
            }
        }

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.println("Enter name for player " + i + ": ");
            String name = scanner.next();
            game.addPlayer(new Player(i, name));
        }

        game.play();
    }
}



