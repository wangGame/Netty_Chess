package kw.chat.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import kw.chat.constant.Constant;
import kw.chat.message.base.Message;
import kw.chat.serializable.MySerializable;

import java.io.Serializable;
import java.util.List;

/*- 魔数   （4）
- 版本   （1）
- 序列化类型   （4）
- 消息类型  （4）
- 消息长度  （4）
- 消息内容  （length）*/
public class MessageDecoder extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        ByteBuf outBuf = ctx.alloc().buffer();
        outBuf.writeBytes(new byte[]{2,0,2,2});//魔数
        outBuf.writeByte(1);//版本
        outBuf.writeInt(Constant.JAVA);//序列化类型
        outBuf.writeInt(msg.getType());//消息类型
        byte[] serializable = MySerializable.JAVA.serializable(msg);//序列化
        outBuf.writeInt(serializable.length);//消息长度
        outBuf.writeBytes(serializable);//消息内容
        out.add(outBuf);//写出去
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        byte [] marc = new byte[4];
        msg.readBytes(marc);
        byte version = msg.readByte();
        int seriaType = msg.readInt();
        int messageType = msg.readInt();
        int messageLength = msg.readInt();
        byte[] content = new byte[messageLength];
        msg.readBytes(content);
        Message message = null;
        if (seriaType == 1){
            message = MySerializable.JAVA.deSerializable(Message.class, content);
        }
        out.add(message);
    }
}
