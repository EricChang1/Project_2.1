package com.mygdx.game;

import DataStructure.Board;
import DataStructure.Hex;
import DataStructure.Vector3;
import Helper.Layout;
import Helper.Point;
import Players.AbstractPlayer.Side;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BoardView extends JPanel {

    public static Layout layout;
    private Board board;

    public BoardView(Board board) {

        Point hexSize = new Point(30, 30);

        this.layout = new Layout(hexSize, new Point(hexSize.x*2, hexSize.y*2));
        this.board = board;
    }

    public void setListener(GameController controller) {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                controller.click(e.getX(), e.getY());
            }
        });

    }

    private void drawHex(Vector3 coord, Hex hex, Graphics g) {

        ArrayList<Point> corners = layout.polygonCorners(coord);

        int[] X = new int[corners.size()];
        int[] Y = new int[corners.size()];

        for (int i = 0; i < corners.size(); i++) {
            X[i] = (int) corners.get(i).x;
            Y[i] = (int) corners.get(i).y;
        }

        //g.setColor(Color.WHITE);
        g.setColor(Color.BLACK);
        g.drawPolygon(X, Y, corners.size());

        if (hex.getOwner() == null)
            return;

        if (hex.getOwner().side == Side.RED) {
            g.setColor((Color.RED));
            g.fillPolygon(X, Y, corners.size());
        }
        else if (hex.getOwner().side == Side.BLUE) {
            g.setColor((Color.BLUE));
            g.fillPolygon(X, Y, corners.size());
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawMap(g);
    }

    private void drawMap(Graphics g) {
        board.map.forEach((coord, hex) -> drawHex(coord, hex, g));
        repaint();
    }
}
