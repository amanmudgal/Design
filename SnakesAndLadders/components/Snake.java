package SnakesAndLadders.components;

public class Snake implements BoardComponent {
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
    public boolean isValidPosition(int boardSize) {
        return this.getStart()>this.getEnd() && this.getEnd() > 1 && this.getEnd() < boardSize && this.getStart() > 1 && this.getStart() < boardSize ;
    }
}

