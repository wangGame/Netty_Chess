package player.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import player.message.DecoMessage;
import player.server.handler.ChannelInHander;

public class App {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        try {
            new ServerBootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DecoMessage());
                            ch.pipeline().addLast(new ChannelInHander());
                        }
                    }).bind(8888).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
