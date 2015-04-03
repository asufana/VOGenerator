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
        final JavaFile javaFile = JavaSourceGenerator.generate(pkg,
                                                               annotation,
                                                               this.getClass());
        final String fqcn = pkg.toString() + "." + annotation.className();
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
