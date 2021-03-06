package com.github.asufana.vogen;

import static com.google.common.truth.Truth.*;
import static com.google.testing.compile.JavaSourceSubjectFactory.*;

import org.junit.*;

import com.google.common.io.*;
import com.google.testing.compile.*;

public class VOProcessorTest {
    
    @Test
    public void test() {
        final String expectSourceCode = "package domain.model.company.vo;\n\n"
                + "import com.github.asufana.ddd.vo.AbstractValueObject;\n"
                + "import java.lang.String;\n"
                + "import javax.annotation.Generated;\n"
                + "import javax.persistence.Column;\n"
                + "import javax.persistence.Embeddable;\n"
                + "import lombok.Getter;\n"
                + "import lombok.experimental.Accessors;\n"
                + "\n"
                + "@Embeddable\n"
                + "@Getter\n"
                + "@Accessors(\n fluent = true\n)\n"
                + "@Generated({\"com.github.asufana.vogen.VOProcessor\"})\n"
                + "public class CompanyName extends AbstractValueObject {\n"
                + "  @Column(name=\"company_name\", nullable=false, length=100, columnDefinition=\"nvarchar(100)\")\n"
                + "  private final String value;\n\n"
                + "  public CompanyName(final String value) {\n"
                + "    this.value = value;\n"
                + "    validate();\n"
                + "  }\n"
                + "}";
        
        assert_().about(javaSource())
                 .that(JavaFileObjects.forResource(Resources.getResource("Company.java")))
                 .processedWith(new VOProcessor())
                 .compilesWithoutError()
                 .and()
                 .generatesSources(JavaFileObjects.forSourceString("domain.model.company.CompanyName",
                                                                   expectSourceCode));
    }
}
