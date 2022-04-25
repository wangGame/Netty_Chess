package kw.chat.serializable;

import kw.chat.message.base.Message;

import java.io.*;

public enum MySerializable {
    JAVA{
        @Override
        public <T> byte[] serializable(T message){
            ByteArrayOutputStream outputStream= new ByteArrayOutputStream();
            try {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                objectOutputStream.writeObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return outputStream.toByteArray();
        }

        @Override
        public <T> T deSerializable(Class<T> tClass, byte[] bytes){
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
                return (T) inputStream.readObject();

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException("  --   ");
            }
        }
    };
    public <T> byte[] serializable(T message){
        return null;
    }

    public <T> T deSerializable(Class<T> tClass, byte[] bytes){
        return null;
    }
}
