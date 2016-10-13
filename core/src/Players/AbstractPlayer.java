package Players;

import DataStructure.Hex.Border;
import java.util.EnumSet;

public abstract class AbstractPlayer {

    public enum Side {
        RED, BLUE
    }

    public Side side;
    public EnumSet<Border> borders;


    public AbstractPlayer(String side) {
        if (side.equals("RED")) {
            this.side = Side.RED;
            this.borders = EnumSet.of(Border.EAST, Border.WEST);
        }
        else {
            this.side = Side.BLUE;
            this.borders = EnumSet.of(Border.SOUTH, Border.NORTH);
        }
    }

    public void makeTurn() {
        // MAKE GAMECONTROLLER WAIT FOR THE CORRECT PLAYER TO MOVE
    }

    @Override
    public int hashCode() {
        return side == Side.RED ? 1 : 0;
    }

    @Override
    public String toString() {
        return super.toString() + " " + side;
    }
}