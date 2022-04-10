package kw.mulitplay.game.netty.message;

import kw.mulitplay.game.netty.message.base.Message;
import kw.mulitplay.game.netty.message.manager.MeaageManager;

import java.util.ArrayList;
import java.util.List;

public class ResultAllUserMessage extends Message {
    private List<String> uerList;

    public void setUerList(List<String> uerList) {
        this.uerList = uerList;
        type = MeaageManager.USER_LIST;
    }

    public List<String> getUerList() {
        return uerList;
    }
}
