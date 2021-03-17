package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToByteConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToByteConverter implements Converter<Byte> {
    @Override
    public Byte convert(String value) throws IllegalArgumentException, NullPointerException {
        return Byte.parseByte(value);
    }
}
