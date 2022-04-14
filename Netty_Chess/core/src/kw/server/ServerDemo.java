package kw.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import kw.mulitplay.game.message.codec.MessageToMessage;
import kw.server.handler.ServerHandler;

public class ServerDemo{
    public static void main(String[] args) {
        try {
            new ServerBootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            System.out.println("---------------------");
                            nioSocketChannel.pipeline().addLast(new MessageToMessage());
                            nioSocketChannel.pipeline().addLast(new ServerHandler());
                        }
                    }).bind(8888).sync().channel().closeFuture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
