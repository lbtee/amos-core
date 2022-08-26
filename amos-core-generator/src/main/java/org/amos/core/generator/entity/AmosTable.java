package org.amos.core.generator.entity;

import lombok.Data;

@Data
public class AmosTable {

    /**
     *  备注
     */
    private String comment;

    /**
     * 数据表名
     */
    private String tableName;

    /**
     * 编码格式
     */
    private String codingFormat;

    /**
     * 创建日期
     */
    private String createTime;

}
