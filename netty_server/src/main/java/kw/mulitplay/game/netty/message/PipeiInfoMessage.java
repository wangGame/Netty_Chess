package kw.mulitplay.game.netty.message;

import kw.mulitplay.game.netty.message.base.Message;

public class PipeiInfoMessage extends Message {
    private String my;
    private String target;

    public PipeiInfoMessage(){

    }

    public void setMy(String my) {
        this.my = my;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMy() {
        return my;
    }

    public String getTarget() {
        return target;
    }
}
