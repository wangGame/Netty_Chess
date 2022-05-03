package kw.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import kw.chat.decoder.MessageDecoder;
import kw.chat.message.*;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            CountDownLatch WAIT_FOR_LOGIN = new CountDownLatch(1);
            AtomicBoolean EXIT = new AtomicBoolean();
            AtomicBoolean LOGIN = new AtomicBoolean();
            LoggingHandler handler = new LoggingHandler();
            new Bootstrap()
                    .group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(handler);
                            ch.pipeline().addLast(new MessageDecoder());
                            ch.pipeline().addLast(new IdleStateHandler(0, 3, 0));
                            // ChannelDuplexHandler 可以同时作为入站和出站处理器
                            ch.pipeline().addLast(new ChannelDuplexHandler() {
                                // 用来触发特殊事件
                                @Override
                                public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
//                                    super.userEventTriggered();
                                    IdleStateEvent event = (IdleStateEvent) evt;
                                    // 触发了写空闲事件
                                    if (event.state() == IdleState.WRITER_IDLE) {
                                        //                                log.debug("3s 没有写数据了，发送一个心跳包");
                                        ctx.writeAndFlush(new PingMessage());
                                    }
                                }
                            });
                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                                @Override
                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                    super.channelRead(ctx, msg);
                                    System.out.println("---------收到消息");
                                    if (msg instanceof LoginResponseMessage){
                                        LoginResponseMessage message = (LoginResponseMessage) msg;
                                        if (!message.isSuccess()) {
                                            EXIT.set(true);
                                        }
                                        LOGIN.set(true);
                                        WAIT_FOR_LOGIN.countDown();
                                    }else if (msg instanceof ChatResponseMessage){
                                        System.out.println(((ChatResponseMessage) (msg)).getMsg());
                                    }
                                }

                                @Override
                                public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelActive(ctx);
                                    new Thread(()->{
                                        System.out.println("输入用户名：");
                                        String username = scanner.nextLine();
                                        if (EXIT.get()) {
                                            return;
                                        }
                                        System.out.println("输入密码：");
                                        String password = scanner.nextLine();
                                        if (EXIT.get()){
                                            return;
                                        }
                                        //使用一个循环来进行
                                        LoginRequestMessage message = new LoginRequestMessage();
                                        message.setName(username);
                                        message.setPassword(password);
                                        ctx.writeAndFlush(message);
                                        System.out.println("login already run");
                                        try {
                                            WAIT_FOR_LOGIN.await();
                                        }catch (InterruptedException e){
                                            e.printStackTrace();
                                        }

                                        if (!LOGIN.get()) {
                                            ctx.channel().closeFuture();
                                            return;
                                        }

                                        while (true){
                                            System.out.println("==================================");
                                            System.out.println("send [username] [content]");
                                            System.out.println("gsend [group name] [content]");
                                            System.out.println("gcreate [group name] [m1,m2,m3...]");
                                            System.out.println("gmembers [group name]");
                                            System.out.println("gjoin [group name]");
                                            System.out.println("gquit [group name]");
                                            System.out.println("quit");
                                            System.out.println("==================================");
                                            String command = null;
                                            try {
                                                command = scanner.nextLine();
                                            }catch (Exception e){
                                                break;
                                            }
                                            if (EXIT.get()){
                                                return;
                                            }
                                            if (command==null)continue;
                                            String[] value = command.split(" ");
                                            switch (value[0]){
                                                case "send":
                                                    ctx.writeAndFlush(new ChatRequestMessage(value[0],value[1],value[2]));
                                                    break;
                                                case "gsend":
                                                    ctx.writeAndFlush(new GroupChatRequestMessage(value[0],value[1],value[2]));
                                                    break;
                                                case "gcreate":
                                                    Set<String> set = new HashSet<>();
                                                    set.add(username);
                                                    GroupCreateMessage groupCreateMessage = new GroupCreateMessage(value[0],set);
                                                    ctx.writeAndFlush(groupCreateMessage);
                                                    break;
                                                case "gmembers":
                                                    ctx.writeAndFlush(new GroupMembersRequestMessage(username));
                                                    break;
                                                case "gjoin":
                                                    //拉人进入
                                                    ctx.writeAndFlush(new GroupJoinPeopleMessage(username));
                                                    break;
                                                case "gquit":
                                                    ctx.writeAndFlush(new GroupQuitRequestMessage(value[0],value[1]));
                                                    break;
                                                case "quit":
                                                    ctx.channel().close();
                                                    return;
                                            }
                                        }
                                    }).start();
                                }

                                @Override
                                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                                    super.channelInactive(ctx);
                                    EXIT.set(true);
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    super.exceptionCaught(ctx, cause);
                                    EXIT.set(true);
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
