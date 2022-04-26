package kw.chat.hander;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.chat.message.ChatRequestMessage;
import kw.chat.message.ChatResponseMessage;
import kw.chat.message.CommonMessage;
import kw.chat.service.UserServiceImpl;
import kw.chat.session.Session;
import kw.chat.session.SessionFactory;

@ChannelHandler.Sharable
public class SendRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        String to = msg.getTo();
        Channel channel = SessionFactory.getSession().getChannel(to);
        // 在线
        if (channel != null) {
            channel.writeAndFlush(new ChatResponseMessage(msg.getForm(), msg.getMsg()));
        }
        // 不在线
        else {
            ctx.writeAndFlush(new CommonMessage(false, "对方用户不存在或者不在线"));
        }
    }
}
