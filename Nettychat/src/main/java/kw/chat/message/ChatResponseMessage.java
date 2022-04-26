package kw.chat.message;

import kw.chat.message.base.Message;

public class ChatResponseMessage extends Message {
    private String to;
    private String msg;
    public ChatResponseMessage(String to,String msg){
        this.to = to;
        this.msg = msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
