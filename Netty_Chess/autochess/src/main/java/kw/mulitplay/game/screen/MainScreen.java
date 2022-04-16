package kw.mulitplay.game.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.screen.base.BaseScreen;

public class MainScreen extends BaseScreen {
    @Override
    public void show() {
        super.show();
        //进入自动游玩
        LevelConfig.currentPlayer = 'r';
        setScreen(new GameScreen());
    }
}
