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
import kw.mulitplay.game.screen.game.GameLogic;

public class GameView extends Group {
    private Image cursorRed;
    private Image cursorBlue;
    private Group chessGroup;

    public GameView(){
        setSize(720,1280);
        setDebug(true);
        initView();
        initChessEr();
        addListener();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public void initView(){
        chessGroup = new Group();
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

    private Chess[][] chessObject = new Chess[9][10];

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
                    chessObject[i][i1] = chess;
                    chessGroup.addActor(chess);
                }
            }
        }
        logic = new GameLogic(chessObject);
    }

    private GameLogic logic;


    public void addListener(){
        chessGroup.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                int xx = (int) (x / Config.chessSize);
                int yy = (int) (y / Config.chessSize);
                setCursorPosVisible(LevelConfig.currentPlayer == LevelConfig.PLAYER1);
                setCursorPos(xx,yy);
                if (LevelConfig.chessSelected!=null) {
                    int startX = LevelConfig.chessSelected.getPosX();
                    int startY = LevelConfig.chessSelected.getPosY();
                    if (LevelConfig.clickType == 3) {
                        if (logic.canMove(startX,startY,xx,yy,LevelConfig.chessSelected.getQiziName())) {
                            chessObject[startX][LevelConfig.chessSelected.getPosY()] = null;
                            chessObject[xx][yy] = LevelConfig.chessSelected;
                            LevelConfig.chessSelected.setPosXY(xx, yy);
                            LevelConfig.chessSelected = null;
                            LevelConfig.currentPlayer = LevelConfig.currentPlayer == LevelConfig.PLAYER0 ?
                                    LevelConfig.PLAYER1 : LevelConfig.PLAYER0;
                        }else {
                            setCursorPosgone();
                        }
                    } else if (LevelConfig.clickType==1){
//                        LevelConfig.chessSelected.setPosition(xx * Config.chessSize, yy * Config.chessSize);
//                        LevelConfig.chessSelected = null;
                        if (logic.canMove(startX,startY,xx,yy,LevelConfig.chessSelected.getQiziName())) {
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
                        setCursorPosgone();
                    }
                }
                if (LevelConfig.chessSelected == null){
                    setCursorPosgone();
                }
                LevelConfig.currentSelected= null;
                LevelConfig.clickType = 3;
//                if (LevelConfig.currentSelected==null) {
//                    if (LevelConfig.chessSelected!=null) {
//
////                        if (logic.canMove(LevelConfig.chessSelected.getPosX(),
////                                LevelConfig.chessSelected.getPosY(),xx,yy,LevelConfig.chessSelected.getQiziName())) {
////                        }
////
//
//                        LevelConfig.chessSelected.setPosition(xx*Config.chessSize,yy*Config.chessSize);
//                        LevelConfig.chessSelected = null;
//                        LevelConfig.tarGetSelected = null;
//                        LevelConfig.currentPlayer = LevelConfig.currentPlayer == LevelConfig.PLAYER0 ?
//                                LevelConfig.PLAYER1:LevelConfig.PLAYER0;
//                        System.out.println(LevelConfig.currentPlayer);
//                    }else {
//                        setCursorPosgone();
//                    }
//                }else {
//                    if (LevelConfig.chessSelected==null){
//                        setCursorPosgone();
//                    }
//                }
//                LevelConfig.tarGetSelected = null;

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
}
