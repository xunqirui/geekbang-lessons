package org.geektimes.serialize.event;

import java.util.EventListener;

/**
 * SerializingEventListener
 *
 * @author qrXun on 2021/4/14
 */
public interface SerializingEventListener<E extends SerializingEvent> extends EventListener {

    void onSerializingEvent(E event);

}
