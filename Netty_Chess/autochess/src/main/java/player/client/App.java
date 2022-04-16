package player.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import player.message.DecoMessage;
import player.client.handler.ChannelInHandler;

public class App {
    public static void main(String[] args) {
        extracted();
    }

    public static void extracted() {
        try {
            new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DecoMessage());
                            ch.pipeline().addLast(new ChannelInHandler());
                        }
                    })
                    .connect("127.0.0.1",8888)
                    .sync().channel().closeFuture();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
