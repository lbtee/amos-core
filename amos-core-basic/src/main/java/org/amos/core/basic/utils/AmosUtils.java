package org.amos.core.basic.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.beans.BeanUtils.copyProperties;
/**
 * @desc: 常用简单工具类 封装
 * @author: liubt
 * @date: 2020-12-31 13:21
 **/
@Slf4j
public class AmosUtils {

    /**
     * 对象属性拷贝
     *@param source     源对象
     * @param clazz     目标对象
     * @param <S>       源对象
     * @param <T>       目标对象
     * @return
     */
    public static <S, T> T copy(S source, Class<T> clazz) {
        T target = null;
        try {
            target = target = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("bean中属性拷贝异常",e);
        }
        copyProperties(source, target);
        return target;
    }

    /**
     * 将list源对象转换为目标对象list
     *
     * @param list      源list对象
     * @param clazz     目标对象
     * @param <S>       源对象
     * @param <T>       目标对象
     * @return 目标对象list集合
     */

    public static <S, T> List<T> listCopy(List<S> list, Class<T> clazz) {
        return list.stream()
                .map(source -> {
                    T target = null;
                    try {
                        target = clazz.newInstance();
                        copyProperties(source, target);
                    } catch (InstantiationException | IllegalAccessException e) {
                        log.error("list中属性拷贝异常",e);
                    }
                    return target;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 将page源对象转换为目标对象page
     * @param page      源page对象
     * @param clazz     目标对象
     * @param <S>       源对象
     * @param <T>       目标对象
     * @return
     */
    public static <S, T> IPage<T> pageCopy(IPage<S> page, Class<T> clazz){
        IPage<T> newPage = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<T> ts = listCopy(page.getRecords(), clazz);
        newPage.setRecords(ts);
        return newPage;
    }
}
