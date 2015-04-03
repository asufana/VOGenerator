package com.github.asufana.vogen;

import javax.lang.model.element.*;

import org.apache.commons.lang3.*;

import com.github.asufana.vogen.annotations.*;
import com.squareup.javapoet.*;
import com.squareup.javapoet.AnnotationSpec.Builder;

public class JavaSourceGenerator {
    
    public static JavaFile generate(final PackageElement pkg,
                                    final VO annotation,
                                    final Class<?> processorClass) {
        final String className = annotation.className();
        final TypeSpec typeSpec = TypeSpec.classBuilder(className)
                                          .superclass(com.github.asufana.ddd.vo.AbstractValueObject.class)
                                          .addJavadoc(annotation.title())
                                          .addAnnotation(javax.persistence.Embeddable.class)
                                          .addAnnotation(lombok.Getter.class)
                                          .addAnnotation(AnnotationSpec.builder(lombok.experimental.Accessors.class)
                                                                       .addMember("fluent",
                                                                                  "$L",
                                                                                  "true")
                                                                       .build())
                                          .addAnnotation(AnnotationSpec.builder(javax.annotation.Generated.class)
                                                                       .addMember("value",
                                                                                  "{$S}",
                                                                                  processorClass.getCanonicalName())
                                                                       .build())
                                          .addModifiers(Modifier.PUBLIC)
                                          .addField(buildField(annotation))
                                          .addMethod(buildConstructor())
                                          .build();
        
        return JavaFile.builder(pkg.getQualifiedName().toString() + ".vo",
                                typeSpec).build();
    }
    
    private static MethodSpec buildConstructor() {
        return MethodSpec.constructorBuilder()
                         .addModifiers(Modifier.PUBLIC)
                         .addParameter(String.class, "value", Modifier.FINAL)
                         .addStatement("this.$N = $N", "value", "value")
                         .addStatement("validate()")
                         .build();
    }
    
    private static FieldSpec buildField(final VO annotation) {
        return FieldSpec.builder(String.class, "value")
                        .addModifiers(Modifier.PRIVATE, Modifier.FINAL)
                        .addAnnotation(buildColumnAnnotation(annotation))
                        .build();
    }
    
    private static AnnotationSpec buildColumnAnnotation(final VO annotation) {
        final Builder builder = AnnotationSpec.builder(javax.persistence.Column.class)
                                              .addMember("name",
                                                         "$S",
                                                         columnName(annotation));
        if (StringUtils.isNotEmpty(columnNullable(annotation))) {
            builder.addMember("nullable", columnNullable(annotation));
        }
        if (StringUtils.isNotEmpty(columnLength(annotation))) {
            builder.addMember("length", columnLength(annotation));
        }
        if (StringUtils.isNotEmpty(columnDefinition(annotation))) {
            builder.addMember("columnDefinition",
                              "$S",
                              columnDefinition(annotation));
        }
        return builder.build();
    }
    
    private static String columnName(final VO annotation) {
        return StringUtils.isNotEmpty(annotation.columnName())
                ? annotation.columnName()
                : toSnakeName(annotation.className());
    }
    
    private static String toSnakeName(final String className) {
        return className.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
                        .replaceAll("([a-z])([A-Z])", "$1_$2")
                        .toLowerCase();
    }
    
    private static String columnNullable(final VO annotation) {
        if (annotation.type().toLowerCase().equals("string") == false) {
            return "";
        }
        return annotation.nullable()
                ? "true"
                : "false";
    }
    
    private static String columnLength(final VO annotation) {
        if (annotation.type().toLowerCase().equals("string") == false) {
            return "";
        }
        return annotation.length();
    }
    
    private static String columnDefinition(final VO annotation) {
        if (annotation.mssql()
                && annotation.type().toLowerCase().equals("string")) {
            return String.format("nvarchar(%s)", annotation.length());
        }
        return "";
    }
}
