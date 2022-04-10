package kw.mulitplay.game.message;

import java.util.UUID;

import kw.mulitplay.game.message.base.Message;

public class RegisterMessage extends Message {
    public RegisterMessage(){
        uuid = UUID.randomUUID().toString();
    }
}
