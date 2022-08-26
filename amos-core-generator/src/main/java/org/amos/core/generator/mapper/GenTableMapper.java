package org.amos.core.generator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.amos.core.generator.entity.AmosGenTableInfo;

/**
 * <p>
 * 代码生成表信息配置 Mapper 接口
 * </p>
 *
 * @author CodeGenerator
 * @since 2020-12-18
 */
public interface GenTableMapper extends BaseMapper<AmosGenTableInfo> {
    /**
     * 查询代码生成配置信息
     * @param tableName
     * @return
     */
    AmosGenTableInfo selectGenInfo(String tableName);
}
