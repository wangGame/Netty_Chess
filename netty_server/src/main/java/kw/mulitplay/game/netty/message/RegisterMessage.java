package kw.mulitplay.game.netty.message;

import kw.mulitplay.game.netty.message.base.Message;
import kw.mulitplay.game.netty.message.manager.MeaageManager;

import java.util.UUID;

public class RegisterMessage extends Message {
    public RegisterMessage(){
        uuid = UUID.randomUUID().toString();
        type = MeaageManager.REGISTER;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
