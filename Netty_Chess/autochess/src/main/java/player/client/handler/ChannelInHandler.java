package player.client.handler;

import java.util.UUID;

import com.badlogic.gdx.Gdx;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import kw.mulitplay.game.config.LevelConfig;
import player.client.dispatch.ClientDispatch;
import player.message.message.ChessMessage;
import player.message.message.Type;

public class ChannelInHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LevelConfig.context = ctx;
        System.out.println(LevelConfig.context+"---------------------------");
        ChessMessage message = new ChessMessage();
        message.setUuid(UUID.randomUUID().toString());
        LevelConfig.currentPlayerUUID = message.getUuid();
        message.setType(Type.REGISTER);
        ctx.writeAndFlush(message);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        ClientDispatch.dispatch((ChessMessage) msg,ctx);
    }
}
