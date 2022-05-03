package kw.mulitplay.game.message.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Serializer {
    // ??????
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // ?????
    <T> byte[] serialize(T object);

    enum Algorithm implements Serializer{
        Java{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("??????", e);
                }
            }

            @Override
            public <T> byte[] serialize(T object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("?????", e);
                }
            }
        }
    }
}
