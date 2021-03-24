package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * AbstractConverter
 *
 * @author qrXun on 2021/3/21
 */
public abstract class AbstractConverter<T> implements Converter<T> {

    @Override
    public T convert(String value) {
        if (value == null) {
            throw new RuntimeException("The value must not be null !");
        }
        return doConvert(value);
    }

    protected abstract T doConvert(String value);


}
