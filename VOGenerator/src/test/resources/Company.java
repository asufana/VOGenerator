package domain.model.company;

import com.github.asufana.vogen.annotations.*;

@VO(title="会社コード", className="CompanyCode", type="String", nullable=false, length="100", mssql=true)
@VO(title="会社名", className="CompanyName", type="String", nullable=false, length="100", mssql=true)
public class Company {
    
}
