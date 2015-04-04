
# VOGenerator

ValueObject generator with APT.

## How to use

### 1. Create class with @VO annotation

```
package com.github.asufana.vogensample.company;

import com.github.asufana.vogen.annotations.*;

@VO(title = "会社名", className = "CompanyName", type = "String", nullable = false, length = "100")
@VO(title = "会社コード", className = "CompanyCode", type = "String", nullable = false, length = "100")
public class Company {
    
}
```

### 2. Maven compile

```
$ mvn clean compile
```

### 3. Generated ValueObject classes

APT generate classes at ```target/generated-sources/annotations/com/github/asufana/vogensample/company/vo/CompanyName.java```.

```
package com.github.asufana.vogensample.company.vo;

import com.github.asufana.ddd.vo.AbstractValueObject;
import java.lang.String;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.experimental.Accessors;

/** 会社名 */
@Embeddable
@Getter
@Accessors(fluent = true)
@Generated({"com.github.asufana.vogen.VOProcessor"})
public class CompanyName extends AbstractValueObject {

  @Column(name = "company_name", nullable = false, length = 100)
  private final String value;

  public CompanyName(final String value) {
    this.value = value;
    validate();
  }
}
```
