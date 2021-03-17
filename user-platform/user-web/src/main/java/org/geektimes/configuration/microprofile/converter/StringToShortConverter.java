package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToShortConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToShortConverter implements Converter<Short> {
    @Override
    public Short convert(String value) throws IllegalArgumentException, NullPointerException {
        return Short.parseShort(value);
    }
}
