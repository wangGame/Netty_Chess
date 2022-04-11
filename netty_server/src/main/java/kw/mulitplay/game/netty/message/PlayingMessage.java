package kw.mulitplay.game.netty.message;

import kw.mulitplay.game.netty.message.base.Message;

import java.util.Vector;

public class PlayingMessage extends Message {
    private String from;
    private String to;
    private Vector startPos;
    private Vector targetPos;
    public PlayingMessage(){

    }
}
