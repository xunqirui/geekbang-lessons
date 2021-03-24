package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToBooleanConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToBooleanConverter extends AbstractConverter<Boolean> {

    @Override
    protected Boolean doConvert(String value) {
        return Boolean.parseBoolean(value);
    }
}
