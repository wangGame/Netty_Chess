package kw.mulitplay.game.message.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kw.mulitplay.game.message.NNMessage;
import kw.mulitplay.game.message.base.Message;

public class GameHandler extends ChannelInboundHandlerAdapter {
    //链接上注册channel  wait result
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        NNMessage.channel = ctx.channel();
        NNMessage.register();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (msg instanceof Message) {
            NNMessage.dispatchMessage((Message) msg,ctx);
        }
    }
}
