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

    public void setSelected(int startX,int startY){
        LevelConfig.chessSelected = chessObject[startX][startY];
    }

    public boolean playerMoveChess(int xx, int yy, int startX, int startY) {
        setSelected(startX,startY);
        if (LevelConfig.clickType == 3) {
            return moveChess(xx, yy, startX, startY);
        } else if (LevelConfig.clickType==1){
            return eatChess(xx, yy, startX, startY);
        }
        return false;
    }


    private boolean eatChess(int xx, int yy, int startX, int startY) {
        if (gameGuiZe.canMove(startX,startY,xx,yy, LevelConfig.chessSelected.getQiziName())) {
            Chess chess = chessObject[xx][yy];
            if (chess != null) {
                if (chess.getQiziName() == 'k'){
                    LevelConfig.currentStatus = LevelConfig.success;
                    System.out.println("end ------------------");
                    return false;
                }
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
            return true;
        }
        listener.setCursorPosgone();
        return false;
    }

    private boolean moveChess(int xx, int yy, int startX, int startY) {
        if (gameGuiZe.canMove(startX,startY,xx,yy, LevelConfig.chessSelected.getQiziName())) {
            chessObject[startX][LevelConfig.chessSelected.getPosY()] = null;
            chessObject[xx][yy] = LevelConfig.chessSelected;
            LevelConfig.chessSelected.setPosXY(xx, yy);
            LevelConfig.chessSelected = null;
            LevelConfig.currentPlayer = LevelConfig.currentPlayer == LevelConfig.PLAYER0 ?
                    LevelConfig.PLAYER1 : LevelConfig.PLAYER0;
            return true;
        }else {
            listener.setCursorPosgone();
            return false;
        }
    }

}
