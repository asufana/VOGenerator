package com.github.asufana.vogen;

import static javax.lang.model.SourceVersion.*;

import java.io.*;
import java.util.*;

import javax.annotation.*;
import javax.annotation.processing.*;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;
import javax.tools.*;

import com.github.asufana.vogen.annotations.*;
import com.google.auto.common.*;
import com.squareup.javapoet.*;

@SupportedAnnotationTypes("com.github.asufana.vogen.annotations.VO")
@SupportedSourceVersion(RELEASE_8)
public class VOProcessor extends AbstractProcessor {
    
    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        if (annotations.isEmpty()) {
            log("no annotation");
            return true;
        }
        
        final Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(VO.class);
        for (final Element element : elements) {
            final PackageElement aPackage = MoreElements.getPackage(element);
            final TypeSpec blah = TypeSpec.classBuilder("Blah")
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
                                                       blah)
                                              .build();
            
            try {
                final JavaFileObject sourceFile = processingEnv.getFiler()
                                                               .createSourceFile(aPackage.toString()
                                                                       + ".Blah");
                final Writer writer = sourceFile.openWriter();
                javaFile.writeTo(writer);
                writer.close();
            }
            catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
    
    private void log(final String msg) {
        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Kind.NOTE, msg);
        }
    }
}
