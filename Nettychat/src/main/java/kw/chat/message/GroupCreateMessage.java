package kw.chat.message;

import kw.chat.message.base.Message;

import java.util.Set;

public class GroupCreateMessage extends Message {
    private String groupName;
    private Set<String> members;

    public GroupCreateMessage(String groupName,Set<String> members){
        this.groupName = groupName;
        this.members = members;
        this.type = MessageType.GROUPCREATE;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public String getGroupName() {
        return groupName;
    }

    public Set<String> getMembers() {
        return members;
    }
}
