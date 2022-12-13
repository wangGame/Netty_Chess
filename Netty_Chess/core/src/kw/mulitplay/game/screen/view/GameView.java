package kw.mulitplay.game.screen.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.ai.AI;
import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.data.Chess;
import kw.mulitplay.game.data.ChessData;
import kw.mulitplay.game.message.MoveMessage;
import kw.mulitplay.game.message.NNMessage;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;
import kw.mulitplay.game.move.GameMove;
import kw.mulitplay.game.screen.GameLogic;
import kw.mulitplay.xqwlight.Position;

public class GameView extends Group {
    private int compute;
    private Image startCursorRed;
    private Image endCursorRed;
    private Image startCursorBlue;
    private Image endCursorBlue;

    private Group chessGroup;
    private Chess[][] chessObject = new Chess[9][10];
    private GameLogic logic;
    private AI ai;

    public GameView(){
        sizeAndPos();
        initView();
        initChessEveryQizi();
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
        Image chessBgImage = new Image(Asset.getInstance().getTexture("qizi/board.jpg"));
        addActor(chessBgImage);
        chessBgImage.setPosition(getWidth()/2,getHeight()/2,Align.center);
        chessBgImage.setOrigin(Align.center);
        chessBgImage.setScale(1.5F);
        startCursorRed = new Image(Asset.getInstance().getTexture("qizi/selected2.png"));
        startCursorBlue = new Image(Asset.getInstance().getTexture("qizi/selected.png"));
        endCursorRed = new Image(Asset.getInstance().getTexture("qizi/selected2.png"));
        endCursorBlue = new Image(Asset.getInstance().getTexture("qizi/selected.png"));

        setCursorPosgone();
        chessGroup = new Group();
        addActor(chessGroup);
        if (LevelConfig.play == 0){
            chessGroup.setRotation(180);
        }
        chessGroup.addActor(startCursorBlue);
        chessGroup.addActor(startCursorRed);
        chessGroup.addActor(endCursorBlue);
        chessGroup.addActor(endCursorRed);
        startCursorBlue.setScale(1.5F);
        startCursorRed.setScale(1.5F);
        endCursorBlue.setScale(1.5f);
        endCursorRed.setScale(1.5f);
    }

    public void initChessEveryQizi(){
        chessGroup.setSize(Config.chessSize*9,Config.chessSize*10);
        chessGroup.setOrigin(Align.center);
        chessGroup.setPosition(getWidth()/2,getHeight()/2,Align.center);
        ChessData data = new ChessData();
        String str = data.getStr();
        ai = new AI(str);
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
                int xx = (int) (x / Config.chessSize);
                int yy = (int) (y / Config.chessSize);
                setCursorPosVisible(LevelConfig.currentPlayer == LevelConfig.PLAYER1);
                setCursorPos(xx,yy);
                boolean success = false;
                if (LevelConfig.chessSelected!=null) {
                    int startX = LevelConfig.chessSelected.getPosX();
                    int startY = LevelConfig.chessSelected.getPosY();

                    int sq1 = Position.COORD_XY(startX + Position.FILE_LEFT, startY + Position.RANK_TOP);
                    int sq = Position.COORD_XY(xx + Position.FILE_LEFT, yy + Position.RANK_TOP);

                    Position position = ai.getPosition();
                    int mv = Position.MOVE(sq1, sq);
                    logic.setSelected(startX,startY);
                    setStatus();

                    if (!position.legalMove(mv)) {
                        System.out.println("feifa");
                        return;
                    }else {
                        if (!position.makeMove(mv)) {
                            System.out.println("feifa");
                            return;
                        }
                    }
                    success = logic.playerMoveChess(xx, yy, startX, startY);

                    if (position.captured()) {
                        position.setIrrev();
                    }
                }
                //用户操作是成功的
                if (success) {
                    compute = 1;
                    time = 0;
                }
            }
        });
    }

    private void setStatus() {
        LevelConfig.clickType = 3;

    }

    public void setCursorPos(int xx,int yy){
        startCursorRed.setPosition(xx*Config.chessSize,yy*Config.chessSize);
        startCursorBlue.setPosition(xx*Config.chessSize,yy*Config.chessSize);
    }

    public void setCursorPosVisible(boolean isVisible){
        startCursorRed.setVisible(isVisible);
        startCursorBlue.setVisible(!isVisible);
    }

    public void setCursorPosgone(){
        startCursorRed.setVisible(false);
        startCursorBlue.setVisible(false);
    }


    @Override
    public void act(float delta) {
        super.act(delta);
        update();
        computeAi(delta);
    }

    private float time = 0;
    public void computeAi(float delta){
        if (compute == 0)return;
        time+=delta;
        if (time>3){
            compute = 0;
            ai.setChessData();
            GameMove ai = GameView.this.ai.ai();
            logic.playerMoveChess(ai.getEndX(), ai.getEndY(), ai.getStartX(), ai.getStartY());
        }
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
