package kw.mulitplay.game.message.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import kw.mulitplay.game.message.base.Message;
import kw.mulitplay.game.message.serializer.Serializer;
import kw.mulitplay.game.message.type.MessageType;


@ChannelHandler.Sharable
public class MessageToMessage extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        /**
         * 魔数  2022
         * version 1
         * 预留  4
         * 长度  4
         * 内容
         */
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(new byte[]{2,0,2,2});
        buffer.writeByte(1);
        buffer.writeBytes(new byte[]{-1,-1,-1});
        buffer.writeByte(msg.getType());
        byte[] serialize = Serializer.Algorithm.Java.serialize(msg);
        buffer.writeInt(serialize.length);
        buffer.writeBytes(serialize);
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        /**
         * 魔数  2022
         * version 1
         * 预留  4
         * 长度  4
         * 内容
         */
        byte[] bytes = new byte[4];
        msg.readBytes(bytes);
        System.out.println("魔数");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
        System.out.println();
        byte b = msg.readByte();
        System.out.println(b);
        for (int i = 0; i < 3; i++) {
            msg.readByte();
        }
        //获取类型
        byte b1 = msg.readByte();
        Class<? extends Message> aClass = MessageType.getClass(b1);
        int length = msg.readInt();
        byte[] content = new byte[length];
        msg.readBytes(content, 0, length);
        Message deserialize = Serializer.Algorithm.Java.deserialize(aClass, content);
        out.add(deserialize);
    }
}
