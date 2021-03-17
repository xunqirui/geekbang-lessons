package org.geektimes.configuration.microprofile.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * StringToCharacterCollectionConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToCharArrayCollectionConverter implements Converter<char[]> {
    @Override
    public char[] convert(String value) throws IllegalArgumentException, NullPointerException {
        return value.toCharArray();
    }
}
