package kw.mulitplay.game.screen;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

import java.util.AbstractList;

import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.screen.base.BaseScreen;
import player.client.App;

public class LoadingScreen extends BaseScreen {
    float time = 0;
    @Override
    public void show() {
        super.show();
//        addActor(new Image(new Texture("orange.png")));

        Group g1 = new Group();
        g1.setSize(720,200);
        g1.setOrigin(Align.center);
        g1.setRotation(270);
        Table g = new Table(){{
            for (int i = 0; i < 20; i++) {
                add(new Image(new Texture("orange.png")));
            }
            pack();
        }};
        ScrollPane pane = new ScrollPane(g,new ScrollPane.ScrollPaneStyle());
        addActor(g1);
        pane.setSize(720,200);
        pane.setX(0);
        pane.setDebug(true);
        g1.addActor(pane);
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
