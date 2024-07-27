1-Code is written in JAVA

2- Design Pattern Used => Factory Design Pattern , Singleton Pattern

3-UML Diagram Link-- https://lucid.app/lucidchart/a9d7dd94-7040-497e-857f-ece6ebcc57fb/edit?viewport_loc=-3028%2C-1167%2C3354%2C1572%2C0_0&invitationId=inv_249ce6e3-3b60-40ec-90bc-0ef44b8d0cc3

4- Initially it will ask for 2 inputs - press 1 for PRODUCTION environment(where dice rolls are randomized and game gets automatically executed) or press 0 for TEST environment(where dice rolls values are given manual inputs )

5- It has option to select multiple dice- in case of more than one dice player game will start when it gets 6 in all the dices

6-Run Main.java

7- If there is any issue with running code on local IDE , you can run code on this online compiler(https://www.programiz.com/java-programming/online-compiler/), just copy  paste the below code(compressed the code into one file) and run it

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Random;
import java.util.Scanner;

interface BoardComponent {
    int getStart();
    int getEnd();
    ComponentType getType();
    boolean isValidPosition(int position, int boardSize);

}

enum ComponentType {
    Snake,
    Ladder,
}

class Snake implements BoardComponent {
    private int head;
    private int tail;

    public Snake(int head, int tail) {
        this.head = head;
        this.tail = tail;
    }

    @Override
    public int getStart() {
        return head;
    }

    @Override
    public int getEnd() {
        return tail;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.Snake;
    }

    @Override
    public boolean isValidPosition(int position, int boardSize) {
        return position > 1 && position < boardSize;
    }
}

class Ladder implements BoardComponent {
    private int bottom;
    private int top;

    public Ladder(int bottom, int top) {
        this.bottom = bottom;
        this.top = top;
    }

    @Override
    public int getStart() {
        return bottom;
    }

    @Override
    public int getEnd() {
        return top;
    }

    @Override
    public ComponentType getType() {
        return ComponentType.Ladder;
    }

    @Override
    public boolean isValidPosition(int position, int boardSize) {
        return position > 0 && position <= boardSize;
    }
}

class Player {


    private int ID;
    private String name;
    private int position;
    private boolean hasStarted;


    public Player(int ID, String name)
    {
        this.ID=ID;
        this.name=name;
        this.position=0;
        this.hasStarted=false;
    }

    public String getName()
    {
        return this.name;
    }

    public int getPosition()
    {
        return this.position;
    }

    public void setPosition(int position)
    {
         this.position=position;
    }

    public boolean hasStarted() {
        return hasStarted;
    }

    public void start() {
        this.hasStarted = true;
    }
}


class Dice {
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




class Game {
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

class Board {

    public static Board instance;
    private int size;
    private Map<ComponentType, Map<Integer, Integer>> components;

    private Board(int size)
    {
        this.size=size;
        this.components = new HashMap<>();
        for (ComponentType type : ComponentType.values()) {
            components.put(type, new HashMap<>());
        }
    }

    public static Board getInstance(int size)
    {
        if(instance==null)
        {
            instance=new Board(size);
        }
        return instance;
    }


    public void addComponent(BoardComponent component) {
        int start = component.getStart();
        int end = component.getEnd();
        ComponentType type = component.getType();

        if (!component.isValidPosition(start, this.size) || !component.isValidPosition(end,this.size)) {
            throw new IllegalArgumentException("Invalid component position: start and end should be valid values.");
        }

        for (Map<Integer, Integer> map : components.values()) {
            if (map.containsKey(start)) {
                throw new IllegalArgumentException("There is already a Component with start at position " + start);
            }
        }

        for (Map.Entry<ComponentType, Map<Integer, Integer>> entry : components.entrySet()) {
            if (entry.getKey() != type) {
                Map<Integer, Integer> map = entry.getValue();
                for (Map.Entry<Integer, Integer> componentEntry : map.entrySet()) {
                    int otherStart = componentEntry.getKey();
                    int otherEnd = componentEntry.getValue();

                    if (start == otherEnd && end == otherStart) {
                        throw new IllegalArgumentException("This component would form an infinite loop with an existing component.");
                    }
                }
            }
        }

        components.get(type).put(start, end);
    }


    public int checkComponent(int position) {
        for (Map<Integer, Integer> map : components.values()) {
            if (map.containsKey(position)) {
                return map.get(position);
            }
        }
        return position;
    }

    public int getSize()
    {
        return size;
    }
}

class Display {
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
