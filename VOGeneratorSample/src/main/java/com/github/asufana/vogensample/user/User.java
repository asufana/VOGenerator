package com.github.asufana.vogensample.user;

import com.github.asufana.vogen.annotations.*;

@VO(title = "社員名", className = "UserName", type = "String", nullable = false, length = "100")
@VO(title = "社員番号", className = "UserId", type = "String", nullable = false, length = "100")
public class User {
    
}
