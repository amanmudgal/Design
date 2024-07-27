package SnakesAndLadders.Game;

import SnakesAndLadders.components.*;


import java.util.ArrayDeque;
import java.util.Deque;

public class Game {
    private Board board;
    private Deque<Player> players;
    private Dice dice;
    private boolean isOver;
    private int env;

    public Game(int size, int numberOfDice,int env) {
        this.board = Board.getInstance(size);
        this.players = new ArrayDeque<>();
        this.dice = new Dice(numberOfDice);
        this.isOver = false;
        this.env=env;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }


    public void addComponent(BoardComponent component) {
        board.addComponent(component);
    }

    public void play() {
        while (!isOver) {
            Player currentPlayer = players.poll();
            int roll = 0;
            if(env==0)
            {
                roll = dice.rollDiceTest(currentPlayer);
            }
            else if(env==1)
            {
                roll = dice.roll(currentPlayer);
            }
            int newPosition=0;

            if (!currentPlayer.hasStarted()) {
                if (roll == dice.getNumberOfDice()*6) {
                    currentPlayer.start();
                    currentPlayer.setPosition(1);
                    newPosition=1;
                } else {
                    players.add(currentPlayer);
                    Display.hasNotStarted(currentPlayer);
                    continue;
                }
            } else {
                newPosition = currentPlayer.getPosition() + roll;
            }
                if (newPosition > board.getSize()) {
                    newPosition = currentPlayer.getPosition();
                } else {
                    newPosition = board.checkComponent(newPosition);
                    int finalPosition = board.checkComponent(newPosition);
                    while (finalPosition != newPosition) {
                        newPosition = finalPosition;
                        finalPosition = board.checkComponent(newPosition);
                    }
                }
                currentPlayer.setPosition(newPosition);
                Display.displayPlayerPosition(currentPlayer);
                if (newPosition == board.getSize()) {
                    isOver = true;
                    Display.displayWinner(currentPlayer);
                    break;
                }
            players.add(currentPlayer);
        }
    }
}
