package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToLongConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToLongConverter extends AbstractConverter<Long> {

    @Override
    protected Long doConvert(String value) {
        return Long.valueOf(value);
    }
}
