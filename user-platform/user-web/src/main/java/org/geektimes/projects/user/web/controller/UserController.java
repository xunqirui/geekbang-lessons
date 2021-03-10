package org.geektimes.projects.user.web.controller;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.service.UserService;
import org.geektimes.projects.user.service.UserServiceImpl;
import org.geektimes.projects.user.validator.DelegatingValidator;
import org.geektimes.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * UserController
 * 通过注册页面进行注册，注册成功之后返回注册成功页面
 *
 * @author qrXun on 2021/3/1
 */
@Path("/register")
public class UserController implements PageController {

    @Resource(name = "bean/UserService")
    private UserService userService;

    @Resource(name = "bean/DelegatingValidator")
    private DelegatingValidator delegatingValidator;

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
        // 校验 user
        Set<ConstraintViolation<User>> constraintViolations = delegatingValidator.validate(user);
        if (constraintViolations.size() > 0){
            StringBuilder builder = new StringBuilder();
            constraintViolations.forEach(c -> builder.append(c.getMessage() + ","));
            String message = builder.toString();
            request.setAttribute("reason", message.substring(0, message.length() - 1));
            return "register-false.jsp";
        }
        boolean flag = userService.register(user);
        if (flag) {
            return "register-success.jsp";
        } else {
            request.setAttribute("reason", "数据插入失败");
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
            } else if (paramValue != null) {
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
