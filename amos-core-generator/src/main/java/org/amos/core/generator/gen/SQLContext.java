package org.amos.core.generator.gen;

import org.amos.core.generator.utils.FieldUtil;
import org.apache.commons.lang.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SQL上下文,这里可以取到表,字段信息<br>
 * 最终会把SQL上下文信息放到velocity中
 */
public class SQLContext {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final LocalDateTime localDateTime = LocalDateTime.now();

    /**
     * 表结构定义
     */
    private final TableDefinition tableDefinition;
    private final JavaColumnDefinition javaPkColumn;
    /**
     * 包名
     */
    private String packageName;

    /**
     * 删除的前缀
     */
    private String delPrefix;

    /**
     * 数据库名
     */
    private String dbName;

    public SQLContext(TableDefinition tableDefinition) {
        this.tableDefinition = tableDefinition;
        // 默认为全字母小写的类名
        this.packageName = getJavaBeanName().toLowerCase();
        this.javaPkColumn = (JavaColumnDefinition) this.tableDefinition.getPkColumn();
    }

    public String getDatetime() {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    public String getDate() {
        return localDateTime.format(DATE_FORMATTER);
    }

    public String getTime() {
        return localDateTime.format(TIME_FORMATTER);
    }

    /**
     * 返回Java类名
     *
     * @return
     */
    public String getJavaBeanName() {
        return getClassName();
    }

    /**
     * 返回类名
     * @return
     */
    public String getClassName() {
        String tableName = getJavaBeanNameLF();
        return FieldUtil.upperFirstLetter(tableName);
    }

    /**
     * 返回Java类名且首字母小写
     *
     * @return
     */
    public String getJavaBeanNameLF() {
        String tableName = tableDefinition.getTableName();
        if(delPrefix != null){
            String[] split = delPrefix.split(",");
            for (String prefix : split){
                tableName = tableName.startsWith(prefix) && !StringUtils.isEmpty(prefix) ? tableName.replace(prefix, "") : tableName;
            }
        }

        tableName = FieldUtil.underlineFilter(tableName);
        tableName = FieldUtil.dotFilter(tableName);
        return FieldUtil.lowerFirstLetter(tableName);
    }

    public String getPkName() {
        if (javaPkColumn != null) {
            return javaPkColumn.getColumnName();
        }
        return "";
    }

    public String getJavaPkName() {
        if (javaPkColumn != null) {
            return javaPkColumn.getJavaFieldName();
        }
        return "";
    }

    public String getJavaPkType() {
        if (javaPkColumn != null) {
            return javaPkColumn.getJavaType();
        }
        return "";
    }

    public String getMybatisPkType() {
        if (javaPkColumn != null) {
            return javaPkColumn.getMybatisJdbcType();
        }
        return "";
    }

    public TableDefinition getTableDefinition() {
        return tableDefinition;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDelPrefix() {
        return delPrefix;
    }

    public void setDelPrefix(String delPrefix) {
        this.delPrefix = delPrefix;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
