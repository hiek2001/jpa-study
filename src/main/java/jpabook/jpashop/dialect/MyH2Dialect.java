package jpabook.jpashop.dialect;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypeTemplate;
import org.hibernate.type.StandardBasicTypes;

public class MyH2Dialect extends H2Dialect {
    // 사용하는 Dialect 상속 받기

    public MyH2Dialect() {
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StandardBasicTypes.STRING));
    }
}
