package com.mygdx.game.desktop;

import DataStructure.Board;
import Players.HumanPlayer;
import com.badlogic.gdx.backends.lwjgl.LwjglAWTCanvas;
import com.mygdx.game.BoardView;
import com.mygdx.game.GameController;
import com.mygdx.game.HexGame;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class DesktopLauncher extends JFrame{
	public static DesktopLauncher dkl;
	private LwjglAWTCanvas canvas;
	private JPanel opt, screen, top, RB1, RB2, BB1, BB2;
	boolean paused, init;
	private int boardW, boardH;

	public DesktopLauncher() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		canvas = new LwjglAWTCanvas(new HexGame());
		canvas.getCanvas().setSize(1280, 720);
		add(canvas.getCanvas());
		pack();
		setVisible(true);

		//Key listener
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(new MyDispatcher());

		//Open frame in center of screen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	private class MyDispatcher implements KeyEventDispatcher {
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.getID() == KeyEvent.KEY_PRESSED && e.getModifiers() == 0) {
				if(!paused && init){
					paused = true;
					remove(top);
					canvas = new LwjglAWTCanvas(new HexGame());
					canvas.getCanvas().setSize(1280, 720);
					add(canvas.getCanvas());
					pack();
				}else if(paused && init){
					paused = false;
					remove(canvas.getCanvas());
					add(top);
					pack();
				}
			}
			return false;
		}
	}

	public void rem(final int i,final int j){
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				boardW = i;
				boardH = j;
				remove(canvas.getCanvas());
				Board board = new Board(i,j);
				BoardView boardView =  new BoardView(board);

				GameController gameController = new GameController(boardView.layout, board, 2);
				gameController.currentPlayer = new HumanPlayer("RED");
				gameController.otherPlayer= new HumanPlayer("BLUE");
				boardView.setListener(gameController);
				createPanel();

				screen = new JPanel();
				screen.setLayout(new BorderLayout());
				screen.add(boardView, BorderLayout.CENTER);
				screen.setBorder(new EmptyBorder(280-i*20, 510-j*40 , 0 , 0));

				RB1 = new JPanel();
				RB1.setSize(10,720);
				RB1.setBackground(Color.RED);
				RB2 = new JPanel();
				RB2.setSize(10,720);
				RB2.setBackground(Color.RED);
				BB1 = new JPanel();
				BB1.setSize(1280,10);
				BB1.setBackground(Color.BLUE);
				BB2 = new JPanel();
				BB2.setSize(1280,10);
				BB2.setBackground(Color.BLUE);

				JPanel center = new JPanel();
				center.setLayout(new BorderLayout());
				center.add(RB1, BorderLayout.WEST);
				center.add(RB2, BorderLayout.EAST);
				center.add(BB1, BorderLayout.NORTH);
				center.add(BB2, BorderLayout.SOUTH);
				center.add(screen, BorderLayout.CENTER);

				top = new JPanel();
				top.setPreferredSize(new Dimension(1280, 720));
				top.setLayout(new BorderLayout());
				//top.add(screen, BorderLayout.CENTER);
				top.add(center, BorderLayout.CENTER);
				top.add(opt, BorderLayout.EAST);
				System.out.println(opt.getSize());
				top.setDoubleBuffered(true);
				add(top);
				pack();
				init = true;
			}
		});
	}
	public static void main (String[] arg) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dkl = new DesktopLauncher();
			}
		});
	}


	public void createPanel(){
		opt = new JPanel(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0.5;

		JLabel top = new JLabel();
		gc.weighty = 10;
		gc.gridx = 0;
		gc.gridy = 0;
		opt.add(top, gc);

		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 1;


		//Enable or disable swap rule
		ButtonGroup group1 = new ButtonGroup();
		JRadioButton yButton = new JRadioButton("Enable       ");
		JRadioButton nButton = new JRadioButton("Disable       ");
		nButton.setSelected(true);
		group1.add(yButton);
		group1.add(nButton);
		JPanel sub1 = new JPanel();
		sub1.add(yButton);sub1.add(nButton);
		sub1.setBorder(new TitledBorder(new EtchedBorder(), "Swap Rule"));
		gc.anchor = GridBagConstraints.LINE_START;
		opt.add(sub1, gc);

		//Choose who begins
		ButtonGroup group2 = new ButtonGroup();
		final JRadioButton rButton = new JRadioButton("Red starts");
		rButton.setSelected(true);

		final JRadioButton bButton = new JRadioButton("Blue starts");
		group2.add(rButton);
		group2.add(bButton);

		JPanel sub2 = new JPanel();
		sub2.add(rButton);sub2.add(bButton);
		sub2.setBorder(new TitledBorder(new EtchedBorder(), "Turns"));
		gc.gridy = 2;
		opt.add(sub2, gc);
		gc.anchor = GridBagConstraints.CENTER;

		//Apply button
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i,j;
				if(bButton.isSelected()){
					i=1;
				}else{
					i=0;
				}
				if(yButton.isSelected()){
					j=1;
				}else{
					j=0;
				}

				remakePanel(i, j);
			}
		});
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 3;
		opt.add(applyButton, gc);

		JLabel bottom = new JLabel();
		gc.gridy = 4;
		gc.weighty = 10;
		opt.add(bottom, gc);
		opt.setBorder(new TitledBorder(new EtchedBorder(), "Game Options", TitledBorder.CENTER, TitledBorder.CENTER));
	}

	public void remakePanel(int player, int gController){
		remove(top);
		Board board = new Board(boardW,boardH);
		BoardView boardView =  new BoardView(board);

		if(player==1 && gController == 0){
			GameController gameController = new GameController(boardView.layout, board, 2);
			gameController.currentPlayer = new HumanPlayer("BLUE");
			gameController.otherPlayer = new HumanPlayer("RED");
			boardView.setListener(gameController);
		}else if(player==0 && gController == 0){
			GameController gameController = new GameController(boardView.layout, board, 2);
			gameController.currentPlayer = new HumanPlayer("RED");
			gameController.otherPlayer = new HumanPlayer("BLUE");
			boardView.setListener(gameController);
		}else if (player==1 && gController == 1) {
			GameController gameController = new GameController(boardView.layout, board, 0);
			gameController.currentPlayer = new HumanPlayer("BLUE");
			gameController.otherPlayer = new HumanPlayer("RED");
			boardView.setListener(gameController);
		}else {
			GameController gameController = new GameController(boardView.layout, board, 0);
			gameController.currentPlayer = new HumanPlayer("RED");
			gameController.otherPlayer = new HumanPlayer("BLUE");
			boardView.setListener(gameController);
		}

		screen = new JPanel();
		screen.setLayout(new BorderLayout());
		screen.add(boardView, BorderLayout.CENTER);
		screen.setBorder(new EmptyBorder(280-boardW*20, 510-boardH*40 , 0 , 0));

		RB1 = new JPanel();
		RB1.setSize(10,720);
		RB1.setBackground(Color.RED);
		RB2 = new JPanel();
		RB2.setSize(10,720);
		RB2.setBackground(Color.RED);
		BB1 = new JPanel();
		BB1.setSize(1280,10);
		BB1.setBackground(Color.BLUE);
		BB2 = new JPanel();
		BB2.setSize(1280,10);
		BB2.setBackground(Color.BLUE);

		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		center.add(RB1, BorderLayout.WEST);
		center.add(RB2, BorderLayout.EAST);
		center.add(BB1, BorderLayout.NORTH);
		center.add(BB2, BorderLayout.SOUTH);
		center.add(screen, BorderLayout.CENTER);

		top = new JPanel();
		top.setPreferredSize(new Dimension(1280, 720));
		top.setLayout(new BorderLayout());
		//top.add(screen, BorderLayout.CENTER);
		top.add(center, BorderLayout.CENTER);
		top.add(opt, BorderLayout.EAST);
		top.setDoubleBuffered(true);
		add(top);
		pack();
	}

	@Override
	public void dispose() {
		canvas.stop();
		super.dispose();
	}

}
