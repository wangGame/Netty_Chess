package kw.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageCodec;
import kw.chat.decoder.MessageDecoder;
import kw.chat.message.LoginMessage;
import kw.chat.message.base.Message;

public class ChatServer {
    public static void main(String[] args) {
        try {
            new ServerBootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    super.channelRead(ctx, msg);
                                    if(msg instanceof Message){
                                        System.out.println(((LoginMessage) (msg)).getType());
                                    }
                                    System.out.println("-----------------------------");
                                }
                            });
                        }
                    }).bind(8888)
                    .sync()
                    .channel()
                    .closeFuture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
