package kw.test;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.CpuSpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import kw.test.asset.Asset1;
import kw.test.config.Config;
import kw.test.log.NLog;
import kw.test.screen.LoadingScreen;

public class MainGame extends Game {
    private Viewport chessGameViewport;
    private CpuSpriteBatch batch;

    @Override
    public void create() {
        Asset1.getInstance();
        Asset1.ChessGame = this;
        chessGameViewport = new ExtendViewport(Config.STD_GAME_WIDTH,Config.STD_GAME_HIGHT);
        batch = new CpuSpriteBatch();
        Asset1.viewport = chessGameViewport;
        Asset1.batch = batch;
        setScreen(new LoadingScreen());
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        chessGameViewport.update(width,height);
        Config.GAME_WIDTH = chessGameViewport.getWorldWidth();
        Config.GAME_HIGHT = chessGameViewport.getWorldHeight();
        NLog.i("%d  %d",width,height);
    }

    @Override
    public void dispose() {
        super.dispose();
        Asset1.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(229.0F/255,204.0F/255,172.0F/255F,1);
        super.render();
    }
}
