package kw.mulitplay.game.message;

//import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;

public class MoveMessage extends Message {
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int clickType;
    public MoveMessage(int startX,int startY,int endX,int endY){
        type = MessageType.move;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
//        uuid = LevelConfig.userUUID1;
//        clickType = LevelConfig.clickType;
    }

    public int getClickType() {
        return clickType;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

}
