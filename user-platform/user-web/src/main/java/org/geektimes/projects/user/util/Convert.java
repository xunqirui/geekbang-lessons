package org.geektimes.projects.user.util;

/**
 * ConvertUtil
 *
 * @author qrXun on 2021/3/2
 */
public interface Convert<S, T> {

    T convert(S source);

}
