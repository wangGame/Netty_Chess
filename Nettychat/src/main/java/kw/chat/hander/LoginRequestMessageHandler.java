package kw.chat.hander;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.chat.factory.UserServiceFactory;
import kw.chat.message.CommonMessage;
import kw.chat.message.LoginRequestMessage;
import kw.chat.message.LoginResponseMessage;
import kw.chat.session.SessionFactory;

@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String name = msg.getName();
        String password = msg.getPassword();
        if (UserServiceFactory.userService.login(name,password)) {
            SessionFactory.getSession().bind(ctx.channel(), name);
            LoginResponseMessage message = new LoginResponseMessage();
            message.setReason("success");
            message.setSuccess(true);
            ctx.writeAndFlush(message);
        }else {
            CommonMessage message = new CommonMessage();
            message.setReason("failed");
            message.setSuccess(false);
            ctx.writeAndFlush(message);
        }
    }
}
