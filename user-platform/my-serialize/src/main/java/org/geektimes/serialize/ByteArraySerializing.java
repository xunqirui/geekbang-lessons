package org.geektimes.serialize;

import org.geektimes.serialize.event.EventMulticaster;
import org.geektimes.serialize.event.SerializingEventListener;

import java.io.*;

/**
 * JavaSerializing
 *
 * @author qrXun on 2021/4/14
 */
public class ByteArraySerializing implements Serializing<byte[]>{

    private final EventMulticaster eventMulticaster;

    public ByteArraySerializing(EventMulticaster eventMulticaster) {
        this.eventMulticaster = eventMulticaster;
    }

    @Override
    public byte[] serialize(Object before) {
        return doSerialize(before);
    }

    @Override
    public <T> T deserialize(byte[] after) {
        return doDeserialize(after);
    }

    @Override
    public void registerSerializingListener(SerializingEventListener<?> listener) {
        eventMulticaster.registerListener(listener);
    }

    @Override
    public EventMulticaster getEventMulticaster() {
        return this.eventMulticaster;
    }

    // 是否可以抽象出一套序列化和反序列化的 API
    private byte[] doSerialize(Object value){
        byte[] bytes = null;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            // Key -> byte[]
            objectOutputStream.writeObject(value);
            bytes = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    private <T> T doDeserialize(byte[] bytes){
        if (bytes == null) {
            return null;
        }
        T value = null;
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            // byte[] -> Value
            value = (T) objectInputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return value;
    }
}
