package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToIntegerConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToIntegerConverter implements Converter<Integer> {

    @Override
    public Integer convert(String value) throws IllegalArgumentException, NullPointerException {
        return Integer.parseInt(value);
    }
}
