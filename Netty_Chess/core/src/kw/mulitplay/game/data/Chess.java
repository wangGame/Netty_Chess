package kw.mulitplay.game.data;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;
import kw.mulitplay.game.config.LevelConfig;

public class Chess extends Group {
    public Image image ;
    private int posX;
    private int posY;
    private int player;
    private char qiziName;
    public Chess(char path){
        if (path>='a'&&path<='z'){
            qiziName = path;
            image = new Image(Asset.getInstance().getTexture("qizi/b"+path+".png"));
            player = LevelConfig.PLAYER0;
        }else{
            qiziName = Character.toLowerCase(path);
            image = new Image(Asset.getInstance().getTexture("qizi/b"+path+"2.png"));
            player = LevelConfig.PLAYER1;
        }
        addActor(image);
        image.setSize(Config.chessSize,Config.chessSize);
        setSize(Config.chessSize,Config.chessSize);
        image.setPosition(getWidth()/2,getHeight()/2, Align.center);
        addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {


                if (LevelConfig.currentPlayer == player) {
                    LevelConfig.clickType = 0;
                    super.clicked(event, x, y);
                    System.out.println("chess click");
                    LevelConfig.chessSelected = Chess.this;




                }else {
                    LevelConfig.clickType = 1;



                }
            }
        });
    }

    public void setPosX(int posX) {
        this.posX = posX;
        setX(Config.chessSize*posX);
    }

    public void setPosY(int posY) {
        this.posY = posY;
        setY(posY*Config.chessSize);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
    }

    public char getQiziName() {
        return qiziName;
    }

    public void setPosXY(int xx, int yy) {
        this.posX = xx;
        this.posY = yy;
        setPosition(xx * Config.chessSize, yy * Config.chessSize);
    }
}
