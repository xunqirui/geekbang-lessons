package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToFloatConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToFloatConverter extends AbstractConverter<Float> {

    @Override
    protected Float doConvert(String value) {
        return Float.parseFloat(value);
    }
}
