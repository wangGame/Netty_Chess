package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.io.FileInputStream;
import java.io.InputStream;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.screen.base.BaseScreen;

public class LoadingScreen extends BaseScreen {
    @Override
    public void show() {
        super.show();
        Image image = new Image(new Texture("orange.png")){
            @Override
            public void act(float delta) {
                super.act(delta);
                System.out.println(getWidth());
            }
        };
        image.setDebug(true);
        addActor(image);


//        Table group = new Table();
//        ScrollPane scrollPane = new ScrollPane(group,new ScrollPane.ScrollPaneStyle());
//        addActor(scrollPane);
//
//        Texture texture = new Texture("orange.png");
//        texture.setWrap(Texture.TextureWrap.Repeat,Texture.TextureWrap.ClampToEdge);
//
//        TextureRegion region = new TextureRegion(texture);
//
//        region.setRegionWidth(10000);
//
//        Image image1 = new Image(region);
//            image1.setWidth(6000);
//            group.add(image1);
//            image1.setDebug(true);
//
//        scrollPane.setSize(720,1280);


        stage.addAction(Actions.delay(1,Actions.run(
                ()->{
                    Asset.ChessGame.setScreen(new GameScreen());
                }
        )));
    }

}
