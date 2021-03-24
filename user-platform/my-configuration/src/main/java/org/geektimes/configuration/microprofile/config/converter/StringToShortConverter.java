package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToShortConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToShortConverter extends AbstractConverter<Short> {

    @Override
    protected Short doConvert(String value) {
        return Short.valueOf(value);
    }
}
