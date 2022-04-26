package kw.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import kw.chat.decoder.MessageDecoder;
import kw.chat.hander.LoginRequestMessageHandler;
import kw.chat.hander.SendRequestMessageHandler;
import kw.chat.hander.GroupCreateRequestMessageHandler;

public class ChatServer {
    public static void main(String[] args) {
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageDecoder messageDecoder = new MessageDecoder();
        LoginRequestMessageHandler loginRequestMessageHandler = new LoginRequestMessageHandler();
        SendRequestMessageHandler sendRequestMessageHandler = new SendRequestMessageHandler();
        GroupCreateRequestMessageHandler groupCreateRequestMessageHandler = new GroupCreateRequestMessageHandler();
        try {
            new ServerBootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(loggingHandler);
                            //编解码
                            ch.pipeline().addLast(messageDecoder);
                            ch.pipeline().addLast(loginRequestMessageHandler);
                            ch.pipeline().addLast(sendRequestMessageHandler);
                            ch.pipeline().addLast(groupCreateRequestMessageHandler);
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
