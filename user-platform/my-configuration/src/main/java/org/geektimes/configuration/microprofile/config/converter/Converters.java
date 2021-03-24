package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Converters
 *
 * @author qrXun on 2021/3/21
 */
public class Converters implements Iterable<Converter> {

    private static final int DEFAULT_PRIORITY = 100;

    private final Map<Class<?>, PriorityQueue<PrioritizedConverter<?>>> typedConverters = new HashMap<>();

    private boolean addedDiscoveredConverters = false;

    private ClassLoader classLoader;

    public Converters() {
        this(Thread.currentThread().getContextClassLoader());
    }

    public Converters(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Iterator<Converter> iterator() {
        List<Converter> allConverters = new LinkedList<>();
        typedConverters.forEach((className, priorityQueue) ->
                priorityQueue.forEach(prioritizedConverter -> allConverters.add(prioritizedConverter.getConverter())));
        return allConverters.iterator();
    }

    /**
     * 通过 spi 加载 converter
     */
    public void addDiscoveredConverters() {
        if (addedDiscoveredConverters) {
            return;
        }
        addConverters(ServiceLoader.load(Converter.class, classLoader));
        addedDiscoveredConverters = true;
    }

    public void addConverters(Iterable<Converter> converters) {
        for (Converter converter : converters) {
            addConverter(converter);
        }
    }

    public void addConverter(Converter converter) {
        addConverter(converter, DEFAULT_PRIORITY);
    }

    public void addConverter(Converter converter, int priority) {
        Class<?> converterType = resolveConverterType(converter);
        addConverter(converter, priority, converterType);
    }

    public void addConverter(Converter converter, int priority, Class<?> converterType) {
        PriorityQueue<PrioritizedConverter<?>> priorityQueue = typedConverters.computeIfAbsent(converterType, t -> new PriorityQueue<>());
        priorityQueue.offer(new PrioritizedConverter<>(converter, priority));
    }

    /**
     * 获取泛型数据类型
     *
     * @param converter
     * @return
     */
    protected Class<?> resolveConverterType(Converter converter) {
        assertConverter(converter);
        Class<?> converterType = null;
        Class<?> converterClass = converter.getClass();
        while (converterClass != null) {
            converterType = resolveConverterType(converterClass);
            if (converterType != null) {
                break;
            }
            Type superType = converterClass.getGenericSuperclass();
            if (superType instanceof ParameterizedType) {
                converterType = resolveConverterType(superType);
            }
            if (converterType != null) {
                break;
            }
            converterClass = converterClass.getSuperclass();
        }
        return converterType;
    }

    private Class<?> resolveConverterType(Class<?> converterClass) {
        Type[] types = converterClass.getGenericInterfaces();
        Class<?> converterType = null;
        for (Type type : types) {
            converterType = resolveConverterType(type);
            if (converterType != null) {
                break;
            }
        }
        return converterType;
    }

    private Class<?> resolveConverterType(Type type) {
        Class<?> converterType = null;
        if (type instanceof ParameterizedType) {
            ParameterizedType pType = (ParameterizedType) type;
            if (pType.getRawType() instanceof Class) {
                Type[] arguments = pType.getActualTypeArguments();
                if (arguments.length == 1 && arguments[0] instanceof Class) {
                    converterType = (Class<?>) arguments[0];
                }
            }
        }
        return converterType;
    }

    private void assertConverter(Converter converter) {
        Class<?> converterClass = converter.getClass();
        if (converterClass.isInterface()) {
            throw new IllegalArgumentException("The implementation class of Converter must not be an interface!");
        }
        if (Modifier.isAbstract(converterClass.getModifiers())) {
            throw new IllegalArgumentException("The implementation class of Converter must not be an abstract!");
        }
    }

    public void addConverters(Converter... converters) {
        addConverters(Arrays.asList(converters));
    }

    public List<Converter> getConverters(Class<?> converterType) {
        PriorityQueue<PrioritizedConverter<?>> priorityQueue = typedConverters.get(converterType);
        if (priorityQueue == null || priorityQueue.isEmpty()) {
            return Collections.emptyList();
        }
        List<Converter> converters = new LinkedList<>();
        for (PrioritizedConverter prioritizedConverter : priorityQueue) {
            converters.add(prioritizedConverter.getConverter());
        }
        return converters;
    }


}
