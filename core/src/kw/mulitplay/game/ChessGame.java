package kw.mulitplay.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.screen.LoadingScreen;

public class ChessGame extends Game {
    private Viewport chessGameViewport;
    private CpuSpriteBatch batch;

    @Override
    public void create() {
        Asset.getInstance();
        Asset.ChessGame = this;
        chessGameViewport = new ExtendViewport(Config.STD_GAME_WIDTH,Config.STD_GAME_HIGHT);
        batch = new CpuSpriteBatch();
        Asset.viewport = chessGameViewport;
        Asset.batch = batch;
        setScreen(new LoadingScreen());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        chessGameViewport.update(width,height);
        Config.GAME_WIDTH = chessGameViewport.getWorldWidth();
        Config.GAME_HIGHT = chessGameViewport.getWorldHeight();
    }

    @Override
    public void dispose() {
        super.dispose();
        Asset.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.valueOf("444444"));
        super.render();
    }

    public void setScreen(String name) {
        if (name.equals("loadcreen")){
            setScreen(new LoadingScreen());
        }
    }
}