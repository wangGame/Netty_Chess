package kw.mulitplay.game.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.mulitplay.game.game.IGameCallback;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.screen.view.GameView;

public class GameScreen  extends BaseScreen implements IGameCallback {

    private GameView gameView;

    @Override
    public void show() {
        super.show();
        gameView = new GameView();
        addActor(gameView);
        gameView.getmGameLogic().setCallback(this);
        gameView.getmGameLogic().setLevel(1);
        gameView.getmGameLogic().restart();
    }

    @Override
    public void postPlaySound(int soundIndex) {

    }

    @Override
    public void postShowMessage(String message) {

    }

    @Override
    public void postShowMessage(int messageId) {

    }

    @Override
    public void postStartThink() {

    }

    @Override
    public void postEndThink() {

    }
}
