package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToStringConverter
 *
 * @author qrXun on 2021/3/24
 */
public class StringToStringConverter extends AbstractConverter<String>{

    @Override
    protected String doConvert(String value) {
        return String.valueOf(value);
    }
}
