package kw.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import kw.chat.decoder.MessageDecoder;
import kw.chat.message.LoginMessage;

public class ChatClient {
    public static void main(String[] args) {
        try {
            new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                    LoginMessage message = new LoginMessage();
                                    ctx.writeAndFlush(message);
                                }
                            });
                        }
                    })
                    .connect("127.0.0.1",8888)
                    .sync()
                    .channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
