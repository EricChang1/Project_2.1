package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.desktop.DesktopLauncher;

/**
 * Created by Envy on 9/20/2016.
 */
public class PlayerOptWindow extends Window {
    Skin skin;
    HexGame game;
    MenuScreen menu;
    Label Player1, Player2, p1AIDiff, p2AIDiff, p1Diff, p2Diff;
    String[] pType = new String[]{" Human", "       AI"}; //Using spaces to center text. Terrible solution
    Slider P1DiffSlider, P2DiffSlider;
    boolean hidePlayOpt;
    int boardX, boardY;

    public PlayerOptWindow(Skin skin, HexGame Game, final MenuScreen screen) {
        super("Player Options", skin);
        this.setModal(true);
        this.skin = skin;
        game = Game;
        menu = screen;
        this.setSize(500, 300);
        this.setMovable(true);

        TextButton gOptButton = new TextButton("Back", skin);
        gOptButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hidePlayOpt = true;
            }
        });
        this.addActor(gOptButton);
        gOptButton.setPosition(135,40);
        gOptButton.setSize(90,40);

        TextButton startButton = new TextButton("Start", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
                setGameScreen();

            }
        });
        this.addActor(startButton);
        startButton.setPosition(275,40);
        startButton.setSize(90,40);

        final SelectBox<String> PlayerType1 = new SelectBox<String>(skin);
        PlayerType1.setSize(83,50);
        this.addActor(PlayerType1);
        PlayerType1.setItems(pType);
        PlayerType1.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                switch(PlayerType1.getSelectedIndex()){
                    case 0:
                        toggleAIOptions(1);
                        break;
                    case 1:
                        toggleAIOptions(0);
                        break;
                }
            }
        });
        PlayerType1.setPosition(160, 182);

        final SelectBox<String> PlayerType2 = new SelectBox<String>(skin);
        PlayerType2.setSize(83,50);
        this.addActor(PlayerType2);
        PlayerType2.setItems(pType);
        PlayerType2.addListener(new ChangeListener() {
            public void changed (ChangeEvent event, Actor actor) {
                switch(PlayerType2.getSelectedIndex()){
                    case 0:
                        toggleAIOptions(3);
                        break;
                    case 1:
                        toggleAIOptions(2);
                        break;
                }
            }
        });
        PlayerType2.setPosition(160, 107);

        Player1 = new Label("Player 1", skin);
        Player1.setPosition(80, 195);
        this.addActor(Player1);

        Player2 = new Label("Player 2", skin);
        Player2.setPosition(80, 120);
        this.addActor(Player2);

        //AI Difficulty stuff
        P1DiffSlider = new Slider(1, 3, 1, false, skin);
        P1DiffSlider.setSize(140,50);
        P1DiffSlider.setPosition(280, 187);

        p1AIDiff = new Label("Set AI Difficulty", skin);
        p1AIDiff.setPosition(295, 222);

        p1Diff = new Label("1             2              3", skin);
        p1Diff.setPosition(281, 177);

        P2DiffSlider = new Slider(1, 3, 1, false, skin);
        P2DiffSlider.setSize(140,50);
        P2DiffSlider.setPosition(280, 112);

        p2AIDiff = new Label("Set AI Difficulty", skin);
        p2AIDiff.setPosition(295, 147);

        p2Diff = new Label("1             2              3", skin);
        p2Diff.setPosition(281, 102);


    }
    public void setGameScreen(){
        DesktopLauncher.dkl.rem(boardX,boardY);
        //game.setScreen(new GameScreen(game));
    }

    public void toggleAIOptions(int x){
        if(x == 0){
            this.addActor(P1DiffSlider);
            this.addActor(p1AIDiff);
            this.addActor(p1Diff);
        }else if(x == 1){
            this.removeActor(P1DiffSlider);
            this.removeActor(p1AIDiff);
            this.removeActor(p1Diff);
        }else if(x == 2){
            this.addActor(P2DiffSlider);
            this.addActor(p2AIDiff);
            this.addActor(p2Diff);
        }else if(x == 3){
            this.removeActor(P2DiffSlider);
            this.removeActor(p2AIDiff);
            this.removeActor(p2Diff);
        }
    }
}
