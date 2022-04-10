package kw.mulitplay.game.message.base;

import java.io.Serializable;

public class Message implements Serializable {

    protected String uuid;


    @Override
    public String toString() {
        return "Message{" +
                "uuid='" + uuid + '\'' +
                '}';
    }
}

