package com.github.asufana.vogen;

import static javax.lang.model.SourceVersion.*;

import java.io.*;
import java.util.*;

import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.*;

import com.github.asufana.vogen.annotations.*;
import com.google.auto.common.*;
import com.squareup.javapoet.*;

@SupportedAnnotationTypes("com.github.asufana.vogen.annotations.*")
@SupportedSourceVersion(RELEASE_8)
public class VOProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(VOs.class);
        for (final Element element : elements) {
            final PackageElement pkg = MoreElements.getPackage(element);
            for (final VO voAnnotations : element.getAnnotationsByType(VO.class)) {
                generateClass(voAnnotations, pkg);
            }
        }
        return true;
    }
    
    private void generateClass(final VO annotation, final PackageElement pkg) {
        final String className = annotation.className();
        
        final MethodSpec constructor = MethodSpec.constructorBuilder()
                                                 .addModifiers(Modifier.PUBLIC)
                                                 .addParameter(String.class,
                                                               "value",
                                                               Modifier.FINAL)
                                                 .addStatement("this.$N = $N",
                                                               "value",
                                                               "value")
                                                 .addStatement("validate()")
                                                 .build();
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
                                                                                  getClass().getCanonicalName())
                                                                       .build())
                                          .addModifiers(Modifier.PUBLIC)
                                          .addField(String.class,
                                                    "value",
                                                    Modifier.PRIVATE,
                                                    Modifier.FINAL)
                                          .addMethod(constructor)
                                          .build();
        
        final JavaFile javaFile = JavaFile.builder(pkg.getQualifiedName()
                                                      .toString(),
                                                   typeSpec).build();
        
        final String fqcn = pkg.toString() + "." + className;
        writeClass(fqcn, javaFile);
    }
    
    private void writeClass(final String fqcn, final JavaFile javaFile) {
        try {
            final JavaFileObject sourceFile = processingEnv.getFiler()
                                                           .createSourceFile(fqcn);
            try (final Writer writer = sourceFile.openWriter()) {
                javaFile.writeTo(writer);
            }
        }
        catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
