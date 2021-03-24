package org.geektimes.context;

import org.geektimes.function.ThrowableAction;
import org.geektimes.function.ThrowableFunction;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.naming.*;
import javax.servlet.ServletContext;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * ComponentContext
 *
 * @author qrXun on 2021/3/9
 */
public class ComponentContext {

    public static String CONTEXT_NAME = ComponentContext.class.getName();

    private static final String COMPONENT_ENV_CONTEXT_NAME = "java:comp/env";

    private static final Logger logger = Logger.getLogger(CONTEXT_NAME);

    private static ServletContext servletContext;

    private Context envContext;

    private ClassLoader classLoader;

    private Map<String, Object> componentsMap = new HashMap<>();

    /**
     * 获取 ComponentContext 实例
     *
     * @return
     */
    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(COMPONENT_ENV_CONTEXT_NAME);
    }

    /**
     * 根据 beanName 返回对应对象
     * @param componentName
     * @return
     */
    public <C> C getComponent(String componentName){
        return (C) componentsMap.get(componentName);
    }

    /**
     * 根据 Class 获取对应对象集合
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> List<T> getComponent(Class<T> clazz){
        List<T> list = new ArrayList<>();
        for (Object object : componentsMap.values()){
            if (clazz.isInstance(object)){
                list.add((T) object);
            }
        }
        return list;
    }


    /**
     * 初始化容器
     *
     * @param servletContext
     */
    public void init(ServletContext servletContext) {
        ComponentContext.servletContext = servletContext;
        servletContext.setAttribute(COMPONENT_ENV_CONTEXT_NAME, this);
        this.classLoader = servletContext.getClassLoader();
        // 初始化 jndi 环境
        initEnvContext();
        // 实例化 bean 对象
        instantiateComponents();
        // 初始化 bean
        initializeComponents();


    }


    private void close(Context context) {
        if (context != null) {
            ThrowableAction.execute(context::close);
        }
    }

    /**
     * 初始化 jndi 环境
     */
    private void initEnvContext() {
        if (this.envContext != null) {
            return;
        }
        Context context = null;
        try {
            context = new InitialContext();
            this.envContext = (Context) context.lookup(COMPONENT_ENV_CONTEXT_NAME);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        } finally {
            close(context);
        }
    }

    /**
     * 实例化配置的 bean 对象
     */
    private void instantiateComponents() {
        List<String> componentNames = listAllComponentNames();
        componentNames.forEach(fullName -> componentsMap.put(fullName, lookupComponent(fullName)));
    }


    /**
     * 初始化 bean 对象
     */
    private void initializeComponents() {
        componentsMap.values().forEach(component -> {
            Class<?> componentClass = component.getClass();
            // 注入阶段 - {@link Resource}
            injectComponents(component, componentClass);
            // 注入完成后执行方法阶段
            injectPostConstruct(component, componentClass);
        });
    }

    /**
     * 初始化注入完成后的方法调用阶段
     *
     * @param component      对应实例化对象
     * @param componentClass 实例化对象 class
     */
    private void injectPostConstruct(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getMethods())
                .filter(method -> !Modifier.isStatic(method.getModifiers()) &&
                        method.getParameterCount() == 0 &&
                        method.isAnnotationPresent(PostConstruct.class))
                .forEach(method -> {
                    try {
                        method.invoke(component);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });


    }

    /**
     * 对实例化的对象的标记了 @Resource 的字段进行依赖注入
     *
     * @param component      对应实例化对象
     * @param componentClass 实例化对象 Class
     */
    private void injectComponents(Object component, Class<?> componentClass) {
        Stream.of(componentClass.getDeclaredFields())
                .filter(field -> {
                    int mods = field.getModifiers();
                    return !Modifier.isStatic(mods) && // 非静态
                            field.isAnnotationPresent(Resource.class); // 包含 resource 注解
                })
                .forEach(field -> {
                    Resource resource = field.getAnnotation(Resource.class);
                    String resourceName = resource.name();
                    Object injectObject = componentsMap.get(resourceName);
                    field.setAccessible(true);
                    try {
                        field.set(component, injectObject);
                    } catch (IllegalAccessException e) {
                    }
                });
    }

    /**
     * 内部用于查找通过 jndi 方式创建的对象
     *
     * @param fullName
     * @param <C>
     * @return
     */
    protected <C> C lookupComponent(String fullName) {
        return executeInContext(context -> (C) context.lookup(fullName));
    }

    /**
     * 获取所有组件的名字
     *
     * @return
     */
    private List<String> listAllComponentNames() {
        return listAllComponentNames("/");
    }

    protected List<String> listAllComponentNames(String name) {
        return executeInContext(context -> {
            NamingEnumeration<NameClassPair> e = executeInContext(context, ctx -> ctx.list(name), false);

            if (e == null) {
                // 当前 jndi 节点下没有子节点
                return Collections.emptyList();
            }

            List<String> fullNames = new LinkedList<>();
            while (e.hasMoreElements()) {
                NameClassPair element = e.nextElement();
                String className = element.getClassName();
                Class<?> targetClass = this.classLoader.loadClass(className);
                if (Context.class.isAssignableFrom(targetClass)) {
                    // 如果当前名称还是目录（Context 实现类） ，继续递归查找
                    fullNames.addAll(listAllComponentNames(element.getName()));
                } else {
                    // 否则，当前名称是目标类型，则添加到集合中
                    String fullName = name.startsWith("/") ?
                            element.getName() : name + "/" + element.getName();
                    fullNames.add(fullName);
                }
            }
            return fullNames;
        });
    }

    /**
     * 在 context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function ThrowableFunction
     * @param <R>      返回结果类型
     * @return 返回
     * @see ThrowableFunction#execute(Object)
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function) {
        return executeInContext(function, false);
    }

    /**
     * 在 context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#execute(Object)
     */
    protected <R> R executeInContext(ThrowableFunction<Context, R> function, boolean ignoredException) {
        return executeInContext(this.envContext, function, ignoredException);
    }

    /**
     * 在 context 中执行，通过指定 ThrowableFunction 返回计算结果
     *
     * @param context          上下文
     * @param function         ThrowableFunction
     * @param ignoredException 是否忽略异常
     * @param <R>              返回结果类型
     * @return 返回
     * @see ThrowableFunction#execute(Object)
     */
    private <R> R executeInContext(Context context, ThrowableFunction<Context, R> function, boolean ignoredException) {
        R result = null;
        try {
            result = ThrowableFunction.execute(context, function);
        } catch (Throwable e) {
            if (ignoredException) {
                logger.warning(e.getMessage());
            } else {
                throw new RuntimeException(e);
            }
        }
        return result;
    }


}
