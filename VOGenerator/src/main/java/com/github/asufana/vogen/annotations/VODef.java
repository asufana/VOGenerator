package com.github.asufana.vogen.annotations;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VODef {
    
    String title();
    
    String className();
    
    String fieldName() default "";
    
    String columnName() default "";
    
    String type() default "String";
    
    boolean nullable() default false;
    
    String length() default "255";
    
    boolean mssql() default false;
}
