package kw.chat.message;

import kw.chat.message.base.Message;

import static kw.chat.message.MessageType.*;

public class LoginRequestMessage extends Message {
    private String name;
    private String password;

    public LoginRequestMessage(){
        type = LOGINREQUEST;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
