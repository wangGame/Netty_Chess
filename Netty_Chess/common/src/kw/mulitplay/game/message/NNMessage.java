package kw.mulitplay.game.message;

import java.rmi.MarshalledObject;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import kw.log.NLog;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;

public class NNMessage {
    public static Channel channel;
    @SuppressWarnings("NewApi")
    public static Queue<Message> queue = new LinkedBlockingDeque<>();

    public static void dispatchMessage(Message msg, ChannelHandlerContext ctx) {
        byte type = msg.getType();
        switch (type){
            case MessageType.pipei:
                //匹配  我们先连接服务器   连接之后   服务器会进行匹配
//                匹配就是  对方是谁
//                LevelConfig.currentStatus = LevelConfig.pipeiSuccess;
                String uuid = ((PipeiMessage) (msg)).getTargetName();
                System.out.println("-------------------------pipei"+uuid);
//                LevelConfig.userUUID1 = uuid;
//                LevelConfig.play = ((PipeiMessage)(msg)).getPlay();
//                LevelConfig.currentStatus = LevelConfig.startGame;
                break;
            case MessageType.level:
//                离开
//                LevelConfig.currentStatus = LevelConfig.level;
                ctx.close();
                break;
            case MessageType.move:
//                移动
                queue.add(msg);
                break;
            case MessageType.renshu:
//                认输
//                LevelConfig.currentStatus = LevelConfig.success;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static void register(){
        RegisterMessage message = new RegisterMessage();
        NLog.i("register message %s",message.toString());
        NLog.i("current user uuid "+message.getUuid());
        channel.writeAndFlush(message);
    }

    public static void move(MoveMessage message){
        NLog.i("send player option ",message.toString());
        channel.writeAndFlush(message);
    }
}
