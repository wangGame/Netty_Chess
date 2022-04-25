package kw.chat.message.base;

import java.io.Serializable;

public class Message implements Serializable {
    protected int type;

    public int getType() {
        return type;
    }
}
