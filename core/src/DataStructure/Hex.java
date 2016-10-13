package DataStructure;

import Players.AbstractPlayer;

import java.util.EnumSet;

public class Hex{

    public enum Border {
        EAST, WEST, SOUTH, NORTH
        //public EnumSet<Border> ALL = EnumSet.allOf(Border.class);
    }

    private AbstractPlayer owner;
    private EnumSet<Border> borders;

    public Hex(){
        this.owner = null;
        this.borders = EnumSet.noneOf(Hex.Border.class);
    }

    public void setOwner(AbstractPlayer owner) {
        this.owner = owner;
    }

    public AbstractPlayer getOwner() {
        return this.owner;
    }

    public void setBorder(EnumSet<Border> borders) {
        this.borders = borders;
    }

    public EnumSet<Border> getBorders() {
        return this.borders;
    }

    @Override
    public String toString() {
        return "[" + borders + ", Owner: " + owner + "]";
    }
}