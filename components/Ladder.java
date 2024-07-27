package SnakesAndLadders.components;

public class Ladder implements BoardComponent {
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