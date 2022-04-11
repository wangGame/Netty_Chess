package kw.mulitplay.game.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.mulitplay.game.netty.message.PipeiInfoMessage;
import kw.mulitplay.game.netty.message.CommonMassage;
import kw.mulitplay.game.service.PlayerRegister;

/**
 * 匹配玩家
 */
public class PipeiPlayerHandler extends SimpleChannelInboundHandler<PipeiInfoMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PipeiInfoMessage msg) throws Exception {
        //这个我认为不需要存储， 因为可以每次带自己的uuid过来
        String my = msg.getMy();
        String target = msg.getTarget();
        boolean connect = PlayerRegister.playerRegister().connect(target);
        if (connect){
            ctx.writeAndFlush(new CommonMassage("匹配成功"));
        }else {
            ctx.writeAndFlush(new CommonMassage("匹配失败"));
        }
    }
}
