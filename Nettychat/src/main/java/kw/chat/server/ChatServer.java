package kw.chat.server;

import com.sun.javafx.util.Logging;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import kw.chat.decoder.MessageDecoder;
import kw.chat.hander.LoginRequestMessageHandler;
import kw.chat.message.LoginRequestMessage;
import kw.chat.message.base.Message;

public class ChatServer {
    public static void main(String[] args) {
        LoggingHandler loggingHandler = new LoggingHandler();
        try {
            new ServerBootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new LoginRequestMessageHandler());

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
