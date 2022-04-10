package kw.mulitplay.game.screen.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.data.Chess;
import kw.mulitplay.game.data.ChessData;
import kw.mulitplay.game.game.GameConfig;
import kw.mulitplay.game.game.GameLogic;
import kw.mulitplay.game.game.IGameView;
import kw.mulitplay.game.light.Position;

public class GameView extends Group implements IGameView {
    private GameLogic mGameLogic;
    private Chess[] mPiecesBitmap;
    private Image cursorRed;
    private Image cursorBlue;

    public GameView(){
        setSize(720,1280);
        setDebug(true);
        initView();
        initChessEr();
//        loadBitmapResources();
        addListener();

    }

//    private void loadBitmapResources() {
//        String[] pieceResArray = GameConfig.PIECE_RES_CARTOON;
//        mPiecesBitmap = new Chess[pieceResArray.length];
//        for (int i = 0; i < pieceResArray.length; i++) {
//            mPiecesBitmap[i] = new Chess(pieceResArray[i]);
//        }
//
//        Group chessGroup = new Group();
//        addActor(chessGroup);
//        chessGroup.setDebug(true);
//        chessGroup.setSize(Config.chessSize*9,Config.chessSize*10);
//        chessGroup.setPosition(getWidth()/2,getHeight()/2,Align.center);
//        ChessData data = new ChessData();
//        int[][] chessData = data.getData();
//        for (int i = 0; i < pieceResArray.length; i++) {
//            mPiecesBitmap[i] = new Chess(pieceResArray[i]);
//            chessGroup.addActor(mPiecesBitmap[i]);
//        }
//
////        for (int i = 0; i < chessData.length; i++) {
////            for (int i1 = 0; i1 < chessData[0].length; i1++) {
////                if (chessData[i][i1]!=Config.NOVALUE){
////                    Chess chess = new Chess();
////                    chess.setPosX(i);
////                    chess.setPosY(i1);
//////                    chessGroup.addActor(chess);
////                }
////            }
////        }
////                    chessGroup.addActor(chess);
//    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        mGameLogic.drawGameBoard();
    }

    public void initView(){
        chessGroup = new Group();
        mGameLogic = new GameLogic(this);
        Image image = new Image(Asset.getInstance().getTexture("qizi/board.jpg"));
        addActor(image);
        image.setPosition(Config.GAME_WIDTH/2,getHeight()/2,Align.center);
        image.setOrigin(Align.center);
        image.setScale(1.5F);
        cursorRed = new Image(Asset.getInstance().getTexture("qizi/selected2.png"));
        cursorBlue = new Image(Asset.getInstance().getTexture("qizi/selected.png"));
        chessGroup.addActor(cursorBlue);
        chessGroup.addActor(cursorRed);
        cursorRed.setDebug(true);
        cursorBlue.setDebug(true);
        cursorBlue.setScale(1.5F);
        cursorRed.setScale(1.5F);
    }
    Group chessGroup;
    public void initChessEr(){

        addActor(chessGroup);
        chessGroup.setDebug(true);
        chessGroup.setSize(Config.chessSize*9,Config.chessSize*10);
        chessGroup.setPosition(getWidth()/2,getHeight()/2,Align.center);
        ChessData data = new ChessData();
        int[][] chessData = data.getData();
        for (int i = 0; i < chessData.length; i++) {
            for (int i1 = 0; i1 < chessData[0].length; i1++) {
                if (chessData[i][i1]!=Config.NOVALUE){
                    Chess chess = new Chess((char)chessData[i][i1]);
                    chess.setPosX(i);
                    chess.setPosY(i1);
                    chessGroup.addActor(chess);
                }
            }
        }
    }


    public void addListener(){
        chessGroup.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                System.out.println("----------------------");
                int xx = (int) (x / Config.chessSize);
                int yy = (int) (y / Config.chessSize);
                setCursorPosVisible(LevelConfig.currentPlayer == LevelConfig.PLAYER1);
                setCursorPos(xx,yy);
                if (LevelConfig.currentSelected==null) {
                    if (LevelConfig.chessSelected!=null) {
                        LevelConfig.chessSelected.setPosition(xx*Config.chessSize,yy*Config.chessSize);
                        LevelConfig.chessSelected = null;
                        LevelConfig.currentPlayer = LevelConfig.currentPlayer == LevelConfig.PLAYER0 ?
                                LevelConfig.PLAYER1:LevelConfig.PLAYER0;
                        System.out.println(LevelConfig.currentPlayer);
                    }else {
                        setCursorPosgone();
                    }
                }else {
                    if (LevelConfig.chessSelected==null){
                        setCursorPosgone();
                    }
                }
                LevelConfig.currentSelected= null;

//                    int sq = Position.COORD_XY(xx + Position.FILE_LEFT, yy + Position.RANK_TOP);
//                    mGameLogic.clickSquare(sq);
////                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    public void setCursorPos(int xx,int yy){
        cursorRed.setPosition(xx*Config.chessSize,yy*Config.chessSize);
        cursorBlue.setPosition(xx*Config.chessSize,yy*Config.chessSize);
    }

    public void setCursorPosVisible(boolean isVisible){
        cursorRed.setVisible(isVisible);
        cursorBlue.setVisible(!isVisible);
    }

    public void setCursorPosgone(){
        cursorRed.setVisible(false);
        cursorBlue.setVisible(false);
    }

    @Override
    public void postRepaint() {
//        postInvalidate();
    }

    @Override
    public void drawPiece(int pc, int xx, int yy) {


    }

    @Override
    public void drawSelected(int xx, int yy) {

    }

    public GameLogic getmGameLogic() {
        return mGameLogic;
    }
}
