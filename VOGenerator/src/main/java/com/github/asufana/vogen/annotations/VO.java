package com.github.asufana.vogen.annotations;

import java.lang.annotation.*;

@Repeatable(VOs.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VO {
    
    String title();
    
    String className();
    
    String fieldName() default "";
    
    String columnName() default "";
    
    String type() default "String";
    
    boolean nullable() default false;
    
    String length() default "255";
    
    boolean mssql() default false;
}
