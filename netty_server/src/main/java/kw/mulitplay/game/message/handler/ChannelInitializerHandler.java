package kw.mulitplay.game.message.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import kw.mulitplay.game.message.codec.MessageToMessage;

public class ChannelInitializerHandler extends ChannelInitializer<NioSocketChannel> {
    private MessageToMessageCodec message = new MessageToMessage();
    private RegisterHandler registerHandler = new RegisterHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
//        ch.pipeline().addLast(new StringEncoder());
//        ch.pipeline().addLast(new StringDecoder());
//        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//            @Override
//            public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                super.channelActive(ctx);
//            }
//
//            @Override
//            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                super.channelRead(ctx, msg);
//                System.out.println(msg);
//            }
//        });
//
        ch.pipeline().addLast(message);
        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
            @Override
            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                super.channelRead(ctx, msg);
                System.out.println("--------read");
            }
        });

    }
}
