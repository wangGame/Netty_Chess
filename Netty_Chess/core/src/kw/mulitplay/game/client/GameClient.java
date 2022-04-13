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
            MessageToMessage message = new MessageToMessage();
            RegisterHandler registerHandler = new RegisterHandler();

            new Bootstrap().group(new NioEventLoopGroup())
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>(){
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(message);
                            ch.pipeline().addLast(registerHandler);
//                            ch.pipeline().addLast()
                        }
                    }).connect("127.0.0.1",8888).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
