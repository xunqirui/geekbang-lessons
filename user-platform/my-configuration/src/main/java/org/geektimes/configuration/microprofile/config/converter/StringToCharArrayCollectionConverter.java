package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToCharacterCollectionConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToCharArrayCollectionConverter extends AbstractConverter<char[]> {

    @Override
    protected char[] doConvert(String value) {
        return value.toCharArray();
    }
}
