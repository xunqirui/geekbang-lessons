package org.geektimes.configuration.microprofile.config.source;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

/**
 * ServletConfigSource
 *
 * @author qrXun on 2021/3/23
 */
public class ServletConfigSource extends MapBasedConfigSource {

    private ServletContext servletContext;

    public ServletConfigSource(ServletContext servletContext) {
        super("ServletConfigSource", 500, true);
        this.servletContext = servletContext;
    }

    @Override
    protected void prepareProperties(Map configData) {
        if (servletContext != null) {
            Enumeration<String> parameterNames = servletContext.getInitParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                configData.put(parameterName, servletContext.getInitParameter(parameterName));
            }
        }
    }
}
