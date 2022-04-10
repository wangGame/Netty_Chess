package kw.mulitplay.game.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.mulitplay.game.netty.message.PlayerInfoMessage;

public class PlayerInfoHandler extends SimpleChannelInboundHandler<PlayerInfoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PlayerInfoMessage msg) throws Exception {

    }
}
