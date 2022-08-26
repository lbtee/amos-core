package org.amos.core.basic.utils;

import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @desc: fieldutil
 * @author: liubt
 * @date: 2022-08-23 09:43
 **/
public class FieldUtils {

    public static List<Field> getFieldsListWithAnnotation(Class<?> cls, Class<? extends Annotation> annotationCls) {
        Assert.notNull(annotationCls, "annotationCls is null");
        List<Field> allFields = getAllFieldsList(cls);
        List<Field> annotatedFields = new ArrayList();
        Iterator var4 = allFields.iterator();

        while(var4.hasNext()) {
            Field field = (Field)var4.next();
            if (field.getAnnotation(annotationCls) != null) {
                annotatedFields.add(field);
            }
        }

        return annotatedFields;
    }

    public static List<Field> getAllFieldsList(Class<?> cls) {
        Assert.notNull(cls, "cls is null");
        List<Field> allFields = new ArrayList();

        for(Class<?> currentClass = cls; currentClass != null; currentClass = currentClass.getSuperclass()) {
            Field[] declaredFields = currentClass.getDeclaredFields();
            Collections.addAll(allFields, declaredFields);
        }

        return allFields;
    }
}
