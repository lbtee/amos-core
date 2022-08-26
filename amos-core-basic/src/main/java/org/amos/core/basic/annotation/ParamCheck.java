package org.amos.core.basic.annotation;

import java.lang.annotation.*;
/**
 * @desc: 参数不为空批量校验注解
 * @author: liubt
 * @date: 2022-08-08 17:06
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamCheck {

    String[] value() default {};
    String message() default "";
}
