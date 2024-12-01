package com.sawwere.sber.homework5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {

    /**
     * Scans object "from" for all getters. If object "to"
     * contains correspondent setter, it will invoke it
     * to set property value for "to" which equals to the property
     * of "from".
     * <p/>
     * The type in setter should be compatible to the value returned
     * by getter (if not, no invocation performed).
     * Compatible means that parameter type in setter should
     * be the same or be superclass of the return type of the getter.
     * <p/>
     * The method takes care only about public methods.
     *
     * @param to   Object which properties will be set.
     * @param from Object which properties will be used to get values.
     */
    public static void assign(Object to, Object from) {
        var fromGetters = ReflectionUtils.getAllGetters(from.getClass());
        var toSetters = ReflectionUtils.getAllSetters(to.getClass());
        for (Method getter : fromGetters) {
            // Пропускаем геттеры с параметрами,
            // вдруг мы имеем дело с объектами коллекций(возможно многомерными) с доступом по индексу
            if (getter.getParameterCount() != 0) {
                continue;
            }
            for (Method setter: toSetters) {
                if (setter.getName().endsWith(getter.getName().substring(3))
                        && setter.getParameterCount() == 1
                        && setter.getParameterTypes()[0].isAssignableFrom(getter.getReturnType())) {
                    try {
                        setter.invoke(to, getter.invoke(from));
                        break;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
