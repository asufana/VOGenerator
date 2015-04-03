package com.github.asufana.vogensample.company;

import com.github.asufana.vogen.annotations.*;

@VO(title = "会社名", className = "CompanyName", type = "String", nullable = false, length = "100")
@VO(title = "会社コード", className = "CompanyCode", type = "String", nullable = false, length = "100")
public class Company {
    
}
