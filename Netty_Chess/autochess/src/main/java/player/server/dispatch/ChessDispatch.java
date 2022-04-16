package player.server.dispatch;

import java.util.ArrayList;
import java.util.HashMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import kw.mulitplay.game.config.LevelConfig;
import player.message.message.*;

public class ChessDispatch {
    static HashMap<String, Channel> busyMap = new HashMap<>();
    static HashMap<String,Channel> freeMap = new HashMap<>();
    static HashMap<Channel,String> AllMap = new HashMap<>();
    public static void dispatch(ChessMessage message, ChannelHandlerContext ctx){
        switch (message.getType()){
            case Type.REGISTER:
                register(message,ctx);
                break;
            case Type.LIST:
                showList(message,ctx);
                break;
            case Type.PIPEI:
                pipei(message,ctx);
                break;
            case Type.MOVE:
                move((MoveMessage)message,ctx);
                break;
        }
    }

    private static void move(MoveMessage message, ChannelHandlerContext ctx) {
        String uuid = message.getUuid();
        Channel channel = busyMap.get(uuid);
        channel.writeAndFlush(message);
    }

    private static void pipei(ChessMessage message, ChannelHandlerContext ctx) {
        if (freeMap.size()>0){
            for (String s : freeMap.keySet()) {
                if (!message.getUuid().equals(s)){
                    PipeiMessage res = new PipeiMessage();
                    res.setUuid(s);
                    res.setPlyer('r');
                    ctx.writeAndFlush(res);
                    PipeiMessage res1 = new PipeiMessage();
                    res1.setUuid(message.getUuid());
                    res1.setPlyer('b');
                    Channel channel = freeMap.get(s);
                    channel.writeAndFlush(res1);


                    busyMap.put(s,channel);
                    busyMap.put(message.getUuid(),ctx.channel());
                    freeMap.remove(s);
                    return;
                }
            }
        }
    }

    private static void register(ChessMessage message, ChannelHandlerContext ctx) {
        freeMap.put(message.getUuid(),ctx.channel());
        AllMap.put(ctx.channel(),message.getUuid());
        showList(message,ctx);
    }

    private static void showList(ChessMessage message, ChannelHandlerContext ctx) {
        ListMessage msg = new ListMessage();
        ArrayList<String> list = new ArrayList<>();
        for (String s : freeMap.keySet()) {
            list.add(s);
        }
        msg.setChessMessages(list);
        ctx.writeAndFlush(msg);
    }

    public static void remove(ChannelHandlerContext ctx) {
        String s = AllMap.get(ctx.channel());
        AllMap.remove(ctx.channel());
        if (busyMap.containsKey(s)) {
            busyMap.remove(s);
            LevelConfig.context.writeAndFlush(new EorrMessage("duankia"));
        }
        if (freeMap.containsKey(s)){
            busyMap.remove(s);
        }
    }
}
