package org.amos.core.generator.gen;

import lombok.Data;
import org.amos.core.basic.utils.AmosUtils;
import org.amos.core.generator.entity.GenDatasource;

@Data
public class GenConfig {

    private Integer dbType;
    /** 数据库名称 */
    private String dbName;
    /** 数据库host */
    private String host;
    /** 数据库端口 */
    private Integer port;
    /** 数据库用户名 */
    private String username;
    /** 数据库密码 */
    private String password;

    public static GenConfig build(GenDatasource datasource) {
        GenConfig config = AmosUtils.copy(datasource, GenConfig.class);
        return config;
    }

    public String getDriverClass() {
        DbType dbType = DbType.of(this.dbType);
        if (dbType == null) {
            throw new RuntimeException("不支持数据库类型" + this.dbType + "，请在DbType.java中配置");
        }
        return dbType.getDriverClass();
    }

    public String getJdbcUrl() {
        DbType dbType = DbType.of(this.dbType);
        if (dbType == null) {
            throw new RuntimeException("不支持数据库类型" + this.dbType + "，请在DbType.java中配置");
        }
        String jdbcUrl = dbType.getJdbcUrl();
        return String.format(jdbcUrl, host, port, dbName);
    }
}
