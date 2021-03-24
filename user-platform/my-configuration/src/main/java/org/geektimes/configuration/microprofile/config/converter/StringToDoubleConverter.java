package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToDoubleConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToDoubleConverter extends AbstractConverter<Double> {


    @Override
    protected Double doConvert(String value) {
        return Double.parseDouble(value);
    }
}

