package kw.mulitplay.game.screen;

import kw.mulitplay.game.screen.base.BaseScreen;

public class LoadingScreen extends BaseScreen {
    float time = 0;
    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        time+=delta;
        if (time > 3){
            setScreen(new MainScreen());
        }
    }
}
