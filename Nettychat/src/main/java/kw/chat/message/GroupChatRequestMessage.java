package kw.chat.message;

import kw.chat.message.base.Message;

public class GroupChatRequestMessage extends Message {
    private String groupName;
    private String from;
    private String msg;
    public GroupChatRequestMessage(String groupName,String from,String msg){
        this.type = MessageType.GROUPCHAT;
        this.groupName = groupName;
        this.from = from;
        this.msg = msg;
    }
}
