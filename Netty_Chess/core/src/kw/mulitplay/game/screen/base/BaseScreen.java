package kw.mulitplay.game.screen.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import kw.mulitplay.game.asset.Asset;

public class BaseScreen implements Screen {
    protected Stage stage;
    public BaseScreen(){
        stage = new Stage(Asset.viewport,Asset.batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void addActor(Actor actor){
        stage.addActor(actor);
    }

    public <T extends Actor> T findActor(String name){
        return stage.getRoot().findActor(name);
    }

    public void setScreen(Class<Screen> screen){
        try {
            Asset.ChessGame.setScreen(screen.newInstance());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void setScreen(Screen screen){
        Asset.ChessGame.setScreen(screen);
    }
}
