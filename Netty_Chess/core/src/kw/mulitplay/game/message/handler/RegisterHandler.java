package kw.mulitplay.game.message.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.mulitplay.game.message.RegisterMessage;


public class RegisterHandler extends SimpleChannelInboundHandler<RegisterMessage> {
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ctx.writeAndFlush(new RegisterMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterMessage msg) throws Exception {

    }
}
