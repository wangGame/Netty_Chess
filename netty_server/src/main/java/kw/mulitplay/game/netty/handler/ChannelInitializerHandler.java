package kw.mulitplay.game.netty.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import kw.mulitplay.game.netty.codec.MessageToMessage;

//编解码
//注册
//匹配玩家
public class ChannelInitializerHandler extends ChannelInitializer<NioSocketChannel> {
    //编解码
    private MessageToMessageCodec message = new MessageToMessage();
    //注册之后返回了它的用户列表
    private RegisterHandler registerHandler = new RegisterHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(message);
        ch.pipeline().addLast(registerHandler);
    }
}
