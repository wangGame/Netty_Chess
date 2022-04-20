package kw.test.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import kw.test.base.BaseScreen;
import kw.test.log.NLog;

public class LoadingScreen extends BaseScreen {
    @Override
    public void show() {
        super.show();
        NLog.i("create screen");
        Image image = new Image(new Texture("orange.png"));
        addActor(image);

    }

}
