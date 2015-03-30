package test;

import javax.persistence.*;

import lombok.*;
import lombok.experimental.*;
import play.modules.ddd.vo.*;

import com.github.asufana.vogen.annotations.*;

import domain.model.resource.apartment.*;
import domain.model.resource.apartment.repositories.*;
import domain.model.services.imports.model.csvrecord.vo.*;

@VO
public abstract class AbstractUserName {}


// /** ユーザ名 */
//@Embeddable
//@Getter
//@Accessors(fluent = true)
//public class UserName extends AbstractValueObject {
//    @Column(name = "user_name", nullable = false, length = 255, columnDefinition = "nvarchar(255)")
//    private final String value;
//    
//    public UserName(final String value) {
//        this.value = value;
//        validate();
//    }
//}


