package kw.server.dispatch;

import java.util.HashMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import kw.mulitplay.game.message.MoveMessage;
import kw.mulitplay.game.message.PipeiMessage;
import kw.mulitplay.game.message.RegisterMessage;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.type.MessageType;

import javax.management.modelmbean.ModelMBean;

public class ServerDataDispatch {

    private static HashMap<String, Channel> hashMap = new HashMap<>();
    public static void disptchMessage(Message msg, ChannelHandlerContext ctx) {
        switch (msg.getType()){
            case MessageType.register:
                String uuid = ((RegisterMessage) (msg)).getUuid();
                String other = null;
                if (hashMap.size()==1){
                    for (String s : hashMap.keySet()) {
                        other = s;
                        PipeiMessage message = new PipeiMessage(uuid,0);
                        hashMap.get(s).writeAndFlush(message);
                    }
                    hashMap.put(uuid,ctx.channel());
                    System.out.println(hashMap.size()+"     "+uuid+"   "+ctx.channel());
                    PipeiMessage message = new PipeiMessage(other,1);
                    ctx.channel().writeAndFlush(message);
                }

                break;
            case MessageType.move:
                String uuid1 = ((MoveMessage) (msg)).getUuid();
                Channel channel = hashMap.get(uuid1);
                channel.writeAndFlush(msg);
            default:
                break;
        }
    }
}
