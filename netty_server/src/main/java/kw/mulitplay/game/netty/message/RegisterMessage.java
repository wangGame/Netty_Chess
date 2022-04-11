package kw.mulitplay.game.netty.message;

import kw.mulitplay.game.netty.message.base.Message;
import kw.mulitplay.game.netty.message.manager.MeaageManager;


public class RegisterMessage extends Message {
    public RegisterMessage(){
        type = MeaageManager.REGISTER;
    }

    @Override
    public String toString() {
        return super.toString();
    }


}
