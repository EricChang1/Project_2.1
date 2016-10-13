package com.mygdx.game;


import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Envy on 9/12/2016.
 */
public class HelpWindow extends Window {
    MenuScreen menu;
    Skin skin;
    public HelpWindow(Skin skin, HexGame Game, MenuScreen screen) {
        super("Instructions", skin);
        this.skin = skin;
        this.setSize(600, 500);
        menu = screen;

        Label Instructions = new Label("READ HERE TO KNOW HOW TO PLAY", skin);
        this.add(Instructions);


        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });
        this.addActor(backButton);
        backButton.setPosition(270,25);
        backButton.setSize(90,40);

    }
}
