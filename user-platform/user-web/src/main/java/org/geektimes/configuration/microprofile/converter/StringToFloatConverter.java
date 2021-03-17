package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToFloatConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToFloatConverter implements Converter<Float> {
    @Override
    public Float convert(String value) throws IllegalArgumentException, NullPointerException {
        return Float.parseFloat(value);
    }
}
