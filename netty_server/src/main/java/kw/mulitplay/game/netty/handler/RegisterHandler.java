package kw.mulitplay.game.netty.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import kw.mulitplay.game.netty.message.RegisterMessage;
import kw.mulitplay.game.netty.message.ResultAllUserMessage;
import kw.mulitplay.game.service.PlayerRegister;

import java.util.ArrayList;

/**
 * 注册 之后  在返回用户列表
 */
@ChannelHandler.Sharable
public class RegisterHandler extends SimpleChannelInboundHandler<RegisterMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterMessage msg) throws Exception {
        PlayerRegister.playerRegister().rigister(msg.getUuid(),ctx.channel());
        ArrayList<String> userList = PlayerRegister.playerRegister().getUserList();
        ResultAllUserMessage message = new ResultAllUserMessage();
        message.setUerList(userList);
        ctx.writeAndFlush(message);
    }
}
