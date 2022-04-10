package kw.mulitplay.game.message.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.mulitplay.game.message.RegisterMessage;


public class RegisterHandler extends SimpleChannelInboundHandler<RegisterMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterMessage msg) throws Exception {

    }
}
