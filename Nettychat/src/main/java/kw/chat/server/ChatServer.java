package kw.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import kw.chat.decoder.MessageDecoder;
import kw.chat.hander.LoginRequestMessageHandler;
import kw.chat.hander.SendRequestMessageHandler;
import kw.chat.hander.GroupCreateRequestMessageHandler;
import kw.chat.message.PingMessage;

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
                            ch.pipeline().addLast(messageDecoder);
                            ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
                    // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                    super.channelRead(ctx, msg);
                                    if (msg instanceof PingMessage) {
                                        System.out.println(((PingMessage)(msg)).getType());
                                    }
                                    System.out.println("---------------------------");
                                }

                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发了读空闲事件
                                    if (event.state() == IdleState.READER_IDLE) {
                                        System.out.println("已经 5s 没有读到数据了");
                                        ctx.channel().close();
                                    }
                                    System.out.println("-----------");
                                }
                            });
                            //编解码
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
