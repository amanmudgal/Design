package SnakesAndLadders.Game;

import SnakesAndLadders.components.BoardComponent;
import SnakesAndLadders.components.ComponentType;

import java.util.HashMap;
import java.util.Map;

public class Board {

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

        if (!component.isValidPosition(this.size) ) {
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
