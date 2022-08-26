package org.amos.core.basic.base;

import lombok.Data;
import org.amos.core.basic.annotation.AmosQuery;
import org.amos.core.basic.constant.SystemConstant;
import org.amos.core.basic.enums.SqlKeyWord;

import java.io.Serializable;

@Data
public class BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 当前页
     */
    private Integer current = SystemConstant.SYS_PAGINATION_PAGE;
    /**
     * 每页显示数据量
     */
    private Integer size = SystemConstant.SYS_PAGINATION_SIZE;
    /**
     * 倒序排序字段
     */
    @AmosQuery(column = "create_time", keyword = SqlKeyWord.DESC)
    private String orderByDesc = SystemConstant.SYS_PAGE_ORDER_DESC;
    /**
     * 正序排序字段
     */
    private String orderByAsc;

}
