package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.projects.user.util.Convert;
import org.geektimes.projects.user.util.StringToLongConvert;
import org.geektimes.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * UserController
 * 通过注册页面进行注册，注册成功之后返回注册成功页面
 *
 * @author qrXun on 2021/3/1
 */
@Path("/register")
public class UserController implements PageController {

    private UserService userService = new UserServiceImpl();

    /**
     * 登录页面 jsp
     *
     * @param request  HTTP 请求
     * @param response HTTP 相应
     * @return
     * @throws Throwable
     */
    @Override
    @POST
    @Path("")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        User user = setUserParam(request);
        boolean flag = userService.register(user);
        if (flag){
            return "register-success.jsp";
        }else{
            return "register-false.jsp";
        }
    }

    @GET
    @Path("page")
    public String page(HttpServletRequest request, HttpServletResponse response) {
        return "register-form.jsp";
    }

    private User setUserParam(HttpServletRequest request) throws Exception {
        User user = new User();
        BeanInfo userBeanInfo = Introspector.getBeanInfo(User.class, Object.class);
        PropertyDescriptor[] propertyDescriptors = userBeanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String name = propertyDescriptor.getName();
            String paramValue = request.getParameter(name);
            Class propertyClass = propertyDescriptor.getPropertyType();
            if (paramValue != null && STRING_CONVERT_MAPPING.get(propertyClass) != null) {
                propertyDescriptor.getWriteMethod().invoke(user, STRING_CONVERT_MAPPING.get(propertyClass).apply(paramValue));
            }else if (paramValue != null){
                propertyDescriptor.getWriteMethod().invoke(user, paramValue);
            }
        }
        return user;
    }

    private static final Map<Class<?>, Function<String, ?>> STRING_CONVERT_MAPPING = new HashMap<>();

    static {
        STRING_CONVERT_MAPPING.put(Long.class, Long::valueOf);
    }

}
