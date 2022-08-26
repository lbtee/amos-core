package org.amos.core.generator.gen;

import lombok.Data;
import org.amos.core.generator.entity.AmosGenColumnInfo;

import java.util.List;

@Data
public class AmosGenInfo {
    /**
     * 作者
     */
    private String author;
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表名前缀
     */
    private String tablePrefix;

    /**
     * 类名
     */
    private String className;

    /**
     * 表格列
     */
    private List<AmosGenColumnInfo> columns;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名称
     */
    private String moduleName;

    private String path;

}
