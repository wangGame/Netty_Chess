package kw.mulitplay.game.netty.message;

import kw.mulitplay.game.dao.PlayerDao;
import kw.mulitplay.game.netty.handler.PlayerInfoHandler;
import kw.mulitplay.game.netty.message.base.Message;

public class PlayerInfoMessage extends Message {
    private String from;
    private String to;

    public PlayerInfoMessage(){

    }
}
