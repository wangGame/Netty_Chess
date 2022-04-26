package kw.chat.message;

import kw.chat.message.base.Message;

public class GroupMembersRequestMessage extends Message {
    public GroupMembersRequestMessage(String name){
        this.type = MessageType.GROUPMEMBER;
    }
}
