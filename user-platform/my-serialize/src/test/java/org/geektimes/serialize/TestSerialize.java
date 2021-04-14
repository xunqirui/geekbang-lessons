package org.geektimes.serialize;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * TestSerialize
 *
 * @author qrXun on 2021/4/15
 */
public class TestSerialize {

    @Test
    public void testByteArraySerialize(){
        SerializingProvider serializingProvider = SerializingGetting.getSerializingProvider();
        Serializing<byte[]> serializing = serializingProvider.getSerializing();
        String message = "123";
        byte[] arrays = serializing.serialize(message);
        String backMessage = serializing.deserialize(arrays);
        assertNotNull(arrays);
        assertEquals(backMessage, message);
    }

}
