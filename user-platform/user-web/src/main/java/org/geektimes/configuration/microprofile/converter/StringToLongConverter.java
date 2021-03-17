package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToLongConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToLongConverter implements Converter<Long> {

    @Override
    public Long convert(String value) throws IllegalArgumentException, NullPointerException {
        return Long.parseLong(value);
    }
}
