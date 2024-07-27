package SnakesAndLadders.components;

public class Player {


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
