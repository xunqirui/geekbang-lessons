package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * PrioritizedConverter
 *
 * @author qrXun on 2021/3/21
 */
public class PrioritizedConverter<T> implements Converter<T>, Comparable<PrioritizedConverter> {

    private final Converter<T> converter;

    /**
     * 优先级
     */
    private final int priority;

    public PrioritizedConverter(Converter<T> converter, int priority) {
        this.converter = converter;
        this.priority = priority;
    }

    @Override
    public int compareTo(PrioritizedConverter other) {
        return Integer.compare(other.getPriority(), this.getPriority());
    }

    @Override
    public T convert(String value) throws IllegalArgumentException, NullPointerException {
        return converter.convert(value);
    }

    public Converter<T> getConverter() {
        return converter;
    }

    public int getPriority() {
        return priority;
    }
}
