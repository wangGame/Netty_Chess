package kw.chat.hander;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.chat.group.Group;
import kw.chat.message.CommonMessage;
import kw.chat.message.GroupCreateMessage;
import kw.chat.session.GroupSession;
import kw.chat.session.GroupSessionFactory;

import java.util.List;
import java.util.Set;

@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateMessage msg) throws Exception {
        String groupName = msg.getGroupName();
        Set<String> members = msg.getMembers();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if (group == null){
            ctx.writeAndFlush(new CommonMessage(true,groupName+" 创建成功！"));
            //通知大家 有人进群
            List<Channel> membersChannel = groupSession.getMembersChannel(groupName);
            for (Channel channel : membersChannel) {
                channel.writeAndFlush(new CommonMessage(true,"已经被拉进群里"+groupName));
            }
        }else {
            ctx.writeAndFlush(new CommonMessage(false,groupName+"已经存在！"));
        }
    }
}
