package kw.mulitplay.game.netty.message.base;

import java.io.Serializable;

public class Message implements Serializable {
    protected byte type;
    protected String uuid;

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "Message{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

    public byte getType() {
        return type;
    }
}

