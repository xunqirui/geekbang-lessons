package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToBooleanConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToBooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(String value) throws IllegalArgumentException, NullPointerException {
        return Boolean.parseBoolean(value);
    }
}
