package org.amos.core.basic.annotation;

import org.amos.core.basic.enums.SqlKeyWord;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AmosQuery {
    /**
     * 条件的关键字
     */
    SqlKeyWord keyword() default SqlKeyWord.EQ;
    /**
     * 对应数据库数据列
     */
    String column() default "";
}
