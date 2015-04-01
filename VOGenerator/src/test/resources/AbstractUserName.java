package test;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.*;
import play.modules.ddd.vo.*;

import com.github.asufana.vogen.annotations.*;

@VO
public abstract class AbstractUserName {}

//import javax.persistence.*;
//import lombok.*;
//import lombok.experimental.*;
//import play.modules.ddd.vo.*;
//
///** 製品名 */
//@Embeddable
//@Getter
//@Accessors(fluent = true)
//public class ProductName extends AbstractValueObject {
//    
//    @Column(name = "name", nullable = false, length = 255)
//    private final String value;
//    
//    public ProductName(final String value) {
//        this.value = value;
//        validate();
//    }
//}

