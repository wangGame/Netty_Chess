package kw.chat.message;

import kw.chat.message.base.Message;

public class GroupJoinPeopleMessage extends Message {
    private String username;
    public GroupJoinPeopleMessage(String username){
        this.username = username;
        type = MessageType.JOINPEOPLE;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
