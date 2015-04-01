package com.github.asufana.vogen;

import static javax.lang.model.SourceVersion.*;

import java.io.*;
import java.util.*;

import javax.annotation.*;
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
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(VO.class);
        for (final Element element : elements) {
            generateClass(element);
        }
        return true;
    }
    
    private void generateClass(final Element element) {
        final String className = "Blah";
        final PackageElement aPackage = MoreElements.getPackage(element);
        final TypeSpec typeSpec = TypeSpec.classBuilder(className)
                                          .addAnnotation(AnnotationSpec.builder(Generated.class)
                                                                       .addMember("value",
                                                                                  "{$S}",
                                                                                  getClass().getCanonicalName())
                                                                       .build())
                                          .addModifiers(Modifier.PUBLIC)
                                          .addMethod(MethodSpec.methodBuilder("hello")
                                                               .addModifiers(Modifier.PUBLIC)
                                                               .addCode(CodeBlock.builder()
                                                                                 .add("return \"hello\";\n")
                                                                                 .build())
                                                               .returns(TypeName.get(String.class))
                                                               .build())
                                          .build();
        final JavaFile javaFile = JavaFile.builder(aPackage.getQualifiedName()
                                                           .toString(),
                                                   typeSpec).build();
        
        final String fqcn = aPackage.toString() + "." + className;
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
