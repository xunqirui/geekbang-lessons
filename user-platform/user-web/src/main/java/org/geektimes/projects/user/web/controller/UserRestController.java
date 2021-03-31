package org.geektimes.projects.user.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.util.ReflectUtil;
import org.geektimes.web.mvc.controller.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;

/**
 * UserRestController
 *
 * @author qrXun on 2021/3/31
 */
@Path("/postRegister")
public class UserRestController implements RestController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Path("")
    @POST
    public User execute(HttpServletRequest request) throws Throwable {
        User user = null;
        try (ServletInputStream inputStream = request.getInputStream()) {
            String contentType = request.getHeader("Content-Type");
            String data = new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
            if (MediaType.APPLICATION_FORM_URLENCODED.equals(contentType)) {
                // form 表单提交模式
                user = setUserParam(data);
            } else if (MediaType.APPLICATION_JSON.equals(contentType)){
                // json 格式提交
                user = objectMapper.readValue(data, User.class);
            }
        }
        return user;
    }

    private User setUserParam(String formData) throws IntrospectionException {
        User user = new User();
        Class userClazz = User.class;
        // BeanInfo
        String[] paramArray = formData.split("&");
        for (String param : paramArray) {
            String[] query = param.split("=");
            if (query.length > 0) {
                try {
                    Field field = userClazz.getDeclaredField(query[0]);
                    if (String.class.isAssignableFrom(field.getType())) {
                        ReflectUtil.getWriteMethod(query[0], User.class).invoke(user, query[1]);
                    } else if (Long.class.isAssignableFrom(field.getType())) {
                        ReflectUtil.getWriteMethod(query[0], User.class).invoke(user, Long.valueOf(query[1]));
                    }
                } catch (NoSuchFieldException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

}
