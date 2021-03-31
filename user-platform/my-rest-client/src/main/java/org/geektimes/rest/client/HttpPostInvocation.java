package org.geektimes.rest.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geektimes.rest.core.DefaultResponse;
import org.geektimes.rest.util.ReflectUtil;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.core.*;
import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * HttpGetInvocation
 *
 * @author qrXun on 2021/3/30
 */
public class HttpPostInvocation implements Invocation {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final URI uri;

    private final URL url;

    private final MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

    private final Entity<?> entity;

    public HttpPostInvocation(URI uri, MultivaluedMap<String, Object> headers, Entity<?> entity) {
        this.uri = uri;
        this.headers.putAll(headers);
        if (entity != null && entity.getMediaType() != null) {
            // 如果有 MediaType 根据 MediaType 来
            MediaType mediaType = entity.getMediaType();
            this.headers.putSingle("Content-Type", mediaType.getType() + "/" + mediaType.getSubtype());
        } else {
            addDefaultHeaders();
        }
        this.entity = entity;
        try {
            this.url = uri.toURL();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException();
        }
    }

    // 给 header 添加默认值
    public void addDefaultHeaders() {
        this.headers.putSingle("Content-Type", "application/json");
    }

    @Override
    public Invocation property(String name, Object value) {
        return this;
    }

    @Override
    public Response invoke() {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.POST);
            setRequestHeaders(connection);
            connection.setDoOutput(true);
            if (entity != null && entity.getEntity() != null) {
                try (OutputStream outputStream = connection.getOutputStream()) {
                    String headers = this.headers.getFirst("Content-Type").toString();
                    if (headers.equals(MediaType.APPLICATION_JSON)) {
                        String jsonData = objectMapper.writeValueAsString(entity.getEntity());
                        byte[] input = jsonData.getBytes(StandardCharsets.UTF_8);
                        outputStream.write(input, 0, input.length);
                    } else if (headers.equals(MediaType.APPLICATION_FORM_URLENCODED)) {
                        try {
                            String requestData = getFormData(entity.getEntity());
                            byte[] input = requestData.getBytes(StandardCharsets.UTF_8);
                            outputStream.write(input, 0, input.length);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            int statusCode = connection.getResponseCode();
            DefaultResponse response = new DefaultResponse();
            response.setConnection(connection);
            response.setStatus(statusCode);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getFormData(Object data) throws Exception {
        StringBuilder request = new StringBuilder();
        Class dataClazz = data.getClass();
        Field[] fields =dataClazz.getDeclaredFields();
        for (Field field : fields){
            String fieldName = field.getName();
            Method readMethod = ReflectUtil.getReadMethod(fieldName, dataClazz);
            Object fieldData = readMethod.invoke(data);
            if (fieldData != null){
                request.append(fieldName)
                        .append("=")
                        .append(URLEncoder.encode(fieldData.toString(), "UTF-8"))
                        .append("&");
            }
        }
        return request.toString();
    }

    private void setRequestHeaders(HttpURLConnection connection) {
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object headerValue : entry.getValue()) {
                connection.setRequestProperty(headerName, headerValue.toString());
            }
        }
    }

    @Override
    public <T> T invoke(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> T invoke(GenericType<T> responseType) {
        return null;
    }

    @Override
    public Future<Response> submit() {
        return null;
    }

    @Override
    public <T> Future<T> submit(Class<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(GenericType<T> responseType) {
        return null;
    }

    @Override
    public <T> Future<T> submit(InvocationCallback<T> callback) {
        return null;
    }
}
