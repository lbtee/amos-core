package org.amos.core.basic.annotation;

import org.amos.core.basic.enums.DesensitizedEnum;
import org.amos.core.basic.enums.DesensitizedLevelEnum;

import java.lang.annotation.*;

/**
 * 敏感信息脱敏注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Desensitized {
    /**
     * 脱敏类型
     * @return
     */
    DesensitizedEnum type() default DesensitizedEnum.MOBILE_PHONE;
    /**
     * 脱敏级别
     * @return
     */
    DesensitizedLevelEnum level() default DesensitizedLevelEnum.DEFAULT;

}
