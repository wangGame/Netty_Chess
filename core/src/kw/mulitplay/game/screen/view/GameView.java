package kw.mulitplay.game.screen.view;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.data.Chess;
import kw.mulitplay.game.data.ChessData;

public class GameView extends Group {
    public GameView(){
        setSize(720,1280);
        setDebug(true);
        initView();
        initChessEr();
    }

    public void initView(){
        Image image = new Image(Asset.getInstance().getTexture("qizi/board.jpg"));
        addActor(image);
        image.setPosition(Config.GAME_WIDTH/2,getHeight()/2,Align.center);
        image.setOrigin(Align.center);
        image.setScale(1.5F);
    }

    public void initChessEr(){
        Group chessGroup = new Group();
        addActor(chessGroup);
        chessGroup.setDebug(true);
        chessGroup.setSize(Config.chessSize*9,Config.chessSize*10);
        chessGroup.setPosition(getWidth()/2,getHeight()/2,Align.center);
        ChessData data = new ChessData();
        int[][] chessData = data.getData();
        for (int i = 0; i < chessData.length; i++) {
            for (int i1 = 0; i1 < chessData[0].length; i1++) {
                if (chessData[i][i1]==Config.VALUE){
                    Chess chess = new Chess();
                    chess.setPosX(i);
                    chess.setPosY(i1);
                    chessGroup.addActor(chess);
                }
            }
        }
    }
}
