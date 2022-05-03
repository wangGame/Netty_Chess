package kw.mulitplay.game.message;

import java.util.UUID;

//import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.codec.MessageToMessage;
import kw.mulitplay.game.message.type.MessageType;

public class RegisterMessage extends Message {

    public RegisterMessage(){
        uuid = UUID.randomUUID().toString();
//        LevelConfig.userUUID = uuid;
        type = MessageType.register;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
