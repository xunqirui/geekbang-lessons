package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToDoubleConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToDoubleConverter implements Converter<Double> {


    @Override
    public Double convert(String value) throws IllegalArgumentException, NullPointerException {
        return Double.parseDouble(value);
    }
}
