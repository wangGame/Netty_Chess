package client.netty;

import io.netty.channel.Channel;

public class MessageSend {
    public static Channel channel;
    public MessageSend(Channel channel){
        this.channel = channel;
    }

    public static void sendNickName(){
        System.out.println("end");
        channel.writeAndFlush("<#NICK_NAME#> test");
    }
}
