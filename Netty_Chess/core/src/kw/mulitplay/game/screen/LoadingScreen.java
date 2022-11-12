package kw.mulitplay.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.log.NLog;
import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.client.GameClient;
import kw.mulitplay.game.screen.base.BaseScreen;

public class LoadingScreen extends BaseScreen {
    @Override
    public void show() {
        super.show();
        NLog.i("create screen");
        stage.addAction(Actions.delay(1,Actions.run(
                ()->{
                    NLog.i("enter game screen");
                    Asset.ChessGame.setScreen(new GameScreen());
                }
        )));
    }

}
