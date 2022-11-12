package kw.mulitplay.game.screen;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.screen.view.GameView;

public class GameScreen  extends BaseScreen {
    private int currentStatus ;
    private GameView gameView;

    @Override
    public void show() {
        super.show();
        startGame();
    }

    private void startGame() {
        Asset.getInstance().loadAsset();
        gameView = new GameView();
        addActor(gameView);
    }

}
