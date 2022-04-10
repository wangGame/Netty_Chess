package kw.mulitplay.game.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import kw.mulitplay.game.message.RegisterMessage;
import kw.mulitplay.game.message.codec.MessageToMessage;
import kw.mulitplay.game.message.handler.RegisterHandler;

public class GameClient {
    public static void main(String[] args) {
        try {
            RegisterHandler registerHandler = new RegisterHandler();
            MessageToMessage message = new MessageToMessage();
            new Bootstrap().group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>(){
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new StringEncoder());
//                            ch.pipeline().addLast(new StringDecoder());
//                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                                @Override
//                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
//                                    super.channelActive(ctx);
//                                    ctx.writeAndFlush("xxxxxxx");
//                                }
//                            });
                            ch.pipeline().addLast(message);
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                    ctx.writeAndFlush(new RegisterMessage());
                                }
                            });
                        }
                    }).connect("127.0.0.1",8888).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
