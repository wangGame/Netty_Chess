package kw.mulitplay.game.ai;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import kw.mulitplay.game.move.GameMove;
import kw.mulitplay.game.screen.view.GameView;
import kw.mulitplay.xqwlight.Position;
import kw.mulitplay.xqwlight.Search;

public class AI {
    private Position position;
    private Search search ;
    private ChessData chessData[][];
    private ChessData oldChessData[][];
    public AI(String str){
        chessData= new ChessData[9][10];
        oldChessData = new ChessData[9][10];
        position =  new Position();
        search = new Search(position, 16);
        position.fromFen(str);
        setChessData();
    }

    public void setChessData() {

        for (int xxx = Position.FILE_LEFT; xxx <= Position.FILE_RIGHT; xxx++) {
            for (int yyy = Position.RANK_TOP; yyy <= Position.RANK_BOTTOM; yyy++) {

                int sq = Position.COORD_XY(xxx,yyy);
//                sq = Position.SQUARE_FLIP(sq);
                int xx1 = xxx - Position.FILE_LEFT;
                int yy1 = yyy - Position.RANK_TOP;
                chessData[xx1][yy1] = null;
                int pc = position.squares[sq];
                if (pc > 0) {
                    pc -= 8;
                    if (pc > 6) {
                        pc--;
                    }
                    ChessData data = new ChessData();
                    data.setChess(pc);
                    data.setPox(xx1);
                    data.setPoy(yy1);
                    chessData[xx1][yy1] = data;
                }
            }
        }
    }

    public Position getPosition() {
        return position;
    }

    public void move(int move){
        if (!position.legalMove(move)) {
            return;
        }
        if (!position.makeMove(move)) {
//            playSound(RESP_ILLEGAL);
            return;
        }
    }

    public GameMove ai(){
        for (int i = 0; i < chessData.length; i++) {
            for (int i1 = 0; i1 < chessData[i].length; i1++) {
                oldChessData[i][i1] = null;
                if (chessData[i][i1]!=null)
                oldChessData[i][i1] = chessData[i][i1];
            }
        }
        search.prepareSearch();
        int mvLast = search.searchMain(100 << 1);
        position.makeMove(mvLast);

        if (position.captured()) {
            position.setIrrev();
        }
//        position.setIrrev();
        setChessData();

        che();

        GameMove gameView = new GameMove();

        for (int i = 0; i < chessData.length; i++) {
            for (int i1 = 0; i1 < chessData[i].length; i1++) {
                if (oldChessData[i][i1] == null && chessData[i][i1] == null)continue;
                if (oldChessData[i][i1]==null &&chessData[i][i1]!=null){
                    System.out.println(oldChessData[i][i1] +"  "+chessData[i][i1]);
                    gameView.setEndX(chessData[i][i1].getPox());
                    gameView.setEndY(chessData[i][i1].getPoy());
                }else if (chessData[i][i1]==null &&oldChessData[i][i1]!=null){
                    System.out.println(oldChessData[i][i1] +"  "+chessData[i][i1]);
                    gameView.setStartX(oldChessData[i][i1].getPox());
                    gameView.setStartY(oldChessData[i][i1].getPoy());
                }else if (oldChessData[i][i1]!=null&&chessData[i][i1]!=null) {
                    {
                        if (oldChessData[i][i1].getChess()!=chessData[i][i1].getChess()){
                            System.out.println(oldChessData[i][i1] +"  "+chessData[i][i1]);
                        }
                    }
                }
            }
        }
        return gameView;
    }

    public void che(){
        setChessData();
        int xx = 0;
        for (ChessData[] chessDatum : chessData) {
            for (ChessData data : chessDatum) {
                if (data!=null){
                    xx++;
                }
            }
        }
        System.out.println(xx+" ----------------");


        xx = 0;
        for (ChessData[] chessDatum : oldChessData) {
            for (ChessData data : chessDatum) {
                if (data!=null){
                    xx++;
                }
            }
        }
        System.out.println(xx+" -===============");
    }
}
