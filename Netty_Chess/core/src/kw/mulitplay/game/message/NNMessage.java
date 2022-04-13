package kw.mulitplay.game.message;

import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import kw.mulitplay.game.config.LevelConfig;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;

public class NNMessage {
    public static Channel channel;
    public static Queue<Message> queue = new LinkedBlockingDeque<>();

    public static void dispatchMessage(Message msg, ChannelHandlerContext ctx) {
        byte type = msg.getType();

        switch (type){
            case MessageType.pipei:
                LevelConfig.currentStatus = LevelConfig.pipeiSuccess;
                break;
            case MessageType.level:
                LevelConfig.currentStatus = LevelConfig.level;
                ctx.close();
                break;
            case MessageType.move:
                queue.add(msg);
                break;
            case MessageType.renshu:
                LevelConfig.currentStatus = LevelConfig.success;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static void sendMessage(){

    }
}
