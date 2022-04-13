package com.kw.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import kw.mulitplay.game.ChessGame;

public class DemoApp{
    public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.x = 0;
        config.y = 0;
        config.width = 360;
        config.height = 640;
        new LwjglApplication(new ChessGame(), config);
    }
}
