package com.github.asufana.vogen.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VO {
    /** VO list */
    VODef[] value();
}
