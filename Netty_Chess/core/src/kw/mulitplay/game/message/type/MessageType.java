package kw.mulitplay.game.message.type;

import java.util.HashMap;

import kw.mulitplay.game.message.PipeiMessage;
import kw.mulitplay.game.message.base.Message;

public class MessageType {
    public static HashMap<Byte, Class<? extends Message>> hashMap = new HashMap<Byte, Class<? extends Message>>();
    public final static byte pipei = 0;
    public final static byte level = 1;
    public final static byte move = 2;
    public final static byte renshu = 3;

    static {
        hashMap.put(pipei, PipeiMessage.class);
    }

    public static Class<? extends Message> getClass(byte type){
        return hashMap.get(type);
    }
}
