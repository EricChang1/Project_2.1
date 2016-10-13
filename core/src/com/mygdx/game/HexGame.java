package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * Created by Envy on 9/9/2016.
 */
public class HexGame extends Game{
    private MenuScreen menuScreen;
    private Screen previousScreen;

    @Override
    public void create() {
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public Screen getMenuScreen(){
        return menuScreen;
    }

    public void showPauseMenu(){
        menuScreen.setGamePaused(true);
        previousScreen = getScreen();
        setScreen(menuScreen);
    }

    public void hidePauseMenu(){
        setScreen(previousScreen);
        menuScreen.setGamePaused(false);
    }

}
