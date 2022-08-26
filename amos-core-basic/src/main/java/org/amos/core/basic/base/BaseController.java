package org.amos.core.basic.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.amos.core.basic.utils.crud.WrapperBuilder;

public abstract class BaseController {

    protected  <E, T> QueryWrapper<E> buildWrapper(T t){
        return new WrapperBuilder().build(t);
    }

    protected <T extends BaseDTO> Page initPage(T  t){
        return new Page<>(t.getCurrent(), t.getSize());
    }
}
