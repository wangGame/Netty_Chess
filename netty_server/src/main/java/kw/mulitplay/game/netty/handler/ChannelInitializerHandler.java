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
    //注册之后返回了列表  下来选择一个用户进行一起玩耍
    private PipeiPlayerHandler pipeiPlayerHandler =  new PipeiPlayerHandler();
    //处理用户请求
    private

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ch.pipeline().addLast(message);
        ch.pipeline().addLast(registerHandler);
        ch.pipeline().addLast(pipeiPlayerHandler);
    }
}
