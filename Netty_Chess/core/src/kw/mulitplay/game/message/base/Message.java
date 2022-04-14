package kw.mulitplay.game.message.base;

import java.io.Serializable;

public class Message implements Serializable {
    protected String uuid;
    protected byte type;
    @Override
    public String toString() {
        return "Message{" +
                "uuid='" + uuid + '\'' +
                '}';
    }

    public String getUuid() {
        return uuid;
    }

    public byte getType() {
        return type;
    }
}
