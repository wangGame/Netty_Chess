package kw.chat.message;

import kw.chat.message.base.Message;

public class ChatRequestMessage extends Message {
    private String form;
    private String to;
    private String msg;

    public ChatRequestMessage(String from,String to,String msg){
        this.form = from;
        this.to = to;
        this.msg = msg;
        type = MessageType.CHATREQUEST;
    }

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
