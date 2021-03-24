package org.geektimes.configuration.microprofile.config.converter;

/**
 * StringToByteConverter
 *
 * @author qrXun on 2021/3/17
 */
public class StringToByteConverter extends AbstractConverter<Byte> {

    @Override
    protected Byte doConvert(String value) {
        return Byte.parseByte(value);
    }
}
