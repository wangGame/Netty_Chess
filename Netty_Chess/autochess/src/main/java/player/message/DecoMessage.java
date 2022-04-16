package player.message;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import player.message.message.ChessMessage;
import player.message.message.Type;
import player.message.serializer.SerializerAlgorithm;

public class DecoMessage extends MessageToMessageCodec<ByteBuf, ChessMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ChessMessage msg, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeInt(msg.getType());
        byte[] bytes = SerializerAlgorithm.javaSerializer(msg);
        int length = bytes.length;
        buffer.writeInt(length);
        buffer.writeBytes(bytes);
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int type = msg.readInt();
        int length = msg.readInt();
        byte[] content = new byte[length];
        msg.readBytes(content, 0, length);
        Class<? extends ChessMessage> aClass = Type.hashMap.get(type);
        ChessMessage message = SerializerAlgorithm.javaDeSerializer(content, aClass);
        out.add(message);
    }
}
