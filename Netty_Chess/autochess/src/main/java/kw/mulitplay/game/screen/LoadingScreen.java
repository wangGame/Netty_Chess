package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import kw.mulitplay.game.all.GameBoardView;
import kw.mulitplay.game.all.GameLogic;
import kw.mulitplay.game.auto.Position;
import kw.mulitplay.game.screen.base.BaseScreen;

public class LoadingScreen extends BaseScreen {
    @Override
    public void show() {
        super.show();
        FileHandle is = Gdx.files.internal("book.dat");
        try {
            InputStream stream = new FileInputStream(is.file());
            Position.loadBook(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        GameBoardView view = new GameBoardView();
        stage.addActor(view);
        GameLogic mGameLogic = view.getGameLogic();
        mGameLogic.setLevel(1);
        mGameLogic.restart(true, "");
//
        // load last saved game
//        String lastFen = mPreference.getString(GameConfig.PREF_LAST_FEN, "");
//        if (StringUtils.isEmpty(lastFen)) {
//            mGameLogic.restart(mComputerFlip, mHandicapIndex);
//        } else {
//            showMessage(getString(R.string.load_last_game_finish));
//            mGameLogic.restart(mComputerFlip, lastFen);
//        }



//        NLog.i("create screen");
//        Image image = new Image(new Texture("orange.png"));
//        addActor(image);
//        GameClient.run();
//        stage.addAction(Actions.delay(1,Actions.run(
//                ()->{
//                    NLog.i("enter game screen");
//                    Asset.ChessGame.setScreen(new GameScreen());
//                }
//        )));
    }

}
