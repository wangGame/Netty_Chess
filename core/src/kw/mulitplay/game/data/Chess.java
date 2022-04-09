package kw.mulitplay.game.data;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;

import kw.mulitplay.game.asset.Asset;
import kw.mulitplay.game.config.Config;

public class Chess extends Group {
    public Image image ;
    private int posX;
    private int posY;
    public Chess(){
        image = new Image(Asset.getInstance().getTexture("qizi/bk2.png"));
        addActor(image);
        image.setSize(Config.chessSize,Config.chessSize);
        image.setPosition(getWidth()/2,getHeight()/2, Align.center);
        setSize(Config.chessSize,Config.chessSize);
    }

    public void setPosX(int posX) {
        this.posX = posX;
        image.setX(Config.chessSize*posX);
    }

    public void setPosY(int posY) {
        this.posY = posY;
        image.setY(posY*Config.chessSize);
    }

}
