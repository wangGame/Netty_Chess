package kw.mulitplay.game.screen;

import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.screen.base.BaseScreen;
import kw.mulitplay.game.screen.view.GameView;

public class GameScreen  extends BaseScreen {

    private GameView gameView;

    @Override
    public void show() {
        super.show();
    }

    private void startGame() {
        gameView = new GameView();
        addActor(gameView);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (LevelConfig.currentStatus == LevelConfig.startGame){
            startGame();
            LevelConfig.currentStatus = LevelConfig.Playing;
        }
    }
}
