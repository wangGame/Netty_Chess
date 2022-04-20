package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import game.GameConfig;
import kw.mulitplay.game.screen.base.BaseScreen;
import xqwlight.GAmeView;
import xqwlight.Position;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LoadingScreen extends BaseScreen {
    float time = 0;
    @Override
    public void show() {
        super.show();

        InputStream is = null;
        try {
            is = new FileInputStream(Gdx.files.internal("book.dat").file());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Position.loadBook(is);
        GAmeView view = new GAmeView();
        stage.addActor(view);

//        ChessBoardMain2 main2 = new ChessBoardMain2();
//        stage.addActor(main2);
//        addActor(new Image(new Texture("orange.png")));
//        Group g1 = new Group();
//        g1.setSize(720,200);
//        g1.setOrigin(Align.center);
//        g1.setRotation(90);
//        Table g = new Table(){{
//            for (int i = 0; i < 20; i++) {
//                add(new Image(new Texture("orange.png")));
//            }
//            pack();
//        }};
//        ScrollPane pane = new ScrollPane(g,new ScrollPane.ScrollPaneStyle());
//        addActor(g1);
//        pane.setSize(720,200);
//        pane.setX(0);
//        pane.setDebug(true);
//        g1.addActor(pane);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        time+=delta;
//        if (time > 3){
//            setScreen(new MainScreen());
//        }
    }
}
