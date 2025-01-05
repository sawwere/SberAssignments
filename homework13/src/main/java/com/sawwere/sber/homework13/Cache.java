package com.sawwere.sber.homework13;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    CacheType cacheType() default CacheType.IN_MEMORY;

    String fileNamePrefix() default "";

    boolean zip() default false;

    int maxListSize() default 0;

    Class<?>[] identityBy() default {};
}
