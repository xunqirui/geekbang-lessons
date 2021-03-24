package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToIntegerConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToIntegerConverter extends AbstractConverter<Integer> {

    @Override
    protected Integer doConvert(String value) {
        return Integer.parseInt(value);
    }
}
