package kw.chat.hander;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.chat.factory.UserServiceFactory;
import kw.chat.message.LoginRequestMessage;
import kw.chat.message.LoginResponseMessage;

public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String name = msg.getName();
        String password = msg.getPassword();
        if (UserServiceFactory.userService.login(name,password)) {
            LoginResponseMessage message = new LoginResponseMessage();
            message.setReason("password not match");
            message.setSuccess(true);
            ctx.writeAndFlush(message);
        }
    }
}
