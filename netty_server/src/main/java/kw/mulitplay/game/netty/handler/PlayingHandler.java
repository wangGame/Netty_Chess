package kw.mulitplay.game.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.mulitplay.game.netty.message.PlayingMessage;

public class PlayingHandler extends SimpleChannelInboundHandler<PlayingMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PlayingMessage msg) throws Exception {

    }
}
