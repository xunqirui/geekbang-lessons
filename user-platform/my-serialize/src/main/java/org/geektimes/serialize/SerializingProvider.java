package org.geektimes.serialize;

/**
 * SerializingProvider
 *
 * @author qrXun on 2021/4/14
 */
public interface SerializingProvider {

    /**
     * get the serializing
     * @param <A> the type of after serializing
     * @return
     */
    <A> Serializing<A> getSerializing();

}
