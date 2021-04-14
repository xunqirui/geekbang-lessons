package org.geektimes.serialize;

import org.geektimes.serialize.event.EventMulticaster;

/**
 * JavaSrializingProvider
 *
 * @author qrXun on 2021/4/14
 */
public class ByteArraySerializingProvider implements SerializingProvider{

    private Serializing serializing;

    @Override
    public <A> Serializing<A> getSerializing() {
        if (serializing == null){
            serializing = new ByteArraySerializing(new EventMulticaster());
        }
        return serializing;
    }
}
