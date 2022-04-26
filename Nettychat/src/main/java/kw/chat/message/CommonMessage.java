package kw.chat.message;

import kw.chat.message.base.Message;

public class CommonMessage extends Message {
    private boolean success;
    private String reason;

    public CommonMessage(){

    }


    public CommonMessage(boolean success,String reason){
        this.success = success;
        this.reason = reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
