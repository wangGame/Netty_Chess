package kw.mulitplay.game.screen;

import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.screen.view.GameView;

public class GameScreen  extends BaseScreen {

    private GameView gameView;

    @Override
    public void show() {
        super.show();
        gameView = new GameView();
        addActor(gameView);
    }
}
