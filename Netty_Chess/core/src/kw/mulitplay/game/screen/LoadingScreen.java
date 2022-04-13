package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.FileInputStream;
import java.io.InputStream;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.screen.base.BaseScreen;

public class LoadingScreen extends BaseScreen {
    @Override
    public void show() {
        super.show();
        Image image = new Image(new Texture("orange.png"));
        addActor(image);

//        Texture texture = new Texture("orange.png");
//        texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);
//        TextureRegion region = new TextureRegion(texture);
//
//        region.setRegionWidth(1000);
//
//
//        Image image = new Image(region);
//        stage.addActor(image);


        stage.addAction(Actions.delay(1,Actions.run(
                ()->{
                    Asset.ChessGame.setScreen(new GameScreen());
                }
        )));
    }

}
