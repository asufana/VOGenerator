package com.github.asufana.vogen.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VOs {
    VO[] value();
}
