package kw.chat.message;

import kw.chat.message.base.Message;

public class LoginResponseMessage extends CommonMessage {

    public LoginResponseMessage(){
        type = MessageType.LOGINRESPONSE;
    }

}
