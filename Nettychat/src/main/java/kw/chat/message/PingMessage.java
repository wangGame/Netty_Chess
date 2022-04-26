package kw.chat.message;

import kw.chat.message.base.Message;

public class PingMessage extends Message {
    public PingMessage(){
        this.type = MessageType.PINGMESSAGE;
    }
}
