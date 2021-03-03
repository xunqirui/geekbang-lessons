package org.geektimes.projects.user.util;

/**
 * StringToLongConvert
 *
 * @author qrXun on 2021/3/2
 */
public class StringToLongConvert implements Convert<String, Long>{

    @Override
    public Long convert(String source) {
        return Long.valueOf(source);
    }
}
