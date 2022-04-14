package kw.mulitplay.game.message;

import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;

public class PipeiMessage extends Message {
    private String targetName;
    private int play;
    public PipeiMessage(String targetName,int play){
        type = MessageType.pipei;
        this.targetName = targetName;
        this.play = play;
    }

    public int getPlay() {
        return play;
    }

    public String getTargetName() {
        return targetName;
    }
}
