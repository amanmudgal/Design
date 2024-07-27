package SnakesAndLadders.components;

public interface BoardComponent {
    int getStart();
    int getEnd();
    ComponentType getType();
    boolean isValidPosition(int position, int boardSize);

}

