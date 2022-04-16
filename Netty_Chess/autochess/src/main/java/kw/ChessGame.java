package kw;

import com.badlogic.gdx.utils.Align;

import kw.alogrithm.SearchModel;
import kw.chess.Board;
import kw.control.GameController;
import kw.mulitplay.game.config.Config;
import kw.view.GameView;

public class ChessGame {
    private Board board;
    private GameController controller;
    private GameView view;
    float time = 0;

    public void init() {
        //控制类
        controller = new GameController();
        SearchModel searchModel = new SearchModel();
        controller.setSearchModel(searchModel);
        board = controller.playChess();
        view = new GameView(controller);
        view.init(board);
        view.setPosition(Config.GAME_WIDTH/2,Config.GAME_HIGHT/2, Align.center);
        view.setOrigin(Align.center);
        view.setRotation(180);
    }

    public GameView getView() {
        return view;
    }

    public void run(float delta)  {
        if (controller.hasWin(board) == 'x') {
            if (controller.hasWin(board) != 'x'){
                view.showWinner('b');
            }
            if (board.player == 'b') {
                time+=delta;
                if (time>3) {
                    time = 0;
                    controller.responseMoveChess(board, view);
                    view.showPlayer('r');
                    if (controller.hasWin(board) != 'x') {
                        view.showWinner('r');
                    }
                }
            }
        }
    }
}