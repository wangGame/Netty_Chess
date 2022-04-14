package kw.mulitplay.game.screen.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.data.Chess;
import kw.mulitplay.game.data.ChessData;
import kw.mulitplay.game.message.MoveMessage;
import kw.mulitplay.game.message.NNMessage;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;
import kw.mulitplay.game.screen.GameLogic;
import kw.mulitplay.game.screen.game.GameGuiZe;

public class GameView extends Group {
    private Image cursorRed;
    private Image cursorBlue;
    private Group chessGroup;
    private Chess[][] chessObject = new Chess[9][10];
    private GameLogic logic;

    public GameView(){
        sizeAndPos();
        initView();
        initChessEr();
        addListener();
        logic = new GameLogic(chessObject,new Listener(){
            @Override
            public void setCursorPosgone() {
                GameView.this.setCursorPosgone();
            }
        });
    }

    public interface Listener{
        void setCursorPosgone();
    }

    private void sizeAndPos() {
        setSize(720,1280);
        setPosition(Config.GAME_WIDTH/2,Config.GAME_HIGHT/2, Align.center);
    }

    public void initView(){
        chessGroup = new Group();
        if (LevelConfig.play == 0){
            chessGroup.setRotation(180);
        }
        Image image = new Image(Asset.getInstance().getTexture("qizi/board.jpg"));
        addActor(image);
        image.setPosition(getWidth()/2,getHeight()/2,Align.center);
        image.setOrigin(Align.center);
        image.setScale(1.5F);
        cursorRed = new Image(Asset.getInstance().getTexture("qizi/selected2.png"));
        cursorBlue = new Image(Asset.getInstance().getTexture("qizi/selected.png"));
        setCursorPosgone();
        chessGroup.addActor(cursorBlue);
        chessGroup.addActor(cursorRed);
        cursorRed.setDebug(true);
        cursorBlue.setDebug(true);
        cursorBlue.setScale(1.5F);
        cursorRed.setScale(1.5F);
    }

    public void initChessEr(){
        addActor(chessGroup);
        chessGroup.setDebug(true);
        chessGroup.setSize(Config.chessSize*9,Config.chessSize*10);
        chessGroup.setOrigin(Align.center);
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
                    chess.setOrigin(Align.center);
                }
            }
        }
    }

    public void addListener(){
        chessGroup.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                if (LevelConfig.play != LevelConfig.currentPlayer)return;
                int xx = (int) (x / Config.chessSize);
                int yy = (int) (y / Config.chessSize);
                setCursorPosVisible(LevelConfig.currentPlayer == LevelConfig.PLAYER1);
                setCursorPos(xx,yy);
                if (LevelConfig.chessSelected!=null) {
                    int startX = LevelConfig.chessSelected.getPosX();
                    int startY = LevelConfig.chessSelected.getPosY();
                    logic.playerMoveChess(xx,yy,startX,startY);
                    MoveMessage message = new MoveMessage(xx,yy,startX,startY);
                    NNMessage.move(message);
                }
                setStatus();
            }
        });
    }

    private void setStatus() {
        if (LevelConfig.chessSelected == null){
            setCursorPosgone();
        }
        LevelConfig.clickType = 3;
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
    public void act(float delta) {
        super.act(delta);
        update();
    }

    public void update(){
        while (NNMessage.queue.size() > 0) {
            Message remove = NNMessage.queue.remove();
            if (remove.getType() == MessageType.move){
                int startX = ((MoveMessage) (remove)).getStartX();
                int startY = ((MoveMessage) (remove)).getStartY();
                int endX = ((MoveMessage) (remove)).getEndX();
                int endY = ((MoveMessage) (remove)).getEndY();
                LevelConfig.clickType = ((MoveMessage)(remove)).getClickType();
                logic.playerMoveChess(startX,startY,endX,endY);
                setStatus();
            }
        }
    }
}
