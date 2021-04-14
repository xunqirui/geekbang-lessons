package org.geektimes.serialize;

import java.util.ServiceLoader;

/**
 * SerializingGetting
 *
 * @author qrXun on 2021/4/14
 */
public class SerializingGetting {

    public static SerializingProvider getSerializingProvider(){
        return getSerializingProvider(Thread.currentThread().getContextClassLoader());
    }

    public static SerializingProvider getSerializingProvider(ClassLoader classLoader){
        ServiceLoader<SerializingProvider> loader = ServiceLoader.load(SerializingProvider.class, classLoader);
        if (loader.iterator().hasNext()){
            return loader.iterator().next();
        }
        return new ByteArraySerializingProvider();
    }

}
