package player.server.handler;

import java.awt.Checkbox;
import java.util.HashMap;
import java.util.UUID;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import player.message.message.ChessMessage;
import player.server.dispatch.ChessDispatch;

public class ChannelInHander extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        if (msg instanceof ChessMessage) {
            ChessDispatch.dispatch((ChessMessage) msg,ctx);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ChessDispatch.remove(ctx);
    }
}
