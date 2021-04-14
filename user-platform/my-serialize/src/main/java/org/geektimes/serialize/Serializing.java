package org.geektimes.serialize;

import org.geektimes.serialize.event.EventMulticaster;
import org.geektimes.serialize.event.SerializingEventListener;

/**
 * Serializing object
 *
 * @param <A> the type of after serialize
 * @author qrXun on 2021/4/14
 */
public interface Serializing<A> {

    /**
     * serialize the object to the type of A.
     *
     * @param before
     * @return
     */
    A serialize(Object before);

    /**
     * deserialize the type of A to the type of T.
     *
     * @param after
     * @return
     */
    <T> T deserialize(A after);

    /**
     * register a {@link SerializingEventListener}.
     *
     * @param listener
     */
    void registerSerializingListener(SerializingEventListener<?> listener);

    EventMulticaster getEventMulticaster();

}
