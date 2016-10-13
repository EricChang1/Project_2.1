package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Created by Envy on 9/9/2016.
 */
public class MenuScreen implements Screen, InputProcessor {
    private HexGame game;
    SpriteBatch batch;
    Texture img;
    Stage stage;
    Skin skin;
    TextButton exitButton;
    TextButton helpButton;
    TextButton buttonNewGame;
    GameOptWindow newGameOpt;
    PlayerOptWindow newPlayOpt;
    HelpWindow helpWindow;
    boolean gamePaused;

    public MenuScreen(HexGame Game) {
        game = Game;
        batch = new SpriteBatch();
        stage = new Stage();

        skin = new Skin(Gdx.files.internal("uiskin.json"));

        buttonNewGame = new TextButton("New Game", skin);
        buttonNewGame.setPosition(550, 425);
        buttonNewGame.setSize(200, 50);
        stage.addActor(buttonNewGame);
        buttonNewGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(gamePaused){
                    resumeGame();
                }else {
                    newGameWindow();
                }
            }
        });

        helpButton = new TextButton("Help", skin);
        helpButton.setPosition(550, 325);
        helpButton.setSize(200, 50);
        stage.addActor(helpButton);
        helpButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                helpWindow();
            }
        });

        exitButton = new TextButton("Exit", skin);
        exitButton.setPosition(550, 225);
        exitButton.setSize(200, 50);
        stage.addActor(exitButton);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                    Gdx.app.exit();
            }

        });

        newPlayOpt = new PlayerOptWindow(skin, game, this);
        newPlayOpt.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 1);
    }

    public void newGameWindow(){
        newGameOpt = new GameOptWindow(skin, game, this);
        newGameOpt.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 1);
        stage.addActor(newGameOpt);
        stage.setKeyboardFocus(newGameOpt.boardSizeX);
    }

    public void helpWindow(){
        /*
        helpWindow = new HelpWindow(skin, game, this);
        stage.addActor(helpWindow);
        helpWindow.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 1);
        */
        this.hide();
    }

    public void resumeGame(){
        game.hidePauseMenu();
    }

    public void setGamePaused(boolean pauseGame){
        gamePaused = pauseGame;
        if(gamePaused){
            buttonNewGame.setText("Resume");
        }else{
            buttonNewGame.setText("New Game");
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        batch.begin();
        batch.end();

        //Other TextField copies text of other TextField
        if(newGameOpt!=null && newGameOpt.equalSelected == true
                && newGameOpt.fieldXSelect == true){
            newGameOpt.boardSizeY.setText(newGameOpt.boardSizeX.getText());
        }
        if(newGameOpt!=null && newGameOpt.equalSelected == true
                && newGameOpt.fieldYSelect == true){
            newGameOpt.boardSizeX.setText(newGameOpt.boardSizeY.getText());
        }

        //Show PlayOptWindow when "Next" is Pressed
        if (newGameOpt!= null && newGameOpt.showPlayOpt==true){
            stage.addActor(newPlayOpt);
            newGameOpt.showPlayOpt = false;
            newGameOpt.visible = false;
            newPlayOpt.boardX = newGameOpt.bX;
            newPlayOpt.boardY = newGameOpt.bY;

        }

        //Hide PlayOptWindow and show previous window when "Back" is pressed
        if(newPlayOpt.hidePlayOpt == true){
            newGameOpt.showPlayOpt = false;
            newPlayOpt.hidePlayOpt = false;
            newPlayOpt.remove();
            stage.addActor(newGameOpt);
            stage.setKeyboardFocus(newGameOpt.boardSizeX);
            newGameOpt.visible = true;
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
