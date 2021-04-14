package org.geektimes.serialize.event;

import org.geektimes.util.ReflectUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * EventMulticaster
 *
 * @author qrXun on 2021/4/14
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EventMulticaster {

    private List<SerializingEventListener> listeners = new LinkedList<>();

    /**
     * register a {@link SerializingEventListener}.
     * @param listener
     */
    public void registerListener(SerializingEventListener listener){
        listeners.add(listener);
    }

    public void publishEvent(SerializingEvent serializingEvent){
        listeners.forEach(listener -> {
            // 获取泛型类型
            try {
                Class<?> typeClass = ReflectUtil.getGenericTypeClass(listener.getClass());
                Class<?> eventTypeClass = serializingEvent.getClass();
                if (typeClass.equals(eventTypeClass)){
                    listener.onSerializingEvent(serializingEvent);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

}
