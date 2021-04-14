package org.geektimes.serialize.event;

import java.util.EventObject;

/**
 * SerializingEvent
 *
 * @author qrXun on 2021/4/14
 */
public abstract class SerializingEvent extends EventObject {

    private static final long serialVersionUID = 8453436432108281898L;

    /** System time when the event happened. */
    private final long timestamp;

    public SerializingEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * Return the system time in milliseconds when the event occurred.
     */
    public final long getTimestamp() {
        return this.timestamp;
    }
}
