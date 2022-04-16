package player.message.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializerAlgorithm {
    public static <T> byte[] javaSerializer(T object){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("序列化失败", e);
        }
    }

    public static <T> T javaDeSerializer(byte[] content, Class<T> aClass){
        try {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(content));
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("序列化失败", e);
        }
    }


}
