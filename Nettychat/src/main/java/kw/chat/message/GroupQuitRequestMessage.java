package kw.chat.message;

import kw.chat.message.base.Message;

public class GroupQuitRequestMessage extends Message {
    private String groupName;
    private String username;
    public GroupQuitRequestMessage(String groupName,String username) {
        this.groupName = groupName;
        this.username = username;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupName() {
        return groupName;
    }
}
