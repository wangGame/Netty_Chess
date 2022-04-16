package kw.mulitplay.game.screen;

import com.badlogic.gdx.Gdx;

import kw.ChessGame;
import kw.mulitplay.game.ChessGameAsset;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.screen.base.BaseScreen;
import player.client.dispatch.ClientDispatch;

public class GameScreen extends BaseScreen {
    ChessGame chessGame;
    @Override
    public void show() {
        super.show();

//        if (LevelConfig.model!=2) {
//            chessGame = new ChessGame();
//            chessGame.init();
//            stage.addActor(chessGame.getView());
//        }else {
            ClientDispatch.pipei(new Runnable(){
                @Override
                public void run() {
                    Gdx.app.postRunnable(()->{
                        chessGame = new ChessGame();
                        chessGame.init();
                        stage.addActor(chessGame.getView());
                    });
                }
            });
//        }
    }

    public float js(float dp){
        //英寸
        int w = 1080;
        int h = 2340;
        float yc = 6.0f;
        float i = h / 1280.0F;
        float ii = w / 720.0F;
        float min = Math.min(i, ii);
        double v = Math.sqrt(w * w + h * h) / yc;
        //px = 1 x v / 160;
        float xxx = (float)( v / 160.0F)/min;
        return xxx * dp;
        //
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (chessGame!=null) {
            chessGame.run(delta);
        }
    }
}
