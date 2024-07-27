package SnakesAndLadders.components;

import SnakesAndLadders.Game.Display;

import java.util.Random;
import java.util.Scanner;

public class Dice {
    private int numberOfDice;

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    public int getNumberOfDice(){
        return this.numberOfDice;
    }

    public int roll(Player currentPlayer) {
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            total += random.nextInt(6) + 1;
        }
        Display.displayDiceValue(total);
        return total;
    }

    public int rollDiceTest(Player currentPlayer) {
        Scanner scanner = new Scanner(System.in);
        int total = 0;
        for (int i = 0; i < numberOfDice; i++) {
            int roll;
            do {
                System.out.println("Enter a dice roll value between 1 and 6 for dice " + (i + 1) + " for "+currentPlayer.getName() +" :");
                roll = scanner.nextInt();
            } while (roll < 1 || roll > 6);
            total += roll;
        }
        Display.displayDiceValue(total);
        return total;
    }
}

