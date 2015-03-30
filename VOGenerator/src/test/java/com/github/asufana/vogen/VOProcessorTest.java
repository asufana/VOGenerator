package com.github.asufana.vogen;

import static com.google.common.truth.Truth.*;
import static com.google.testing.compile.JavaSourceSubjectFactory.*;

import org.junit.*;

import com.google.common.io.*;
import com.google.testing.compile.*;

public class VOProcessorTest {
    
    @Test
    public void test() {
        assert_().about(javaSource())
                 .that(JavaFileObjects.forResource(Resources.getResource("AbstractUserName.java")))
                 .processedWith(new VOProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString("test.Blah",
                                                                   "package test;\n"
                                                                           + "\n"
                                                                           + "import java.lang.String;\n"
                                                                           + "import javax.annotation.Generated;\n"
                                                                           + "\n"
                                                                           + "@Generated({\"com.github.asufana.vogen.VOProcessor\"})\n"
                                                                           + "public class Blah {\n"
                                                                           + "  public String hello() {\n"
                                                                           + "    return \"hello\";\n"
                                                                           + "  }\n"
                                                                           + "}"));
    }
}
