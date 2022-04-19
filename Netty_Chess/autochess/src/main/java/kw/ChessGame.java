package kw;

import com.badlogic.gdx.utils.Align;

import kw.alogrithm.SearchModel;
import kw.chess.Board;
import kw.control.GameController;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.config.LevelConfig;
import kw.view.GameView;

/**
 * 实现的思路是通过得到牌之后，
 * 根据深度进行
 */
public class ChessGame {
    private Board board;
    private GameController controller;
    private GameView view;
    float time = 0;

    public ChessGame(){
        init();
    }
    public void init() {
        //控制类
        controller = new GameController();
        board = controller.playChess();
        view = new GameView(controller);
        view.setPosition(Config.GAME_WIDTH/2,Config.GAME_HIGHT/2, Align.center);
        view.setOrigin(Align.center);
        view.setRotation(180);
    }

    public GameView getView() {
        return view;
    }

    public void run(float delta)  {
        if(LevelConfig.model == 0) {
            //人机对战   机器实在里面进行的
            if (controller.hasWin() == 'x') {
                if (board.player == 'b') {
                    time += delta;
                    if (time > 1) {
                        time = 0;
                        controller.responseMoveChess(view);
                        view.showPlayer('r');
                        if (controller.hasWin() != 'x') {
                            view.showWinner('r');
                        }
                    }
                }
            }
        }
    }
}