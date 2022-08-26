package org.amos.core.basic.utils.crud;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import org.amos.core.basic.annotation.AmosQuery;
import org.amos.core.basic.enums.SqlKeyWord;
import org.amos.core.basic.utils.FieldUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * @desc: mybatisPlus 注解化构造
 * @author: liubt
 * @date: 2021-01-04 19:21
 **/
public class WrapperBuilder<T, E> {

    @SneakyThrows
    public <E> QueryWrapper<E> build(T t){
        QueryWrapper<E> wrapper = new QueryWrapper<>();
        Class<?> clazz = t.getClass();
        // 获取被Wrapper 标注的fields
        List<Field> fields= FieldUtils.getFieldsListWithAnnotation(clazz, AmosQuery.class);

        for (Field field : fields) {
            field.setAccessible(true);
            AmosQuery annotationAmosQuery = field.getAnnotation(AmosQuery.class);
            if(Objects.nonNull(annotationAmosQuery)){
                Object fieldValue = field.get(t);
                if(Objects.nonNull(fieldValue)){
                    SqlKeyWord keyword = annotationAmosQuery.keyword();
                    String columnName = annotationAmosQuery.column();

                    switch (keyword) {
                        case EQ:
                            wrapper.eq(columnName, fieldValue);
                            break;
                        case NE:
                            wrapper.ne(columnName, fieldValue);
                            break;
                        case GT:
                            wrapper.gt(columnName, fieldValue);
                            break;
                        case LT:
                            wrapper.lt(columnName, fieldValue);
                            break;
                        case GE:
                            wrapper.ge(columnName, fieldValue);
                            break;
                        case LE:
                            wrapper.le(columnName, fieldValue);
                            break;
                        case START_WITH:
                            wrapper.likeLeft(columnName, fieldValue);
                            break;
                        case END_WITH:
                            wrapper.likeRight(columnName, fieldValue);
                            break;
                        case LIKE:
                            wrapper.like(columnName, fieldValue);
                            break;
                        case NOT_LIKE:
                            wrapper.notLike(columnName, fieldValue);
                            break;
                        case IN:
                            wrapper.in(columnName, fieldValue);
                            break;
                        case NOT_IN:
                            wrapper.notIn(columnName, fieldValue);
                            break;
                        case BETWEEN:
                            Assert.isInstanceOf(Between.class, fieldValue);
                            Between between = (Between)fieldValue;
                            wrapper.between(columnName, between.getBLeft(), between.getBRight());
                            break;
                        case NOT_BETWEEN:
                            Assert.isInstanceOf(Between.class, fieldValue);
                            Between notBetween = (Between)fieldValue;
                            wrapper.notBetween(columnName, notBetween.getBLeft(), notBetween.getBRight());
                            break;
                        case DESC:
                            wrapper.orderByDesc(columnName);
                            break;
                        default:
                            wrapper.eq(columnName, fieldValue);
                            break;
                    }
                }
            }
        }
        return wrapper;
    }
}
