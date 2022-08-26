package org.amos.core.generator.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.amos.core.generator.entity.AmosTable;
import org.amos.core.generator.vo.GenColumnVo;

import java.util.List;

public interface GenMapper {
    /**
     * 查询 MySQL数据表信息
     * @return
     */
    IPage<AmosTable> queryMySqlTablesPage(Page<AmosTable> page);

    /**
     * 查询MySQL 数据表列信息
     * @param tableName
     * @return
     */
    List<GenColumnVo> queryMysqlColumnList(String tableName);

    /**
     * 查询 PostgreSql数据表信息
     * @return
     */
    IPage<AmosTable> queryPostgreSqlTablesPage(Page<AmosTable> page);

    /**
     * 查询 Oracle 数据表信息
     * @return
     */
    IPage<AmosTable> queryOracleTablesPage(Page<AmosTable> page);
}
