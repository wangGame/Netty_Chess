package kw.mulitplay.game.netty.message.manager;

import kw.mulitplay.game.netty.message.RegisterMessage;
import kw.mulitplay.game.netty.message.ResultAllUserMessage;
import kw.mulitplay.game.netty.message.base.Message;

import java.util.HashMap;
import java.util.Map;

public class MeaageManager {
    public static final int REGISTER = 1;
    public static final int USER_LIST = 2;

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    static {
        messageClasses.put(REGISTER, RegisterMessage.class);
        messageClasses.put(USER_LIST, ResultAllUserMessage.class);
    }

    public MeaageManager(){

    }

    public static Class<? extends Message> getMessageClass(int type){
        return messageClasses.get(type);
    }
}
