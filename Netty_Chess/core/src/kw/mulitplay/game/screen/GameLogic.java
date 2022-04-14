package kw.mulitplay.game.screen;

import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.data.Chess;
import kw.mulitplay.game.screen.game.GameGuiZe;
import kw.mulitplay.game.screen.view.GameView;

public class GameLogic {
    private Chess[][] chessObject;
    private GameGuiZe gameGuiZe;
    private GameView.Listener listener;

    public GameLogic(Chess[][] chessObject, GameView.Listener listener){
        this.chessObject = chessObject;
        gameGuiZe = new GameGuiZe(chessObject);
        this.listener = listener;
    }

    public void playerMoveChess(int xx, int yy, int startX, int startY) {
        LevelConfig.chessSelected = chessObject[startX][startY];
        if (LevelConfig.clickType == 3) {
            moveChess(xx, yy, startX, startY);
        } else if (LevelConfig.clickType==1){
            eatChess(xx, yy, startX, startY);
        }
    }


    private void eatChess(int xx, int yy, int startX, int startY) {
        if (gameGuiZe.canMove(startX,startY,xx,yy, LevelConfig.chessSelected.getQiziName())) {
            Chess chess = chessObject[xx][yy];
            if (chess != null) {
                chess.remove();
            }
            chessObject[xx][yy] = null;
//                        LevelConfig.chessSelected.setPosition(xx * Config.chessSize, yy * Config.chessSize);
            chessObject[startX][LevelConfig.chessSelected.getPosY()] = null;
            chessObject[xx][yy] = LevelConfig.chessSelected;
            LevelConfig.chessSelected.setPosXY(xx, yy);
            LevelConfig.chessSelected = null;
            LevelConfig.currentPlayer = LevelConfig.currentPlayer == LevelConfig.PLAYER0 ?
                    LevelConfig.PLAYER1 : LevelConfig.PLAYER0;
        }
        listener.setCursorPosgone();
    }

    private void moveChess(int xx, int yy, int startX, int startY) {
        if (gameGuiZe.canMove(startX,startY,xx,yy, LevelConfig.chessSelected.getQiziName())) {
            chessObject[startX][LevelConfig.chessSelected.getPosY()] = null;
            chessObject[xx][yy] = LevelConfig.chessSelected;
            LevelConfig.chessSelected.setPosXY(xx, yy);
            LevelConfig.chessSelected = null;
            LevelConfig.currentPlayer = LevelConfig.currentPlayer == LevelConfig.PLAYER0 ?
                    LevelConfig.PLAYER1 : LevelConfig.PLAYER0;
        }else {
            listener.setCursorPosgone();
        }
    }

}
