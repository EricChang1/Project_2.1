package com.mygdx.game;

import Players.AbstractPlayer;
import DataStructure.Board;
import DataStructure.Hex;
import DataStructure.Hex.Border;
import DataStructure.Vector3;
import Helper.Layout;
import Helper.Point;

import java.util.ArrayList;
import java.util.EnumSet;

public class GameController {

    public AbstractPlayer currentPlayer;
    public AbstractPlayer otherPlayer;
    private int turnCounter; //Used for swap rule
    public boolean gameOver;

    private Board board;
    private Layout layout;
    private ArrayList<Vector3> visited;

    public GameController(Layout layout, Board board, int tCounter) {

        this.layout = layout;
        this.board = board;
        turnCounter = tCounter;
    }

    public void click(int x, int y) {
        if(!gameOver) {
            Vector3 coord = layout.pixelToVector3(new Point(x, y));
            Hex h = board.map.get(coord);

            if (h != null && h.getOwner() != null && turnCounter == 1) {
                h.setOwner(currentPlayer);

                if (checkEnd(coord)) {
                    System.out.println(currentPlayer + " WINS!");
                    gameOver = true;
                    currentPlayer = null;
                }

                AbstractPlayer temp = currentPlayer;
                currentPlayer = otherPlayer;
                otherPlayer = temp;
                turnCounter++;
            } else if (h != null && h.getOwner() == null) {
                turnCounter++;

                h.setOwner(currentPlayer);

            /*
            board.getNeighbours(coord).forEach(c -> {
                board.map.get(c).setOwner(currentPlayer);
                System.out.println(c);
            });
            */

                if (checkEnd(coord)) {
                    System.out.println(currentPlayer + " WINS!");
                    gameOver = true;
                }

                AbstractPlayer temp = currentPlayer;
                currentPlayer = otherPlayer;
                otherPlayer = temp;
            }
        }
    }

    private boolean checkEnd(Vector3 coord) {

        EnumSet<Border> toFind = currentPlayer.borders.clone();
        this.visited = new ArrayList<Vector3>();
        dfs(coord, toFind);
        return toFind.isEmpty();
    }

    private void dfs(Vector3 coord, EnumSet<Border> borders) {

        this.visited.add(coord);
        borders.removeIf(b -> board.map.get(coord).getBorders().contains(b));

        for (Vector3 dir: board.getNeighbours(coord)) {
            AbstractPlayer p = board.map.get(dir).getOwner();
            if (p != null && p.equals(currentPlayer) && !visited.contains(dir))
                dfs(dir, borders);
        }
    }
}
