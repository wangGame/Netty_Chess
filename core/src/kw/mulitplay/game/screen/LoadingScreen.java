package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.FileInputStream;
import java.io.InputStream;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.game.GameConfig;
import kw.mulitplay.game.light.Position;
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
        try (  InputStream is = new FileInputStream(Gdx.files.internal(GameConfig.DAT_ASSETS_PATH).file());){
            Position.loadBook(is);
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.addAction(Actions.delay(1,Actions.run(
                ()->{
                    Asset.ChessGame.setScreen(new GameScreen());
                }
        )));
    }

}
