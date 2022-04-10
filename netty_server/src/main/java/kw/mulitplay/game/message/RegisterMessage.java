package kw.mulitplay.game.message;

import kw.mulitplay.game.message.base.Message;

import java.util.UUID;


public class RegisterMessage extends Message {
    public RegisterMessage(){
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
