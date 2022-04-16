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

import kw.log.NLog;
import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.screen.LoadingScreen;

public class ChessGameAsset extends Game {
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



//        mSoundEnable = mPreference.getBoolean(getString(R.string.pref_sound_key), true);
        int mHandicapIndex = 0;
        boolean mComputerFlip = false;
//        mPieceStyle = Integer.parseInt(mPreference.getString(getString(R.string.pref_piece_style_key), "0"));
        int mAILevel = 0;



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
        Asset.dispose();
    }

    @Override
    public void render() {
        ScreenUtils.clear(229.0F/255,204.0F/255,172.0F/255F,1);
        super.render();
    }
}