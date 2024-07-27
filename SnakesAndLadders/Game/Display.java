package SnakesAndLadders.Game;

import SnakesAndLadders.components.Player;

public class Display {
    public static void displayPlayerPosition(Player player) {
        System.out.println(player.getName() + " is at position " + player.getPosition());
    }

    public static void displayWinner(Player player) {
        System.out.println(player.getName() + " wins the game");
    }

    public static void hasNotStarted(Player player) {
        System.out.println(player.getName() + " game has not started yet");
    }

    public static void displayDiceValue(int value) {
        System.out.println("Dice get rolled "+ value);
    }

}
