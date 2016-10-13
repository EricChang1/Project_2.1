package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;


/**
 * Created by Envy on 9/11/2016.
 */
public class GameOptWindow extends Window {
    MenuScreen menu;
    Label textLabel;
    Label x;
    Skin skin;
    TextField boardSizeX;
    TextField boardSizeY;
    HexGame game;
    CheckBox equalBoard;
    boolean equalSelected = true, fieldXSelect, fieldYSelect, showPlayOpt, visible = true;
    String tempString;
    TextButton startButton, cancelButton;
    int bX, bY;

    public GameOptWindow(Skin skin, HexGame Game, final MenuScreen screen) {
        super("Game Options", skin);
        this.setModal(true);
        this.skin = skin;
        this.setSize(500, 300);
        this.setMovable(true);
        menu = screen;
        game = Game;

        textLabel = new Label("Enter Board Size:", skin);
        textLabel.setPosition(188, 175);
        this.addActor(textLabel);

        x = new Label("X", skin);
        x.setPosition(245,131);
        x.setFontScale(1.1f);
        this.addActor(x);

        boardSizeX = new TextField("", skin);
        boardSizeX.addListener(new FocusListener(){
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if(equalSelected == true) {
                    fieldYSelect = false;
                    fieldXSelect = true;
                }
            }
        });
        boardSizeX.setAlignment(1);
        boardSizeX.setPosition(130,120);
        boardSizeX.setSize(100,50);
        boardSizeX.setMaxLength(5);
        this.addActor(boardSizeX);

        boardSizeY = new TextField("", skin);
        boardSizeY.addListener(new FocusListener(){
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
               if(equalSelected == true){
                   fieldXSelect = false;
                   fieldYSelect = true;
               }
           }
        });
        boardSizeY.setAlignment(1);
        boardSizeY.setPosition(270,120);
        boardSizeY.setSize(100,50);
        boardSizeY.setMaxLength(5);
        this.addActor(boardSizeY);

        cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                remove();
            }
        });
        this.addActor(cancelButton);
        cancelButton.setPosition(135,40);
        cancelButton.setSize(90,40);

        startButton = new TextButton("Next", skin);
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int boardX = tryParseInt(boardSizeX.getText());
                int boardY = tryParseInt(boardSizeY.getText());

                if(boardX>0&&boardY>0){
                    bX = boardX;
                    bY  = boardY;
                    remove();
                    showPlayOpt = true;
                }
            }
        });
        this.addActor(startButton);
        startButton.setPosition(275,40);
        startButton.setSize(90,40);

        equalBoard = new CheckBox(" Equal Board", skin);
        equalBoard.toggle();
        equalBoard.setPosition(375,140);
        equalBoard.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (equalSelected == false){
                    removeActor(boardSizeX);
                    removeActor(boardSizeY);
                    equalSelected = true;
                    if(tryParseInt(boardSizeX.getText())==0&&tryParseInt(boardSizeY.getText())>0){
                        remakeBoardY(1);
                        remakeBoardX(1);
                    }else {
                        remakeBoardY(0);
                        remakeBoardX(0);
                    }
                }else{
                    removeActor(boardSizeY);
                    equalSelected = false;
                    remakeBoardY(1);
                }
            }
        });
        this.addActor(equalBoard);
    }

    public int tryParseInt(String value)
    {
        try{
            return Integer.parseInt(value);
        }catch (NumberFormatException nfe){
            return 0;
        }
    }
    public void remakeBoardY(int x){
        if(x==0){
            getStringX();
        }else if (x==1){
            getStringY();
        }
        boardSizeY = new TextField("", skin);
        boardSizeY.addListener(new FocusListener(){
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if(equalSelected == true){
                    fieldXSelect = false;
                    fieldYSelect = true;
                }
            }
        });
        boardSizeY.setText(tempString);
        boardSizeY.setAlignment(1);
        boardSizeY.setPosition(270,120);
        boardSizeY.setSize(100,50);
        boardSizeY.setMaxLength(5);
        this.addActor(boardSizeY);
    }

    public void remakeBoardX(int x){
        if(x==0){
            getStringX();
        }else if (x==1){
            getStringY();
        }
        boardSizeX = new TextField("", skin);
        boardSizeX.addListener(new FocusListener(){
            public void keyboardFocusChanged(FocusListener.FocusEvent event, Actor actor, boolean focused) {
                if(equalSelected == true) {
                    fieldYSelect = false;
                    fieldXSelect = true;
                }
            }
        });
        boardSizeX.setText(tempString);
        boardSizeX.setAlignment(1);
        boardSizeX.setPosition(130,120);
        boardSizeX.setSize(100,50);
        boardSizeX.setMaxLength(5);
        this.addActor(boardSizeX);
    }


    public void getStringX(){
        tempString = boardSizeX.getText();
    }

    public void getStringY(){
        tempString = boardSizeY.getText();
    }
}
