package org.amos.core.basic.valid;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.amos.core.basic.annotation.EnumValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @desc: 枚举值校验方法
 * @author: liubt
 * @date: 2022-08-08 17:09
 **/
@Slf4j
public class EnumValidator implements ConstraintValidator<EnumValid, Object> {

    String validField;
    /**
     * 枚举类
     */
    Class<?>[] clazz;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        clazz = constraintAnnotation.target();
        validField = constraintAnnotation.validField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (ObjectUtil.isAllNotEmpty(value, clazz)) {
            for (Class<?> cl : clazz) {
                try {
                    if (cl.isEnum()) {
                        //枚举类验证
                        Object[] objs = cl.getEnumConstants();
                        Method method = cl.getMethod("name");
                        for (Object obj : objs) {
                            Object code = method.invoke(obj, null);
                            if (value.equals(code.toString())) {
                                return true;
                            }
                        }
                        Method codeMethod = cl.getMethod(validField);
                        for (Object obj : objs) {
                            Object code = codeMethod.invoke(obj, null);
                            if (value.toString().equals(code.toString())) {
                                return true;
                            }
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    log.error("[EnumValidator error],error info %s",e.getMessage());
                }
            }
        }else {
            return true;
        }
        return false;
    }

}

